package models;

import interfaces.ITransaction;
import utils.CurrencyTransactionType;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionExchange implements ITransaction {
    private final Account currentAccount;
    private final Account targetAccount;

    private final double conversionRate;

    private final double currentTransactionAmount;
    private final double targetTransactionAmount;

    private final CurrencyTransactionType currencyTransactionType;
    private final LocalDateTime transactionDate;

    public TransactionExchange(Account currentAccount,
                               Account targetAccount,
                               double conversionRate,
                               double currentTransactionAmount,
                               double targetTransactionAmount
    ) {
        this.currentAccount = currentAccount;
        this.targetAccount = targetAccount;

        this.conversionRate = conversionRate;

        this.currencyTransactionType = CurrencyTransactionType.EXCHANGE;
        this.currentTransactionAmount = currentTransactionAmount;
        this.targetTransactionAmount = targetTransactionAmount;

        transactionDate = LocalDate.now().atStartOfDay();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "currentAccount=" + currentAccount +
                ", targetAccount=" + targetAccount +
                ", conversionRate=" + conversionRate +
                ", currentTransactionAmount=" + currentTransactionAmount +
                ", targetTransactionAmount=" + targetTransactionAmount +
                ", currencyTransactionType=" + currencyTransactionType +
                ", transactionDate=" + transactionDate +
                '}';
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public Currency getCurrentCurrency() {
        return currentAccount.getCurrency();
    }

    public Currency getTargetCurrency() {
        return targetAccount.getCurrency();
    }

    public double getCurrentTransactionAmount() {
        return currentTransactionAmount;
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
        return String.format("FROM: %s %s\nTO: %s %s\n\n%f %s --> %f %s\n\nConversion Rate: %f\n",
                currentAccount.getUserEmail(),
                currentAccount.getCurrency().getCode(),
                targetAccount.getUserEmail(),
                targetAccount.getCurrency().getCode(),
                currentTransactionAmount,
                currentAccount.getCurrency().getCode(),
                targetTransactionAmount,
                targetAccount.getCurrency().getCode(),
                conversionRate
        );
    }

    @Override
    public CurrencyTransactionType getType() {
        return null;
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
        return df.format(targetTransactionAmount);
    }
}