//package exchangeRate;
//
//import models.TransactionExchange;
//import utils.CurrencyTransactionType;
//
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
///**
// * Created by Volodymyr Sh on 28.11.2023
// * project name: exchanger_currency
// */
//public class TransactionRepository {
//    private final AtomicInteger transactionId = new AtomicInteger(1);
//    private List<TransactionExchange> transactionExchanges;
//
//    public TransactionRepository() {
//        this.transactionExchanges = new ArrayList<>();
//    }
//
//    public TransactionExchange createTransaction(String userEmail, String account, String currency, CurrencyTransactionType type, double amount) {
//        TransactionExchange newTransactionExchange = new TransactionExchange(transactionId.getAndIncrement(), userEmail, account, currency, type, amount);
//        transactionExchanges.add(newTransactionExchange);
//        return newTransactionExchange;
//    }
//
//    public Optional<TransactionExchange> getTransactionById(int id) {
//        TransactionExchange transactionExchangeById = transactionExchanges.stream()
//                .filter(transaction1 -> transaction1.getId() == id)
//                .findFirst()
//                .orElse(null);
//
//        return Optional.ofNullable(transactionExchangeById);
//    }
//
//    public Optional<List<TransactionExchange>> getAllTransactions() {
//        return Optional.ofNullable(new ArrayList<>(transactionExchanges));
//    }
//
//    public Optional<List<TransactionExchange>> getTransactionsByPredicate(Predicate<TransactionExchange> predicate) {
//        return Optional.of(transactionExchanges.stream()
//                .filter(predicate)
//                .collect(Collectors.toList()));
//
//    }
//
//
//}
