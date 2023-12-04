package view;


import exceptions.*;
import model.*;
import service.AccountService;
import service.CurrencyService;
import service.TransactionService;
import service.UserService;
import util.UserRole;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
    TransactionService transactionService = new TransactionService();

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
                    runAdminMenu();
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

        String tempEmail;
        String tempPass;

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

                    try {
                        userService.createUser(tempEmail, tempPass, UserRole.USER);
                    } catch (DataInUseException | ValidationException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

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

                    UserRole loginResult;

                    try {
                        loginResult = userService.login(tempEmail, tempPass);
                    } catch (LoginException | DataNotFoundException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    switch (loginResult) {
                        case USER:
                            isMenuRunning = false;
                            state = UserRole.USER;
                            break;
                        case ADMIN:
                            isMenuRunning = false;
                            state = UserRole.ADMIN;
                            break;
                        default:
                            System.err.println("Error: this role doesn't exist.");
                            break;
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

    private void runUserMenu() {
        boolean isMenuRunning = true;

        while (isMenuRunning) {
            clearConsole();

            String currentUserEmail;

            try {
                currentUserEmail = userService.getCurrentUserEmail();
            } catch (LoginException e) {
                System.err.println(e.getMessage());

                break;
            }

            System.out.printf("                       Welcome %s!\n\n", currentUserEmail);
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
                    clearConsole();

                    Currency currentCurrency;
                    Currency targetCurrency;

                    double currentAmount;

                    try {
                        System.out.println("Enter Current currency code:");
                        currentCurrency = currencyService.getCurrencyByCode(scanner.nextLine());

                        clearConsole();

                        System.out.println("Enter Target currency code:");
                        targetCurrency = currencyService.getCurrencyByCode(scanner.nextLine());

                        clearConsole();

                        System.out.println("Enter sum to exchange:");
                        currentAmount = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (DataNotFoundException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    clearConsole();

                    AccountData currentAccount;
                    AccountData targetAccount;

                    TransactionExchangeData exchangeData;

                    try {
                        currentAccount = accountService.getAccountData(currentUserEmail, currentCurrency);
                        targetAccount = accountService.getAccountData(currentUserEmail, targetCurrency);
                        exchangeData = currencyService.exchangeCurrency(currentAccount, targetAccount, currentAmount);
                    } catch (DataNotFoundException | TransactionException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    transactionService.addNewTransaction(exchangeData);

                    try {
                        accountService.withdrawCurrency(
                                currentUserEmail,
                                exchangeData.getCurrentTransactionAmount(),
                                currentCurrency
                        );

                        accountService.depositCurrency(
                                currentUserEmail,
                                exchangeData.getTargetTransactionAmount(),
                                targetCurrency
                        );
                    } catch (TransactionException | DataAlreadyExistsException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }
                    break;
                case "2":
                    clearConsole();

                    TransactionDepositData dataDeposit;

                    try {
                        System.out.println("Enter deposit sum:");
                        depositSum = scanner.nextDouble();
                        scanner.nextLine();

                        clearConsole();

                        System.out.println("Enter currency type:");
                        currencyType = scanner.nextLine();
                        currencyService.getCurrencyByCode(currencyType);

                        dataDeposit = accountService.depositCurrency(
                                currentUserEmail,
                                depositSum,
                                currencyService.getCurrencyByCode(currencyType)
                        );

                        transactionService.addNewTransaction(dataDeposit);
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (NoSuchElementException | DataAlreadyExistsException | TransactionException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "3":
                    clearConsole();

                    TransactionWithdrawData dataWithdraw;

                    try {
                        System.out.println("Enter withdrawal sum:");
                        depositSum = scanner.nextDouble();
                        scanner.nextLine();

                        clearConsole();

                        System.out.println("Enter currency type:");
                        currencyType = scanner.nextLine();

                        dataWithdraw = accountService.withdrawCurrency(
                                currentUserEmail,
                                depositSum,
                                currencyService.getCurrencyByCode(currencyType)
                        );

                        transactionService.addNewTransaction(dataWithdraw);
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (NoSuchElementException | TransactionException | DataNotFoundException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();

                    break;
                case "4":
                    clearConsole();

                    try {
                        System.out.println("Enter deposit sum:");
                        depositSum = scanner.nextDouble();
                        scanner.nextLine();

                        clearConsole();

                        System.out.println("Enter currency type:");
                        currencyType = scanner.nextLine();

                        accountService.openAccount(currentUserEmail, depositSum, currencyService.getCurrencyByCode(currencyType));
                    } catch (InputMismatchException | TransactionException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (DataNotFoundException | DataAlreadyExistsException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();

                    break;
                case "5":
                    clearConsole();

                    try {
                        System.out.println("Enter currency type:");
                        currencyType = scanner.nextLine();

                        clearConsole();

                        accountService.closeAccount(
                                currentUserEmail,
                                currencyService.getCurrencyByCode(currencyType)
                        );
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (DataNotFoundException | DataInUseException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();

                    break;
                case "6":
                    clearConsole();

                    System.out.println("Display user transaction history:\n" +
                            "1. All transactions\n" +
                            "2. By currency");

                    try {
                        ans = scanner.nextLine();

                        switch (ans) {
                            case "1":
                                transactionService.displayTransactionsByUserEmail(currentUserEmail);
                                break;
                            case "2":
                                System.out.println("Enter currency code:");
                                ans = scanner.nextLine();

                                Currency currencyFilter = currencyService.getCurrencyByCode(ans);
                                transactionService.displayTransactionsByCurrency(currencyFilter);
                                break;
                            default:
                                System.err.println("Error: wrong option selection.");
                                break;
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (DataNotFoundException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();

                    break;
                case "7":
                    clearConsole();

                    try {
                        System.out.println("Enter currency type (leave field empty to display all accounts):");
                        currencyType = scanner.nextLine();

                        clearConsole();

                        if (currencyType.isBlank()) {
                            accountService.printUserAccounts(currentUserEmail);
                        } else {
                            accountService.printUserAccount(
                                    currentUserEmail,
                                    currencyService.getCurrencyByCode(currencyType)
                            );
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (DataNotFoundException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

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

    private void runAdminMenu() {
        boolean isMenuRunning = true;

        while (isMenuRunning) {
            clearConsole();

            System.out.println("Select:");

            System.out.println("1. Edit Exchange Rate");
            System.out.println("2. Delete Currency");
            System.out.println("3. Get Transactions Log");
            System.out.println("4. Edit User Role");

            System.out.println();

            System.out.println("5. Log Out");

            System.out.println();

            System.out.println("0. Exit");

            String ans = scanner.nextLine();

            clearConsole();
            switch (ans) {
                case "1":
                    clearConsole();

                    Currency currencyToDelete;

                    String editCurrency1;
                    String editCurrency2;
                    double editRate;

                    try {
                        System.out.println("Enter Current currency:");
                        editCurrency1 = scanner.nextLine();

                        clearConsole();

                        System.out.println("Enter Target currency:");
                        editCurrency2 = scanner.nextLine();

                        clearConsole();

                        System.out.println("Enter exchange rate:");
                        editRate = scanner.nextDouble();
                        scanner.nextLine();

                        clearConsole();

                        currencyService.updateExchangeRate(editCurrency1, editCurrency2, editRate);
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (DataNotFoundException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();

                    break;
                case "2":
                    System.out.println("Enter currency code you want to delete:");
                    String queryCodeToDelete = scanner.nextLine();

                    clearConsole();

                    try {
                        currencyToDelete = currencyService.getCurrencyByCode(queryCodeToDelete);

                        clearConsole();

                        currencyService.deleteCurrency(currencyToDelete);
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (DataNotFoundException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();

                    break;
                case "3":
                    System.out.println("Select option:\n1. By User Email\n2. By Currency");
                    String queryOption = scanner.nextLine();

                    clearConsole();

                    switch (queryOption) {
                        case "1":
                            System.out.println("Enter user email:");
                            String queryEmail = scanner.nextLine();

                            clearConsole();

                            try {
                                transactionService.displayTransactionsByUserEmail(queryEmail);
                            } catch (DataNotFoundException e) {
                                System.err.println(e.getMessage());

                                System.out.println("Press enter to continue...");
                                scanner.nextLine();

                                break;
                            }

                            System.out.println("Press enter to continue...");
                            scanner.nextLine();

                            break;
                        case "2":
                            System.out.println("Enter currency code:");
                            String queryCode = scanner.nextLine();

                            clearConsole();
                            try {
                                Currency fetchedCurrency = currencyService.getCurrencyByCode(queryCode);
                                transactionService.displayTransactionsByCurrency(fetchedCurrency);
                            } catch (DataNotFoundException e) {
                                System.err.println(e.getMessage());

                                System.out.println("Press enter to continue...");
                                scanner.nextLine();

                                break;
                            }

                            System.out.println("Press enter to continue...");
                            scanner.nextLine();

                            break;
                        default:
                            System.err.println("Error: please choose a valid option.");

                            break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();

                    break;
                case "4":
                    System.out.println("Enter user email:");
                    String queryEmail = scanner.nextLine();

                    clearConsole();

                    System.out.println("Enter user role type:");
                    String queryRole = scanner.nextLine();

                    clearConsole();

                    try {
                        userService.assignUserRole(queryEmail, UserRole.valueOf(queryRole));
                    } catch (LoginException | PermissionException e) {
                        System.err.println(e.getMessage());

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: this role doesn't exist.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();

                        break;
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();

                    break;
                case "5":
                    isMenuRunning = false;
                    state = UserRole.GUEST;

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