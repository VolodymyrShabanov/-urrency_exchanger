package repository;

import interfaces.repository.IExchangeRateRepository;
import model.Currency;
import model.ExchangeRate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExchangeRateRepository implements IExchangeRateRepository {
    private final Map<String, ExchangeRate> exchangeRates;

    public ExchangeRateRepository() {
        this.exchangeRates = new HashMap<>();
    }

    @Override
    public Optional<ExchangeRate> getExchangeRate(Currency baseCurrency, Currency targetCurrency) {
        String key = generateKey(baseCurrency, targetCurrency);
        return Optional.ofNullable(exchangeRates.get(key));
    }

    @Override
    public boolean addExchangeRate(ExchangeRate exchangeRate) {
        String key = generateKey(exchangeRate.getFromCurrency(), exchangeRate.getToCurrency());

        if (exchangeRates.containsKey(key)) return false;

        exchangeRates.put(key, exchangeRate);
        return true;
    }

    @Override
    public Optional<Map<String, ExchangeRate>> getAllExchangeRates() {
        if (exchangeRates.isEmpty()) return Optional.empty();

        return Optional.of(exchangeRates);
    }

    @Override
    public int getSize() {
        return exchangeRates.size();
    }

    private String generateKey(Currency fromCurrency, Currency toCurrency) {
        return fromCurrency.getCode() + toCurrency.getCode();
    }
}