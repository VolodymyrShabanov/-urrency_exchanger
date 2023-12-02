package interfaces;

import util.UserRole;

import java.util.Optional;

public interface IUserService {
    public Optional<String> getCurrentUserEmail();

    public boolean createUser(String email, String password, UserRole role);

    public UserRole login(String email, String password);

    public boolean logout();
}
