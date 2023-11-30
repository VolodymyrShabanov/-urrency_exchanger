package view;


import models.Transaction;
import repository.*;
import services.AccountService;
import services.CurrencyService;
import services.TransactionService;
import services.UserService;
import services.DataInitializer;
import utils.Currency;
import utils.TransactionType;
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

    AccountRepository accountRepository = new AccountRepository();
    CurrencyRepository currencyRepository = new CurrencyRepository();
    TransactionRepository transactionRepository = new TransactionRepository();
    ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
    UserRepository userRepository = new UserRepository();
    AccountService accountService = new AccountService(accountRepository);
    UserService userService = new UserService(userRepository);
    CurrencyService currencyService = new CurrencyService(currencyRepository, accountRepository, exchangeRateRepository);

    TransactionService transactionService = new TransactionService();

    UserRole state = UserRole.GUEST;

    public Menu() {
        DataInitializer dataInitializer = new DataInitializer(userRepository, currencyRepository, exchangeRateRepository);
        dataInitializer.initializeData();
    }

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
            clearConsole();

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

                    userService.createUser(tempEmail, tempPass, UserRole.USER);

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
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

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "0":
                    isMenuRunning = false;
                    isAppRunning = false;
                    break;
                default:
                    clearConsole();

                    System.err.println("Error: please choose a valid option.");

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
            }

            clearConsole();
        }
    }

    // TODO: add 'scanner input type' exception handling
    private void runUserMenu() {
        boolean isMenuRunning = true;

        while (isMenuRunning) {
            clearConsole();

            System.out.printf("                       Welcome %s!\n\n", userService.getCurrentUserEmail().get());
            System.out.println(
                    "1. Exchange Currency   | 4. Open Account    | 6. Transaction History\n" +
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
                    clearConsole();

                    System.out.println("Enter deposit sum:");
                    depositSum = scanner.nextDouble();
                    scanner.nextLine();

                    clearConsole();

                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();

                    clearConsole();

                    accountService.depositCurrency(
                            userService.getCurrentUserEmail().get(),
                            depositSum,
                            Currency.valueOf(currencyType)
                    );

                    transactionService.createNewTransaction(
                            userService.getCurrentUserEmail().get(),
                            "account", //TODO - нужен счет
                            String.valueOf(currencyType),
                            TransactionType.CREDIT,
                            depositSum
                    );

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "3":
                    clearConsole();

                    System.out.println("Enter withdrawal sum:");
                    depositSum = scanner.nextDouble();
                    scanner.nextLine();

                    clearConsole();

                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();

                    clearConsole();

                    accountService.withdrawCurrency(
                            userService.getCurrentUserEmail().get(),
                            depositSum,
                            Currency.valueOf(currencyType)
                    );


                    transactionService.createNewTransaction(
                            userService.getCurrentUserEmail().get(),
                            "account", //TODO - нужен счет
                            String.valueOf(currencyType),
                            TransactionType.CREDIT,
                            depositSum
                    );

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "4":
                    clearConsole();

                    String email = userService.getCurrentUserEmail().get();

                    clearConsole();

                    System.out.println("Enter deposit sum:");
                    depositSum = scanner.nextDouble();
                    scanner.nextLine();

                    clearConsole();

                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();

                    clearConsole();

                    accountService.openAccount(email, depositSum, Currency.valueOf(currencyType));

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "5":
                    clearConsole();

                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();

                    clearConsole();

                    accountService.closeAccount(
                            userService.getCurrentUserEmail().get(),
                            Currency.valueOf(currencyType)
                    );

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "6":
                    clearConsole();

                    // TODO
                    transactionService.printTransactionsByUserEmail(
                            userService.getCurrentUserEmail().get()
                    );
                    System.out.println("Transaction history displayed");
                    break;
                case "7":
                    clearConsole();

                    System.out.println("Enter currency type (leave field empty to display all accounts):");
                    currencyType = scanner.nextLine();

                    clearConsole();

                    if (currencyType.isBlank())
                        accountService.printUserAccounts(userService.getCurrentUserEmail().get());
                    else
                        accountService.printUserAccount(userService.getCurrentUserEmail().get(), Currency.valueOf(currencyType));

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "8":
                    clearConsole();

                    System.out.println("Logged out.");
                    state = UserRole.GUEST;
                    isMenuRunning = false;

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "0":
                    isMenuRunning = false;
                    isAppRunning = false;
                    break;
                default:
                    clearConsole();

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
            clearConsole();

            System.out.println("Select:");

            System.out.println("1. admin menu");

            System.out.println("0. Exit");

            String ans = scanner.nextLine();

            switch (ans) {
                case "1":
                    clearConsole();

                    System.out.println("Admin menu");
                    break;
                case "0":
                    clearConsole();

                    isMenuRunning = false;
                    isAppRunning = false;
                    break;
                default:
                    clearConsole();

                    System.err.println("Error: please choose a valid option.");

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
            }

            clearConsole();
        }
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++) System.out.println();
    }
}
