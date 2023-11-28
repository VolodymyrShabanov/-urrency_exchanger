package models;

public class Account {
    private final int id;
    private User user;
    private double balance;
    private Currency currency;

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
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
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

    public void debit(double amount) {
        balance += amount;
    }

    public void credit(double amount) {
        balance -= amount;
    }
}