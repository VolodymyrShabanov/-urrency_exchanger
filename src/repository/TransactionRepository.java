package repository;

import models.Account;
import models.Transaction;
import models.User;
import utils.TransactionType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Volodymyr Sh on 28.11.2023
 * project name: exchanger_currency
 */
public class TransactionRepository {
    private final AtomicInteger transactionId = new AtomicInteger(1);
    private List<Transaction> transactions;

    public TransactionRepository() {
        this.transactions = new ArrayList<>();
    }

    public Transaction createTransaction(String userEmail, String account, String currency, TransactionType type, double amount) {
        Transaction newTransaction = new Transaction(transactionId.getAndIncrement(), userEmail, account, currency, type, amount);
        transactions.add(newTransaction);
        return newTransaction;
    }

    public Optional<Transaction> getTransactionById(int id) {
        Transaction transactionById = transactions.stream()
                .filter(transaction1 -> transaction1.getId() == id)
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(transactionById);
    }

    public Optional<List<Transaction>> getAllTransactions() {
        return Optional.ofNullable(new ArrayList<>(transactions));
    }

    public Optional<List<Transaction>> getTransactionsByPredicate(Predicate<Transaction> predicate) {
        return Optional.of(transactions.stream()
                .filter(predicate)
                .collect(Collectors.toList()));

    }


}
