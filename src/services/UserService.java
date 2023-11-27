package services;

import exceptions.ValidationException;
import interfaces.IUserService;
import models.User;
import repository.UserRepository;
import utils.UserRole;
import utils.Validator;

public class UserService implements IUserService {
    private static User currentUser;
    private UserRepository userRepository;

    @Override
    public String getCurrentUserEmail() {
        return null;
    }

    @Override
    public boolean createUser(String email, String password, UserRole role) {
        validateUserData(email, password);

        User newUser = new User(email, password, role);
        userRepository.saveUser(newUser);

        return true;
    }

    @Override
    public boolean login(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }

        currentUser = user;

        return true;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void validateUserData(String email, String password) {
        if (!Validator.isValidEmail(email) || !Validator.isValidPassword(password)) {
            throw new ValidationException("Invalid email or password. Please try again.");
        }

        if (userRepository.isUserExist(email)) {
            throw new ValidationException("User with this email already exists.");
        }
    }
}
