package utils;

public enum Currency {
    USD(1),
    EUR(1.1),
    PLN(3.9);

    public double rate;

    Currency(double rate) {
        this.rate = rate;
    }
}
