package view;

import services.AccountService;
import services.CurrencyService;
import services.UserService;
import utils.Currency;
import utils.UserRole;

import java.sql.SQLOutput;
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

    UserRole state = UserRole.GUEST;

    public void run() {
        while (isAppRunning) {
            switch (state) {
                case GUEST: {
                    runGuestMenu();
                    break;
                }
                case USER: {
                    runUserMenu();
                    break;
                }
                case ADMIN: {
                    runUserMenu();
                    break;
                }
            }

            clearConsole();
        }

        System.out.println("Exiting...");
        System.out.println("Thank you for using our app!\n");
    }

    private void runGuestMenu() {
        boolean isMenuRunning = true;

        String tempEmail = "";
        String tempPass = "";

        while (isMenuRunning) {
            System.out.println("Welcome Guest!\n");
            System.out.println("Select:");
            System.out.println("1. Register new user");
            System.out.println("2. Login");

            System.out.println("0. Exit");

            String ans = scanner.nextLine();

            switch (ans) {
                case "1":
                    clearConsole();

                    System.out.println("Enter email:");
                    tempEmail = scanner.nextLine();

                    clearConsole();

                    System.out.println("Enter password:");
                    tempPass = scanner.nextLine();

                    clearConsole();

                    System.out.println("Choose role:\n1. User");
//                    System.out.println("Choose role:\n1. User\n2. Admin");
                    String roleSelect = scanner.nextLine();

                    clearConsole();

                    UserRole tempRole;

                    switch (roleSelect) {
                        case "1":
                            tempRole = UserRole.USER;
                            break;
//                        case "2":
//                            tempRole = UserRole.ADMIN;
//                            break;
                        default:
                            tempRole = null;
                            break;
                    }

                    userService.createUser(tempEmail, tempPass, tempRole);
                    break;
                case "2":
                    clearConsole();

                    System.out.println("Enter email:");
                    tempEmail = scanner.nextLine();

                    clearConsole();

                    System.out.println("Enter password:");
                    tempPass = scanner.nextLine();

                    clearConsole();

                    if (userService.login(tempEmail, tempPass)) {
                      isMenuRunning = false;
                      state = UserRole.USER;
                    }
                    break;
                case "0":
                    isMenuRunning = false;
                    isAppRunning = false;
                    break;
                default:
                    System.err.println("Error: please choose a valid option.");

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
            }

            clearConsole();
        }
    }

    // TODO: add 'press enter to continue...' prompts
    // TODO: add 'scanner input type' exception handling
    private void runUserMenu() {
        boolean isMenuRunning = true;

        while (isMenuRunning) {
            System.out.printf("                       Welcome %s!\n\n", userService.getCurrentUserEmail().get());
            System.out.println("1. Exchange Currency   | 4. Open Account    | 6. Transaction History\n" +
                               "                       |                    |\n" +
                               "2. Deposit Currency    | 5. Close Account   | 7. Account Balance\n" +
                               "                       |                    |\n" +
                               "3. Withdraw Currency   |                    |\n\n" +
                               "                            8. Logout       \n" +
                               "                            0. Exit App     ");

            String ans = scanner.nextLine();

            double depositSum;
            String currencyType;

            switch (ans) {
                case "1":
                    // TODO
                    System.out.println("Currency exchanged");
                    break;
                case "2":
                    System.out.println("Enter deposit sum:");
                    depositSum = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();

                    accountService.depositCurrency(
                            userService.getCurrentUserEmail().get(),
                            depositSum,
                            Currency.valueOf(currencyType)
                    );
                    break;
                case "3":
                    System.out.println("Enter withdrawal sum:");
                    depositSum = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();

                    accountService.withdrawCurrency(
                            userService.getCurrentUserEmail().get(),
                            depositSum,
                            Currency.valueOf(currencyType)
                    );
                    break;
                case "4":
                    String email = userService.getCurrentUserEmail().get();

                    System.out.println("Enter deposit sum:");
                    depositSum = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();

                    accountService.openAccount(email, depositSum, Currency.valueOf(currencyType));
                    break;
                case "5":
                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();

                    accountService.closeAccount(
                            userService.getCurrentUserEmail().get(),
                            Currency.valueOf(currencyType)
                    );
                    break;
                case "6":
                    // TODO
                    System.out.println("Transaction history displayed");
                    break;
                case "7":
                    accountService.printUserAccounts(userService.getCurrentUserEmail().get());

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "8":
                    System.out.println("Logged out.");
                    state = UserRole.GUEST;
                    isMenuRunning = false;
                    break;
                case "0":
                    isMenuRunning = false;
                    isAppRunning = false;
                    break;
                default:
                    System.err.println("Error: please choose a valid option.");

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
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
        for (int i = 0; i < 50; i++) System.out.println();
    }
}
