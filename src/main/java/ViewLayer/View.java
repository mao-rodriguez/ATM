package ViewLayer;

import BOLayer.Admin;
import BOLayer.Customer;
import LogicLayer.Logic;

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
                Scanner console = new Scanner(System.in);
                Customer customer = new Customer();
                boolean isSigned = true;
                int attempt = 0;
                while (attempt <= 3){



                }
        }

    }
}
