package interfaces.repository;

import model.User;
import util.UserRole;

import java.util.Optional;

public interface IUserRepository {

    boolean userExists(String email);

    boolean addUser(String email, String password, UserRole userRole);

    Optional<User> getUserByEmail(String email);

    int getSize();
}
