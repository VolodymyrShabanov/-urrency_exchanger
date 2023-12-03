package interfaces.model;

import model.Currency;
import model.Account;
import util.CurrencyTransactionType;

import java.util.List;

public interface ITransactionData {
    String getInfo();

    CurrencyTransactionType getType();

    String getDateInfo();

    String getUserEmail();

    List<Account> getAccounts();

    List<Double> getAmounts();

    List<Currency> getCurrencies();
}
