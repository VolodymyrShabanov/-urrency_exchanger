package currency;


import model.Account;
import model.Currency;
import org.junit.Test;
import service.CurrencyService;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyServiceTest {
    @Test
    public void testCurrencyCreation() {
        CurrencyService currencyService = new CurrencyService();

        // CurrencyService is using initializator
        assertFalse(currencyService.addCurrency("USD", "US Dollar").isPresent());
        assertFalse(currencyService.addCurrency("EUR", "Euro").isPresent());
        assertFalse(currencyService.addCurrency("PLN", "Polish Zloty").isPresent());
    }

    @Test
    public void testCurrencyDeletion() {
        CurrencyService currencyService = new CurrencyService();

        currencyService.addCurrency("USD", "US Dollar");
        currencyService.addCurrency("EUR", "Euro");
        currencyService.addCurrency("PLN", "Polish Zloty");

        assertTrue(currencyService.deleteCurrency("USD"));
        assertTrue(currencyService.deleteCurrency("EUR"));
        assertTrue(currencyService.deleteCurrency("PLN"));

        assertFalse(currencyService.deleteCurrency("USD"));
        assertFalse(currencyService.deleteCurrency("EUR"));
        assertFalse(currencyService.deleteCurrency("PLN"));

        assertFalse(currencyService.deleteCurrency("UAH"));
        assertFalse(currencyService.deleteCurrency("RUB"));
    }

    @Test
    public void testCurrencyRetrieval() {
        CurrencyService currencyService = new CurrencyService();

        currencyService.addCurrency("USD", "US Dollar");
        currencyService.addCurrency("EUR", "Euro");
        currencyService.addCurrency("PLN", "Polish Zloty");

        assertTrue(currencyService.getCurrencyByCode("USD").isPresent());
        assertTrue(currencyService.getCurrencyByCode("EUR").isPresent());
        assertTrue(currencyService.getCurrencyByCode("PLN").isPresent());

        assertFalse(currencyService.getCurrencyByCode("UAH").isPresent());
        assertFalse(currencyService.getCurrencyByCode("RUB").isPresent());
        assertFalse(currencyService.getCurrencyByCode("CAD").isPresent());
    }

    @Test
    public void testCurrencyExchange() {
        var acc1 = new Account("andrey@gmail.com", new Currency("EUR", "Euro"));
        acc1.deposit(1000);
        var acc2 = new Account("andrey@gmail.com", new Currency("USD", "US Dollar"));
        acc2.deposit(2000);
        var acc3 = new Account("andrey@gmail.com", new Currency("PLN", "Polish Zloty"));
        acc1.deposit(1000);

        var currencyService = new CurrencyService();
        currencyService.createExchangeRate("EUR", "USD", 1.1);
        currencyService.createExchangeRate("USD", "PLN", 4);
        currencyService.createExchangeRate("PLN", "EUR", 0.23);
        currencyService.createExchangeRate("USD", "EUR", 0.9);

        assertEquals("110", currencyService.exchangeCurrency(acc1, acc2, 100).get().getCurrentAmount());
        assertEquals("400", currencyService.exchangeCurrency(acc2, acc3, 100).get().getCurrentAmount());
        assertEquals("90", currencyService.exchangeCurrency(acc2, acc1, 100).get().getCurrentAmount());
        assertEquals("23", currencyService.exchangeCurrency(acc3, acc1, 100).get().getCurrentAmount());

        assertNotEquals("", currencyService.exchangeCurrency(acc2, acc1, 100).get().getCurrentAmount());
        assertNotEquals("1234", currencyService.exchangeCurrency(acc3, acc1, 100).get().getCurrentAmount());
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
        currencyService.createExchangeRate("EUR", "USD", 1.1);
        currencyService.createExchangeRate("USD", "PLN", 4);
        currencyService.createExchangeRate("PLN", "EUR", 0.23);
        currencyService.createExchangeRate("USD", "EUR", 0.9);

        assertTrue(currencyService.updateExchangeRate("EUR", "USD", 1.05).isPresent());
        assertTrue(currencyService.updateExchangeRate("USD", "PLN", 3.9).isPresent());
        assertTrue(currencyService.updateExchangeRate("USD", "EUR", 0.95).isPresent());

        assertEquals(3.9, currencyService.getExchangeRateByCode("USD", "PLN").get().getRate());
        assertEquals(1.05, currencyService.getExchangeRateByCode("EUR", "USD").get().getRate());
    }
}