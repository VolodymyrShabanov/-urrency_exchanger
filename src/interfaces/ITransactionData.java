package interfaces;

import model.Currency;
import model.Account;
import util.CurrencyTransactionType;

import java.util.List;

public interface ITransactionData {
    public String getInfo();

    public CurrencyTransactionType getType();

    public String getDateInfo();

    public String getUserEmail();

    public List<Account> getAccounts();

    public List<Double> getAmounts();

    public List<Currency> getCurrencies();
}
