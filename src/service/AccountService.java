package service;

import interfaces.ITransactionData;
import model.*;
import repository.AccountRepository;

import java.util.Optional;
import java.util.Set;


public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public Optional<ITransactionData> openAccount(String email, double depositSum, Currency currency) {
        boolean accountExists = accountRepository.accountExists(email, currency);

        if (!accountExists) {
            Optional<Account> newAccount = accountRepository.createAccount(email, depositSum, currency);
            return Optional.of(new TransactionDepositData(newAccount.get(), depositSum));
        } else {
            System.err.println("Error: this account already exists.");
        }

        return Optional.empty();
    }

    public boolean closeAccount(String email, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(email, currency);

        if (account.isEmpty()) {
            System.err.println("Error: this account doesn't exist.");
        } else {
            if (account.get().getBalance() == 0) {
                accountRepository.deleteAccount(email, currency);
                System.out.printf("%s account successfully closed.\n", currency.toString());
                return true;
            } else {
                System.err.println("Error: account can't be closed (account balance isn't empty)");
            }
        }

        return false;
    }

    public Optional<ITransactionData> depositCurrency(String email, double depositSum, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(email, currency);

        if (account.isPresent()) {
            account.get().deposit(depositSum);

            return Optional.of(new TransactionDepositData(account.get(), depositSum));
        } else {
            Optional<ITransactionData> transactionData = openAccount(email, depositSum, currency);
            System.out.printf("%s account is open \n%f %s added\n", currency.toString(), depositSum, currency.toString());

            return transactionData;
        }
    }

    public Optional<ITransactionData> withdrawCurrency(String email, double withdrawalSum, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(email, currency);

        if (account.isPresent()) {
            if (account.get().getBalance() >= withdrawalSum) {
                account.get().withdraw(withdrawalSum);

                return Optional.of(new TransactionWithdrawData(account.get(), withdrawalSum));
            } else {
                System.err.println("Error: there is not enough balance.");
            }
        } else {
            System.err.println("Error: this account doesn't exist.");
        }

        return Optional.empty();
    }

    public Optional<Account> getAccountCopy(String email, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(email, currency);

        return account.map(Account::new);
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