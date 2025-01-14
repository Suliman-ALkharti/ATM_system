import java.util.Scanner;

public class Main {
    private static ATMSystem atmSystem;

    public static void main(String[] args) {
        ATMSystem atmSystem = new ATMSystem();
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("Welcome to ATM System System ! ");
            System.out.println("1. Login");
            System.out.println("2. Create New Account");
            System.out.println("3. Find User");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (Enter the number Only): ");
            int transactionType = input.nextInt();
            input.nextLine();
            switch (transactionType) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accountNumber = input.next();
                    System.out.print("Enter password: ");
                    int password = input.nextInt();
                    UserAccount account =atmSystem.login(accountNumber,password);
                    if(account != null) {
                        boolean IsLogin= true;
                        while (IsLogin) {
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. View Transaction History");
                            System.out.println("4. Check Balance");
                            System.out.println("5. Change Password");
                            System.out.println("6. Logout");
                            System.out.print("Enter your choice (Enter the number Only): ");
                            transactionType = input.nextInt();
                            switch (transactionType) {
                                case 1:
                                    System.out.print("Enter deposit amount: ");
                                    double depositAmount = input.nextDouble();
                                    atmSystem.PerformTransactions(account,depositAmount,transactionType);
                                    ATMSystem.moreTransactions();
                                    break;
                                case 2:
                                    System.out.print("Enter withdraw amount: ");
                                    double withdrawAmount = input.nextDouble();
                                    atmSystem.PerformTransactions(account,withdrawAmount,transactionType);
                                    ATMSystem.moreTransactions();
                                    break;
                                case 3:
                                    System.out.print("Enter specific date (yyyy-MM-dd) Or press Enter to view All Transactions History : ");
                                    String dateFilter = input.nextLine();
                                    input.nextLine();
                                    account.ViewTransactionsHistory(account,dateFilter);
                                    atmSystem.PerformTransactions(account,account.getBalance(),transactionType);
                                    ATMSystem.moreTransactions();
                                    break;
                                case 4:
                                    account.CheckBalance();
                                    ATMSystem.moreTransactions();
                                    break;
                                case 5:
                                    System.out.print("Enter a new password: ");
                                    int newPassword = input.nextInt();
                                    atmSystem.changePassword(account,newPassword);
                                    ATMSystem.moreTransactions();
                                    break;
                                case 6:
                                    IsLogin = false;
                                    break;    
                                default:
                                    System.out.println("Invalid choice. Try again.");
                            }
                        }
                    }else {System.out.println("Invalid account Or password! Try again.");}

                    break;
                case 2:
                    System.out.print("Enter Your Full name: ");
                    String fullName = input.nextLine();
                    System.out.print("Enter Your Phone Number: ");
                    String phoneNumber = input.nextLine();
                    System.out.print("Enter a Password (only Digits): ");
                    int newPassword = input.nextInt();
                    System.out.print("Enter the initialBalance: ");
                    double initialBalance = input.nextDouble();
                    atmSystem.createNewAccount(fullName,phoneNumber,newPassword,initialBalance);
                    ATMSystem.moreTransactions();
                    break;
                case 3:
                    System.out.print("Enter a the Account Number: ");
                    accountNumber = input.nextLine();
                    atmSystem.findUser(accountNumber);
                    ATMSystem.moreTransactions();
                    break;
                case 4:
                    System.out.println("thank you for using ATM System ! , see you soon!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

    }
}