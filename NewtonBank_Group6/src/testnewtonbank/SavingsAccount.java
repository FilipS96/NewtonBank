package testnewtonbank;

import Repository.DBConnection;
import static testnewtonbank.BankLogic.getInstanceOf;

public class SavingsAccount {
    private double balance;
    private double interestRate;
    private String accountType;
    private final int accountTypeId = 1;
    private int accountNo;
    BankLogic p = getInstanceOf();


    public SavingsAccount() { //Ingenting behövs tas emot när man skapar ett konto. Man får använda deposit för att sätta in pengar.
        balance = 0;
        interestRate = 1;
        accountType = "Savings account";
        
    }

    public double getBalance() {
        //balance = conn.getBalance(accountNo);
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public int increaseCounter(){
        accountNo = p.getLatestAccountNo() + 1;
        return accountNo;
    }
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interest) {
        this.interestRate = interest;
    }
    public double getClosingBalance() {
        double closingBalance = balance * (1 + (interestRate / 100));
        return closingBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }
    
    
    @Override
    public String toString(){
        return("Account no: " + accountNo + "\tBalance: " + balance + "\tAccount type: " + accountType + "\tInterest rate: " + interestRate);
    }
}

