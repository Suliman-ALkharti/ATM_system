import java.util.*;

public class ATMSystem {
    private Map<String,UserAccount> UsersAccounts;
    private fileHandler filesHandler;

    public ATMSystem() {
        filesHandler = new fileHandler();
        UsersAccounts = filesHandler.getUserAccounts();
    }
    public void createNewAccount(String fullName, String phoneNumber, int password, Double initialBalance) {
        String accountNumber = generateAccountNumber();
        UserAccount userAccount = new UserAccount(fullName, phoneNumber, password, initialBalance, accountNumber);
        UsersAccounts.put(accountNumber, userAccount);
        filesHandler.saveUserAccount(UsersAccounts);
        System.out.println("Your account created successfully! Your account number is: " + accountNumber);
    }
    private String generateAccountNumber() {
        String accountNumber;
        Random random = new Random();
        do {
            accountNumber = String.valueOf(random.nextInt(9999999));
        }while(UsersAccounts.containsKey(accountNumber));
        return accountNumber;
    }
    public UserAccount login (String accountNumber,int password) {
        UserAccount userAccount = UsersAccounts.get(accountNumber);
        if (userAccount == null) {
            return null;
        }

        if (userAccount.isLocked()) {
            System.out.println("Your account is locked, please contact the bank");
            return null;
        }

        if(userAccount.getPassword() == password) {
            userAccount.resetAttemptsOfLogin();
            System.out.println("Your account has been successfully logged in, Welcome!");
            return userAccount;
        } else {
            userAccount.increasedAttemptsOfLogin();
            System.out.println("the password you entered is incorrect. Please try again!\nAttempts left: " + (3 - userAccount.getAttemptsOfLogin()));
        }
        return null;
    }
    public void findUser (String accountNumber) {
        UserAccount userAccount = UsersAccounts.get(accountNumber);
        if (userAccount != null) {
            System.out.println("Account owner name: " + userAccount.getFullName());
            System.out.println("Account owner phone number: " + userAccount.getPhoneNumber());
        } else
            System.out.println("The account is Invalid");
    }
    public void PerformTransactions (UserAccount account, double amount, int numberOfTransaction){
        boolean transactionsDone =true;
        switch(numberOfTransaction){
            case 1:
                account.deposit(amount);
                transactionsDone=true;
                break;
            case 2:
                transactionsDone = account.withdraw(amount);
                if(!transactionsDone){
                    System.out.println("Insufficient Balance !! \n Check your Balance");
                }
                break;
        }
        String transaction="";
        switch (numberOfTransaction) {
            case 1:
                transaction="Deposit";
                break;
            case 2:
                transaction="WithDraw";
                break;
            case 3:
                transaction="View Transaction History";
                break;
        }
        if (transactionsDone){
            filesHandler.saveUserAccount(UsersAccounts);
            filesHandler.transactionHistory(account.getAccountNumber(),transaction,amount);
            System.out.println("Transaction Done Successfully.");
        }
    }
    public void changePassword(UserAccount account, int newPassword) {
        account.setPassword(newPassword);
        filesHandler.saveUserAccount(UsersAccounts);
        System.out.println("Password changed successfully! \n Your account password is: " + account.getPassword());
    }
    public static void moreTransactions() {
        {
            Scanner input = new Scanner(System.in);
            System.out.println("Do you want other transactions?\n0.No\n1.Yes");
            System.out.print("Enter your choice (Enter the number Only): ");
            int num = input.nextInt();
            input.nextLine();
            if (num == 0) {
                System.out.println("thank you for using ATM System ! , see you soon!");
                System.exit(0);
            }
        }
    }
}
