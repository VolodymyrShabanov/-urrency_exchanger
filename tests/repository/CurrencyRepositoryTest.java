package repository;

import exceptions.DataInUseException;
import org.junit.jupiter.api.Test;
import repository.CurrencyRepository;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRepositoryTest {

    @Test
    void addCurrency() {
        var currencyRepo = new CurrencyRepository();

        assertDoesNotThrow(() -> {
            currencyRepo.addCurrency("USD", "US Dollar");
        });
        assertDoesNotThrow(() -> {
            currencyRepo.addCurrency("EUR", "Euro");
        });
        assertDoesNotThrow(() -> {
            currencyRepo.addCurrency("PLN", "Polish Zloty");
        });

        assertThrows(DataInUseException.class, () -> {
            currencyRepo.addCurrency("USD", "US Dollar");
        });
        assertThrows(DataInUseException.class, () -> {
            currencyRepo.addCurrency("EUR", "Euro");
        });
        assertThrows(DataInUseException.class, () -> {
            currencyRepo.addCurrency("PLN", "Polish Zloty");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            currencyRepo.addCurrency("", "");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            currencyRepo.addCurrency("", "Rubble");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            currencyRepo.addCurrency("Pun intended", "");
        });
    }

    @Test
    void getCurrencyByName() {
        var currencyRepo = new CurrencyRepository();

        try {
            currencyRepo.addCurrency("USD", "US Dollar");
            currencyRepo.addCurrency("EUR", "Euro");
            currencyRepo.addCurrency("PLN", "Polish Zloty");
        } catch (DataInUseException e) {
            throw new RuntimeException(e);
        }

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

        try {
            currencyRepo.addCurrency("USD", "US Dollar");
            currencyRepo.addCurrency("EUR", "Euro");
            currencyRepo.addCurrency("PLN", "Polish Zloty");
        } catch (DataInUseException e) {
            throw new RuntimeException(e);
        }

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

        try {
            currencyRepo.addCurrency("USD", "US Dollar");
            currencyRepo.addCurrency("EUR", "Euro");
            currencyRepo.addCurrency("PLN", "Polish Zloty");
        } catch (DataInUseException e) {
            throw new RuntimeException(e);
        }

        assertTrue(currencyRepo.getAllCurrencies().isPresent());
    }

    @Test
    void deleteCurrencyByCode() {
        var currencyRepo = new CurrencyRepository();

        assertFalse(currencyRepo.deleteCurrencyByCode("USD"));

        try {
            currencyRepo.addCurrency("USD", "US Dollar");
            currencyRepo.addCurrency("EUR", "Euro");
            currencyRepo.addCurrency("PLN", "Polish Zloty");
        } catch (DataInUseException e) {
            throw new RuntimeException(e);
        }

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

        try {
            currencyRepo.addCurrency("USD", "US Dollar");
            currencyRepo.addCurrency("EUR", "Euro");
            currencyRepo.addCurrency("PLN", "Polish Zloty");
        } catch (DataInUseException e) {
            throw new RuntimeException(e);
        }

        assertTrue(currencyRepo.deleteCurrencyByName("US Dollar"));
        assertTrue(currencyRepo.deleteCurrencyByName("Euro"));
        assertTrue(currencyRepo.deleteCurrencyByName("Polish Zloty"));

        assertFalse(currencyRepo.deleteCurrencyByName("Canadian Dollar"));
        assertFalse(currencyRepo.deleteCurrencyByName("UK Pounds"));
        assertFalse(currencyRepo.deleteCurrencyByName("Argentinian Peso"));

        assertFalse(currencyRepo.deleteCurrencyByName(""));
    }
}