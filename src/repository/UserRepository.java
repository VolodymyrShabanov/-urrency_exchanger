package repository;

import models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private Map<String, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    public boolean isUserExist(String email) {
        return users.containsKey(email);
    }

    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }
}
