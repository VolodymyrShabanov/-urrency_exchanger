package repository;

import models.Account;
import utils.Currency;

import java.util.*;
import java.util.stream.Collectors;

public class AccountRepository {
    private int repositorySize = 0;

    private final Map<String, Set<Account>> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public boolean createAccount(String email, double depositSum, Currency currency) {
        if (!accountExists(email, currency)) {
            if (accounts.containsKey(email)) {
                accounts.get(email).add(new Account(email, currency, depositSum));
            } else {
                accounts.put(email, new HashSet<Account>());
                accounts.get(email).add(new Account(email, currency, depositSum));
            }

            repositorySize++;
            return true;
        }

        return false;
    }

    public boolean deleteAccount(String email, Currency currency) {
        Optional<Account> account = fetchAccount(email, currency);

        if(account.isEmpty()) return false;

        accounts.get(email).remove(account.get());
        repositorySize--;

        return true;
    }

    // TODO: Refactor account search, optimize number of times program has to search through Repo

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

    public Set<Account> getAccountsByCurrency(Currency currency) {
        return accounts.values().stream()
                .flatMap(Set::stream)
                .filter(account -> account.getCurrency().equals(currency))
                .collect(Collectors.toSet());
    }
}
