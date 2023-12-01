package services;

import models.TransactionExchange;
import exchangeRate.TransactionRepository;
import utils.CurrencyTransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Volodymyr Sh on 28.11.2023
 * project name: exchanger_currency
 */
public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService() {
        this.transactionRepository = new TransactionRepository();
    }

    public TransactionExchange createNewTransaction(String userEmail, String account, String currency, CurrencyTransactionType type, double amount) {
        System.out.println("Transaction is created.");
        return transactionRepository.createTransaction(userEmail, account, currency, type, amount);
    }

    public void printTransactionById(int id) {

        Optional<TransactionExchange> transactionOptional = transactionRepository.getTransactionById(id);

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction by id%s not found", id);
        }
    }

    public void printTransactionsByUserEmail(String userEmail) {
        Optional<List<TransactionExchange>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getUserEmail().equals(userEmail));

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction by User: %s not found", userEmail);
        }

    }

    public void printTransactionsByAccount(String account) {
        Optional<List<TransactionExchange>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getAccount().equals(account));

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction by account: %s not found", account);
        }
    }

    public void printTransactionsByCurrency(String currency) {
        Optional<List<TransactionExchange>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getCurrency().equals(currency));

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction by currency: %s not found", currency);
        }
    }

    public void printTransactionsBetweenAmounts(double amountFrom, double amountTo) {
        Optional<List<TransactionExchange>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getTransactionAmount() <= amountFrom &&
                                                          transaction.getTransactionAmount() >= amountTo);

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction between amounts: %s and %s not found", amountFrom,  amountTo);
        }
    }

    public void printTransactionsBetweenDates(LocalDateTime dateAfter, LocalDateTime dateBefore) {
        Optional<List<TransactionExchange>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getTransactionDate().isAfter(dateAfter) &&
                        transaction.getTransactionDate().isBefore(dateBefore));

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction between dates: %s and %s not found", dateAfter,  dateBefore);
        }
    }


}
