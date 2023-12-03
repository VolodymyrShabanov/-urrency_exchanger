package interfaces.model;

import interfaces.model.IAccount;
import util.UserRole;

import java.util.List;

public interface IUser {

    boolean checkPassword(String password);

    String getEmail();

    UserRole getRole();

    void setRole(UserRole role);
}
