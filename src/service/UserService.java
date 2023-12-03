package service;

import exceptions.*;
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
    public String getCurrentUserEmail() throws LoginException {
        if (currentUser != null) {
            return currentUser.getEmail();
        } else {
            throw new LoginException("Error: user hasn't logged in.");
        }
    }

    @Override
    public boolean createUser(String email, String password, UserRole role) throws DataInUseException, ValidationException {
        validateInput(email, password);

        boolean isUserAdded = userRepository.addUser(email, password, role);

        if (isUserAdded) {
            System.out.printf("User %s successfully created.\n", email);
            return true;
        } else {
            throw new DataInUseException("Error: user with this email already exists.");
        }
    }

    @Override
    public UserRole login(String email, String password) throws LoginException {
        Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isPresent()) {
            if (user.get().checkPassword(password)) {
                currentUser = user.get();

                System.out.printf("User %s has successfully logged in.\n", email);
                return user.get().getRole();
            } else {
                throw new LoginException("Error: login details are incorrect.");
            }
        } else {
            throw new DataNotFoundException("Error: this user doesn't exist.");
        }
    }

    @Override
    public boolean logout() throws LoginException {
        if (currentUser != null) {
            currentUser = null;

            System.out.println("You have logged out.");
            return true;
        } else {
            throw new LoginException("Error: you haven't logged in.");
        }
    }

    @Override
    public UserRole assignUserRole(String userEmail, UserRole newRole) throws LoginException, PermissionException {
        Optional<User> user = userRepository.getUserByEmail(userEmail);

        if (user.isPresent()) {
            if (currentUser != null) {
                if (currentUser.getRole().equals(UserRole.ADMIN)) {
                    user.get().setNewRole(newRole, currentUser.getRole());

                    System.out.println("User role updated successfully.");
                    return newRole;
                } else {
                    throw new PermissionException("Error: you don't have access to this command.");
                }
            } else {
                throw new LoginException("Error: you haven't logged in.");
            }
        } else {
            throw new DataNotFoundException("Error: user not found.");
        }
    }

    private void validateInput(String email, String password) throws ValidationException {
        if (!Validator.isValidEmail(email) || !Validator.isValidPassword(password)) {
            throw new ValidationException("Invalid email or password. Please try again.");
        }
    }
}
