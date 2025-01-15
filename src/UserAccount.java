// Suliman Fadi ALkharti
// 120222098

public class UserAccount {

    private String FullName;
    private String phoneNumber;
    private String AccountNumber;
    private Double balance;
    private int password;
    private int AttemptsOfLogin;
    private boolean locked;

    public UserAccount(String fullName, String phoneNumber, int password, Double balance, String AccountNumber) {
        this.FullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.AccountNumber = AccountNumber;
    }

    public String getFullName() {
        return FullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public int getPassword() {
        return password;
    }

    public int getAttemptsOfLogin() {
        return AttemptsOfLogin;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println(amount + "$ has been deposited.");
    }

    public boolean withdraw(double amount) {
        if(amount <= this.balance){
            this.balance -= amount;
            System.out.println("Your Balance is now " + this.balance);
            return true;
        } else{
            return false;
        }
    }

    public void CheckBalance() {
        System.out.println("Your balance is " + this.balance);
    }

    public void ViewTransactionsHistory(UserAccount account,String filter) {
        if (filter.equals("null")) {
            fileHandler.viewTransactionHistory(account.getAccountNumber(), filter);
        } else {
            String[] date = filter.split("/");
            String spiltDate = date[0] + " " + date[1] + " " + date[2];
            System.out.println("\t \t Your transactions history in " + spiltDate);
            fileHandler.viewTransactionHistory(account.getAccountNumber(), spiltDate);
        }
    }

    public void increasedAttemptsOfLogin() {
        AttemptsOfLogin++;
        if (AttemptsOfLogin >= 3) {
            locked = true;
            System.out.println("You account has locked due to many failed attempts to login!.");
            System.out.println("Contact your bank to unlock your account!");
        }
    }

    public void resetAttemptsOfLogin() {
        AttemptsOfLogin = 0;
    }

    @Override
    public String toString() {
        return  AccountNumber + "," + FullName + "," + phoneNumber + "," + password + "," + balance;
    }


}
