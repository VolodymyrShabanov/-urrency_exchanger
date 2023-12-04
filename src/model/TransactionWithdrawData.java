package model;

import interfaces.model.ITransactionData;
import util.CurrencyTransactionType;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionWithdrawData implements ITransactionData {
    private final AccountData currentAccount;

    private final double currentTransactionAmount;

    private final CurrencyTransactionType currencyTransactionType;

    private final LocalDateTime transactionDate;

    public TransactionWithdrawData(AccountData currentAccount, double currentTransactionAmount) {
        this.currentAccount = currentAccount;
        this.currentTransactionAmount = currentTransactionAmount;

        this.currencyTransactionType = CurrencyTransactionType.WITHDRAW;

        transactionDate = LocalDate.now().atStartOfDay();
    }

    public AccountData getCurrentAccount() {
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

    public String getCurrent() {
        return currentAccount.toString();
    }

    public String getFormattedCurrentAmount() {
        DecimalFormat df = new DecimalFormat("0.##");

        return df.format(currentTransactionAmount);
    }

    @Override
    public String getInfo() {
        return String.format("%s withdrawn %s %s (-%s %s) from %s account\nDate: %s",
                currentAccount.getUserEmail(),
                getFormattedCurrentAmount(),
                currentAccount.getCurrency().getCode(),
                getFormattedCurrentAmount(),
                currentAccount.getCurrency().getCode(),
                currentAccount.getCurrency().getName(),
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
        return currentAccount.getUserEmail();
    }

    @Override
    public List<AccountData> getAccounts() {
        return List.of(currentAccount);
    }

    @Override
    public List<Double> getAmounts() {
        return List.of(currentTransactionAmount);
    }

    @Override
    public List<Currency> getCurrencies() {
        return List.of(currentAccount.getCurrency());
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
}