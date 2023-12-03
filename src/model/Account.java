package model;

import interfaces.model.IAccount;

public class Account implements IAccount {
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

    public Account(Account account) {
        this.userEmail = account.userEmail;
        this.balance = account.balance;
        this.currency = account.currency;
    }

    @Override
    public String toString() {
        return String.format("User: %s | Currency: %s | Account Balance %f",
                userEmail,
                currency.toString(),
                balance
        );

    }
    @Override
    public String getUserEmail() {
        return userEmail;
    }
    @Override
    public double getBalance() {
        return balance;
    }
    @Override
    public Currency getCurrency() {
        return currency;
    }
    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    @Override
    public void deposit(double amount) {
        balance += amount;
    }
    @Override
    public void withdraw(double amount) {
        balance -= amount;
    }
}