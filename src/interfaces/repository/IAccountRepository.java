package interfaces.repository;

import model.Account;
import model.Currency;

import java.util.Optional;
import java.util.Set;

public interface IAccountRepository {

    Optional<Account> createAccount(String email, double depositSum, Currency currency);

    boolean deleteAccount(String email, Currency currency);

    boolean accountExists(String email, Currency currency);

    Optional<Account> fetchAccount(String email, Currency currency);

    Optional<Set<Account>> fetchAccounts(String email);

    Optional<Set<Account>> getAccountsByCurrency(Currency currency);

    int getSize();
}