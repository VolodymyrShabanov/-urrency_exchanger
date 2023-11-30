package repository;

import models.Currency;

import java.util.*;

public class CurrencyRepository {
    private Set<Currency> currencies;

    public CurrencyRepository() {
        this.currencies = new HashSet<>();
    }

    public boolean addCurrency(String currencyCode, String currencyName) {
        if (getCurrencyByCode(currencyCode).isEmpty() && getCurrencyByName(currencyName).isEmpty()) {
            currencies.add(new Currency(currencyCode, currencyName));
            return true;
        } else {
            System.err.println("Error: currencies with similar data already exist.");
        }

        return false;
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
