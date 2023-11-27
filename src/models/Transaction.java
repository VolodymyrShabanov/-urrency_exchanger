package models;

import utils.TransactionType;
import java.time.LocalDateTime;

public class Transaction {
    private final int id;
    private User user;
    private Account account;
    private TransactionType type;
    private double amount;
    private LocalDateTime date;
    private static int counter;


    public Transaction(User user, Account account, TransactionType type, double amount) {
        this.id = counter++;
        this.user = user;
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", user=" + user +
                ", account=" + account +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}