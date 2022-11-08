package DataLayer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import BOLayer.Admin;
import BOLayer.Customer;
import BOLayer.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Data {

    // Appends object to a file in json format
    public <T> void AddToFile(T obj, Boolean appending) {
        String fileBoString = getFileToManage(obj);

        try (FileWriter fWriter = new FileWriter(fileBoString, appending)) {
            String jsonString;
            ObjectMapper mapper = new ObjectMapper();
            jsonString = mapper.writeValueAsString(obj);
            fWriter.write(jsonString + System.lineSeparator());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

//     Clears last data and save new list to file in json format
    public <T> void saveToFile(ArrayList<T> list) throws JsonProcessingException {
        if(list.get(0) instanceof Admin){
            AddToFile(list.get(0), false);
        }
        else if(list.get(0) instanceof Customer){
            AddToFile(list.get(0), false);
        }
        // Appends the other objects of list to the file
        for (int i = 1; i < list.size(); i++) {
            AddToFile(list.get(i), true);
        }
    }

    // Choose the file to operate.
    private <T> String getFileToManage(T obj) {
        // Check what is the right file to write the object
        String fileBoString = "";
        if (obj instanceof Admin) {
            fileBoString = "Admin.txt";
        } else if (obj instanceof Customer) {
            fileBoString = "Customer.txt";
        } else if (obj instanceof Transaction) {
            fileBoString = "Transaction.txt";
        }
        return fileBoString;
    }

    public <T> ArrayList ReadFile(String fileName, Class<T> c) {
        // TODO: How to pass a class as parameter method
        List<String> lines;
        ArrayList<T> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            lines = Files.readAllLines(Paths.get(fileName.concat(".txt")));
            for (String Obj : lines) {
                T Object = mapper.readValue(Obj, c);
                list.add(Object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    // Deletes a customer object from a file.
    public void deleteFromFile(Customer customer) throws JsonProcessingException {
        ArrayList<Customer> list = ReadFile("Customer", Customer.class);
        // Check if the customer is in file and remove
        for (Customer item : list){
            if (item.getAccountNo() == customer.getAccountNo()){
                list.remove(item);
                break;
            }
        }
        // Save the new list the file
        saveToFile(list);
    }

    // Updates a customer object in the file.
    public void updateInFile(Customer customer) throws JsonProcessingException {
        ArrayList<Customer> list = ReadFile("Customer", Customer.class);
        // Check if the customer is in the file and update.
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAccountNo() == customer.getAccountNo()){
                list.set(i, customer);
            }
        }
        // Update de list with the specified object.
        saveToFile(list);
    }

    // Checks if an Admin object is in file.
    public boolean isInFIle(Admin user){
        ArrayList<Admin> list = ReadFile("Admin", Admin.class);
        for (Admin admin : list){
            if(admin.getUsername().equalsIgnoreCase(user.getUsername()) && admin.getPin().equalsIgnoreCase(user.getPin())){
                return true;
            }
        }
        return false;
    }

    // TODO: make only one mothod to check user, pin and account status.
    public boolean isUserActive(String user){
        ArrayList<Customer> list = ReadFile("Customer", Customer.class);
        for (Customer customer : list){
            return customer.getUsername().equalsIgnoreCase(user) && customer.getStatus().equalsIgnoreCase("Open");
        }
        return false;
    }



}