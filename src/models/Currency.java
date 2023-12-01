package models;

import java.util.Objects;

public class Currency {
    private final String code;
    private final String name;

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public Currency(Currency currency) {
        this.code = currency.getCode();
        this.name = currency.getName();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return Objects.requireNonNullElse(name, "");
    }

    @Override
    public String toString() {
        return getName();
    }
}