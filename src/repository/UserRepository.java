package repository;

import models.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, User> usersByEmail;

    public UserRepository() {
        this.usersByEmail = new HashMap<>();
    }

    public boolean isUserExist(String email) {
        return usersByEmail.containsKey(email);
    }

    public void saveUser(User user) {
        usersByEmail.put(user.getEmail(), user);
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }
}
