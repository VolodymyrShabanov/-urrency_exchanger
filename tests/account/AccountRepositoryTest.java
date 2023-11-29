package account;

import models.Account;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import repository.AccountRepository;
import utils.Currency;

import java.util.Optional;

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
        assertTrue(accountRepository.createAccount("andrey@gmail.com", 1000, Currency.EUR));
        assertEquals(accountRepository.getRepositorySize(), 1);

        assertTrue(accountRepository.createAccount("andrey@gmail.com", 1000, Currency.PLN));
        assertEquals(accountRepository.getRepositorySize(), 2);

        assertFalse(accountRepository.createAccount("andrey@gmail.com", 1000, Currency.EUR));
        assertEquals(accountRepository.getRepositorySize(), 2);

        assertTrue(accountRepository.createAccount("alex@gmail.com", 1000, Currency.PLN));
        assertEquals(accountRepository.getRepositorySize(), 3);
    }

    @Test
    public void testAccountRetrieval() {
        accountRepository.createAccount("andrey@gmail.com", 1000, Currency.EUR);
        assertTrue(accountRepository.fetchAccount("andrey@gmail.com", Currency.EUR).isPresent());

        accountRepository.createAccount("andrey@gmail.com", 1000, Currency.PLN);
        assertTrue(accountRepository.fetchAccount("andrey@gmail.com", Currency.PLN).isPresent());

        assertFalse(accountRepository.fetchAccount("andrey@gmail.com", Currency.USD).isPresent());

        assertFalse(accountRepository.fetchAccount("alex@gmail.com", Currency.USD).isPresent());
    }

    @Test
    public void testAccountExistence() {
        accountRepository.createAccount("andrey@gmail.com", 1000, Currency.EUR);
        assertTrue(accountRepository.accountExists("andrey@gmail.com", Currency.EUR));

        accountRepository.createAccount("andrey@gmail.com", 1000, Currency.PLN);
        assertTrue(accountRepository.accountExists("andrey@gmail.com", Currency.PLN));

        assertFalse(accountRepository.accountExists("andrey@gmail.com", Currency.USD));

        assertFalse(accountRepository.accountExists("alex@gmail.com", Currency.USD));
    }

    @Test
    public void testAccountDeletion() {
        accountRepository.createAccount("andrey@gmail.com", 1000, Currency.EUR);
        Account eurAccount = accountRepository.fetchAccount("andrey@gmail.com", Currency.EUR).get();
        assertTrue(accountRepository.deleteAccount("andrey@gmail.com", Currency.EUR));

        assertFalse(accountRepository.deleteAccount("andrey@gmail.com", Currency.PLN));
    }
}
