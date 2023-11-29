package models;


import utils.Currency;

import java.util.List;

public class Account {
    private String userEmail;
    private double balance;
    private Currency currency;

    public Account(String userEmail, Currency currency) {
        this.userEmail = userEmail;
        this.currency = currency;
        this.balance = 0;
    }

    public Account(String userEmail, Currency currency, double depositSum) {
        this.userEmail = userEmail;
        this.currency = currency;
        this.balance = depositSum;
    }

    @Override
    public String toString() {
        return String.format("User: %s | Currency: %s | Account Balance %f",
                userEmail,
                currency.toString(),
                balance
        );

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

    public void addTransaction(Transaction transaction) {

    }
}