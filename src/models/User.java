package models;

import interfaces.IAccount;
import interfaces.IUser;

import java.util.List;

/**
 * Created by Volodymyr Sh on 27.11.2023
 * project name: exchanger_currency
 */
public class User implements IUser {
    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public boolean checkPass(String password) {
        return false;
    }

    @Override
    public List<IAccount> getAccounts() {
        return null;
    }
}
