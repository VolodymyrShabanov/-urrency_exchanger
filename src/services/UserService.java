package services;

import interfaces.IUserService;

/**
 * Created by Volodymyr Sh on 27.11.2023
 * project name: exchanger_currency
 */
public class UserService implements IUserService {
    @Override
    public String getCurrentUserEmail() {
        return null;
    }

    @Override
    public boolean createUser(String email, String password) {
        return false;
    }

    @Override
    public boolean login(String email, String password) {
        return false;
    }
}
