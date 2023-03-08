package BOLayer;

import java.io.Serializable;

public class Customer implements Serializable {
    private String Username;
    private String Name;
    private String AccountType;
    private int Balance;
    private String Status;
    private int AccountNo;
    private String Pin;

    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getAccountType() {
        return AccountType;
    }
    public void setAccountType(String accountType) {
        AccountType = accountType;
    }
    public int getBalance() {
        return Balance;
    }
    public void setBalance(int balance) {
        Balance = balance;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        Status = status;
    }
    public int getAccountNo() {
        return AccountNo;
    }
    public void setAccountNo(int accountNo) {
        AccountNo = accountNo;
    }
    public String getPin() {
        return Pin;
    }
    public void setPin(String pin) {
        Pin = pin;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Username='" + Username + '\'' +
                ", Name='" + Name + '\'' +
                ", AccountType='" + AccountType + '\'' +
                ", Status='" + Status + '\'' +
                ", AccountNo=" + AccountNo +
                '}';
    }
}