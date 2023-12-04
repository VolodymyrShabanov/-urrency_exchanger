package model;

import interfaces.model.ITransactionData;
import util.CurrencyTransactionType;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionDepositData implements ITransactionData {
    private final AccountData targetAccount;

    private final double targetTransactionAmount;

    private final CurrencyTransactionType currencyTransactionType;

    private final LocalDateTime transactionDate;

    public TransactionDepositData(AccountData targetAccount, double targetTransactionAmount) {
        this.targetAccount = targetAccount;
        this.targetTransactionAmount = targetTransactionAmount;

        this.currencyTransactionType = CurrencyTransactionType.DEPOSIT;

        transactionDate = LocalDate.now().atStartOfDay();
    }

    public AccountData getTargetAccount() {
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

    public String getCurrent() {
        return targetAccount.toString();
    }

    public String getFormattedTargetAmount() {
        DecimalFormat df = new DecimalFormat("0.##");

        return df.format(targetTransactionAmount);
    }

    @Override
    public String getInfo() {
        return String.format("%s deposited %s %s (+%s %s) to %s account\nDate: %s",
                targetAccount.getUserEmail(),
                getFormattedTargetAmount(),
                targetAccount.getCurrency().getCode(),
                getFormattedTargetAmount(),
                targetAccount.getCurrency().getCode(),
                targetAccount.getCurrency().getName(),
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
    public List<AccountData> getAccounts() {
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

    @Override
    public String toString() {
        return "TransactionDepositData{" +
                "targetAccount=" + targetAccount +
                ", targetTransactionAmount=" + targetTransactionAmount +
                ", currencyTransactionType=" + currencyTransactionType +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
