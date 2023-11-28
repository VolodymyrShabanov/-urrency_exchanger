package models;

public class Currency {
    private final String code;
    private String name;
    private double exchangeRate;

    public Currency(String code, String name, double exchangeRate) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public String getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}