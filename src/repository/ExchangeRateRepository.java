package repository;

import model.Currency;
import model.ExchangeRate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExchangeRateRepository {
    private final Map<String, ExchangeRate> exchangeRates;

    public ExchangeRateRepository() {
        this.exchangeRates = new HashMap<>();
    }

    public Optional<ExchangeRate> getExchangeRate(Currency baseCurrency, Currency targetCurrency) {
        String key = generateKey(baseCurrency, targetCurrency);
        return Optional.ofNullable(exchangeRates.get(key));
    }

    public boolean createExchangeRate(ExchangeRate exchangeRate) {
        String key = generateKey(exchangeRate.getFromCurrency(), exchangeRate.getToCurrency());

        if(exchangeRates.containsKey(key)) return false;

        exchangeRates.put(key, exchangeRate);
        return true;
    }

    public Optional<Map<String, ExchangeRate>> getAllExchangeRates() {
        if (exchangeRates.isEmpty()) return Optional.empty();

        return Optional.of(exchangeRates);
    }

    private String generateKey(Currency fromCurrency, Currency toCurrency) {
        return fromCurrency.getCode() + toCurrency.getCode();
    }
}