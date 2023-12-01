package interfaces;

import models.Account;
import utils.CurrencyTransactionType;

import java.time.LocalDate;

public interface ITransaction {
    public String getInfo();

    public CurrencyTransactionType getType();

    public String getDateInfo();

    public String getCurrentInfo();
}
