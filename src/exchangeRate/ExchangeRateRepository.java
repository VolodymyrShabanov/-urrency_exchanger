package exchangeRate;

import models.Currency;
import models.ExchangeRate;

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

    public void createExchangeRate(ExchangeRate exchangeRate) {
        String key = generateKey(exchangeRate.getFromCurrency(), exchangeRate.getToCurrency());
        exchangeRates.put(key, exchangeRate);
    }

    public Optional<Map<String, ExchangeRate>> getAllExchangeRates() {
        if (exchangeRates.isEmpty()) return Optional.empty();

        return Optional.of(exchangeRates);
    }

    private String generateKey(Currency fromCurrency, Currency toCurrency) {
        return fromCurrency.getCode() + toCurrency.getCode();
    }
}