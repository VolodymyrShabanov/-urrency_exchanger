package models;

import utils.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private final Account currentAccount;
    private final Account targetAccount;

    private final double conversionRate;

    private final Currency currentCurrency;
    private final Currency targetCurrency;

    private final double currentTransactionAmount;
    private final double targetTransactionAmount;

    private final TransactionType transactionType;
    private final LocalDateTime transactionDate;

    public Transaction(Account currentAccount,
                       Account targetAccount,
                       double conversionRate,
                       Currency currentCurrency,
                       Currency targetCurrency,
                       double currentTransactionAmount,
                       double targetTransactionAmount,
                       TransactionType transactionType
    ) {
        this.currentAccount = currentAccount;
        this.targetAccount = targetAccount;

        this.conversionRate = conversionRate;

        this.currentCurrency = currentCurrency;
        this.targetCurrency = targetCurrency;

        this.transactionType = transactionType;
        this.currentTransactionAmount = currentTransactionAmount;
        this.targetTransactionAmount = targetTransactionAmount;

        transactionDate = LocalDate.now().atStartOfDay();
    }

    @Override
    public String toString() {
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

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public Currency getCurrentCurrency() {
        return currentCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public double getCurrentTransactionAmount() {
        return currentTransactionAmount;
    }

    public double getTargetTransactionAmount() {
        return targetTransactionAmount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}