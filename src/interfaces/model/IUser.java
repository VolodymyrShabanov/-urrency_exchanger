package interfaces.model;

import util.UserRole;

public interface IUser {

    String getEmail();

    UserRole getRole();

    boolean checkPassword(String password);

    void setNewEmail(String newEmail, String currentPassword);

    void setNewPassword(String newPassword, String currentPassword);

    void setNewRole(UserRole newRole, String currentPassword);

    void setNewRole(UserRole newRole, UserRole userRole);
}
