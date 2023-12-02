package models;

public class ExchangeRate {
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

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
