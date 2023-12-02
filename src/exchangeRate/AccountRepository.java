package exchangeRate;

import models.Account;
import models.Currency;

import java.util.*;
import java.util.stream.Collectors;

public class AccountRepository {
    private int repositorySize = 0;

    private final Map<String, Set<Account>> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public Optional<Account> createAccount(String email, double depositSum, Currency currency) {
        if (!accountExists(email, currency)) {
            Account newAccount = new Account(email, currency, depositSum);

            if (accounts.containsKey(email)) {
                accounts.get(email).add(newAccount);
            } else {
                accounts.put(email, new HashSet<Account>());
                accounts.get(email).add(newAccount);
            }

            repositorySize++;
            return Optional.of(new Account(newAccount));
        }

        return Optional.empty();
    }

    public boolean deleteAccount(String email, Currency currency) {
        Optional<Account> account = fetchAccount(email, currency);

        if (account.isEmpty()) return false;

        accounts.get(email).remove(account.get());
        repositorySize--;

        return true;
    }

    public boolean accountExists(String email, Currency currency) {
        boolean isAccountOpen = accounts.containsKey(email);
        Optional<Account> accountOptional = Optional.empty();

        if (isAccountOpen) {
            accountOptional = accounts.get(email).stream()
                    .filter(account -> account.getCurrency().equals(currency))
                    .findFirst();
        }

        return accountOptional.isPresent();
    }

    public Optional<Account> fetchAccount(String email, Currency currency) {
        boolean isAccountOpen = accounts.containsKey(email);
        Optional<Account> accountOptional = Optional.empty();

        if (isAccountOpen) {
            accountOptional = accounts.get(email).stream()
                    .filter(account -> account.getCurrency().equals(currency))
                    .findFirst();
        }

        return accountOptional;
    }

    public Optional<Set<Account>> fetchAccounts(String email) {
        if (accounts.containsKey(email)) {
            return Optional.ofNullable(accounts.get(email));
        } else {
            return Optional.empty();
        }
    }

    public int getRepositorySize() {
        return repositorySize;
    }

    public Optional<Set<Account>> getAccountsByCurrency(Currency currency) {
        Set<Account> fetchedAccounts = accounts.values().stream()
                .flatMap(Set::stream)
                .filter(account -> account.getCurrency().equals(currency))
                .collect(Collectors.toSet());

        return Optional.of(Set.copyOf(fetchedAccounts));
    }
}
