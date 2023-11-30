package repository;

import models.Currency;

import java.util.*;

public class CurrencyRepository {
    private Set<Currency> currencies;

    public CurrencyRepository() {
        this.currencies = new HashSet<>();
    }

    public Optional<Currency> addCurrency(String currencyCode, String currencyName) {
        if (getCurrencyByCode(currencyCode).isEmpty() && getCurrencyByName(currencyName).isEmpty()) {
            Currency currency = new Currency(currencyCode, currencyName);
            Currency currencyCopy = new Currency(currencyCode, currencyName);

            currencies.add(currency);

            return Optional.of(currencyCopy);
        } else {
            System.err.println("Error: currencies with similar data already exist.");
        }

        return Optional.empty();
    }

    public Optional<Currency> getCurrencyByName(String name) {
        return currencies.stream()
                .filter(currency -> currency.getName().equals(name))
                .findFirst();
    }

    public Optional<Currency> getCurrencyByCode(String code) {
        return currencies.stream()
                .filter(currency -> currency.getCode().equals(code))
                .findFirst();
    }

    public Set<Currency> getAllCurrencies() {
        return Set.copyOf(currencies);
    }

    public void deleteCurrency(Currency currency) {
        currencies.remove(currency);
    }
}
