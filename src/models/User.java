package models;

import utils.UserRole;


public class User {
    private final int id;
    private String email;
    private String password;
    private UserRole role;
    private static int counter;

    public User(String email, String password, UserRole role) {
        this.id = counter++;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
