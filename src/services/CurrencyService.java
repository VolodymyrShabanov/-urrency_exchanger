package services;


import models.Currency;
import models.ExchangeRate;
import repository.CurrencyRepository;
import repository.ExchangeRateRepository;

import java.util.Optional;

public class CurrencyService {
    private final ExchangeRateRepository exchangeRateRepository;
    private CurrencyRepository currencyRepository;

    public CurrencyService() {
        this.exchangeRateRepository = new ExchangeRateRepository();
        this.currencyRepository = new CurrencyRepository();
    }

    public boolean updateExchangeRate(String fromCurrency, String toCurrency, double newRate) {
        Optional<Currency> sourceCurrency = currencyRepository.getCurrencyByCode(fromCurrency);
        Optional<Currency> targetCurrency = currencyRepository.getCurrencyByCode(toCurrency);

        if (sourceCurrency.isEmpty() || targetCurrency.isEmpty()) {
            System.out.println("Invalid currency codes.");
            return false;
        }

        ExchangeRate existingRate = exchangeRateRepository.getExchangeRate(sourceCurrency.get(), targetCurrency.get());

        if (existingRate != null) {
            existingRate.setRate(newRate);
            System.out.println("Exchange rate updated successfully.");
        } else {
            ExchangeRate newExchangeRate = new ExchangeRate(sourceCurrency.get(), targetCurrency.get(), newRate);
            exchangeRateRepository.createExchangeRate(newExchangeRate);
            System.out.println("New exchange rate added successfully.");
        }

        return true;
    }

    public boolean exchangeCurrency(double amount, Currency sourceCurrency, Currency targetCurrency) {
        ExchangeRate exchangeRate = exchangeRateRepository.getExchangeRate(sourceCurrency, targetCurrency);

        if (exchangeRate == null) {
            System.out.println("Exchange rate not found.");

            return false;
        }

        double exchangedAmount = amount * exchangeRate.getRate();
        System.out.println("Exchanged amount: " + exchangedAmount);

        return true;
    }
}