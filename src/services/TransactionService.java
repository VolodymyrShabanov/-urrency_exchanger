package services;

import models.Transaction;
import exchangeRate.TransactionRepository;
import utils.TransactionType;

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

    public Transaction createNewTransaction(String userEmail, String account, String currency, TransactionType type, double amount) {
        System.out.println("Transaction is created.");
        return transactionRepository.createTransaction(userEmail, account, currency, type, amount);
    }

    public void printTransactionById(int id) {

        Optional<Transaction> transactionOptional = transactionRepository.getTransactionById(id);

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction by id%s not found", id);
        }
    }

    public void printTransactionsByUserEmail(String userEmail) {
        Optional<List<Transaction>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getUserEmail().equals(userEmail));

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction by User: %s not found", userEmail);
        }

    }

    public void printTransactionsByAccount(String account) {
        Optional<List<Transaction>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getAccount().equals(account));

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction by account: %s not found", account);
        }
    }

    public void printTransactionsByCurrency(String currency) {
        Optional<List<Transaction>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getCurrency().equals(currency));

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction by currency: %s not found", currency);
        }
    }

    public void printTransactionsBetweenAmounts(double amountFrom, double amountTo) {
        Optional<List<Transaction>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getAmount() <= amountFrom &&
                                                          transaction.getAmount() >= amountTo);

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction between amounts: %s and %s not found", amountFrom,  amountTo);
        }
    }

    public void printTransactionsBetweenDates(LocalDateTime dateAfter, LocalDateTime dateBefore) {
        Optional<List<Transaction>> transactionOptional = transactionRepository.
                getTransactionsByPredicate(transaction -> transaction.getDate().isAfter(dateAfter) &&
                        transaction.getDate().isBefore(dateBefore));

        if (transactionOptional.isPresent()) {
            System.out.println(transactionOptional);
        } else {
            System.out.printf("Transaction between dates: %s and %s not found", dateAfter,  dateBefore);
        }
    }


}
