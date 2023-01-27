package LogicLayer;

import BOLayer.Admin;
import BOLayer.Customer;
import DataLayer.Data;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class Logic {

    // Verify if admin data is in file.
    public boolean verifyLogin(Admin admin){
        Data adminData = new Data();
        return adminData.isInFile(admin);
    }

    // Verify the login of customer
    public boolean canLogin(Customer user){
        Data customerData = new Data();
        return customerData.canLogin(user);
    }

    // Method to verify if username is valid (username can only contain A-Z, a-z y 0-9)
    private boolean isValidUserName(String username){
        if (username.isBlank()){
            return false;
        }
        char[] s = username.toCharArray();
        for (char c : s){
            if(!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))){
                return false;
            }
        }
        return true;
    }

    private boolean isValidPin(String s){
        if(s.length() != 5){
            return false;
        }
        for (char c: s.toCharArray()) {
            if(!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    // Guest the user to enter a valid number.
    private Integer getValidNumber(String msg){
        int num = 0;
        Scanner console = new Scanner(System.in);
        boolean check ;
        do {
            check = false;
            System.out.print(msg);
            try{
                num = Integer.parseInt(console.next());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input, want to try again?. 1. Yes , 2. No: ");
                String opt = console.next();
                if (opt.equals("1")){
                    check = true;
                } else {
                    console.close();
                    return null;
                }
            }
        } while (check);
        console.close();
        return num;
    }

    // Encryption Method
    // For alphabets we swap A with Z, B with Y and so on.
    // A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    // Z Y X W V U T S R Q P O N M L K J I H G F E D C B A
    // For Number we have
    // 0123456789
    // 9876543210
    public String EncryptionDecryption(String username) {
        StringBuilder output = new StringBuilder();
        char[] ch = username.toCharArray();
        for (char c : ch) {

            if(c >= 'A' && c <= 'Z'){
                output.append((char) ('Z' - (c - 'A')));

            } else if(c >= 'a' && c <= 'z'){
                output.append((char) ('z' - (c - 'a')));

            } else if(c >= '0' && c <= '9'){
                output.append(9 - Character.getNumericValue(c));
            }
        }
        return output.toString();
    }

    // Return a valid username.
    private String getUsername() {
        Scanner console = new Scanner(System.in);
        boolean check;
        String username;
        do {
            check = false;
            System.out.print("Username: ");
            username = console.next();

            if (username.isBlank() || !isValidUserName(username)){
                System.out.println("Please enter valid username (Username can only contain A-Z, a-z & 0-9)");
                check = true;
            }
        } while (check);
        console.close();
        return username;
    }

    // Return a valid username. Used when user updating username.
    private String getUsername(String currentUserName) {
        Scanner console = new Scanner(System.in);
        boolean check;
        String username;
        do {
            check = false;
            System.out.print("Username: ");
            username = console.next();

            if (username.isBlank()){
                username = currentUserName;
                console.close();
                return username;
            }

            if(!isValidUserName(username)){
                System.out.println("Please enter valid username (Username can only contain A-Z, a-z & 0-9)");
                check = true;
            }
        } while (check);
        console.close();
        return username;
    }

    // Disables an account.
    public void disableAccount(String username) throws JsonProcessingException {
        Data data = new Data();
        Customer customer = data.getCustomer(username);
        customer.setStatus("Disable");
        data.updateInFile(customer);
    }

    // Create an account of customer
    public void createAccount(){
        Data data = new Data();
        Customer customer = new Customer();
        Scanner console = new Scanner(System.in);

        System.out.println("--- Creating new account ---");
        // Check and set the username.
        customer.setUsername(EncryptionDecryption(getUsername()));

        // Checks and set de PIN.
        boolean check = true;
        while (check){
            System.out.println("5 digit PIN: ");
            String pin = console.next();
            customer.setPin(EncryptionDecryption(pin));
            // Checks if the PIN typed is valid (PIN is only 5 digit long and can only contain numbers from 0 to 9)
            if (pin.equals("") || !isValidPin(pin)){
                System.out.println("Please enter valid PIN (PIN is only 5 digit long and can only contain numbers from 0 to 9)");
            } else {
                check = false;
            }
        }

        // Checks and sets the holder's name.
        do {
            check = false;
            System.out.println("Holder's Name: ");
            customer.setName(console.next().trim());
            if (customer.getName().isBlank()){
                check = true;
                System.out.println("Wrong holder's name input, please enter again.");
            }
        } while (check);

        // Checks and sets account type.
        check = true;
        while (check){
            System.out.println("Account type: 1.Savings , 2.Current");
            customer.setAccountType(console.next());
            if (!(customer.getAccountType().equals("1") || customer.getAccountType().equals("2"))){
                System.out.println("Wrong input. Enter 1.Savings , 2.Current");
            } else {
                check = false;
            }
        }

        // Sets starting balance.
        do{
            check = false;
            try {
                System.out.print("Starting Balance: ");
                customer.setBalance(Integer.parseInt(console.next()));
            } catch (NumberFormatException e){
                System.out.println("Wrong input. Enters numbers only.");
                check = true;
            }
        }while(check);

        // Sets status of balance.
        do {
            check = false;
            System.out.print("Status: 1. Active, 2. Inactive");
            customer.setStatus(console.next());
            if (!(customer.getStatus().equals("1") || customer.getStatus().equals("2"))){
                System.out.print("Wrong input. Enter 1. Active, 2. Inactive");
                check = true;
            }
        }while(check);

        // Assign last number account.
        customer.setAccountNo(data.getLastAccountNumber() + 1);

        // Appending customer to a file
        data.AddToFile(customer, true);
        System.out.printf("Account Successfully Created – the account number assigned is: %d%n", customer.getAccountNo());
    }

    public void deleteAccount() throws JsonProcessingException {
        Integer accNo = getValidNumber("Number account: ");
        // Checks if is a valid number.
        if(accNo == null) return;

        Data data = new Data();
        // Checks if the account number is in file.
        if (!(data.isInFile(accNo))){
            System.out.printf("Account number %d does not exist.%n", accNo);
            return;
        }

        Customer customer;
        customer = data.getCustomer(accNo);
        System.out.printf("You wish to delete the account held by Mr %s. If this information is correct please re-enter the account number:%n", customer.getUsername());
        Integer tempAccount = getValidNumber("Number account: ");
        // Checks if the number typed match with the customer number account.
        if (!(Integer.valueOf(customer.getAccountNo()).equals(tempAccount))){
            System.out.println("No account was deleted.");
            return;
        }

        data.deleteFromFile(customer);
        System.out.println("Account delete successfully.");

    }

    public void updateAccount(){
        Integer accNo = getValidNumber("Number account: ");
        // Checks if is a valid number.
        if(accNo == null) return;

        Data data = new Data();
        // Checks if the account number is in file.
        if (!(data.isInFile(accNo))){
            System.out.printf("Account number %d does not exist.%n", accNo);
            return;
        }

        Customer customer;
        customer = data.getCustomer(accNo);
        System.out.println(customer.toString());
        System.out.println("Please enter in the fields you wish to update, leave blank otherwise.");

        // Updating username.
        boolean check;
        do{
            check = false;
            customer.setUsername(EncryptionDecryption(getUsername(customer.getUsername())));
            if (data.isInFile(customer.getUsername())) {
                System.out.println("Username is in use. Enter again.");
                check = true;
            }
        }while (check);

    }


}