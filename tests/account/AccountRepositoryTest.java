package account;

import models.Account;
import models.Currency;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryTest {
    private AccountRepository accountRepository;

    public AccountRepositoryTest() {
        accountRepository = new AccountRepository();
    }

    @BeforeEach
    public void init() {

    }

    @Test
    public void testCreateValidAccount() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency USD = new Currency("USD", "US Dollar");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        assertTrue(accountRepository.createAccount("andrey@gmail.com", 1000, EUR));
        assertEquals(accountRepository.getRepositorySize(), 1);

        assertTrue(accountRepository.createAccount("andrey@gmail.com", 1000, PLN));
        assertEquals(accountRepository.getRepositorySize(), 2);

        assertFalse(accountRepository.createAccount("andrey@gmail.com", 1000, EUR));
        assertEquals(accountRepository.getRepositorySize(), 2);

        assertTrue(accountRepository.createAccount("alex@gmail.com", 1000, PLN));
        assertEquals(accountRepository.getRepositorySize(), 3);
    }

    @Test
    public void testAccountRetrieval() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency USD = new Currency("USD", "US Dollar");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        accountRepository.createAccount("andrey@gmail.com", 1000, EUR);
        assertTrue(accountRepository.fetchAccount("andrey@gmail.com", EUR).isPresent());

        accountRepository.createAccount("andrey@gmail.com", 1000, PLN);
        assertTrue(accountRepository.fetchAccount("andrey@gmail.com", PLN).isPresent());

        assertFalse(accountRepository.fetchAccount("andrey@gmail.com", USD).isPresent());

        assertFalse(accountRepository.fetchAccount("alex@gmail.com", USD).isPresent());
    }

    @Test
    public void testAccountExistence() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency USD = new Currency("USD", "US Dollar");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        accountRepository.createAccount("andrey@gmail.com", 1000, EUR);
        assertTrue(accountRepository.accountExists("andrey@gmail.com", EUR));

        accountRepository.createAccount("andrey@gmail.com", 1000, PLN);
        assertTrue(accountRepository.accountExists("andrey@gmail.com", PLN));

        assertFalse(accountRepository.accountExists("andrey@gmail.com", USD));

        assertFalse(accountRepository.accountExists("alex@gmail.com", USD));
    }

    @Test
    public void testAccountDeletion() {
        Currency EUR = new Currency("EUR", "Euro");
        Currency PLN = new Currency("PLN", "Polish Zloty");

        accountRepository.createAccount("andrey@gmail.com", 1000, EUR);
        Account eurAccount = accountRepository.fetchAccount("andrey@gmail.com", EUR).get();
        assertTrue(accountRepository.deleteAccount("andrey@gmail.com", EUR));

        assertFalse(accountRepository.deleteAccount("andrey@gmail.com", PLN));
    }
}
