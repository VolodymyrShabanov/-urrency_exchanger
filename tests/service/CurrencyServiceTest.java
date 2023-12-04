package service;


import exceptions.DataInUseException;
import exceptions.DataNotFoundException;
import exceptions.TransactionException;
import model.Account;
import model.AccountData;
import model.Currency;
import org.junit.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyServiceTest {
    @Test
    public void testCurrencyCreation() {
        CurrencyService currencyService = new CurrencyService();

        // Should throw an exception because CurrencyService is using data initializator
        assertThrows(DataInUseException.class, () -> {
            currencyService.addCurrency("USD", "US Dollar");
        });
        assertThrows(DataInUseException.class, () -> {
            currencyService.addCurrency("EUR", "Euro");
        });
        assertThrows(DataInUseException.class, () -> {
            currencyService.addCurrency("PLN", "Polish Zloty");
        });
    }

    @Test
    public void testCurrencyDeletion() {
        CurrencyService currencyService = new CurrencyService();

        Currency usd = currencyService.getCurrencyByCode("USD");
        Currency eur = currencyService.getCurrencyByCode("EUR");
        Currency pln = currencyService.getCurrencyByCode("PLN");

        Currency uah;
        Currency cad;

        try {
            uah = currencyService.addCurrency("UAH", "Ukrainian Hryvnia");
            cad = currencyService.addCurrency("CAD", "Canadian Dollar");
        } catch (DataInUseException e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() -> {
            currencyService.deleteCurrency(usd);
        });
        assertDoesNotThrow(() -> {
            currencyService.deleteCurrency(eur);
        });
        assertDoesNotThrow(() -> {
            currencyService.deleteCurrency(pln);
        });


        assertThrows(DataNotFoundException.class, () -> {
            currencyService.deleteCurrency(usd);
        });
        assertThrows(DataNotFoundException.class, () -> {
            currencyService.deleteCurrency(eur);
        });
        assertThrows(DataNotFoundException.class, () -> {
            currencyService.deleteCurrency(pln);
        });

        assertDoesNotThrow(() -> {
            currencyService.deleteCurrency(uah);
        });
        assertDoesNotThrow(() -> {
            currencyService.deleteCurrency(cad);
        });
    }

    @Test
    public void testCurrencyRetrieval() {
        CurrencyService currencyService = new CurrencyService();

        assertDoesNotThrow(() -> {
            currencyService.getCurrencyByCode("USD");
        });
        assertDoesNotThrow(() -> {
            currencyService.getCurrencyByCode("EUR");
        });
        assertDoesNotThrow(() -> {
            currencyService.getCurrencyByCode("PLN");
        });

        assertThrows(DataNotFoundException.class, () -> {
            currencyService.getCurrencyByCode("UAH");
        });
        assertThrows(DataNotFoundException.class, () -> {
            currencyService.getCurrencyByCode("RUB");
        });
        assertThrows(DataNotFoundException.class, () -> {
            currencyService.getCurrencyByCode("CAD");
        });
    }

    @Test
    public void testCurrencyExchange() {
        var acc1 = new AccountData("andrey@gmail.com", new Currency("EUR", "Euro"), 1000);
        var acc2 = new AccountData("andrey@gmail.com", new Currency("USD", "US Dollar"), 2000);
        var acc3 = new AccountData("andrey@gmail.com", new Currency("PLN", "Polish Zloty"), 1000);

        var currencyService = new CurrencyService();

        try {
            assertEquals("110", currencyService.exchangeCurrency(acc1, acc2, 100).getCurrentAmount());
            assertEquals("400", currencyService.exchangeCurrency(acc2, acc3, 100).getCurrentAmount());
            assertEquals("90", currencyService.exchangeCurrency(acc2, acc1, 100).getCurrentAmount());
            assertEquals("23", currencyService.exchangeCurrency(acc3, acc1, 100).getCurrentAmount());

            assertNotEquals("", currencyService.exchangeCurrency(acc2, acc1, 100).getCurrentAmount());
            assertNotEquals("1234", currencyService.exchangeCurrency(acc3, acc1, 100).getCurrentAmount());
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateExchangeRate() {
        var acc1 = new Account("andrey@gmail.com", new Currency("EUR", "Euro"));
        acc1.deposit(1000);
        var acc2 = new Account("andrey@gmail.com", new Currency("USD", "US Dollar"));
        acc2.deposit(2000);
        var acc3 = new Account("andrey@gmail.com", new Currency("PLN", "Polish Zloty"));
        acc1.deposit(1000);

        var currencyService = new CurrencyService();

        assertDoesNotThrow(() -> {
            currencyService.updateExchangeRate("EUR", "USD", 1.05);
        });
        assertDoesNotThrow(() -> {
            currencyService.updateExchangeRate("USD", "PLN", 3.9);
        });
        assertDoesNotThrow(() -> {
            currencyService.updateExchangeRate("USD", "EUR", 0.95);
        });

        assertEquals(3.9, currencyService.getExchangeRateByCode("USD", "PLN").getRate());
        assertEquals(1.05, currencyService.getExchangeRateByCode("EUR", "USD").getRate());
    }
}