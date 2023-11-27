package models;

public class Account {
    private final int id;
    private User user;
    private double balance;
    private Currency currency;
    private static int counter;

    public Account(User user, double balance, Currency currency) {
        this.id = counter++;
        this.user = user;
        this.balance = balance;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}