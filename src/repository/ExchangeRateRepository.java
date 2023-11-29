package repository;

import models.Currency;
import models.ExchangeRate;

import java.util.HashMap;
import java.util.Map;

public class ExchangeRateRepository {
    private Map<String, ExchangeRate> exchangeRates;

    public ExchangeRateRepository() {
        this.exchangeRates = new HashMap<>();
        initializeExchangeRates();
    }

    public ExchangeRate getExchangeRate(Currency baseCurrency, Currency targetCurrency) {
        String key = generateKey(baseCurrency, targetCurrency);
        return exchangeRates.get(key);
    }

    public void saveExchangeRate(ExchangeRate exchangeRate) {
        String key = generateKey(exchangeRate.getFromCurrency(), exchangeRate.getToCurrency());
        exchangeRates.put(key, exchangeRate);
    }

    public Map<String, ExchangeRate> getAllExchangeRates() {
        return new HashMap<>(exchangeRates);
    }

    public void initializeExchangeRates() {
        Currency usdCurrency = new Currency("USD", "US Dollar");
        Currency eurCurrency = new Currency("EUR", "Euro");
        Currency gbpCurrency = new Currency("GBP", "British pound");

        saveExchangeRate(new ExchangeRate(usdCurrency, eurCurrency, 0.85));
        saveExchangeRate(new ExchangeRate(eurCurrency, usdCurrency, 1.18));
        saveExchangeRate(new ExchangeRate(usdCurrency, gbpCurrency, 0.75));
        saveExchangeRate(new ExchangeRate(gbpCurrency, usdCurrency, 1.33));
    }

    private String generateKey(Currency fromCurrency, Currency toCurrency) {
        return fromCurrency.getCode() + toCurrency.getCode();
    }
}