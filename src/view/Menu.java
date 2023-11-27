package view;

import services.AccountService;
import services.CurrencyService;
import services.UserService;

import java.util.Scanner;

/**
 * Created by Volodymyr Sh on 27.11.2023
 * project name: exchanger_currency
 */

public class Menu {
    Scanner scanner = new Scanner(System.in);

    private boolean isAppRunning = true;

    AccountService accountService = new AccountService();
    UserService userService = new UserService();
    CurrencyService currencyService = new CurrencyService();

    // TODO: Replace with enums
    String state = "GUEST";

    public void run() {
        while (isAppRunning) {
            switch (state) {
                case "GUEST": {
                    runGuestMenu();
                    break;
                }
                case "USER": {
                    runUserMenu();
                    break;
                }
                case "ADMIN": {
                    runUserMenu();
                    break;
                }
                default:
                    System.err.println("Error: please choose a valid option.");
                    break;
            }

            clearConsole();
        }

        System.out.println("Exiting...");
        System.out.println("Thank you for using our app!");
    }

    private void runGuestMenu() {
        boolean isMenuRunning = true;

        while (isMenuRunning) {
            System.out.println("Select:");
            System.out.println("1. Register new user");
            System.out.println("2. Login");

            System.out.println("0. Exit");

            String ans = scanner.nextLine();

            switch (ans) {
                case "1":
                    System.out.println("User registered");
                    break;
                case "2":
                    System.out.println("You have logged in");
                    state = "USER";
                    isMenuRunning = false;
                    break;
                case "0":
                    isMenuRunning = false;
                    isAppRunning = false;
                    break;
                default:
                    System.err.println("Error: please choose a valid option.");
                    break;
            }

            clearConsole();
        }
    }

    private void runUserMenu() {
        boolean isMenuRunning = true;

        while (isMenuRunning) {
            System.out.println("Select:");

            System.out.println("1. Exchange Currency");
            System.out.println("2. Deposit Currency");
            System.out.println("3. Withdraw Currency");

            System.out.println();

            System.out.println("4. Open Account");
            System.out.println("5. Close Account");

            System.out.println();

            System.out.println("6. Display Transaction History");

            System.out.println();

            System.out.println("7. Display Account Balance");

            System.out.println();

            System.out.println("8. Logout");

            System.out.println();

            System.out.println("0. Exit");

            String ans = scanner.nextLine();

            switch (ans) {
                case "1":
                    System.out.println("Currency exchanged");
                    break;
                case "2":
                    System.out.println("Currency deposited");
                    break;
                case "3":
                    System.out.println("Currency withdrawn");
                    break;
                case "4":
                    System.out.println("New account is open");
                    break;
                case "5":
                    System.out.println("Current account is closed");
                    break;
                case "6":
                    System.out.println("Transaction history displayed");
                    break;
                case "7":
                    System.out.println("Account balance is displayed");
                    break;
                case "8":
                    System.out.println("Logged out.");
                    state = "GUEST";
                    isMenuRunning = false;
                    break;
                case "0":
                    isMenuRunning = false;
                    isAppRunning = false;
                    break;
                default:
                    System.err.println("Error: please choose a valid option.");
                    break;
            }

            clearConsole();
        }
    }

    // TODO: Implement admin application logic
    private void runAdminMenu() {
        boolean isMenuRunning = true;

        while (isMenuRunning) {
            System.out.println("Select:");

            System.out.println("1. admin menu");

            System.out.println("0. Exit");

            String ans = scanner.nextLine();

            switch (ans) {
                case "1":
                    System.out.println("Admin menu");
                    break;
                case "0":
                    isMenuRunning = false;
                    isAppRunning = false;
                    break;
                default:
                    System.err.println("Error: please choose a valid option.");
                    break;
            }

            clearConsole();
        }
    }

    private void clearConsole() {
        System.out.println("Press enter to continue...");

        scanner.nextLine();

        for (int i = 0; i < 50; i++) System.out.println();
    }
}
