package interfaces.service;

import exceptions.DataInUseException;
import exceptions.LoginException;
import util.UserRole;

public interface IUserService {

    String getCurrentUserEmail() throws LoginException;

    boolean createUser(String email, String password, UserRole role) throws DataInUseException;

    UserRole login(String email, String password) throws LoginException;

    boolean logout() throws LoginException;

    UserRole assignUserRole(String userEmail, UserRole newRole) throws LoginException;
}
