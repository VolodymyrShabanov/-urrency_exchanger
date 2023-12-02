package service;


import exception.TransactionException;
import model.Account;
import model.Currency;
import model.ExchangeRate;
import repository.CurrencyRepository;
import repository.ExchangeRateRepository;
import model.TransactionExchangeData;

import java.util.Optional;

public class CurrencyService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    private final boolean dataInitStatus;

    public CurrencyService() {
        this.currencyRepository = new CurrencyRepository();
        this.exchangeRateRepository = new ExchangeRateRepository();

        init();
        dataInitStatus = true;
    }

    public Optional<TransactionExchangeData> exchangeCurrency(Account current, Account target, double amount) {
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.getExchangeRate(
                current.getCurrency(),
                target.getCurrency()
        );

        if (exchangeRate.isEmpty()) {
            throw new TransactionException("Exchange rate not found");
        }

        double exchangedSum = amount * exchangeRate.get().getRate();

        return Optional.of(new TransactionExchangeData(
                current,
                target,
                exchangeRate.get().getRate(),
                amount,
                exchangedSum)
        );
    }

    public Optional<Currency> addCurrency(String code, String name) {
        return currencyRepository.addCurrency(code, name);
    }

    public boolean deleteCurrency(String code) {
        Optional<Currency> currency = currencyRepository.getCurrencyByCode(code);

        if (currency.isEmpty()) {
            System.err.println("Currency not found.");
            return false;
        }

        currencyRepository.deleteCurrencyByCode(currency.get().getCode());

        return true;
    }

    public Optional<ExchangeRate> createExchangeRate(String fromCurrency, String toCurrency, double rate) {
        Optional<Currency> sourceCurrency = currencyRepository.getCurrencyByCode(fromCurrency);
        Optional<Currency> targetCurrency = currencyRepository.getCurrencyByCode(toCurrency);

        if (sourceCurrency.isEmpty() || targetCurrency.isEmpty()) {
            System.out.println("Invalid currency codes.");
            return Optional.empty();
        }

        Optional<ExchangeRate> existingRate = exchangeRateRepository.getExchangeRate(sourceCurrency.get(), targetCurrency.get());

        if (existingRate.isEmpty()) {
            ExchangeRate newExchangeRate = new ExchangeRate(sourceCurrency.get(), targetCurrency.get(), rate);
            exchangeRateRepository.createExchangeRate(newExchangeRate);

            return Optional.of(new ExchangeRate(newExchangeRate));
        } else {
            System.err.println("Error: this rate already exists.");
        }

        return Optional.empty();
    }

    public Optional<ExchangeRate> updateExchangeRate(String currentCode, String targetCode, double newRate) {
        Optional<Currency> sourceCurrency = currencyRepository.getCurrencyByCode(currentCode);
        Optional<Currency> targetCurrency = currencyRepository.getCurrencyByCode(targetCode);

        if (sourceCurrency.isEmpty() || targetCurrency.isEmpty()) {
            System.out.println("Invalid currency codes.");

            return Optional.empty();
        }

        Optional<ExchangeRate> existingRate = exchangeRateRepository.getExchangeRate(sourceCurrency.get(), targetCurrency.get());

        if (existingRate.isPresent()) {
            existingRate.get().setRate(newRate);
            return Optional.of(new ExchangeRate(existingRate.get()));
        } else {
            System.out.println("Error: this rate doesn't exist.");
        }

        return Optional.empty();
    }

    public Optional<Currency> getCurrencyByCode(String code) {
        return currencyRepository.getCurrencyByCode(code);
    }

    public Optional<ExchangeRate> getExchangeRateByCode(String currentCode, String targetCode) {
        Optional<Currency> current = currencyRepository.getCurrencyByCode(currentCode);
        Optional<Currency> target = currencyRepository.getCurrencyByCode(targetCode);

        if(current.isEmpty() || target.isEmpty()) {
            return Optional.empty();
        }

        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.getExchangeRate(current.get(), target.get());

        return exchangeRate.map(ExchangeRate::new);
    }

    private void init() {
        if (!dataInitStatus) {
            addCurrency("USD", "US Dollar");
            addCurrency("EUR", "Euro");
            addCurrency("PLN", "Polish Zloty");

            createExchangeRate("EUR", "USD", 1.1);
            createExchangeRate("USD", "EUR", 0.9);
            createExchangeRate("USD", "PLN", 4);
            createExchangeRate("PLN", "USD", 0.25);
            createExchangeRate("EUR", "PLN", 4.3);
            createExchangeRate("PLN", "EUR", 0.23);
        } else {
            System.err.println("Error: repo has already been initialized.");
        }
    }
}