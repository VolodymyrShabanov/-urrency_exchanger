package models;


import utils.Currency;

public class Account {
    private final int id;
    private String userEmail;
    private double balance;
    private Currency currency;
    private static int counter;

    public Account(String userID, double balance, Currency currency) {
        this.id = counter++;
        this.userEmail = userID;
        this.balance = balance;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return String.format("User: %s | Currency: %s | Account Balance %f",
                userEmail,
                currency.toString(),
                balance
        );

    }

    public long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public double getBalance() {
        return balance;
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