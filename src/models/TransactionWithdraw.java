package models;

import interfaces.ITransaction;
import utils.CurrencyTransactionType;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionWithdraw implements ITransaction {
    private final Account currentAccount;

    private final double currentTransactionAmount;

    private final CurrencyTransactionType currencyTransactionType;

    private final LocalDateTime transactionDate;

    public TransactionWithdraw(Account currentAccount, double currentTransactionAmount) {
        this.currentAccount = currentAccount;
        this.currentTransactionAmount = currentTransactionAmount;

        this.currencyTransactionType = CurrencyTransactionType.WITHDRAW;

        transactionDate = LocalDate.now().atStartOfDay();
    }

    @Override
    public String toString() {
        return "TransactionWithdraw{" +
                "currentAccount=" + currentAccount +
                ", currentTransactionAmount=" + currentTransactionAmount +
                ", currencyTransactionType=" + currencyTransactionType +
                ", transactionDate=" + transactionDate +
                '}';
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public Currency getCurrentCurrency() {
        return currentAccount.getCurrency();
    }

    public double getCurrentTransactionAmount() {
        return currentTransactionAmount;
    }

    public CurrencyTransactionType getCurrencyTransactionType() {
        return currencyTransactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String getInfo() {
        return String.format("%s withdrawn %f (-%f) from %s account\nDate: %s",
                currentAccount.getUserEmail(),
                currentTransactionAmount,
                currentTransactionAmount,
                currentAccount.getCurrency().getCode(),
                transactionDate.toString()
        );
    }

    @Override
    public CurrencyTransactionType getType() {
        return currencyTransactionType;
    }

    @Override
    public String getDateInfo() {
        return transactionDate.toString();
    }

    @Override
    public String getCurrentInfo() {
        return currentAccount.toString();
    }

    @Override
    public String getCurrentAmount() {
        DecimalFormat df = new DecimalFormat("0.##");
        return df.format(currentTransactionAmount);
    }
}
