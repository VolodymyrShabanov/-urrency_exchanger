package interfaces.service;

import model.*;

import java.util.Optional;

public interface IAccountService {

    Optional<TransactionDepositData> openAccount(String email, double depositSum, Currency currency);

    boolean closeAccount(String email, Currency currency);

    Optional<TransactionDepositData> depositCurrency(String email, double depositSum, Currency currency);

    Optional<TransactionWithdrawData> withdrawCurrency(String email, double withdrawalSum, Currency currency);

    boolean isAccountOpenByCurrency(Currency currency);

    Optional<AccountData> getAccountData(String email, Currency currency);

    void printUserAccounts(String email);

    void printUserAccount(String email, Currency currency);
}