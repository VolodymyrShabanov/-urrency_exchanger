package interfaces;

public interface IUserService {
    public String getCurrentUserEmail();

    public boolean createUser(String email, String password);

    public boolean login(String email, String password);
}
