package interfaces;

import java.util.List;

public interface IUser {
    public String getEmail();

    public boolean checkPass(String password);

    public List<IAccount> getAccounts();
}
