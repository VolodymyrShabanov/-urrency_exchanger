package model;

public class AccountData {

    private final String userEmail;
    private final double balance;
    private final Currency currency;

    public AccountData(String userEmail, Currency currency, double depositSum) {
        this.userEmail = userEmail;
        this.currency = currency;
        this.balance = depositSum;
    }

    public AccountData(Account account) {
        this.userEmail = account.getUserEmail();
        this.balance = account.getBalance();
        this.currency = account.getCurrency();
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
}