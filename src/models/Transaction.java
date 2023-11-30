package models;

import utils.DocumentStatus;
import utils.TransactionType;
import java.time.LocalDateTime;

public class Transaction {
    private final int id;
    private String userEmail;
    private String account; // - ?
    private String currency;
    private TransactionType type;
    private double amount;
    private LocalDateTime date;
    private DocumentStatus status; // можно реализовать позже если будет время


    // конструктор для - 1. Exchange Currency
    public Transaction(int id, String userEmail, String account, String currency, TransactionType type, double amount) {
        this.id = id;
        this.userEmail = userEmail;
        this.account = account;
        this.currency = currency;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();

        this.status = DocumentStatus.DRAFT; // можно реализовать позже если будет время
    }


    public long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userID='" + userEmail + '\'' +
                ", account='" + account + '\'' +
                ", currency='" + currency + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}