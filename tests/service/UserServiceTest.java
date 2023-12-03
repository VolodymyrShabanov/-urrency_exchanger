package service;

import org.junit.jupiter.api.Test;
import util.UserRole;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService us = new UserService();

    @Test
    void getCurrentUserEmail() {
        assertFalse(us.getCurrentUserEmail().isPresent());

        us.login("admin", "admin");

        assertTrue(us.getCurrentUserEmail().isPresent());
        assertEquals("admin", us.getCurrentUserEmail().get());

        us.logout();
        us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
        us.login("a@gmail.com", "1234qwert");


        assertEquals("a@gmail.com", us.getCurrentUserEmail().get());
    }

    @Test
    void createUser() {
        assertTrue(us.createUser("a@gmail.com", "1234qwert", UserRole.USER));
        assertFalse(us.createUser("a@gmail.com", "1234qwert", UserRole.USER));
    }

    @Test
    void login() {
        us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
        us.createUser("alex@gmail.com", "1234qwert", UserRole.USER);

        assertEquals(UserRole.USER, us.login("a@gmail.com", "1234qwert"));
        assertEquals(UserRole.USER, us.login("alex@gmail.com", "1234qwert"));

        assertEquals(UserRole.GUEST, us.login("john@gmail.com", "12ert"));
        assertEquals(UserRole.GUEST, us.login("a@gmail.com", "12ert"));
        assertEquals(UserRole.GUEST, us.login("admin", "1234"));

        assertEquals(UserRole.ADMIN, us.login("admin", "admin"));
    }

    @Test
    void logout() {
        assertFalse(us.logout());

        us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
        us.createUser("alex@gmail.com", "1234qwert", UserRole.USER);

        us.login("a@gmail.com","1234qwert");

        assertTrue(us.logout());
        assertFalse(us.logout());

        us.login("alex@gmail.com", "1234qwert");

        assertTrue(us.logout());

        us.login("admin", "admin");

        assertTrue(us.logout());
        assertFalse(us.logout());
    }

    @Test
    void assignUserRole() {
        us.createUser("a@gmail.com", "1234qwert", UserRole.USER);
        us.createUser("alex@gmail.com", "1234qwert", UserRole.USER);

        assertEquals(UserRole.GUEST, us.assignUserRole("axzlaksjd@gmail.com", UserRole.ADMIN));
        assertEquals(UserRole.GUEST, us.assignUserRole("asdf@gmail.com", UserRole.ADMIN));

        assertEquals(UserRole.USER, us.assignUserRole("a@gmail.com", UserRole.USER));
        assertEquals(UserRole.ADMIN, us.assignUserRole("alex@gmail.com", UserRole.ADMIN));
    }
}