
public class UserAccount {

    private String FullName;
    private String phoneNumber;
    private String AccountNumber;
    private Double balance;
    private int password;
    private int AttemptsOfLogin;
    private boolean locked;
    private fileHandler filesHandler;

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
    public int getPassword() {
        return password;
    }
    public Double getBalance() {
        return balance;
    }
    public String getAccountNumber() {
        return AccountNumber;
    }
    public void setPassword(int password) {
        this.password = password;
    }
    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("Deposited " + amount + " to " + FullName);
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
        System.out.println("Your transactions history:");
        filesHandler.viewTransactionHistory(account.getAccountNumber(),filter);
    }
    public int getAttemptsOfLogin() {
        return AttemptsOfLogin;
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
    public boolean isLocked() {
        return locked;
    }
    @Override
    public String toString() {
        return  AccountNumber + "," + FullName + "," + phoneNumber + "," + password + "," + balance;
    }


}
