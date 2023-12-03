package interfaces.service;

import model.*;

public interface ICurrencyService {

    TransactionExchangeData exchangeCurrency(AccountData current, AccountData target, double amount);

    Currency addCurrency(String code, String name);

    void deleteCurrency(Currency currencyToDelete);

    ExchangeRate createExchangeRate(String fromCurrency, String toCurrency, double rate);

    ExchangeRate updateExchangeRate(String currentCode, String targetCode, double newRate);

    Currency getCurrencyByCode(String code);

    ExchangeRate getExchangeRateByCode(String currentCode, String targetCode);
}
