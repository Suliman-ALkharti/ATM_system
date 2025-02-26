// Suliman Fadi ALkharti
// 120222098

import java.io.*;
import java.util.*;

public class fileHandler {
    private static final String USERS_FILE = "users.txt";
    private static final String TRANSACTION_HISTORY_FILE = "transactions_history.txt";

    public static void saveUserAccount(ArrayList<UserAccount> usersAccounts) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))){
            for (UserAccount account: usersAccounts){
                writer.write(account.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving user account:" + e.getMessage());
        }
    }

    public static ArrayList<UserAccount> getUserAccounts() {
        ArrayList<UserAccount> usersAccounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader( new FileReader(USERS_FILE))){
            String line;
            while ((line = reader.readLine())!= null){
                String[] splitted = line.split(",");
                UserAccount account =new UserAccount(splitted[1],splitted[2],Integer.parseInt(splitted[3]),Double.parseDouble(splitted[4]),splitted[0]);
                usersAccounts.add(account);
            }
        } catch (IOException e){
            System.out.println("Error loading users accounts:" + e.getMessage());
        }
        return usersAccounts;
    }

    public static void transactionHistory(String accountNumber,String transaction ,double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_HISTORY_FILE,true))){
            writer.write("Account Number: "+accountNumber +" , " + transaction +" , the Amount: " + amount + "$, " + new Date().toGMTString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction history:" + e.getMessage());
        }
    }

    public static void viewTransactionHistory (String accountNumber,String Filter) {
        try (BufferedReader reader = new BufferedReader( new FileReader(TRANSACTION_HISTORY_FILE))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(accountNumber)) {
                    if (Filter.equals("null")) {
                        System.out.println(line);
                    }else if (line.toLowerCase().contains(Filter.toLowerCase()))
                        System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving transaction history:" + e.getMessage());
        }
    }

}
