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
    public boolean isValidUserName(String username){
        char[] s = username.toCharArray();
        for (char c : s){
            if((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')){
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isValidPin(String s){
        if(s.length() != 5){
            return false;
        }
        for (char c: s.toCharArray()) {
            if(c >= '0' || c <= '9'){
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    // Encryption Method
    // For alphabets we swap A with Z, B with Y and so on.
    // A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    // Z Y X W V U T S R Q P O N M L K J I H G F E D C B A
    // For Number we have
    // 0123456789
    // 9876543210
    public String EncryptionDecryption(String username) {
        String output = "";
        char[] ch = username.toCharArray();
        for (char c : ch) {

            if(c >= 'A' && c <= 'Z'){
                output += (char)('Z' - (c - 'A'));

            } else if(c >= 'a' && c <= 'z'){
                output += (char)('z' - (c - 'a'));

            } else if(c >= '0' && c <= '9'){
                output += 9 - Character.getNumericValue(c);
            }
        }
        return output;
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
        boolean check = true;
        while (check){
            System.out.println("Username: ");
            String un = console.next();
            customer.setUsername(EncryptionDecryption(un));
            // Checks if the username typed is valid (Username can only contain A-Z, a-z & 0-9)
            if (un == "" || !isValidUserName(un)){
                System.out.println("Please enter valid username (Username can only contain A-Z, a-z & 0-9)");
            } else if (data.isInFile(customer.getUsername())) {
                System.out.println("Username is in use, enter again.");
            } else {
                check = false;
            }
        }

        // Checks and set de PIN.
        check = true;
        while (check){
            System.out.println("5 digit PIN: ");
            String pin = console.next();
            customer.setPin(EncryptionDecryption(pin));
            // Checks if the PIN typed is valid (PIN is only 5 digit long and can only contain numbers from 0 to 9)
            if (pin == "" || !isValidPin(pin)){
                System.out.println("Please enter valid PIN (PIN is only 5 digit long and can only contain numbers from 0 to 9)");
            } else {
                check = false;
            }
        }

        // Checks and sets the holder's name.
        do {
            check = true;
            System.out.println("Holder's Name: ");
            String name = console.next().trim();
            if (name.isBlank()){
                System.out.println("Wrong holder's name input, please enter again.");
            }else {
                customer.setName(name);
                check = false;
            }
        } while (check);

        // Checks and sets account type.
        check = true;
        while (check){
            System.out.println("Account type: 1.Savings , 2.Current");
            customer.setAccountType(console.next());
            if (!(customer.getUsername().equals("1") || customer.getUsername().equals("2"))){
                System.out.println("Wrong input. Enter 1.Savings , 2.Current");
            } else {
                check = false;
            }
        }


    }
}