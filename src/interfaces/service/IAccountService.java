package interfaces.service;

import model.Account;
import model.Currency;
import model.TransactionDepositData;
import model.TransactionWithdrawData;

import java.util.Optional;
import java.util.Set;

public interface IAccountService {

    Optional<TransactionDepositData> openAccount(String email, double depositSum, Currency currency);

    boolean closeAccount(String email, Currency currency);

    Optional<TransactionDepositData> depositCurrency(String email, double depositSum, Currency currency);

    Optional<TransactionWithdrawData> withdrawCurrency(String email, double withdrawalSum, Currency currency);

    boolean isAccountOpenByCurrency(Currency currency);

    Optional<Account> getAccountCopy(String email, Currency currency);

    void printUserAccounts(String email);

    void printUserAccount(String email, Currency currency);
}
