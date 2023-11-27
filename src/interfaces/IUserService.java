package interfaces;

import utils.UserRole;

public interface IUserService {
    public String getCurrentUserEmail();

    public boolean createUser(String email, String password, UserRole role);

    public boolean login(String email, String password);

    public void logout();
}
