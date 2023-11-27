package models;

import utils.UserRole;


public class User {
    private final int id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private static int counter;

    public User(String name, String email, String password, UserRole role) {
        this.id = counter++;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
