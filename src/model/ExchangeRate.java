package model;

import interfaces.model.IExchangeRate;

public class ExchangeRate implements IExchangeRate {
    private final Currency fromCurrency;
    private final Currency toCurrency;
    private double rate;

    public ExchangeRate(Currency fromCurrency, Currency toCurrency, double rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }

    public ExchangeRate(ExchangeRate exchangeRate) {
        this.fromCurrency = exchangeRate.fromCurrency;
        this.toCurrency = exchangeRate.toCurrency;
        this.rate = exchangeRate.rate;
    }

    @Override
    public Currency getFromCurrency() {
        return fromCurrency;
    }

    @Override
    public Currency getToCurrency() {
        return toCurrency;
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public double getRate() {
        return rate;
    }
}
