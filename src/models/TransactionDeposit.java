package models;

import interfaces.ITransaction;
import utils.CurrencyTransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDeposit implements ITransaction {
    private final Account targetAccount;

    private final double currentTransactionAmount;

    private final CurrencyTransactionType currencyTransactionType;

    private final LocalDateTime transactionDate;

    public TransactionDeposit(Account currentAccount, double currentTransactionAmount) {
        this.targetAccount = currentAccount;
        this.currentTransactionAmount = currentTransactionAmount;

        this.currencyTransactionType = CurrencyTransactionType.DEPOSIT;

        transactionDate = LocalDate.now().atStartOfDay();
    }

    @Override
    public String toString() {
        return "TransactionDeposit{" +
                "targetAccount=" + targetAccount +
                ", currentTransactionAmount=" + currentTransactionAmount +
                ", currencyTransactionType=" + currencyTransactionType +
                ", transactionDate=" + transactionDate +
                '}';
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public Currency getCurrentCurrency() {
        return targetAccount.getCurrency();
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
        return String.format("%s deposited %f to %s account\nDate: %s",
                targetAccount.getUserEmail(),
                currentTransactionAmount,
                targetAccount.getCurrency().getCode(),
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
        return targetAccount.toString();
    }
}
