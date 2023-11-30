package services;


import models.Account;
import models.Currency;
import models.ExchangeRate;
import repository.AccountRepository;
import repository.CurrencyRepository;
import repository.ExchangeRateRepository;

import java.util.Optional;
import java.util.Set;

public class CurrencyService {
    private final ExchangeRateRepository exchangeRateRepository;
    private CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository, AccountRepository accountRepository, ExchangeRateRepository exchangeRateRepository) {
        this.currencyRepository = currencyRepository;
        this.accountRepository = accountRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public boolean addCurrency(String code, String name) {
        return currencyRepository.addCurrency(code, name);
    }

    public boolean deleteCurrency(String code) {
        Optional<Currency> currency = currencyRepository.getCurrencyByCode(code);

        if (currency.isEmpty()) {
            System.err.println("Currency not found.");
            return false;
        }
        utils.Currency currencyEnum = utils.Currency.valueOf(currency.get().getCode());
//        if (areOpenAccountsExist(currencyEnum)) {
//            System.err.println("Cannot delete currency. Open accounts exist.");
//
//            return false;
//        }

        currencyRepository.deleteCurrency(currency.get());

        return true;
    }
//
//    private boolean areOpenAccountsExist(utils.Currency currency) {
//        Set<Account> openAccounts = accountRepository.getAccountsByCurrency(currency);
//        return !openAccounts.isEmpty();
//    }

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