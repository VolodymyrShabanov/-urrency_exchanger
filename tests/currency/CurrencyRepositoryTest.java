package currency;

import org.junit.jupiter.api.Test;
import exchangeRate.CurrencyRepository;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRepositoryTest {

    @Test
    void addCurrency() {
        var currencyRepo = new CurrencyRepository();

        assertTrue(currencyRepo.addCurrency("USD", "US Dollar").isPresent());
        assertTrue(currencyRepo.addCurrency("EUR", "Euro").isPresent());
        assertTrue(currencyRepo.addCurrency("PLN", "Polish Zloty").isPresent());

        assertFalse(currencyRepo.addCurrency("USD", "US Dollar").isPresent());
        assertFalse(currencyRepo.addCurrency("EUR", "Euro").isPresent());
        assertFalse(currencyRepo.addCurrency("PLN", "Polish Zloty").isPresent());

        assertFalse(currencyRepo.addCurrency("", "").isPresent());
        assertFalse(currencyRepo.addCurrency("", "Rubble").isPresent());
        assertFalse(currencyRepo.addCurrency("Pun intended", "").isPresent());
    }

    @Test
    void getCurrencyByName() {
        var currencyRepo = new CurrencyRepository();

        currencyRepo.addCurrency("USD", "US Dollar");
        currencyRepo.addCurrency("EUR", "Euro");
        currencyRepo.addCurrency("PLN", "Polish Zloty");

        assertTrue(currencyRepo.getCurrencyByName("US Dollar").isPresent());
        assertTrue(currencyRepo.getCurrencyByName("Euro").isPresent());
        assertTrue(currencyRepo.getCurrencyByName("Polish Zloty").isPresent());

        assertFalse(currencyRepo.getCurrencyByName("").isPresent());
        assertFalse(currencyRepo.getCurrencyByName("BitCoin").isPresent());
        assertFalse(currencyRepo.getCurrencyByName("Canadian Dollar").isPresent());
    }

    @Test
    void getCurrencyByCode() {
        var currencyRepo = new CurrencyRepository();

        currencyRepo.addCurrency("USD", "US Dollar");
        currencyRepo.addCurrency("EUR", "Euro");
        currencyRepo.addCurrency("PLN", "Polish Zloty");

        assertTrue(currencyRepo.getCurrencyByCode("USD").isPresent());
        assertTrue(currencyRepo.getCurrencyByCode("EUR").isPresent());
        assertTrue(currencyRepo.getCurrencyByCode("PLN").isPresent());

        assertFalse(currencyRepo.getCurrencyByCode("").isPresent());
        assertFalse(currencyRepo.getCurrencyByCode("BTC").isPresent());
        assertFalse(currencyRepo.getCurrencyByCode("CAD").isPresent());
    }

    @Test
    void getAllCurrencies() {
        var currencyRepo = new CurrencyRepository();

        assertFalse(currencyRepo.getAllCurrencies().isPresent());

        currencyRepo.addCurrency("USD", "US Dollar");
        currencyRepo.addCurrency("EUR", "Euro");
        currencyRepo.addCurrency("PLN", "Polish Zloty");

        assertTrue(currencyRepo.getAllCurrencies().isPresent());
    }

    @Test
    void deleteCurrencyByCode() {
        var currencyRepo = new CurrencyRepository();

        assertFalse(currencyRepo.deleteCurrencyByCode("USD"));

        currencyRepo.addCurrency("USD", "US Dollar");
        currencyRepo.addCurrency("EUR", "Euro");
        currencyRepo.addCurrency("PLN", "Polish Zloty");

        assertTrue(currencyRepo.deleteCurrencyByCode("USD"));
        assertTrue(currencyRepo.deleteCurrencyByCode("EUR"));
        assertTrue(currencyRepo.deleteCurrencyByCode("PLN"));

        assertFalse(currencyRepo.deleteCurrencyByCode("CAD"));
        assertFalse(currencyRepo.deleteCurrencyByCode("UAH"));
        assertFalse(currencyRepo.deleteCurrencyByCode("Dollars"));

        assertFalse(currencyRepo.deleteCurrencyByCode(""));
    }

    @Test
    void deleteCurrencyByName() {
        var currencyRepo = new CurrencyRepository();

        assertFalse(currencyRepo.deleteCurrencyByName("USD"));

        currencyRepo.addCurrency("USD", "US Dollar");
        currencyRepo.addCurrency("EUR", "Euro");
        currencyRepo.addCurrency("PLN", "Polish Zloty");

        assertTrue(currencyRepo.deleteCurrencyByName("US Dollar"));
        assertTrue(currencyRepo.deleteCurrencyByName("Euro"));
        assertTrue(currencyRepo.deleteCurrencyByName("Polish Zloty"));

        assertFalse(currencyRepo.deleteCurrencyByName("Canadian Dollar"));
        assertFalse(currencyRepo.deleteCurrencyByName("UK Pounds"));
        assertFalse(currencyRepo.deleteCurrencyByName("Argentinian Peso"));

        assertFalse(currencyRepo.deleteCurrencyByName(""));
    }
}