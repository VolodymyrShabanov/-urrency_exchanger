package models;

import utils.UserRole;

import java.util.ArrayList;
import java.util.List;


public class User {
    private final String email;
    private String password;
    private UserRole role;
    private List<Account> accounts;
    private static int counter;

    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.accounts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public String getEmail() {
        return email;
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

    public void addAccount(Account account){  // у user есть счета
        accounts.add(account);
    }
}
