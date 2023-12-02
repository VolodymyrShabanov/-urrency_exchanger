package repository;

import interfaces.ITransactionData;
import model.Currency;

import java.util.*;

/**
 * Created by Volodymyr Sh on 28.11.2023
 * project name: exchanger_currency
 */
public class TransactionRepository {
    private final Map<String, List<ITransactionData>> transactions;

    public TransactionRepository() {
        this.transactions = new HashMap<>();
    }

    public boolean addTransaction(ITransactionData transactionData) {
        boolean emailExists = transactions.containsKey(transactionData.getUserEmail());

        if (emailExists) {
            transactions.get(transactionData.getUserEmail()).add(transactionData);
            return true;
        } else {
            transactions.put(transactionData.getUserEmail(), new ArrayList<>(List.of(transactionData)));
        }

        return false;
    }

    public Optional<List<ITransactionData>> getTransactionsByCurrency(Currency currency) {
        if (transactions.isEmpty()) return Optional.empty();

        List<ITransactionData> transactionList = new ArrayList<>();

        transactions.keySet()
                .forEach((userEl) -> {
                    transactions.get(userEl)
                            .forEach(transactionDataEl -> {
                                transactionDataEl.getCurrencies()
                                        .forEach(currencyEl -> {
                                            if (currencyEl.getCode().equals(currency.getCode())) {
                                                transactionList.add(transactionDataEl);
                                            }
                                        });
                            });
                });

        if(transactionList.isEmpty()) return Optional.empty();

        return Optional.of(transactionList);
    }

    public Optional<List<ITransactionData>> getTransactionsByUserEmail(String userEmail) {
        boolean emailExists = transactions.containsKey(userEmail);

        if (emailExists) return Optional.of(transactions.get(userEmail));

        return Optional.empty();
    }
}
