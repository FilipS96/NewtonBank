
package testnewtonbank;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class BankLogic {

    private static BankLogic p = null;
    private ArrayList<Customer> customerList = new ArrayList();
    private Customer d;

    public static BankLogic getInstanceOf() {
        if (p == null) {
            p = new BankLogic();
        }
        return p;
    }

    private BankLogic() {

    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    public boolean addCustomer(String name, Long ssn) {

        boolean flag = true;
        for (Customer c : customerList) {

            if (ssn.equals(c.getSsn())) {

                flag = false;
            }
        }

        if (flag) {
            customerList.add(new Customer(name, ssn));
        }

        return flag;

    }

    public List<String> getCostumer(long ssn) {

        List<String> cust = new ArrayList<>();
        for (Customer c : customerList) {
            if (c.getSsn() == ssn) {
                cust.add(c.getName() + " " + c.getSsn());
                for (SavingsAccount sa : c.getNumberOfAccount()) {
                    cust.add(sa.getAccountNo() + " " + sa.getAccountType() + " " + sa.getBalance());
                }
            }
        }

        return cust;
    }

    public int addSavingsAccount(long ssn) {
        boolean flag = false;
        SavingsAccount sa = new SavingsAccount();
        for (Customer c : customerList) {
            if (c.getSsn().equals(ssn)) {
                c.getNumberOfAccount().add(sa);
                flag = true;
            }
        }
        if (flag == false) {
            return -1;
        } else {
            return sa.getAccountNo();
        }
    }

    public boolean deposit(Long ssn, int accountNo, double amount) {
        boolean flag = false;
        if (amount > 0) {
            for (Customer c : customerList) {
                if (c.getSsn() == ssn) {
                    for (SavingsAccount sa : c.getNumberOfAccount()) {
                        if (sa.getAccountNo() == accountNo) {
                            sa.setBalance(sa.getBalance() + amount);
                            flag = true;
                        }

                    }
                }
            }
        }

        return flag;
    }

    public List<String> removeCustomer(long ssn) {
        List<String> rm = new ArrayList();
        Customer temp = new Customer();
        for (Customer c : customerList) {
            if (c.getSsn().equals(ssn)) {
                temp = c;
                for (SavingsAccount sa : c.getNumberOfAccount()) {
                    rm.add(sa.getAccountNo() + " " + sa.getAccountType() + " Balance: " + sa.getClosingBalance() + " of which " + (sa.getClosingBalance() - sa.getBalance()) + " is interest.\n\n");
                }
                rm.add("\n");

            }

        }
        customerList.remove(temp);

        return rm;

    }

    public boolean changeCustomerName(String name, long ssn) {

        for (Customer c : customerList) {
            if (c.getSsn() == ssn) {
                c.setName(name);
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public String getAccount(long ssn, int accountNo) {
        ArrayList<SavingsAccount> ac;
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getSsn() == ssn) {
                ac = customerList.get(i).getNumberOfAccount();
                for (SavingsAccount account : ac) {
                    if (account.getAccountNo() == accountNo) {
                        return account.toString();
                    }
                }
            }
        }
        return null;
    }

    //----------------------------------------------------------------------
// Beskrivning: gör ett uttag på kontonummer med accountNo som
// tillhör kunden ssn
// Inparametrar: long ssn, int accountNo, double amount
// Returvärde: true om insättningen lyckades annars false
//----------------------------------------------------------------------
    public boolean withdraw(long ssn, int accountNo, double amount) {
        boolean flag = false;
        if (amount > 0) {
            for (Customer customer : customerList) {
                if (ssn == customer.getSsn()) { //Hämtar kunden person nr.           
                    for (SavingsAccount account : customer.getNumberOfAccount()) {
                        if (accountNo == account.getAccountNo()) {
                            if (amount <= account.getBalance()) {
                                account.setBalance(account.getBalance() - amount);
                                flag = true;
                            }
                        }
                    }
                }
            }
        }

        return flag;
    }

//----------------------------------------------------------------------
// Beskrivning: stänger konto accountNo
// Inparametrar: long ssn, int accountNo - personnummer och 
// kontonummer kontrolleras
// Returvärde: String - returnerar saldo och ränta
//----------------------------------------------------------------------
    public String closeAccount(long ssn, int accountNo) { //stänger ett konto och returnerar saldo och ränta

        boolean flag = false;
        SavingsAccount s = new SavingsAccount();
        Customer ce = new Customer();
        for (Customer c : customerList) {
            if (ssn == c.getSsn()) {
                for (SavingsAccount sa : c.getNumberOfAccount()) {
                    if (accountNo == sa.getAccountNo()) {
                        s = sa;
                        ce = c;
                        flag = true;
                    }
                }
            }
        }
        if (flag) {
            String result = "" + s.getClosingBalance();
            ce.getNumberOfAccount().remove(s);
            return "Balance including interest: " + result;
        } else {
            return "Couldn't delete account.";
        }
    }

    public List<String> getCustomers() {
        List<String> customerInfo = new ArrayList();
        for (Customer c : customerList) {
            customerInfo.add(c.getName() + " -- " + c.getSsn());
        }
        return customerInfo;
    }

    public String writeToTxt() {
        String content = "";
        for (String s : getCustomers()) {                //loopar genom listan
            content += s + "\n";
        }

        try {
            File file = new File("./filename.txt");

            // Om fil inte finns, så skapas den.
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            }

            return "Customer list saved to file";

        } catch (IOException e) {
            return "Error saving to file";
        }
    }

}
