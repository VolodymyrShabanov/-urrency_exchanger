package models;

import java.time.LocalDate;
import java.util.Currency;

/**
 * Created by Volodymyr Sh on 27.11.2023
 * project name: exchanger_currency
 */
public class ExchangerOperation {
    private User user;
    private Currency fromCurrency;
    private Currency toCurrency;
    private Double amount;
    private LocalDate date;

}
