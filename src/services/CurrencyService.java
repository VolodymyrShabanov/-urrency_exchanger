package services;


import exceptions.TransactionException;
import interfaces.ITransaction;
import models.Account;
import models.Currency;
import models.ExchangeRate;
import exchangeRate.CurrencyRepository;
import exchangeRate.ExchangeRateRepository;
import models.TransactionExchange;

import java.util.Optional;

public class CurrencyService {
    private final ExchangeRateRepository exchangeRateRepository;
    private CurrencyRepository currencyRepository;

    private final boolean dataInitStatus;

    public CurrencyService() {
        this.currencyRepository = new CurrencyRepository();
        this.exchangeRateRepository = new ExchangeRateRepository();

        init();
        dataInitStatus = true;
    }

    public Optional<ITransaction> exchangeCurrency(Account current, Account target, double amount) {
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.getExchangeRate(
                current.getCurrency(),
                target.getCurrency()
        );

        if (exchangeRate.isEmpty()) {
            throw new TransactionException("Exchange rate not found");
        }
        

        double exchangedSum = amount * exchangeRate.get().getRate();

        current.withdraw(amount);
        target.deposit(exchangedSum);

        return Optional.of(new TransactionExchange(
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

    public double createExchangeRate(String fromCurrency, String toCurrency, double rate) {
        Optional<Currency> sourceCurrency = currencyRepository.getCurrencyByCode(fromCurrency);
        Optional<Currency> targetCurrency = currencyRepository.getCurrencyByCode(toCurrency);

        if (sourceCurrency.isEmpty() || targetCurrency.isEmpty()) {
            System.out.println("Invalid currency codes.");
            return 0;
        }

        Optional<ExchangeRate> existingRate = exchangeRateRepository.getExchangeRate(sourceCurrency.get(), targetCurrency.get());

        if (existingRate.isEmpty()) {
            ExchangeRate newExchangeRate = new ExchangeRate(sourceCurrency.get(), targetCurrency.get(), rate);
            exchangeRateRepository.createExchangeRate(newExchangeRate);

            return rate;
        } else {
            System.err.println("Error: this rate already exists.");
        }

        return 0;
    }

    public double updateExchangeRate(String fromCurrency, String toCurrency, double newRate) {
        Optional<Currency> sourceCurrency = currencyRepository.getCurrencyByCode(fromCurrency);
        Optional<Currency> targetCurrency = currencyRepository.getCurrencyByCode(toCurrency);

        if (sourceCurrency.isEmpty() || targetCurrency.isEmpty()) {
            System.out.println("Invalid currency codes.");
            return 0;
        }

        Optional<ExchangeRate> existingRate = exchangeRateRepository.getExchangeRate(sourceCurrency.get(), targetCurrency.get());

        if (existingRate.isPresent()) {
            existingRate.get().setRate(newRate);
            return newRate;
        } else {
            System.out.println("Error: this rate doesn't exist.");
        }

        return 0;
    }

    public Optional<Currency> getCurrencyByCode(String code) {
        return currencyRepository.getCurrencyByCode(code);
    }

    private void init() {
        if (!dataInitStatus) {
            addCurrency("USD", "US Dollar");
            addCurrency("EUR", "Euro");
            addCurrency("PLN", "Polish Zloty");
        } else {
            System.err.println("Error: repo has already been initialized.");
        }
    }
}