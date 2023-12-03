package repository;

import interfaces.repository.IAccountRepository;
import model.Account;
import model.Currency;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountRepository implements IAccountRepository {

    private final Map<String, Set<Account>> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    @Override
    public Optional<Account> createAccount(String email, double depositSum, Currency currency) {
        if (!accountExists(email, currency)) {
            Account newAccount = new Account(email, currency, depositSum);

            if (accounts.containsKey(email)) {
                accounts.get(email).add(newAccount);
            } else {
                accounts.put(email, new HashSet<Account>());
                accounts.get(email).add(newAccount);
            }

            return Optional.of(new Account(newAccount));
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteAccount(String email, Currency currency) {
        Optional<Account> account = fetchAccount(email, currency);

        if (account.isEmpty()) {
            return false;
        } else {
            accounts.get(email).remove(account.get());
            return true;
        }
    }

    @Override
    public boolean accountExists(String email, Currency currency) {
        if (!accounts.containsKey(email)) return false;

        return accounts.get(email).stream()
                .anyMatch(account -> account.getCurrency().getCode().equals(currency.getCode()));
    }

    @Override
    public Optional<Account> fetchAccount(String email, Currency currency) {
        boolean isAccountOpen = accounts.containsKey(email);

        if (isAccountOpen) {
            return accounts.get(email).stream()
                    .filter(account -> account.getCurrency().getCode().equals(currency.getCode()))
                    .findFirst();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Set<Account>> fetchAccounts(String email) {
        if (accounts.containsKey(email)) {
            return Optional.of(accounts.get(email));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Set<Account>> getAccountsByCurrency(Currency currency) {
        Set<Account> fetchedAccounts = new HashSet<>();

        accounts.values()
                .forEach(accountSet -> {
                    Optional<Account> tempAccount = accountSet.stream()
                            .filter(account -> account.getCurrency().getCode().equals(currency.getCode()))
                            .findFirst();

                    if (tempAccount.isPresent()) fetchedAccounts.add(tempAccount.get());
                });

        if (fetchedAccounts.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(fetchedAccounts);
        }
    }

    @Override
    public int getSize() {
        AtomicInteger size = new AtomicInteger();

        accounts.values().forEach(accountSet -> {
            size.addAndGet(accountSet.size());
        });

        return size.get();
    }
}