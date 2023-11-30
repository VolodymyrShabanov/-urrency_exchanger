package account;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import repository.AccountRepository;
import services.AccountService;
import utils.Currency;

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
        assertTrue(accountService.openAccount("andrey@gmail.com", 1000, Currency.EUR));
        assertFalse(accountService.openAccount("andrey@gmail.com", 1000, Currency.EUR));
        assertTrue(accountService.openAccount("andrey@gmail.com", 1000, Currency.PLN));
        assertTrue(accountService.openAccount("alex@gmail.com", 1000, Currency.EUR));
    }

    @Test
    public void testCloseAccount() {
        accountService.openAccount("andrey@gmail.com", 1000, Currency.EUR);
        assertFalse(accountService.closeAccount("andrey@gmail.com", Currency.EUR));

        accountService.withdrawCurrency("andrey@gmail.com", 1000 ,Currency.EUR);
        assertTrue(accountService.closeAccount("andrey@gmail.com", Currency.EUR));
        assertFalse(accountService.closeAccount("alex@gmail.com", Currency.PLN));
    }

    @Test
    public void testDepositCurrency() {
        accountService.openAccount("andrey@gmail.com", 1000, Currency.EUR);
        assertEquals(accountService.depositCurrency("andrey@gmail.com", 1000, Currency.EUR), 1000.0);
        assertEquals(accountService.depositCurrency("andrey@gmail.com", 1000, Currency.PLN), 1000.0);
    }

    @Test
    public void testWithdrawCurrency() {
        accountService.openAccount("simon@gmail.com", 1000, Currency.EUR);
        assertEquals(accountService.withdrawCurrency("simon@gmail.com", 999, Currency.EUR), 999);
        assertEquals(accountService.withdrawCurrency("andrey@gmail.com", 1000, Currency.PLN), 0);
    }

}
