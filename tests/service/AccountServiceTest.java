package service;

import exceptions.DataAlreadyExistsException;
import exceptions.DataInUseException;
import exceptions.DataNotFoundException;
import exceptions.TransactionException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import model.Currency;


import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    private AccountService accountService;

    public AccountServiceTest() {
        accountService = new AccountService();
    }

    @BeforeEach
    public void init() {

    }

    @Test
    public void testOpenAccount() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        assertDoesNotThrow(() -> {
            accountService.openAccount("andrey@gmail.com", 1000, EUR);
        });

        assertThrows(DataAlreadyExistsException.class, () -> {
            accountService.openAccount("andrey@gmail.com", 1000, EUR);
        });

        assertDoesNotThrow(() -> {
            accountService.openAccount("andrey@gmail.com", 1000, PLN);
        });

        assertDoesNotThrow(() -> {
            accountService.openAccount("alex@gmail.com", 1000, EUR);
        });
    }

    @Test
    public void testCloseAccount() {
        Currency EUR = new Currency("EUR", "Euro");

        try {
            accountService.openAccount("andrey@gmail.com", 1000, EUR);
        } catch (DataAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

        assertThrows(DataInUseException.class, () -> {
            accountService.closeAccount("andrey@gmail.com", EUR);
        });

        try {
            accountService.withdrawCurrency("andrey@gmail.com", 1000, EUR);
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() -> {
            accountService.closeAccount("andrey@gmail.com", EUR);
        });

        assertThrows(DataNotFoundException.class, () -> {
            accountService.closeAccount("alex@gmail.com", EUR);
        });
    }

    @Test
    public void testDepositCurrency() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        try {
            accountService.openAccount("andrey@gmail.com", 1000, EUR);
            assertEquals(accountService.depositCurrency("andrey@gmail.com", 1000, EUR).getCurrentAmount(), "1000");
            assertEquals(accountService.depositCurrency("andrey@gmail.com", 1000, PLN).getCurrentAmount(), "1000");
        } catch (DataAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testWithdrawCurrency() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        try {
            accountService.openAccount("simon@gmail.com", 1000, EUR);
            assertEquals(accountService.withdrawCurrency("simon@gmail.com", 999, EUR).getCurrentAmount(), "999");
        } catch (DataAlreadyExistsException | TransactionException e) {
            throw new RuntimeException(e);
        }

        assertThrows(DataNotFoundException.class , () -> {
            accountService.withdrawCurrency("andrey@gmail.com", 1000, PLN).getCurrentAmount();
        });
    }
}