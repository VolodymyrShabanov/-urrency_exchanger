package services;

import exceptions.ValidationException;
import interfaces.IUserService;
import models.User;
import repository.UserRepository;
import utils.UserRole;
import utils.Validator;

import java.util.Optional;

public class UserService implements IUserService {
    private static Optional<User> currentUser;
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    @Override
    public Optional<String> getCurrentUserEmail() {
        return currentUser.map(User::getEmail);
    }

    @Override
    public boolean createUser(String email, String password, UserRole role) {
        try {
            validateUserData(email, password);
        } catch (ValidationException e) {
            System.err.println(e.getMessage());
            return false;
        }

        User newUser = new User(email, password, role);
        userRepository.addUser(newUser);

        System.out.printf("User %s successfully created.\n", email);

        return true;
    }

    @Override
    public boolean login(String email, String password) {
        Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isPresent() && user.get().checkPassword(password)) {
            currentUser = user;
            System.out.printf("User %s has successfully logged in.\n", email);
            return true;
        }

        System.err.println("Error: login details are incorrect.");
        return false;
    }

    public boolean logout() {
        if (currentUser.isPresent()) {
            currentUser = Optional.empty();
            System.out.println("You have logged out.");
            return true;
        } else {
            return false;
        }
    }

    private void validateUserData(String email, String password) throws ValidationException {
        if (!Validator.isValidEmail(email) || !Validator.isValidPassword(password)) {
            throw new ValidationException("Invalid email or password. Please try again.");
        }

        if (userRepository.isUserExist(email)) {
            throw new ValidationException("User with this email already exists.");
        }
    }
}
