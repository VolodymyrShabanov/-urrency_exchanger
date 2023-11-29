package models;


import utils.Currency;

import java.util.List;

public class Account {
    private final int id;
    private String userEmail;
    private double balance; // при создании счета балан должен быть нулевой
    private Currency currency;
    private static int counter;
    private List<Transaction> transactions; //  у счета есть транзакции

    public Account(String userID, Currency currency) {
        this.id = counter++;
        this.userEmail = userID;
        this.currency = currency;
    }

    public Account(User user, Currency currency) { //нужно принимать User что бы можно было связать с их между собой
        this.id = counter++;
        this.userEmail = user.getEmail();
        this.currency = currency;
        user.addAccount(this);
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

    public void addTransaction(Transaction transaction){ // у счета есть транзакции.
        transactions.add(transaction);
    }
}