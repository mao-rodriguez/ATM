package LogicLayer;

import BOLayer.Admin;
import BOLayer.Customer;
import DataLayer.Data;
import com.fasterxml.jackson.core.JsonProcessingException;

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
}