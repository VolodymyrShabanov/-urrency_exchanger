package repository;

import interfaces.repository.IUserRepository;
import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository implements IUserRepository {
    private Map<String, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public boolean userExists(String email) {
        return users.containsKey(email);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public int getSize() {
        return users.size();
    }
}
