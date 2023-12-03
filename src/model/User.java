package model;

import interfaces.model.IUser;
import util.UserRole;

public class User implements IUser {
    private String email;
    private String password;
    private UserRole role;

    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setNewEmail(String newEmail, String currentPassword) {
        if (checkPassword(currentPassword)) {
            this.email = newEmail;
        }
    }

    @Override
    public void setNewPassword(String newPassword, String currentPassword) {
        if (checkPassword(currentPassword)) {
            this.password = newPassword;
        }
    }

    @Override
    public UserRole getRole() {
        return role;

    }

    @Override
    public void setNewRole(UserRole newRole, String currentPassword) {
        if (checkPassword(currentPassword)) {
            this.role = newRole;
        }
    }

    @Override
    public void setNewRole(UserRole newRole, UserRole userRole) {
        if (userRole.equals(UserRole.ADMIN)) {
            role = newRole;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
