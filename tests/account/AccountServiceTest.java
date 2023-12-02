package account;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import service.AccountService;
import model.Currency;

import java.util.NoSuchElementException;

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

        assertTrue(accountService.openAccount("andrey@gmail.com", 1000, EUR).isPresent());
        assertFalse(accountService.openAccount("andrey@gmail.com", 1000, EUR).isPresent());
        assertTrue(accountService.openAccount("andrey@gmail.com", 1000, PLN).isPresent());
        assertTrue(accountService.openAccount("alex@gmail.com", 1000, EUR).isPresent());
    }

    @Test
    public void testCloseAccount() {
        Currency EUR = new Currency("EUR", "Euro");

        accountService.openAccount("andrey@gmail.com", 1000, EUR);
        assertFalse(accountService.closeAccount("andrey@gmail.com", EUR));

        accountService.withdrawCurrency("andrey@gmail.com", 1000 , EUR);
        assertTrue(accountService.closeAccount("andrey@gmail.com", EUR));
        assertFalse(accountService.closeAccount("alex@gmail.com", EUR));
    }

    @Test
    public void testDepositCurrency() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        accountService.openAccount("andrey@gmail.com", 1000, EUR);
        assertEquals(accountService.depositCurrency("andrey@gmail.com", 1000, EUR).get().getCurrentAmount(), "1000");
        assertEquals(accountService.depositCurrency("andrey@gmail.com", 1000, PLN).get().getCurrentAmount(), "1000");
    }

    @Test
    public void testWithdrawCurrency() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        accountService.openAccount("simon@gmail.com", 1000, EUR);
        assertEquals(accountService.withdrawCurrency("simon@gmail.com", 999, EUR).get().getCurrentAmount(), "999");

        assertThrows(NoSuchElementException.class, () -> {
            accountService.withdrawCurrency("andrey@gmail.com", 1000, PLN).get().getCurrentAmount();
        });
    }
}
