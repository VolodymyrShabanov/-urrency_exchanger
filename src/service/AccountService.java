package service;

import interfaces.service.IAccountService;
import model.*;
import repository.AccountRepository;

import java.util.Optional;
import java.util.Set;


public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    @Override
    public Optional<TransactionDepositData> openAccount(String userEmail, double depositSum, Currency currency) {
        boolean accountExists = accountRepository.accountExists(userEmail, currency);

        if (!accountExists) {
            Optional<Account> newAccount = accountRepository.createAccount(userEmail, depositSum, currency);
            return Optional.of(new TransactionDepositData(newAccount.get(), depositSum));
        } else {
            System.err.println("Error: this account already exists.");
        }

        return Optional.empty();
    }

    @Override
    public boolean closeAccount(String userEmail, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(userEmail, currency);

        if (account.isEmpty()) {
            System.err.println("Error: this account doesn't exist.");
        } else {
            if (account.get().getBalance() == 0) {
                accountRepository.deleteAccount(userEmail, currency);
                System.out.printf("%s account successfully closed.\n", currency.toString());
                return true;
            } else {
                System.err.println("Error: account can't be closed (account balance isn't empty)");
            }
        }

        return false;
    }

    @Override
    public Optional<TransactionDepositData> depositCurrency(String userEmail, double depositSum, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(userEmail, currency);

        if (account.isPresent()) {
            account.get().deposit(depositSum);

            return Optional.of(new TransactionDepositData(account.get(), depositSum));
        } else {
            Optional<TransactionDepositData> transactionData = openAccount(userEmail, depositSum, currency);
            System.out.printf("%s account is open \n%f %s added\n", currency.toString(), depositSum, currency.toString());

            return transactionData;
        }
    }

    @Override
    public Optional<TransactionWithdrawData> withdrawCurrency(String userEmail, double withdrawalSum, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(userEmail, currency);

        if (account.isPresent()) {
            if (account.get().getBalance() >= withdrawalSum) {
                account.get().withdraw(withdrawalSum);

                return Optional.of(new TransactionWithdrawData(account.get(), withdrawalSum));
            } else {
                System.err.println("Transaction Error: there is not enough balance.");
            }
        } else {
            System.err.println("Data Fetching Error: this account doesn't exist.");
        }

        return Optional.empty();
    }

    @Override
    public boolean isAccountOpenByCurrency(Currency currency) {
        Optional<Set<Account>> accounts = accountRepository.getAccountsByCurrency(currency);

        return accounts.isPresent();
    }

    @Override
    public Optional<Account> getAccountCopy(String userEmail, Currency currency) {
        Optional<Account> account = accountRepository.fetchAccount(userEmail, currency);

        return account.map(Account::new);
    }

    @Override
    public void printUserAccounts(String userEmail) {
        Optional<Set<Account>> userAccounts = accountRepository.fetchAccounts(userEmail);

        if (userAccounts.isPresent()) {
            userAccounts.get().forEach(System.out::println);
        } else {
            System.err.println("Error: no such accounts associated with this user.");
        }
    }

    @Override
    public void printUserAccount(String userEmail, Currency currency) {
        Optional<Account> userAccount = accountRepository.fetchAccount(userEmail, currency);

        if (userAccount.isPresent()) {
            System.out.println(userAccount.get());
        } else {
            System.err.println("Error: no such accounts associated with this user.");
        }
    }
}