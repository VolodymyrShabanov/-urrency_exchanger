package interfaces.service;

import exceptions.DataInUseException;
import exceptions.DataNotFoundException;
import exceptions.TransactionException;
import model.*;

public interface ICurrencyService {

    TransactionExchangeData exchangeCurrency(AccountData current, AccountData target, double amount) throws TransactionException;

    Currency addCurrency(String code, String name) throws DataInUseException;

    void deleteCurrency(Currency currencyToDelete, boolean currencyUsed) throws DataInUseException;

    void createExchangeRate(String fromCurrency, String toCurrency, double rate) throws DataInUseException;

    void updateExchangeRate(String currentCode, String targetCode, double newRate);

    Currency getCurrencyByCode(String code);

    ExchangeRate getExchangeRateByCode(String currentCode, String targetCode);
}
