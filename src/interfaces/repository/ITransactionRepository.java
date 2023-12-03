package interfaces.repository;

import interfaces.model.ITransactionData;
import model.Currency;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {

    void addTransaction(ITransactionData transactionData);

    Optional<List<ITransactionData>> getTransactionsByCurrency(Currency currency);

    Optional<List<ITransactionData>> getTransactionsByUserEmail(String userEmail);

    int getSize();
}