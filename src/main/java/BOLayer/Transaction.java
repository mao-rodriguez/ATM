package BOLayer;

public class Transaction {
    private int AccountNo;
    private String HoldersName;
    private String TransactionType;
    private int TransactionAmount;
    private String Date;
    private int Balance;

    public int getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(int accountNo) {
        AccountNo = accountNo;
    }

    public String getHoldersName() {
        return HoldersName;
    }

    public void setHoldersName(String holdersName) {
        HoldersName = holdersName;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public int getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        TransactionAmount = transactionAmount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }
}
