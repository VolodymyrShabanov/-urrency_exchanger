package service;

import exceptions.DataNotFoundException;
import interfaces.model.ITransactionData;
import model.Account;
import model.AccountData;
import model.Currency;
import model.TransactionDepositData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    TransactionService tr = new TransactionService();
    Currency usd = new Currency("USD", "US Dollar");
    Currency eur = new Currency("EUR", "Euro");
    Currency pln = new Currency("PLN", "Polish Zloty");
    Currency cad = new Currency("CAD", "Canadian Dollar");
    Currency uah = new Currency("UAH", "Ukrainian Hryvnia");

    @BeforeEach
    void init() {
        var acc1 = new AccountData("a@gm.com", usd);
        var acc2 = new AccountData("a@gm.com", eur);
        var acc3 = new AccountData("alex@gm.com", pln);

        ITransactionData tr1 = new TransactionDepositData(acc1, 100);
        ITransactionData tr2 = new TransactionDepositData(acc2, 100);
        ITransactionData tr3 = new TransactionDepositData(acc2, 100);
        ITransactionData tr4 = new TransactionDepositData(acc3, 100);

        tr.addNewTransaction(tr1);
        tr.addNewTransaction(tr2);
        tr.addNewTransaction(tr3);
        tr.addNewTransaction(tr4);
    }

    @Test
    void displayTransactionsByUserEmail() {
        assertTrue(tr.displayTransactionsByUserEmail("a@gm.com"));
        assertTrue(tr.displayTransactionsByUserEmail("alex@gm.com"));

        assertThrows(DataNotFoundException.class, () -> {
            tr.displayTransactionsByUserEmail("john@gm.com");
        });
        assertThrows(DataNotFoundException.class, () -> {
            tr.displayTransactionsByUserEmail("kate@gm.com");
        });
    }

    @Test
    void displayTransactionsByCurrency() {
        assertDoesNotThrow(() -> {
            tr.displayTransactionsByCurrency(usd);
        });
        assertDoesNotThrow(() -> {
            tr.displayTransactionsByCurrency(eur);
        });
        assertDoesNotThrow(() -> {
            tr.displayTransactionsByCurrency(pln);
        });

        assertThrows(DataNotFoundException.class, () -> {
            tr.displayTransactionsByCurrency(cad);
        });
        assertThrows(DataNotFoundException.class, () -> {
            tr.displayTransactionsByCurrency(uah);
        });
    }
}