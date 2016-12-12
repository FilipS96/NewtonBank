package testnewtonbank;

import Repository.DBConnection;
import java.util.ArrayList;

public class Customer {
    private String name;
    private Long ssn;
    
    private ArrayList <SavingsAccount> numberOfAccount = new ArrayList();
    
    public Customer(){
    }
    
    public Customer(String name,Long ssn){
    setName(name);
    setSsn(ssn);
    }

    public ArrayList<SavingsAccount> getNumberOfAccount() {
        //numberOfAccount = conn.getNumberOfAccount(ssn);
        return numberOfAccount;
    }

    public void setNumberOfAccount(ArrayList<SavingsAccount> numberOfAccount) {
        this.numberOfAccount = numberOfAccount;
    }
    
    public Long getSsn() {
        return ssn;
    }

    public void setSsn(Long ssn) {
        this.ssn = ssn;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    
}
