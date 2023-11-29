package services;

import models.Account;
import repository.AccountRepository;
import utils.Currency;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    // TODO: decide on account fetching/boolean
    public boolean openAccount(String email, double depositSum, Currency currency) {
        boolean accountExists = accountRepository.accountExists(email, currency);

        if(!accountExists) {
            accountRepository.createAccount(email, depositSum, currency);
            return true;
        } else {
            System.err.println("Error: this account already exists.");
        }

        return false;
    }

    // TODO: decide on account fetching/boolean
    public boolean closeAccount(String email, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(email, currency);

        if(account.isEmpty()) {
            System.err.println("Error: this account doesn't exist.");
        } else {
            if(account.get().getBalance() == 0) {
                accountRepository.removeAccount(email, account.get());
            } else {
                System.err.println("Error: account can't be closed (account balance isn't empty)");
            }
        }

        return false;
    }

    public double depositCurrency(String email, double depositSum, Currency currency) {
        Scanner scanner = new Scanner(System.in);

        Optional<Account> account = accountRepository.fetchAccount(email, currency);

        if (account.isPresent()) {
                account.get().deposit(depositSum);
                return depositSum;
        } else {
            System.out.println("Warning: such account isn't open yet. Open new account? y/n");

            String ans = scanner.nextLine();

            switch (ans) {
                case "y":
                    openAccount(email, depositSum, currency);
                    System.out.printf("%s account is open \n%f %s added\n", currency.toString(), depositSum, currency.toString());
                    break;
                default:
                    System.err.println("Error: currency can't be deposited to a non-existent account.");
                    break;
            }

        }

        return 0;
    }

    public double withdrawCurrency(String email, double withdrawalSum, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(email, currency);

        if (account.isPresent()) {
            if (account.get().getBalance() >= withdrawalSum) {
                account.get().withdraw(withdrawalSum);
                return withdrawalSum;
            } else {
                System.err.println("Error: there is not enough balance.");
            }
        } else {
            System.err.println("Error: this account doesn't exist.");
        }

        return 0;
    }

    public void printUserAccounts(String email) {
        Optional<Set<Account>> userAccounts = accountRepository.fetchAccounts(email);

        if (userAccounts.isPresent()) {
            userAccounts.get().forEach(System.out::println);
        } else {
            System.err.println("Error: no such accounts associated with this user.");
        }
    }
    public void printUserAccount(String email, Currency currency) {
        Optional<Account> userAccount = accountRepository.fetchAccount(email, currency);

        if (userAccount.isPresent()) {
            System.out.println(userAccount.get());
        } else {
            System.err.println("Error: no such accounts associated with this user.");
        }
    }
}
