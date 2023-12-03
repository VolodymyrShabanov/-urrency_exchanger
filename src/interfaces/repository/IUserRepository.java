package interfaces.repository;

import model.User;

import java.util.Optional;

public interface IUserRepository {

    boolean userExists(String email);

    void addUser(User user);

    Optional<User> getUserByEmail(String email);

    void updateUser(User user);

    int getSize();
}
