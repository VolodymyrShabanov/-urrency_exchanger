package interfaces.model;

import model.AccountData;
import model.Currency;
import util.CurrencyTransactionType;

import java.util.List;

public interface ITransactionData {

    String getInfo();

    CurrencyTransactionType getType();

    String getDateInfo();

    String getUserEmail();

    List<AccountData> getAccounts();

    List<Double> getAmounts();

    List<Currency> getCurrencies();
}