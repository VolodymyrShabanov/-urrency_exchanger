package interfaces.model;

import model.Currency;

public interface IAccount {

    String getUserEmail();

    double getBalance();

    Currency getCurrency();

    void setCurrency(Currency currency);

    void deposit(double amount);

    void withdraw(double amount);
}
