package service;

import exceptions.DataNotFoundException;
import interfaces.model.ITransactionData;
import interfaces.service.ITransactionService;
import model.Currency;
import repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Volodymyr Sh on 28.11.2023
 * project name: exchanger_currency
 */
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService() {
        this.transactionRepository = new TransactionRepository();
    }

    @Override
    public void addNewTransaction(ITransactionData transactionData) {
        transactionRepository.addTransaction(transactionData);
    }

    @Override
    public boolean displayTransactionsByUserEmail(String userEmail) throws DataNotFoundException {
        Optional<List<ITransactionData>> dataList = transactionRepository.getTransactionsByUserEmail(userEmail);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("Error: this user's history doesn't exist.");
        }

        System.out.printf("\n-----------------user '%s' transaction history:\n", dataList.get().get(0).getUserEmail());

        for (int i = 0; i < dataList.get().size(); i++) {
            System.out.printf("%d. %s\n\n", (i + 1), dataList.get().get(i).getInfo());
        }

        return true;
    }

    @Override
    public boolean displayTransactionsByCurrency(Currency currency) throws DataNotFoundException {
        Optional<List<ITransactionData>> dataList = transactionRepository.getTransactionsByCurrency(currency);

        if (dataList.isEmpty()) {
            throw new DataNotFoundException("Error: this user's history doesn't exist.");
        }

        System.out.printf("\n-----------------%s currency transaction history:\n", currency.getCode());

        for (int i = 0; i < dataList.get().size(); i++) {
            System.out.printf("%d. %s\n\n", (i + 1), dataList.get().get(i).getInfo());
        }

        return true;
    }
}