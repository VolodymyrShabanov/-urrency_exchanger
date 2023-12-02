package service;

import exception.ValidationException;
import exception.PermissionException;
import interfaces.IUserService;
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
        userRepository.addUser(new User("admin", "admin", UserRole.ADMIN));
    }

    @Override
    public Optional<String> getCurrentUserEmail() {
        return currentUser == null ? Optional.empty() : Optional.of(currentUser.getEmail());
    }

    @Override
    public boolean createUser(String email, String password, UserRole role) {
        validateUserData(email, password);

        if (userRepository.isUserExist(email)) {
            return false;
        }

        User newUser = new User(email, password, role);
        userRepository.addUser(newUser);

        System.out.printf("User %s successfully created.\n", email);

        return true;
    }

    @Override
    public UserRole login(String email, String password) {
        Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isPresent() && user.get().checkPassword(password)) {
            currentUser = user.get();
            System.out.printf("User %s has successfully logged in.\n", email);
            return user.get().getRole();
        }

        System.err.println("Error: login details are incorrect.");
        return UserRole.GUEST;
    }

    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            System.out.println("You have logged out.");
            return true;
        } else {
            return false;
        }
    }

    public UserRole assignUserRole(String userEmail, UserRole newRole) {
        Optional<User> user = userRepository.getUserByEmail(userEmail);

        if (user.isEmpty()) {
            System.out.println("User not found.");
            return UserRole.GUEST;
        }

        User userModel = user.get();
        userModel.setRole(newRole);
        userRepository.updateUser(userModel);
        System.out.println("User role updated successfully");

        return newRole;
    }

    private void validateUserData(String email, String password) throws ValidationException {
        if (!Validator.isValidEmail(email) || !Validator.isValidPassword(password)) {
            throw new ValidationException("Invalid email or password. Please try again.");
        }
    }
}
