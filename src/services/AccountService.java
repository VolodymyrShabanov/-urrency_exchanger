package services;

import models.Account;
import repository.AccountRepository;
import utils.Currency;

import java.util.Optional;
import java.util.Set;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public boolean openAccount(String email, double depositSum, Currency currency) {
        boolean created = accountRepository.addAccount(email, depositSum, currency);

        if(created) {
            System.out.println("Account successfully created.");
            return false;
        } else {
            System.err.println("Error: account with such credentials can't be created.");
        }

        return false;
    }

    public void printUserAccounts(String email) {
        Optional<Set<Account>> userAccounts = accountRepository.getUserAccounts(email);

        if(userAccounts.isPresent()) {
            userAccounts.get().forEach(System.out::println);
        } else {
            System.err.println("Error: no accounts associated with this user.");
        }
    }
}
