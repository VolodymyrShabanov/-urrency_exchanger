package models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Volodymyr Sh on 27.11.2023
 * project name: exchanger_currency
 */
public class ExchangerRate {
    private Currency fromCurrency;
    private Currency toCurrency;
    private Double rate;
    private LocalDate date;

}
