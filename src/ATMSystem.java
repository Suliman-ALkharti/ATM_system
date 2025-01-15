// Suliman Fadi ALkharti
// 120222098

import java.util.*;

public class ATMSystem {
    private ArrayList<UserAccount> UsersAccounts;

    public ATMSystem() {
        UsersAccounts = fileHandler.getUserAccounts();
    }

    public UserAccount login(String accountNumber, int password) {
        UserAccount userAccount = null;

        for (UserAccount account : UsersAccounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                userAccount = account;
            }
        }

        if (userAccount == null) {
            return null;
        }

        if (userAccount.isLocked()) {
            System.out.println("Your account is locked, please contact the bank");
            return null;
        }

        if (userAccount.getPassword() == password) {
            userAccount.resetAttemptsOfLogin();
            System.out.println("Your account has been successfully logged in, Welcome!");
            return userAccount;
        } else {
            userAccount.increasedAttemptsOfLogin();
            System.out.println("the password you entered is incorrect. Please try again!\nAttempts left: " + (3 - userAccount.getAttemptsOfLogin()));
        }
        return null;
    }

    public void createNewAccount(String fullName, String phoneNumber, int password, Double initialBalance) {
        String accountNumber = generateAccountNumber();
        UserAccount userAccount = new UserAccount(fullName, phoneNumber, password, initialBalance, accountNumber);
        UsersAccounts.add(userAccount);
        fileHandler.saveUserAccount(UsersAccounts);
        System.out.println("Your account created successfully! Your account number is: " + accountNumber);
    }

    public void findUser(String accountNumber) {
        boolean found = false;
        for (UserAccount userAccount : UsersAccounts) {
            if (userAccount.getAccountNumber().equals(accountNumber)) {
                System.out.println("The Name of the account owner: " + userAccount.getFullName() +"and his phone number: "+ userAccount.getPhoneNumber());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Account not found");
        }
    }

    public void PerformTransactions(UserAccount account, double amount, int numberOfTransaction) {
        boolean transactionsDone = true;
        switch (numberOfTransaction) {
            case 1:
                account.deposit(amount);
                transactionsDone = true;
                break;
            case 2:
                transactionsDone = account.withdraw(amount);
                if (!transactionsDone) {
                    System.out.println("Insufficient Balance! \nCheck your Balance");
                }
                break;
        }
        String transaction = "";
        switch (numberOfTransaction) {
            case 1:
                transaction = "Deposit";
                break;
            case 2:
                transaction = "WithDraw";
                break;
            case 3:
                transaction = "View Transaction History";
                break;
        }
        if (transactionsDone) {
            fileHandler.saveUserAccount(UsersAccounts);
            fileHandler.transactionHistory(account.getAccountNumber(), transaction, amount);
            System.out.println("Transaction Done Successfully.");
        }
    }

    public void changePassword(UserAccount account, int newPassword, int confirmPassword) {
        if (newPassword != confirmPassword) {
            System.out.println("Passwords do not match");
        } else {
            account.setPassword(newPassword);
            fileHandler.saveUserAccount(UsersAccounts);
            System.out.println("Password changed successfully!");
        }
    }

    public static void moreTransactions() {
        {
            Scanner input = new Scanner(System.in);
            System.out.println("Do you want other transactions?\n0.No\n1.Yes");
            System.out.println("========================================================");
            System.out.print("Enter your choice (Enter the number Only): ");

            int num = input.nextInt();
            input.nextLine();
            if (num == 0) {
                System.out.println("========================================================");
                System.out.println("thank you for using ATM System! , see you soon!");
                System.exit(0);
            }
        }
    }

    private String generateAccountNumber() {
        String accountNumber;
        Random random = new Random();
        do {
            accountNumber = String.valueOf(random.nextInt(9999999));
        } while (UsersAccounts.contains(accountNumber));
        return accountNumber;
    }

    public void RunAtmSystem() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to ATM System System ! ");
            System.out.println("1. Login");
            System.out.println("2. Create New Account");
            System.out.println("3. Find User");
            System.out.println("4. Exit");
            System.out.println("======================================");
            System.out.print("Enter your choice (Enter the number Only): ");
            int transactionType = input.nextInt();
            input.nextLine();
            switch (transactionType) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accountNumber = input.next();
                    System.out.print("Enter password: ");
                    int password = input.nextInt();
                    UserAccount account = login(accountNumber, password);
                    if (account != null) {
                        boolean IsLogin = true;
                        while (IsLogin) {
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. View Transaction History");
                            System.out.println("4. Check Balance");
                            System.out.println("5. Change Password");
                            System.out.println("6. Logout");
                            System.out.println("=======================================");
                            System.out.print("Enter your choice (Enter the number Only): ");
                            transactionType = input.nextInt();
                            switch (transactionType) {
                                case 1:
                                    System.out.print("Enter deposit amount: ");
                                    double depositAmount = input.nextDouble();
                                    PerformTransactions(account, depositAmount, transactionType);
                                    ATMSystem.moreTransactions();
                                    break;
                                case 2:
                                    System.out.print("Enter withdraw amount: ");
                                    double withdrawAmount = input.nextDouble();
                                    PerformTransactions(account, withdrawAmount, transactionType);
                                    ATMSystem.moreTransactions();
                                    break;
                                case 3:
                                    System.out.println("0. if you want All Transactions History.");
                                    System.out.println("1. if you want Transactions History for specific date (dd/mmm/yyyy).");
                                    System.out.println("=======================================================================");
                                    System.out.print("Choice one of the this options: ");
                                    int choice = input.nextInt();
                                    input.nextLine();
                                    switch (choice) {
                                        case 0:
                                            String dateFilter = "null";
                                            System.out.println("\t\t All Transactions History");
                                            account.ViewTransactionsHistory(account, dateFilter);
                                            break;
                                        case 1:
                                            System.out.print("Enter the date: ");
                                            String date = input.nextLine();
                                            account.ViewTransactionsHistory(account, date);
                                    }
                                    PerformTransactions(account, account.getBalance(), transactionType);
                                    ATMSystem.moreTransactions();
                                    break;
                                case 4:
                                    account.CheckBalance();
                                    ATMSystem.moreTransactions();
                                    break;
                                case 5:
                                    System.out.print("Enter Current password: ");
                                    int CurrentPassword = input.nextInt();
                                    if (CurrentPassword == account.getPassword()) {
                                        System.out.print("Enter new password: ");
                                        int newPassword = input.nextInt();
                                        System.out.print("Confirm new password: ");
                                        int ConfirmPassword = input.nextInt();
                                        changePassword(account, newPassword, ConfirmPassword);
                                    }else
                                        System.out.println("InCorrect password!");
                                    ATMSystem.moreTransactions();
                                    break;
                                case 6:
                                    IsLogin = false;
                                    System.out.println("logged out.");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Try again.");
                            }
                        }
                    } else {
                        System.out.println("Invalid account Or password! Try again.");
                    }

                    break;
                case 2:
                    System.out.print("Enter Your Full name: ");
                    String fullName = input.nextLine();
                    System.out.print("Enter Your Phone Number: ");
                    String phoneNumber = input.nextLine();
                    System.out.print("Enter a Password (only Digits): ");
                    int newPassword = input.nextInt();
                    double initialBalance = 0;
                    createNewAccount(fullName, phoneNumber, newPassword, initialBalance);
                    ATMSystem.moreTransactions();
                    break;
                case 3:
                    System.out.print("Enter a the Account Number: ");
                    accountNumber = input.nextLine();
                    findUser(accountNumber);
                    ATMSystem.moreTransactions();
                    break;
                case 4:
                    System.out.println("thank you for using ATM System!, see you soon!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}