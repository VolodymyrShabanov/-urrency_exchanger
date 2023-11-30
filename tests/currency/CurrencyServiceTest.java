package currency;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import services.CurrencyService;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyServiceTest {

    @BeforeEach
    public void init () {

    }

    @Test
    public void testCurrencyCreation() {
        CurrencyService currencyService = new CurrencyService();

        assertTrue(currencyService.addCurrency("USD", "US Dollar").isPresent());
        assertTrue(currencyService.addCurrency("EUR", "Euro").isPresent());
        assertTrue(currencyService.addCurrency("PLN", "Polish Zloty").isPresent());

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
}