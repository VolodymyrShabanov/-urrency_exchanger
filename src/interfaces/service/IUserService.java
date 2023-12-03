package interfaces.service;

import util.UserRole;

import java.util.Optional;

public interface IUserService {

    Optional<String> getCurrentUserEmail();

    boolean createUser(String email, String password, UserRole role);

    UserRole login(String email, String password);

    boolean logout();

    UserRole assignUserRole(String userEmail, UserRole newRole);
}
