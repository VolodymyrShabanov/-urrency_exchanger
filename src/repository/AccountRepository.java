package repository;

import models.Account;
import utils.Currency;

import java.util.*;

public class AccountRepository {
    private final Map<String, Set<Account>> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public boolean addAccount(String email, double depositSum, Currency currency) {
        if (!accountExists(email, currency)) {
            if (accounts.containsKey(email)) {
//                accounts.get(email).add(new Account(email, depositSum, currency));
                accounts.get(email).add(new Account(email, currency)); // при создании счета depositSum - остаток должен быть нулевым
            } else {
                accounts.put(email, new HashSet<Account>());
//                accounts.get(email).add(new Account(email, depositSum, currency));
                accounts.get(email).add(new Account(email, currency)); // при создании счета depositSum - остаток должен быть нулевым
            }

            return true;
        }

        return false;
    }

    public boolean removeAccount(String email, Account account) {
        accounts.get(email).remove(account);
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
}
