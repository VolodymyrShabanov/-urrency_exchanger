package repository;

import models.Currency;
import models.ExchangeRate;

import java.util.HashMap;
import java.util.Map;

public class ExchangeRateRepository {
    private Map<String, ExchangeRate> exchangeRates;

    public ExchangeRateRepository() {
        this.exchangeRates = new HashMap<>();
    }

    public ExchangeRate getExchangeRate(Currency baseCurrency, Currency targetCurrency) {
        String key = generateKey(baseCurrency, targetCurrency);
        return exchangeRates.get(key);
    }

    public void createExchangeRate(ExchangeRate exchangeRate) {
        String key = generateKey(exchangeRate.getFromCurrency(), exchangeRate.getToCurrency());
        exchangeRates.put(key, exchangeRate);
    }

    public Map<String, ExchangeRate> getAllExchangeRates() {
        return Map.copyOf(exchangeRates);
    }

    private String generateKey(Currency fromCurrency, Currency toCurrency) {
        return fromCurrency.getCode() + toCurrency.getCode();
    }
}