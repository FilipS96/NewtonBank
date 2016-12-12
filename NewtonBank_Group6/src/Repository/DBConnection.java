/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import testnewtonbank.Customer;
import testnewtonbank.SavingsAccount;

/**
 *
 * @author Joel
 */
public class DBConnection {

    Connection connection;
    Statement st;
    String url = "jdbc:mysql://127.0.0.1:3306/bankDatabase?useSSL=false&user=root&password=root";
    String query1 = "SELECT * FROM Customers";
    String query2 = "INSERT INTO `bankDatabase`.`customers` (`ssn`, `name`) VALUES (?, ?);";
    String query3 = "SELECT accountNr, balance FROM SavingsAccounts WHERE customer_ssn = ?;";
    String query4 = "INSERT INTO `bankDatabase`.`savingsaccounts` (`accountNr`,`balance`,`customer_ssn`, `accounttypes_typeId`) VALUES (?, ?, ?, ?);";
    String query5 = "SELECT MAX(accountNr) FROM savingsaccounts;";
    String query6 = "DELETE FROM customers WHERE ssn = ?";
    String query7 = "UPDATE `bankDatabase`.`SavingsAccounts` SET `Balance`=? WHERE `accountNr`=?;";
    String query8 = "UPDATE Customers SET name=? WHERE ssn=?;";
    String query9 = "DELETE FROM savingsaccounts WHERE accountNr = ?";
    
    public DBConnection() {
        try {
            connection = (Connection) DriverManager.getConnection(url);
            st = (Statement) connection.createStatement();
        } catch (SQLException ex) {
            System.out.println("Fel med koppling till databas " + ex.getMessage());
        }
    }

    public ArrayList<Customer> getCustomerList() {
        ArrayList<Customer> customerList = new ArrayList();
        try {
            ResultSet result = st.executeQuery(query1);
            while (result.next()) {
                String name = result.getString(2);
                Long ssn = result.getLong(1);
                Customer c = new Customer(name, ssn);
                customerList.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
        return customerList;
    }

    public int getLatestAccountNo() {
        int latestAccountNo = 0;
        try {
            ResultSet result = st.executeQuery(query5);
            while (result.next()) {
                latestAccountNo = result.getInt(1);

            }
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
        return latestAccountNo;
    }

    public void addCustomer(String name, Long ssn) {
        String ssnConverted = String.valueOf(ssn);
        try {
            PreparedStatement ps = connection.prepareStatement(query2);
            ps.setString(1, ssnConverted);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
    }

    public void setName(Long ssn, String name) {
        String ssnConverted = String.valueOf(ssn);
        try {
            PreparedStatement ps = connection.prepareStatement(query8);
            ps.setString(1, name);
            ps.setString(2, ssnConverted);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
    }
    
    public void removeCustomer(long ssn){
        String ssnConverted = String.valueOf(ssn);
        try {
            PreparedStatement ps = connection.prepareStatement(query6);
            ps.setString(1, ssnConverted);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
    }
    
    public void removeAccount(int accountNo){
        
        try {
            PreparedStatement ps = connection.prepareStatement(query9);
            ps.setInt(1, accountNo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
    }
    
    public void addSavingsAccount(int accountNo, double balance, Long ssn, int accountTypeId) {
        String ssnConverted = String.valueOf(ssn);
        try {
            PreparedStatement ps = connection.prepareStatement(query4);
            ps.setInt(1, accountNo);
            ps.setDouble(2, balance);
            ps.setString(3, ssnConverted);
            ps.setInt(4, accountTypeId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
    }

    public ArrayList<SavingsAccount> getNumberOfAccount(Long ssn) {
        String ssnConverted = String.valueOf(ssn);
        ArrayList<SavingsAccount> accountList = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement(query3);
            ps.setString(1, ssnConverted);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int accountNo = result.getInt(1);
                double balance = result.getDouble(2);
                SavingsAccount a = new SavingsAccount();
                a.setAccountNo(accountNo);
                a.setBalance(balance);
                accountList.add(a);
            }
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
        return accountList;
    }

    public void deposit(int accountNo, double amount) {
        try {
            PreparedStatement ps = connection.prepareStatement(query7);
            ps.setDouble(1, getBalance(accountNo) + amount);
            ps.setInt(2, accountNo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
    }

    public void withdraw(int accountNo, double amount) {
        try {
            PreparedStatement ps = connection.prepareStatement(query7);
            ps.setDouble(1, getBalance(accountNo) - amount);
            ps.setInt(2, accountNo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
    }

    public double getBalance(int accountNo) {
        double balance = 0;
        try {
            ResultSet result = st.executeQuery("SELECT balance FROM savingsaccounts WHERE accountNr = " + accountNo + ";");
            while (result.next()) {
                balance = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Fel i sql-satsen " + ex.getMessage());
        }
        return balance;
    }

    public void closeConn() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }

}
