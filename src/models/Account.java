package models;

import java.util.Currency;

public class Account {
    private final int id;
    private String userEmail;
    private double balance;
    private Currency currency;

<<<<<<< HEAD
    public Account(int id, User user, Currency currency) {
        this.id = id;
        this.user = user;
        this.currency = currency;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
=======
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
>>>>>>> f7e6ef14b60aabb5f9af5df86f1170abfb04740f
    }

    public long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

<<<<<<< HEAD
=======
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

>>>>>>> f7e6ef14b60aabb5f9af5df86f1170abfb04740f
    public double getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void debit(double amount) {
        balance += amount;
    }

    public void credit(double amount) {
        balance -= amount;
    }
}