package repository;

import model.Currency;
import model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateRepositoryTest {
    ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();

    @BeforeEach
    void init() {
        var usd = new Currency("USD", "Us Dollar");
        var eur = new Currency("EUR", "Euro");
        var pln = new Currency("PLN", "Polish Zloty");

        var eurusd = new ExchangeRate(eur, usd, 1.1);
        var usdeur = new ExchangeRate(usd, eur, 0.9);
        var usdpln = new ExchangeRate(usd, pln, 4);
        var plnusd = new ExchangeRate(pln, usd, 0.25);
        var eurpln = new ExchangeRate(eur, pln, 4.3);
        var plneur = new ExchangeRate(pln, eur, 0.23);

        exchangeRateRepository.addExchangeRate(eurusd);
        exchangeRateRepository.addExchangeRate(usdeur);
        exchangeRateRepository.addExchangeRate(usdpln);
        exchangeRateRepository.addExchangeRate(plnusd);
        exchangeRateRepository.addExchangeRate(eurpln);
        exchangeRateRepository.addExchangeRate(plneur);
    }

    @Test
    void getExchangeRate() {
        var usd = new Currency("USD", "Us Dollar");
        var eur = new Currency("EUR", "Euro");
        var pln = new Currency("PLN", "Polish Zloty");
        var cad = new Currency("CAD", "Canadian Dollar");

        assertTrue(exchangeRateRepository.getExchangeRate(eur, usd).isPresent());
        assertTrue(exchangeRateRepository.getExchangeRate(usd, eur).isPresent());
        assertTrue(exchangeRateRepository.getExchangeRate(usd, pln).isPresent());
        assertTrue(exchangeRateRepository.getExchangeRate(pln, usd).isPresent());
        assertTrue(exchangeRateRepository.getExchangeRate(eur, pln).isPresent());
        assertTrue(exchangeRateRepository.getExchangeRate(pln, eur).isPresent());

        assertFalse(exchangeRateRepository.getExchangeRate(cad, usd).isPresent());
        assertFalse(exchangeRateRepository.getExchangeRate(usd, cad).isPresent());
        assertFalse(exchangeRateRepository.getExchangeRate(cad, pln).isPresent());
    }

    @Test
    void createExchangeRate() {
        var usd = new Currency("USD", "Us Dollar");
        var eur = new Currency("EUR", "Euro");
        var pln = new Currency("PLN", "Polish Zloty");
        var cad = new Currency("CAD", "Canadian Dollar");

        var eurusd = new ExchangeRate(eur, usd, 1.1);
        var usdeur = new ExchangeRate(usd, eur, 0.9);
        var usdpln = new ExchangeRate(usd, pln, 4);

        var usdcad = new ExchangeRate(usd, cad, 10);
        var cadusd = new ExchangeRate(cad, usd, 0.1);
        var cadpln = new ExchangeRate(cad, pln, 100);
        var plncad = new ExchangeRate(pln, cad, 0.01);

        assertTrue(exchangeRateRepository.addExchangeRate(usdcad));
        assertTrue(exchangeRateRepository.addExchangeRate(cadusd));
        assertTrue(exchangeRateRepository.addExchangeRate(cadpln));
        assertTrue(exchangeRateRepository.addExchangeRate(plncad));

        assertFalse(exchangeRateRepository.addExchangeRate(eurusd));
        assertFalse(exchangeRateRepository.addExchangeRate(usdeur));
        assertFalse(exchangeRateRepository.addExchangeRate(usdpln));
    }

    @Test
    void getAllExchangeRates() {
        assertTrue(exchangeRateRepository.getAllExchangeRates().isPresent());
        assertEquals(6 ,exchangeRateRepository.getAllExchangeRates().get().size());
    }
}