package repository;

import interfaces.repository.IUserRepository;
import model.User;
import util.UserRole;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository implements IUserRepository {

    private final Map<String, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public boolean userExists(String email) {
        return users.containsKey(email);
    }

    @Override
    public boolean addUser(String email, String password, UserRole userRole) {
        if(userExists(email)) return false;

        User newUser = new User(email, password, userRole);
        users.put(email, newUser);

        return true;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    @Override
    public int getSize() {
        return users.size();
    }
}
