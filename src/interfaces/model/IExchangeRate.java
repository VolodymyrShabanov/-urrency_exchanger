package interfaces.model;

import model.Currency;

public interface IExchangeRate {

    Currency getFromCurrency();

    Currency getToCurrency();

    void setRate(double rate);

    double getRate();
}
