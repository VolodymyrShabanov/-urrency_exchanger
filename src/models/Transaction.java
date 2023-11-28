package models;

import utils.TransactionType;
import java.time.LocalDateTime;

public class Transaction {
    private final int id;
    private User user;
    private Account fromAccount;
    private Account toAccount;
    private TransactionType type;
    private double amount;
    private LocalDateTime date;



    public Transaction(int id, User user, Account fromAccount, Account toAccount, TransactionType type, double amount) {
        this.id = id;
        this.user = user;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
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

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
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
                ", account=" + fromAccount +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}