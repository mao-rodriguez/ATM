package ViewLayer;

import BOLayer.Admin;
import BOLayer.Customer;
import LogicLayer.Logic;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class View {
    public void loginScreen() {
        System.out.printf("\"-----Welcome to ATM-----%n%n" +
                "Login as:%n" +
                "1----Administrator%n" +
                "2----Customer%n");
        Logic logic = new Logic();
        int user = logic.getValidNumber("Enter 1 or 2: ");
        if(user != 1 || user != 2){
            System.out.println("Invalid input user");
            return;
        }
        switch (user){
            case 1: {
                System.out.printf("-----Administrator Login-----%n" + "Please Enter your username & 5-digit Pin");
                // Declaring an Admin object
                Scanner console = new Scanner(System.in);
                Admin admin = new Admin();
                boolean isSigned = true;
                while (isSigned) {
                    // Reading and storing username
                    System.out.println("Username: ");
                    admin.setUsername(console.next());
                    // Applying encryption
                    admin.setUsername(logic.EncryptionDecryption(admin.getUsername()));

                    // Reading and storing pin.
                    System.out.println("PIN: ");
                    admin.setPin(console.next());
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
                break;

            // Case 2 for Customer Login
            case 2:
                System.out.printf("-----Customer Login-----%n" + "Please Enter your username & 5-digit Pin");
                // Declaring a customer object
                Customer customer = new Customer();
                Scanner console = new Scanner(System.in);

                // Reading and storing username and PIN.
                System.out.print("Username: ");
                customer.setUsername(console.next());
                System.out.print("PIN: ");
                customer.setPin(console.next());
                // Doing encryption
                customer.setUsername(logic.EncryptionDecryption(customer.getUsername()));
                customer.setPin(logic.EncryptionDecryption(customer.getPin()));

                if(!logic.isValidUserName(customer.getUsername())){
                    System.out.println("Invalid Username input. Enter again");
                    return;
                }

                boolean isSigned = true;
                int attempt = 0;
                while (attempt < 3){
                    if(logic.canLogin(customer)){
                        System.out.println("--- Logged in as Customer ---");
                        customerScreen(customer.getUsername());
                        break;
                    }
                    if(!logic.canLogin(customer)){
                        System.out.println("Invalid Username/PIN or user is disabled");
                        attempt++;
                    }
                    if(attempt == 3){
                        logic.disableAccount(customer.getUsername());
                        System.out.println("Wrong input 3 times. Account is disabled!");
                    }
                }
        }

    }

    public void adminScreen(){
        clearConsole();

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
