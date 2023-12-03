package interfaces.repository;

import exceptions.DataInUseException;
import model.Currency;

import java.util.Optional;
import java.util.Set;

public interface ICurrencyRepository {

    Optional<Currency> addCurrency(String currencyCode, String currencyName) throws DataInUseException;

    Optional<Currency> getCurrencyByName(String name);

    Optional<Currency> getCurrencyByCode(String code);

    boolean deleteCurrencyByCode(String currencyCode);

    boolean deleteCurrencyByName(String currencyName);

    Optional<Set<Currency>> getAllCurrencies();

    int getSize();
}
