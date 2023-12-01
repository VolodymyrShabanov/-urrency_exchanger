package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.CurrencyTransactionType;

class TransactionExchangeTest {
    @BeforeEach
    void init() {
        var currentCurrency = new Currency("EUR", "Euro");
        var targetCurrency = new Currency("USD", "US Dollar");

        var currentAcconut = new Account("andrey@gmail.com", currentCurrency);
        var targetAcconut = new Account("alex@gmail.com", targetCurrency);

        var converstionRate = 1.1;

        var currentTransactionAmount = 100;
        var targetTransactionAmount = 110;

        var transactionType = CurrencyTransactionType.EXCHANGE;

        var transaction1 = new TransactionExchange(
                currentAcconut,
                targetAcconut,
                converstionRate,
                currentCurrency,
                targetCurrency,
                currentTransactionAmount,
                targetTransactionAmount,
                transactionType
        );
    }

    @Test
    void testToString() {
    }

    @Test
    void getCurrentAccount() {
    }

    @Test
    void getTargetAccount() {
    }

    @Test
    void getCurrentCurrency() {
    }

    @Test
    void getTargetCurrency() {
    }

    @Test
    void getCurrentTransactionAmount() {
    }

    @Test
    void getTargetTransactionAmount() {
    }

    @Test
    void getTransactionType() {
    }

    @Test
    void getTransactionDate() {
    }
}