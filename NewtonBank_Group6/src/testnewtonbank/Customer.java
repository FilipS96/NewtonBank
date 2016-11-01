/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testnewtonbank;

import java.util.ArrayList;

/**
 *
 * @author skate
 */
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
