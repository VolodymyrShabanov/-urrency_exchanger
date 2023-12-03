package interfaces.repository;

import model.Currency;
import model.ExchangeRate;

import java.util.Map;
import java.util.Optional;

public interface IExchangeRateRepository {

    Optional<ExchangeRate> getExchangeRate(Currency baseCurrency, Currency targetCurrency);

    boolean createExchangeRate(ExchangeRate exchangeRate);

    Optional<Map<String, ExchangeRate>> getAllExchangeRates();

    int getSize();
}
