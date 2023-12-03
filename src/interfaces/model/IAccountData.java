package interfaces.model;

import model.Currency;

public interface IAccountData {

    String getUserEmail();

    double getBalance();

    Currency getCurrency();
}
