package repository;

import exceptions.DataInUseException;
import interfaces.repository.ICurrencyRepository;
import model.Currency;

import java.util.*;

public class CurrencyRepository implements ICurrencyRepository {
    private final Set<Currency> currencies;

    public CurrencyRepository() {
        this.currencies = new HashSet<>();
    }

    @Override
    public Optional<Currency> addCurrency(String currencyCode, String currencyName) throws DataInUseException {
        if (currencyCode.isBlank() || currencyName.isBlank()) {
            throw new IllegalArgumentException("Error: empty user input.");
        }

        if (getCurrencyByCode(currencyCode).isEmpty() && getCurrencyByName(currencyName).isEmpty()) {
            Currency currency = new Currency(currencyCode, currencyName);

            currencies.add(currency);

            return Optional.of(new Currency(currency));
        } else {
            throw new DataInUseException("Error: this currency already exists.");
        }
    }

    @Override
    public Optional<Currency> getCurrencyByName(String name) {
        if (name.isBlank()) return Optional.empty();

        Optional<Currency> temp = currencies.stream()
                .filter(currency -> currency.getName().equals(name))
                .findFirst();

        return temp.map(Currency::new);
    }

    @Override
    public Optional<Currency> getCurrencyByCode(String code) {
        if (code.isBlank()) return Optional.empty();

        Optional<Currency> temp = currencies.stream()
                .filter(currency -> currency.getCode().equals(code))
                .findFirst();

        return temp.map(Currency::new);
    }

    @Override
    public boolean deleteCurrencyByCode(String currencyCode) {
        if (currencyCode.isBlank()) return false;

        Optional<Currency> tempCurrency = currencies.stream()
                .filter(currency -> currency.getCode().equals(currencyCode))
                .findFirst();

        if (tempCurrency.isPresent()) {
            currencies.remove(tempCurrency.get());
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteCurrencyByName(String currencyName) {
        if (currencyName.isBlank()) return false;

        Optional<Currency> tempCurrency = currencies.stream()
                .filter(currency -> currency.getName().equals(currencyName))
                .findFirst();

        if (tempCurrency.isPresent()) {
            currencies.remove(tempCurrency.get());
            return true;
        }

        return false;
    }

    @Override
    public Optional<Set<Currency>> getAllCurrencies() {
        if (currencies.isEmpty()) return Optional.empty();

        return Optional.of(Set.copyOf(currencies));
    }

    @Override
    public int getSize() {
        return currencies.size();
    }
}