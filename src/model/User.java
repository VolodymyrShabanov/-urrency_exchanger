package model;

import interfaces.model.IUser;
import util.UserRole;

public class User implements IUser {
    private final String email;
    private final String password;
    private UserRole role;

    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
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
    public UserRole getRole() {
        return role;
    }

    @Override
    public void setRole(UserRole role) {
        this.role = role;
    }

}
