package service;

import exceptions.DataAlreadyExistsException;
import exceptions.DataInUseException;
import exceptions.DataNotFoundException;
import exceptions.TransactionException;
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
    public TransactionDepositData openAccount(String userEmail, double depositSum, Currency currency) throws DataAlreadyExistsException, TransactionException {
        boolean accountExists = accountRepository.accountExists(userEmail, currency);

        if(depositSum < 1) {
            throw new TransactionException("Error: invalid deposit sum.");
        }

        if (!accountExists) {
            Optional<Account> newAccount = accountRepository.createAccount(userEmail, depositSum, currency);

            AccountData accountData = new AccountData(newAccount.get());

            return new TransactionDepositData(accountData, depositSum);
        } else {
            throw new DataAlreadyExistsException("Error: this account already exists.");
        }
    }

    @Override
    public boolean closeAccount(String userEmail, Currency currency) throws DataInUseException, DataNotFoundException {
        Optional<Account> account = accountRepository.fetchAccount(userEmail, currency);

        if (account.isEmpty()) {
            throw new DataNotFoundException("Error: can't close Account (this Account doesn't exist).");
        } else {
            if (account.get().getBalance() == 0) {
                accountRepository.deleteAccount(userEmail, currency);

                System.out.printf("- %s account successfully closed.\n", account.get().getCurrency().getCode());

                return true;
            } else {
                throw new DataInUseException("Error: account can't be closed (account balance isn't empty).");
            }
        }
    }

    @Override
    public TransactionDepositData depositCurrency(String userEmail, double depositSum, Currency currency)
            throws DataAlreadyExistsException, TransactionException {
        Optional<Account> account = accountRepository.fetchAccount(userEmail, currency);

        if(depositSum < 1) {
            throw new TransactionException("Error: invalid deposit sum.");
        }

        if (account.isPresent()) {
            AccountData accountData = new AccountData(account.get());

            TransactionDepositData transactionData = new TransactionDepositData(accountData, depositSum);

            account.get().deposit(depositSum);

            System.out.printf("- %s %s deposited\n",
                    transactionData.getFormattedTargetAmount(),
                    transactionData.getCurrencies().get(0).getCode()
            );

            return new TransactionDepositData(accountData, depositSum);
        } else {
            TransactionDepositData transactionData = openAccount(userEmail, depositSum, currency);
            System.out.printf("- %s account is open \n- %s %s deposited\n",
                    transactionData.getCurrencies().get(0).getCode(),
                    transactionData.getFormattedTargetAmount(),
                    transactionData.getCurrencies().get(0).getCode()
            );

            return transactionData;
        }
    }

    @Override
    public TransactionWithdrawData withdrawCurrency(String userEmail, double withdrawalSum, Currency currency)
            throws TransactionException, DataNotFoundException {
        Optional<Account> account = accountRepository.fetchAccount(userEmail, currency);

        if(withdrawalSum < 1) {
            throw new TransactionException("Error: invalid withdrawal sum.");
        }

        if (account.isPresent()) {
            if (account.get().getBalance() >= withdrawalSum) {
                AccountData accountData = new AccountData(account.get());

                TransactionWithdrawData transactionData = new TransactionWithdrawData(accountData, withdrawalSum);

                account.get().withdraw(withdrawalSum);

                System.out.printf("- %s %s withdrawn\n",
                        transactionData.getFormattedCurrentAmount(),
                        transactionData.getCurrencies().get(0).getCode()
                );

                return transactionData;
            } else {
                throw new TransactionException("Error: there is not enough balance.");
            }
        } else {
            throw new DataNotFoundException("Error: this account doesn't exist.");
        }
    }

    @Override
    public boolean isAccountOpenByCurrency(Currency currency) {
        return accountRepository.getAccountsByCurrency(currency).isPresent();
    }

    @Override
    public AccountData getAccountData(String userEmail, Currency currency) throws DataNotFoundException {
        Optional<Account> account = accountRepository.fetchAccount(userEmail, currency);

        if (account.isPresent()) {
            return new AccountData(account.get());
        } else {
            throw new DataNotFoundException("Error: this account doesn't exist.");
        }
    }

    @Override
    public void printUserAccounts(String userEmail) {
        Optional<Set<Account>> userAccounts = accountRepository.fetchAccounts(userEmail);

        if (userAccounts.isPresent()) {
            userAccounts.get().forEach(System.out::println);
        } else {
            System.err.println("This user doesn't have any open accounts.");
        }
    }

    @Override
    public void printUserAccount(String userEmail, Currency currency) {
        Optional<Account> userAccount = accountRepository.fetchAccount(userEmail, currency);

        if (userAccount.isPresent()) {
            System.out.println(userAccount.get());
        } else {
            System.err.println("This user doesn't have any open accounts.");
        }
    }
}