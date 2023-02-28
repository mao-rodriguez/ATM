package ATM;

import BOLayer.Admin;
import BOLayer.Customer;
import DataLayer.Data;
import LogicLayer.Logic;
import ViewLayer.View;

public class App
{
    public static void main( String[] args )
    {
        // Code Used to write an admin to the file

//        Logic logic = new Logic();
//        Admin admin = new Admin(logic.EncryptionDecryption("maorodriguez"), logic.EncryptionDecryption("123456"));
//        Data data = new Data();
//        data.AddToFile(admin, true);

        View view = new View();
        view.loginScreen();
    }
}
