package utils;

public enum ExchangeRate {
    USD(1),
    EUR(1.1),
    PLN(3.9);

    double rate;

    ExchangeRate(double rate) {
        this.rate = rate;
    }
}
