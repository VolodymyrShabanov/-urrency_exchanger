package interfaces;

public interface IExchangerController {
    public boolean createUser(String email, String password);

    public boolean login(String email, String password);

    public boolean displayAccountBalance();

    public boolean displayAccountBalance(String currencyName);

    public boolean openAccount(String currencyName);

    public boolean closeAccount(String currencyName);

    public boolean depositCurrency(String currencyName, double sum);

    public double withdrawCurrency(String currencyName, double sum);

    public double exchangeCurrency(String currencyCurrent, String currencyTarget, double sum);

    public boolean displayTransactionHistory();

    
}
