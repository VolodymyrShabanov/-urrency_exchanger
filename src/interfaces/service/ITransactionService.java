package interfaces.service;

import interfaces.model.ITransactionData;
import model.Currency;

public interface ITransactionService {

    void addNewTransaction(ITransactionData transactionData);

    boolean displayTransactionsByUserEmail(String userEmail);

    boolean displayTransactionsByCurrency(Currency currency);
}
