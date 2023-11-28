package repository;

import models.Account;

import java.util.*;

public class AccountRepository {
    private final Map<String, Set<Account>> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public boolean addAccount(String email, double depositSum, Currency currency) {
        if (!accountExists(email, currency)) {
            if (accounts.get(email).isEmpty()) {
                accounts.put(email, new HashSet<Account>());
                accounts.get(email).add(new Account(email, depositSum, currency));
            } else {
                accounts.get(email).add(new Account(email, depositSum, currency));
            }

            return true;
        }

        return false;
    }

    public Optional<Set<Account>> getUserAccounts(String email) {
        if(accounts.containsKey(email)) {
            return Optional.ofNullable(accounts.get(email));
        } else {
            return Optional.empty();
        }
    }

    // Used as helper class to check if account with user email has already been open
    private boolean accountExists(String email, Currency currency) {
        Optional<Account> accountOptional = accounts.get(email).stream()
                .filter(account -> account.getCurrency().equals(currency))
                .findFirst();

        return accountOptional.isPresent();
    }
}
