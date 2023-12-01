package services;

import exceptions.ValidationException;
import models.Currency;
import models.User;
import exchangeRate.UserRepository;
import utils.UserRole;
import models.ExchangeRate;
import exchangeRate.CurrencyRepository;
import exchangeRate.ExchangeRateRepository;

public class DataInitializer {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public DataInitializer(UserRepository userRepository, CurrencyRepository currencyRepository, ExchangeRateRepository exchangeRateRepository) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public void initializeData() {
        try {
            createUser("max@example.com", "Password1", UserRole.USER);
            createUser("vova@example.com", "Admin123", UserRole.ADMIN);
            createUser("andrew@example.com", "Admin123", UserRole.ADMIN);

            Currency usdCurrency = createCurrency("USD", "US Dollar");
            Currency eurCurrency = createCurrency("EUR", "Euro");
            Currency gbpCurrency = createCurrency("GBP", "British Pound");

            createExchangeRate(new ExchangeRate(usdCurrency, eurCurrency, 0.85));
            createExchangeRate(new ExchangeRate(eurCurrency, usdCurrency, 1.18));
            createExchangeRate(new ExchangeRate(usdCurrency, gbpCurrency, 0.75));
            createExchangeRate(new ExchangeRate(gbpCurrency, usdCurrency, 1.33));
        } catch (ValidationException e) {
            System.err.println("Failed to initialize data: " + e.getMessage());
        }
    }

    private void createUser(String email, String password, UserRole role) {
            User user = new User(email, password, role);
            userRepository.addUser(user);
    }

    private Currency createCurrency(String code, String name) throws ValidationException {
        Currency currency = new Currency(code, name);
        currencyRepository.addCurrency(currency.getCode(), currency.getName());

        return currency;
    }

    private void createExchangeRate(ExchangeRate exchangeRate) throws ValidationException {
        exchangeRateRepository.createExchangeRate(exchangeRate);
    }
}