package interfaces.service;

import exceptions.DataAlreadyExistsException;
import exceptions.DataInUseException;
import exceptions.TransactionException;
import model.*;

import java.util.Optional;

public interface IAccountService {

    TransactionDepositData openAccount(String email, double depositSum, Currency currency) throws DataAlreadyExistsException;

    boolean closeAccount(String email, Currency currency) throws DataInUseException;

    TransactionDepositData depositCurrency(String email, double depositSum, Currency currency) throws DataAlreadyExistsException;

    TransactionWithdrawData withdrawCurrency(String email, double withdrawalSum, Currency currency) throws TransactionException;

    boolean isAccountOpenByCurrency(Currency currency);

    AccountData getAccountData(String email, Currency currency);

    void printUserAccounts(String email);

    void printUserAccount(String email, Currency currency);
}