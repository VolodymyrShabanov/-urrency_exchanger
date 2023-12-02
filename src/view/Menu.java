package view;


import interfaces.ITransactionData;
import model.*;
import service.AccountService;
import service.CurrencyService;
import service.TransactionService;
import service.UserService;
import util.UserRole;

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

                    System.out.println("Enter Current currency code:");
                    Currency currentCurrency = currencyService.getCurrencyByCode(scanner.nextLine()).get();

                    clearConsole();

                    System.out.println("Enter Target currency code:");
                    Currency targetCurrency = currencyService.getCurrencyByCode(scanner.nextLine()).get();

                    clearConsole();

                    System.out.println("Enter sum to exchange:");
                    double currentAmount = scanner.nextDouble();
                    scanner.nextLine();

                    clearConsole();

                    Account currentAccount = accountService.getAccountCopy(userService.getCurrentUserEmail().get(), currentCurrency).get();
                    Account targetAccount = accountService.getAccountCopy(userService.getCurrentUserEmail().get(), targetCurrency).get();

                    Optional<TransactionExchangeData> exchangeData = currencyService.exchangeCurrency(currentAccount, targetAccount, currentAmount);

                    if(exchangeData.isPresent()) transactionService.addNewTransaction(exchangeData.get());

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
                    break;
                case "2":
                    clearConsole();

                    System.out.println("Enter deposit sum:");
                    depositSum = scanner.nextDouble();
                    scanner.nextLine();

                    clearConsole();

                    System.out.println("Enter currency type:");
                    currencyType = scanner.nextLine();
                    currencyService.getCurrencyByCode(currencyType);

                    clearConsole();

                    Optional<TransactionDepositData> dataDeposit = accountService.depositCurrency(
                            userService.getCurrentUserEmail().get(),
                            depositSum,
                            currencyService.getCurrencyByCode(currencyType).get()
                    );

                    if(dataDeposit.isPresent()) transactionService.addNewTransaction(dataDeposit.get());

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

                    Optional<TransactionWithdrawData> dataWithdraw = accountService.withdrawCurrency(
                            userService.getCurrentUserEmail().get(),
                            depositSum,
                            currencyService.getCurrencyByCode(currencyType).get()
                    );

                    if(dataWithdraw.isPresent()) transactionService.addNewTransaction(dataWithdraw.get());

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

                    accountService.openAccount(email, depositSum, currencyService.getCurrencyByCode(currencyType).get());

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
                            currencyService.getCurrencyByCode(currencyType).get()
                    );

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;
                case "6":
                    clearConsole();

                    System.out.println("Display user transaction history:\n" +
                            "1. All transactions\n" +
                            "2. By currency");
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

                    break;
                case "7":
                    clearConsole();

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
