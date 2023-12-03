package service;

import exceptions.ValidationException;
import interfaces.service.IUserService;
import model.User;
import repository.UserRepository;
import util.UserRole;
import util.Validator;

import java.util.Optional;

public class UserService implements IUserService {

    private static User currentUser;
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
        userRepository.addUser("admin", "admin", UserRole.ADMIN);
    }

    @Override
    public Optional<String> getCurrentUserEmail() {
        if (currentUser != null) {
            return Optional.of(currentUser.getEmail());
        } else {
            System.err.println("Error: you haven't logged in.");
            return Optional.empty();
        }
    }

    @Override
    public boolean createUser(String email, String password, UserRole role) {
        try {
            validateInput(email, password);
        } catch (ValidationException e) {
            System.err.println(e.getMessage());
            return false;
        }

        boolean operationResult = userRepository.addUser(email, password, role);

        if (operationResult) {
            System.out.printf("User %s successfully created.\n", email);
            return true;
        } else {
            System.out.println("Login Error: user with this email already exists.");
            return false;
        }
    }

    @Override
    public UserRole login(String email, String password) {
        Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isPresent()) {
            if (user.get().checkPassword(password)) {
                currentUser = user.get();

                System.out.printf("User %s has successfully logged in.\n", email);
                return user.get().getRole();
            } else {
                System.err.println("Login Error: login details are incorrect.");
            }
        } else {
            System.err.println("Login Error: this user doesn't exist.");
        }

        return UserRole.GUEST;
    }

    @Override
    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;

            System.out.println("You have logged out.");
            return true;
        } else {
            System.err.println("Login Error: you haven't logged in.");
            return false;
        }
    }

    @Override
    public UserRole assignUserRole(String userEmail, UserRole newRole) {
        Optional<User> user = userRepository.getUserByEmail(userEmail);

        if (user.isPresent()) {
            if (currentUser != null) {
                if (currentUser.getRole().equals(UserRole.ADMIN)) {
                    user.get().setNewRole(newRole, currentUser.getRole());

                    System.out.println("User role updated successfully.");
                    return newRole;
                } else {
                    System.err.println("Permission Error: you don't have access to this command.");
                }
            } else {
                System.err.println("Error: you haven't logged in.");
            }
        } else {
            System.err.println("Error: user not found.");
        }

        return UserRole.GUEST;
    }

    private void validateInput(String email, String password) throws ValidationException {
        if (!Validator.isValidEmail(email) || !Validator.isValidPassword(password)) {
            throw new ValidationException("Invalid email or password. Please try again.");
        }
    }
}
