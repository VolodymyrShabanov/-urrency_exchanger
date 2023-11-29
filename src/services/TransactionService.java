package services;

import models.Account;
import models.Currency;
import models.Transaction;
import models.User;
import repository.TransactionRepository;
import utils.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Volodymyr Sh on 28.11.2023
 * project name: exchanger_currency
 */
public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService() {
        this.transactionRepository = new TransactionRepository();
    }

    public Transaction newTransaction(User user, Account fromAccount, Account toAccount, TransactionType type, double amount) {

        if (!validateTransaction(user, fromAccount, toAccount, type, amount)) {
            System.out.println("Data is not exists");
            return null;
        }
        return transactionRepository.addTransaction(user, fromAccount, toAccount, type, amount);
    }

    private boolean validateTransaction(User user, Account fromAccount, Account toAccount, TransactionType type, double amount) {
        // TODO - дописать проверку на валидность данных
        if(user != null);
        if(fromAccount !=null);
        if (toAccount !=null);
        if (type != null);
        if (amount != 0.0);

        return true;
    }

    public List<Transaction> allTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public Transaction transactionsById(int id) {
        return transactionRepository.getTransactionsByPredicate
                (transaction -> transaction.getId() == id).get(0);
    }

    public List<Transaction> transactionsByUser(User user) {
        return transactionRepository.getTransactionsByPredicate
                (transaction -> transaction.getUser().equals(user));
    }

    public List<Transaction> transactionsByCurrency(Currency currency) {
        return transactionRepository.getTransactionsByPredicate(
                transaction -> transaction.getFromAccount().getCurrency().equals(currency) ||
                        transaction.getToAccount().getCurrency().equals(currency));
    }

    public List<Transaction> transactionsBetweenDates(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return transactionRepository.getTransactionsByPredicate
                (transaction -> transaction.getDate().isAfter(dateStart) &&
                        transaction.getDate().isBefore(dateEnd));
    }


}
