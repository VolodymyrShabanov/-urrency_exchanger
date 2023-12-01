package interfaces;

import utils.CurrencyTransactionType;

public interface ITransaction {
    public String getInfo();

    public CurrencyTransactionType getType();

    public String getDateInfo();

    public String getCurrentInfo();

    public String getCurrentAmount();
}
