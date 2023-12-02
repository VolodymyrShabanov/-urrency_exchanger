package service;

import interfaces.ITransactionData;
import model.Account;
import model.Currency;
import model.TransactionDepositData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TransactionRepository;

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
        var acc1 = new Account("a@gm.com", usd);
        var acc2 = new Account("a@gm.com", eur);
        var acc3 = new Account("alex@gm.com", pln);

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

        assertFalse(tr.displayTransactionsByUserEmail("john@gm.com"));
        assertFalse(tr.displayTransactionsByUserEmail("kate@gm.com"));
    }

    @Test
    void displayTransactionsByCurrency() {
        assertTrue(tr.displayTransactionsByCurrency(usd));
        assertTrue(tr.displayTransactionsByCurrency(eur));
        assertTrue(tr.displayTransactionsByCurrency(pln));

        assertFalse(tr.displayTransactionsByCurrency(cad));
        assertFalse(tr.displayTransactionsByCurrency(uah));
    }
}