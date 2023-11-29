package repository;

import models.Account;
import models.Transaction;
import models.User;
import utils.TransactionType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

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

    public Transaction addTransaction(User user, Account fromAccount, Account toAccount, TransactionType type, double amount) {
        Transaction newTransaction = new Transaction(transactionId.getAndIncrement(), user, fromAccount, toAccount, type, amount);
        transactions.add(newTransaction);
        return newTransaction;
    }

    public List<Transaction> getTransactionsByPredicate(Predicate<Transaction> predicate) {
        List<Transaction> result = new ArrayList<>();
        for(Transaction transaction: transactions) {
            if(predicate.test(transaction)){
                result.add(transaction);
            }
        }
        return result;

    }

    public List<Transaction> getAllTransactions(){
        return transactions;
    }

}
