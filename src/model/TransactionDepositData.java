package model;

import interfaces.ITransactionData;
import util.CurrencyTransactionType;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionDepositData implements ITransactionData {
    private final Account targetAccount;

    private final double targetTransactionAmount;

    private final CurrencyTransactionType currencyTransactionType;

    private final LocalDateTime transactionDate;

    public TransactionDepositData(Account targetAccount, double targetTransactionAmount) {
        this.targetAccount = targetAccount;
        this.targetTransactionAmount = targetTransactionAmount;

        this.currencyTransactionType = CurrencyTransactionType.DEPOSIT;

        transactionDate = LocalDate.now().atStartOfDay();
    }

    @Override
    public String toString() {
        return "TransactionDepositData{" +
                "targetAccount=" + targetAccount +
                ", targetTransactionAmount=" + targetTransactionAmount +
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
    public String getUserEmail() {
        return targetAccount.getUserEmail();
    }

    @Override
    public List<Account> getAccounts() {
        return List.of(targetAccount);
    }

    @Override
    public List<Double> getAmounts() {
        return List.of(targetTransactionAmount);
    }

    @Override
    public List<Currency> getCurrencies() {
        return List.of(targetAccount.getCurrency());
    }

    public String getCurrent() {
        return targetAccount.toString();
    }

    public String getCurrentAmount() {
        DecimalFormat df = new DecimalFormat("0.##");
        return df.format(targetTransactionAmount);
    }
}
