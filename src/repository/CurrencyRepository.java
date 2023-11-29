package repository;

import models.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository {
    private List<Currency> currencies;

    public CurrencyRepository() {
        this.currencies = new ArrayList<>();
    }

    public void saveCurrency(Currency currency) {
        currencies.add(currency);
    }

    public Currency getCurrencyByCode(String code) {
        return currencies.stream()
                .filter(currency -> currency.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public List<Currency> getAllCurrencies() {
        return new ArrayList<>(currencies);
    }
}
