package model;

import interfaces.ITransactionData;
import util.CurrencyTransactionType;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDepositData implements ITransactionData {
    private final Account targetAccount;

    private final double targetTransactionAmount;

    private final CurrencyTransactionType currencyTransactionType;

    private final LocalDateTime transactionDate;

    public TransactionDepositData(Account currentAccount, double currentTransactionAmount) {
        this.targetAccount = currentAccount;
        this.targetTransactionAmount = currentTransactionAmount;

        this.currencyTransactionType = CurrencyTransactionType.DEPOSIT;

        transactionDate = LocalDate.now().atStartOfDay();
    }

    @Override
    public String toString() {
        return "TransactionDeposit{" +
                "targetAccount=" + targetAccount +
                ", currentTransactionAmount=" + targetTransactionAmount +
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

    public double getTargetTransactionAmount() {
        return targetTransactionAmount;
    }

    public CurrencyTransactionType getCurrencyTransactionType() {
        return currencyTransactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String getInfo() {
        return String.format("%s deposited %f (+%f) to %s account\nDate: %s",
                targetAccount.getUserEmail(),
                targetTransactionAmount,
                targetTransactionAmount,
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
    public String getCurrent() {
        return targetAccount.toString();
    }

    @Override
    public String getCurrentAmount() {
        DecimalFormat df = new DecimalFormat("0.##");
        return df.format(targetTransactionAmount);
    }
}
