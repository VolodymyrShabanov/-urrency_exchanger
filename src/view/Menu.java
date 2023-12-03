package view;


import exceptions.DataAlreadyExistsException;
import exceptions.DataInUseException;
import exceptions.TransactionException;
import model.*;
import service.AccountService;
import service.CurrencyService;
import service.TransactionService;
import service.UserService;
import util.UserRole;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Optional;
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

                    switch (userService.login(tempEmail, tempPass)) {
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
                    clearConsole();

                    Currency currentCurrency;
                    Currency targetCurrency;

                    double currentAmount;

                    try {
                        System.out.println("Enter Current currency code:");
                        currentCurrency = currencyService.getCurrencyByCode(scanner.nextLine()).get();

                        clearConsole();

                        System.out.println("Enter Target currency code:");
                        targetCurrency = currencyService.getCurrencyByCode(scanner.nextLine()).get();

                        clearConsole();

                        System.out.println("Enter sum to exchange:");
                        currentAmount = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (NoSuchElementException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    }

                    clearConsole();

                    AccountData currentAccount = accountService.getAccountData(userService.getCurrentUserEmail().get(), currentCurrency);
                    AccountData targetAccount = accountService.getAccountData(userService.getCurrentUserEmail().get(), targetCurrency);

                    Optional<TransactionExchangeData> exchangeData = currencyService.exchangeCurrency(currentAccount, targetAccount, currentAmount);

                    if (exchangeData.isPresent()) transactionService.addNewTransaction(exchangeData.get());

                    try {
                        accountService.withdrawCurrency(
                                userService.getCurrentUserEmail().get(),
                                exchangeData.get().getCurrentTransactionAmount(),
                                currentCurrency
                        );

                        accountService.depositCurrency(
                                userService.getCurrentUserEmail().get(),
                                exchangeData.get().getTargetTransactionAmount(),
                                targetCurrency
                        );
                    } catch (TransactionException e) {
                        throw new RuntimeException(e);
                    } catch (DataAlreadyExistsException e) {
                        throw new RuntimeException(e);
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
                                userService.getCurrentUserEmail().get(),
                                depositSum,
                                currencyService.getCurrencyByCode(currencyType).get()
                        );
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (NoSuchElementException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (DataAlreadyExistsException e) {
                        throw new RuntimeException(e);
                    }

                    clearConsole();

                    transactionService.addNewTransaction(dataDeposit);

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
                                userService.getCurrentUserEmail().get(),
                                depositSum,
                                currencyService.getCurrencyByCode(currencyType).get()
                        );
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (NoSuchElementException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (TransactionException e) {
                        throw new RuntimeException(e);
                    }

                    clearConsole();

                    transactionService.addNewTransaction(dataWithdraw);

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "4":
                    clearConsole();

                    String email = userService.getCurrentUserEmail().get();

                    clearConsole();

                    try {
                        System.out.println("Enter deposit sum:");
                        depositSum = scanner.nextDouble();
                        scanner.nextLine();

                        clearConsole();

                        System.out.println("Enter currency type:");
                        currencyType = scanner.nextLine();

                        accountService.openAccount(email, depositSum, currencyService.getCurrencyByCode(currencyType).get());
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (NoSuchElementException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (DataAlreadyExistsException e) {
                        throw new RuntimeException(e);
                    }

                    clearConsole();

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
                                userService.getCurrentUserEmail().get(),
                                currencyService.getCurrencyByCode(currencyType).get()
                        );
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (NoSuchElementException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (DataInUseException e) {
                        throw new RuntimeException(e);
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
                                transactionService.displayTransactionsByUserEmail(userService.getCurrentUserEmail().get());
                                break;
                            case "2":
                                System.out.println("Enter currency code:");
                                ans = scanner.nextLine();

                                Currency currencyFilter = currencyService.getCurrencyByCode(ans).get();
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
                    } catch (NoSuchElementException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

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

                        if (currencyType.isBlank())
                            accountService.printUserAccounts(userService.getCurrentUserEmail().get());
                        else
                            accountService.printUserAccount(
                                    userService.getCurrentUserEmail().get(),
                                    currencyService.getCurrencyByCode(currencyType).get()
                            );
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (NoSuchElementException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

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
            System.out.println("5. Log Out");

            System.out.println("0. Exit");

            String ans = scanner.nextLine();

            clearConsole();
            switch (ans) {
                case "1":
                    clearConsole();

                    Optional<Currency> currencyToDelete;

                    String editCurrency1;
                    String editCurrency2;
                    double editRate;

                    try {
                        System.out.println("Enter Current currency:");
                        editCurrency1 = scanner.nextLine();

                        System.out.println("Enter Target currency:");
                        editCurrency2 = scanner.nextLine();

                        System.out.println("Enter exchange rate:");
                        editRate = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    }

                    clearConsole();

                    currencyService.updateExchangeRate(editCurrency1, editCurrency2, editRate);

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "2":
                    System.out.println("Enter currency code you want to delete:");
                    String queryCodeToDelete = scanner.nextLine();

                    clearConsole();

                    try {
                        queryCodeToDelete = scanner.nextLine();

                        currencyToDelete = currencyService.getCurrencyByCode(queryCodeToDelete);
                    } catch (InputMismatchException e) {
                        System.err.println("Error: please provide correct input.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (NoSuchElementException e) {
                        System.err.println("Error: data you've provided doesn't exist.");

                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        break;
                    }

                    clearConsole();

                    if (currencyToDelete.isPresent()) {
                        if (accountService.isAccountOpenByCurrency(currencyToDelete.get())) {
                            System.err.println("Error: can't delete currency in use.");
                        } else {
                            currencyService.deleteCurrency(currencyToDelete.get());
                        }
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

                            transactionService.displayTransactionsByUserEmail(queryEmail);

                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            break;
                        case "2":
                            System.out.println("Enter currency code:");
                            String queryCode = scanner.nextLine();

                            clearConsole();

                            Optional<Currency> fetchedCurrency = currencyService.getCurrencyByCode(queryCode);

                            if (fetchedCurrency.isPresent()) {
                                transactionService.displayTransactionsByCurrency(fetchedCurrency.get());
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
                    } catch (Exception e) {
                        System.err.println("Error: error has occurred when assigning roles.");

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
