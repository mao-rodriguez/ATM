package ViewLayer;

import BOLayer.Admin;
import BOLayer.Customer;
import LogicLayer.Logic;

import java.io.IOException;
import java.util.Scanner;

public class View {
    public void loginScreen() {
        System.out.printf("\"-----Welcome to ATM-----%n%n" +
                "Login as:%n" +
                "1----Administrator%n" +
                "2----Customer%n");
        Logic logic = new Logic();
        int user = logic.getValidNumber("Enter 1 or 2: ");
        if(user < 1 || user > 2){
            System.out.println("Invalid input user");
            return;
        }
        switch (user) {
            case 1 -> {
                System.out.printf("-----Administrator Login-----%n" + "Please Enter your username & 5-digit Pin%n");
                // Declaring an Admin object
                Scanner console = new Scanner(System.in);
                Admin admin = new Admin();
                boolean isSigned = true;
                while (isSigned) {
                    // Reading and storing username
                    System.out.print("Username: ");
                    String username = console.nextLine();
                    admin.setUsername(username);
                    // Applying encryption
                    admin.setUsername(logic.EncryptionDecryption(admin.getUsername()));

                    // Reading and storing pin.
                    System.out.print("PIN: ");
                    String pin = console.nextLine();
                    admin.setPin(pin);
                    // Applying encryption
                    admin.setPin(logic.EncryptionDecryption(admin.getPin()));

                    // Verify login details
                    if (logic.verifyLogin(admin)) {
                        System.out.println("---- Logged in as an administrator ----");
                        isSigned = false;
                    } else {
                        System.out.println("---- Wrong username/PIN. Try again. ----");
                    }
                }
                // As successfully signedin, displaying admin screen
                adminScreen();
            }


            // Case 2 for Customer Login
            case 2 -> {
                System.out.printf("-----Customer Login-----%n" + "Please Enter your username & 5-digit Pin%n");
                // Declaring a customer object
                Customer customer = new Customer();
                Scanner console = new Scanner(System.in);


                int attempt = 0;
                while (true) {
                    if (attempt >= 3) break;
                    // Reading and storing username and PIN.
                    System.out.print("Username: ");
                    String username = console.nextLine();
                    customer.setUsername(username);
                    System.out.print("PIN: ");
                    String pin = console.nextLine();
                    customer.setPin(pin);
                    // Doing encryption
                    customer.setUsername(logic.EncryptionDecryption(customer.getUsername()));
                    customer.setPin(logic.EncryptionDecryption(customer.getPin()));
                    if (!logic.isValidUserName(customer.getUsername())) {
                        System.out.println("Invalid Username input. Enter again");
                        return;
                    }
                    if (logic.canLogin(customer)) {
                        System.out.println("--- Logged in as Customer ---");
                        customerScreen(customer.getUsername());
                        break;
                    }
                    if (!logic.canLogin(customer)) {
                        System.out.println("Invalid Username/PIN or user is disabled");
                        attempt++;
                    }
                    if (attempt == 3) {
                        logic.disableAccount(customer.getUsername());
                        System.out.println("Wrong input 3 times. Account is disabled!");
                    }
                }
            }
        }
    }

    public void adminScreen(){
        boolean validInput;
        int option;
        Logic logic = new Logic();
        do{
            validInput = true;
            clearConsole();
            System.out.println("-----Admin Menu-----");
            System.out.println("""
                    1----Create New Account
                    2----Delete Existing Account
                    3----Update Account Information
                    4----Search for Account
                    5----Exit""");
            option = logic.getValidNumber("Enter option: ");
            if(option < 1 || option > 5){
                System.out.println("Invalid input. Try again.");
                validInput = false;
            }
        }while (!validInput);

        boolean continuing = true;
        do{
            switch (option){
                case 1:
                    logic.createAccount();
                    break;
                case 2:
                    logic.deleteAccount();
                    break;
                case 3:
                    logic.updateAccount();
                    break;
                case 4:
                    logic.searchAccount();
                    break;
                case 5:
                    // Terminate the JVM with a status code of 0
                    System.exit(0);
            }
            System.out.print("Do you want to continue(Y/N): ");
            Scanner console = new Scanner(System.in);
            String check = console.nextLine();
            if(check.equalsIgnoreCase("N")){
                continuing = false;
            }
        }while (continuing);
        System.exit(0);
    }

    public void customerScreen(String username){
        boolean validInput;
        int option;
        Logic logic = new Logic();
        do{
            validInput = true;
            clearConsole();
            System.out.println("-----Customer Menu-----");
            System.out.println("""
                    1----Withdraw Cash
                    2----Cash Transfer
                    3----Deposit Cash
                    4----Display Balance
                    5----Exit""");
            option = logic.getValidNumber("Enter option: ");
            if(option < 1 || option > 5){
                System.out.println("Invalid input. Try again.");
                validInput = false;
            }
        }while (!validInput);

        boolean continuing = true;
        do{
            switch (option){
                case 1:
                    logic.cashWithdraw(username);
                    break;
                case 2:
                    logic.cashTransfer(username);
                    break;
                case 3:
                    logic.cashDeposit(username);
                    break;
                case 4:
                    logic.displayBalance(username);
                    break;
                case 5:
                    // Terminate the JVM with a status code of 0
                    System.exit(0);
            }
            System.out.println("Do you want to continue(Y/N): ");
            Scanner console = new Scanner(System.in);
            String check = console.nextLine();
            if(check.equalsIgnoreCase("N")){
                continuing = false;
            }
        }while (continuing);
        System.exit(0);
    }

    private static void clearConsole() {
        try {
            if(System.getProperty("os.name").contains("Windows")){
                // Clear console in Windows.
                new ProcessBuilder("cmd","/c", "cls").inheritIO().start().waitFor();
            } else {
                // Clear console in linux, Unix, Mac os.
                System.out.println("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException  e) {
            throw new RuntimeException(e);
        }
    }
}
