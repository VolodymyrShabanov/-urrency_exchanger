package service;

import exceptions.DataInUseException;
import exceptions.DataNotFoundException;
import exceptions.LoginException;
import exceptions.PermissionException;
import org.junit.jupiter.api.Test;
import util.UserRole;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService us = new UserService();

    @Test
    void getCurrentUserEmail() {
        assertThrows(LoginException.class, () -> {
            us.getCurrentUserEmail();
        });

        try {
            us.login("admin", "admin");
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() -> {
            us.getCurrentUserEmail();
        });

        try {
            assertEquals("admin", us.getCurrentUserEmail());
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        try {
            us.logout();
            us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
            us.login("a@gmail.com", "1234qwert");
        } catch (LoginException | DataInUseException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals("a@gmail.com", us.getCurrentUserEmail());
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createUser() {
        assertDoesNotThrow(() -> {
            us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
        });
        assertThrows(DataInUseException.class, () -> {
            us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
        });
    }

    @Test
    void login() {
        try {
            us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
            us.createUser("alex@gmail.com", "1234qwert", UserRole.USER);
        } catch (DataInUseException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals(UserRole.USER, us.login("a@gmail.com", "1234qwert"));
            assertEquals(UserRole.USER, us.login("alex@gmail.com", "1234qwert"));
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        assertThrows(DataNotFoundException.class, () -> {
            us.login("john@gmail.com", "12ert");
        });
        assertThrows(LoginException.class, () -> {
            us.login("a@gmail.com", "12ert");
        });
        assertThrows(LoginException.class, () -> {
            us.login("admin", "1234");
        });

        try {
            assertEquals(UserRole.ADMIN, us.login("admin", "admin"));
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void logout() {
        assertThrows(LoginException.class, () -> {
            us.logout();
        });

        try {
            us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
            us.createUser("alex@gmail.com", "1234qwert", UserRole.USER);

            us.login("a@gmail.com", "1234qwert");
        } catch (DataInUseException | LoginException e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() -> {
            us.logout();
        });
        assertThrows(LoginException.class, () -> {
            us.logout();
        });

        try {
            us.login("alex@gmail.com", "1234qwert");
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() -> {
            us.logout();
        });

        try {
            us.login("admin", "admin");
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() -> {
            us.logout();
        });
        assertThrows(LoginException.class, () -> {
            us.logout();
        });
    }

    @Test
    void assignUserRole() {
        try {
            us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
            us.createUser("alex@gmail.com", "1234qwert", UserRole.USER);
        } catch (DataInUseException e) {
            throw new RuntimeException(e);
        }

        assertThrows(DataNotFoundException.class, () -> {
            us.assignUserRole("axzlaksjd@gmail.com", UserRole.ADMIN);
        });
        assertThrows(DataNotFoundException.class, () -> {
            us.assignUserRole("asdf@gmail.com", UserRole.ADMIN);
        });

        assertDoesNotThrow(() -> {
            us.assignUserRole("a@gmail.com", UserRole.USER);
        });
        assertDoesNotThrow(() -> {
            us.assignUserRole("alex@gmail.com", UserRole.ADMIN);
        });
    }
}