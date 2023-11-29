package models;

import java.util.Objects;

public class Currency {
    private final String code;
    private String name;

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return Objects.requireNonNullElse(name, "");
    }

    public void setName(String name) {
        this.name = name;
    }
}