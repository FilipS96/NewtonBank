/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testnewtonbank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import static testnewtonbank.FXMLDocumentController.p;

/**
 *
 * @author skate
 */
public class checkAccountController implements Initializable {

    public static int addHardCodedCostumers = 0;
    private Customer tempCust = new Customer();
    private SavingsAccount tempAccount = new SavingsAccount();

    @FXML
    public static ObservableList<String> customer = FXCollections.observableArrayList();

    @FXML
    private ListView cust;
    @FXML
    private ListView accountView;
    @FXML
    private Label showSsn;
    @FXML
    private Label showName;
    @FXML
    private Label showAccount;
    @FXML
    private Button removeSavingsAcc;
    @FXML
    private Button addSavingsAcc;
    @FXML
    private HBox depositWithdraw;
    @FXML
    private TextField amount;
    @FXML
    public static ObservableList<String> accounts = FXCollections.observableArrayList();
    @FXML
    private Label showNr;
    @FXML
    private Label showBalance;
    @FXML
    private Label showInterest;
    @FXML
    private ObservableList customerList;

    @FXML
    private void addCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }
// CUSTOMER INFORMATION 

    @FXML
    private void seeAccountInfo(MouseEvent event) {

        if (cust.getSelectionModel().getSelectedItem() != null) {
            accountView.setVisible(true);
            addSavingsAcc.setVisible(true);
//            accountView.setItems(null);
            accounts.clear();

            for (Customer c : FXMLDocumentController.p.getCustomerList()) {

                String str = (String) cust.getSelectionModel().getSelectedItem();

                if (str.substring(str.length() - 12, str.length()).equals(String.valueOf(c.getSsn()))) {
                    tempCust = c;
                    showName.setText("Name: " + c.getName());

                    showSsn.setText("Ssn: " + c.getSsn());

                    showAccount.setText("Accounts");
                    for (SavingsAccount sa : c.getNumberOfAccount()) {

                        accounts.add(sa.getAccountNo() + " " + sa.getAccountType());
                    }
                    accountView.setItems(accounts);

                }

            }
        }

    }

    //SAVINGS ACCOUNT INFORMATION
    @FXML
    private void seeSavingsAccount(MouseEvent event) {

        if (cust.getSelectionModel().getSelectedItem() != null) {

            removeSavingsAcc.setVisible(true);
            depositWithdraw.setVisible(true);
            showNr.setVisible(true);
            showBalance.setVisible(true);
            showInterest.setVisible(true);

            for (SavingsAccount a : tempCust.getNumberOfAccount()) {

                String str = (String) cust.getSelectionModel().getSelectedItem();

                if (Integer.parseInt(str.substring(0, 4)) == a.getAccountNo()) {
                    tempAccount = a;
                    showNr.setText("Number: " + a.getAccountNo());

                    showBalance.setText("Balance: " + a.getBalance());

                    showInterest.setText("Interest rate: " + a.getInterestRate());
                }
            }
        }
    }

    @FXML
    private void saveTxt(ActionEvent event) {
        p.writeToTxt();

    }
    
    @FXML
    private void withdraw(ActionEvent event){
        double amount2 = Double.parseDouble(amount.getText());
        p.withdraw(tempCust.getSsn(), 1002, amount2);
    }
    
    @FXML
    private void deposit(ActionEvent event){
        p.writeToTxt();
        
    }

    @FXML
    private void addSavingsAcc(ActionEvent event) {
        p.addSavingsAccount(tempCust.getSsn());

    }

    @FXML
    private void removeCust(ActionEvent event) {
        p.removeCustomer(tempCust.getSsn());
        checkAccountController.customer = FXCollections.observableArrayList(p.getCustomers());
        cust.setItems(customer);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(addHardCodedCostumers);
        if (addHardCodedCostumers == 0) {

            p.addCustomer("Hampus", 199112253519L);
            p.addSavingsAccount(199112253519L);
            p.addSavingsAccount(199112253519L);
            p.addSavingsAccount(199112253519L);
            p.addCustomer("Joel", 199112245401L);
            p.addSavingsAccount(199112245401L);
            p.addSavingsAccount(199112245401L);
            p.addCustomer("Alexiz", 199112253192L);
            p.addSavingsAccount(199112253192L);
            p.addSavingsAccount(199112253192L);
            p.addSavingsAccount(199112253192L);
            p.addSavingsAccount(199112253192L);

            System.out.println(addHardCodedCostumers);

        }
        addHardCodedCostumers++;

        accountView.setVisible(false);
        addSavingsAcc.setVisible(false);
        removeSavingsAcc.setVisible(false);
        depositWithdraw.setVisible(false);
        showNr.setVisible(false);
        showBalance.setVisible(false);
        showInterest.setVisible(false);

        checkAccountController.customer = FXCollections.observableArrayList(p.getCustomers());
        cust.setItems(customer);

    }

}
