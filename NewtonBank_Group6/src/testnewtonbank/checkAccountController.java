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
import java.util.List;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import static testnewtonbank.FXMLDocumentController.p;

/**
 *
 * @author skate
 */
public class checkAccountController implements Initializable {

    @FXML
    BankLogic p = BankLogic.getInstanceOf();
    public static int addHardCodedCostumers = 0;
    private static Customer tempCust = new Customer();
    private SavingsAccount tempAccount = new SavingsAccount();
    private static List<String> closingInfo = new ArrayList();

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
    private Button removeCust;
    @FXML
    private Button removeSavingsAcc;
    @FXML
    private Button addSavingsAcc;
    @FXML
    private Button changeName;
    @FXML
    private HBox depositWithdraw;
    @FXML
    private TextField amount;
    @FXML
    public static ObservableList<String> accounts = FXCollections.observableArrayList();
    @FXML
    private Label showNr;
    @FXML
    private Label depWithLabel;
    @FXML
    private Label showBalance;
    @FXML
    private Label showInterest;
    @FXML
    private Label removedAccountLabel;

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
        depWithLabel.setVisible(false);
        removeSavingsAcc.setVisible(false);
        depositWithdraw.setVisible(false);
        removedAccountLabel.setVisible(false);
        if (cust.getSelectionModel().getSelectedItem() != null) {
            changeName.setVisible(true);
            removeCust.setVisible(true);
            accountView.setVisible(true);
            addSavingsAcc.setVisible(true);
//            accountView.setItems(null);
            accounts.clear();

            for (Customer c : p.getCustomerList()) {

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
        depWithLabel.setVisible(false);
        removedAccountLabel.setVisible(false);
        if (accountView.getSelectionModel().getSelectedItem() != null) {

            removeSavingsAcc.setVisible(true);
            depositWithdraw.setVisible(true);
            showNr.setVisible(true);
            showBalance.setVisible(true);
            showInterest.setVisible(true);

            for (SavingsAccount a : tempCust.getNumberOfAccount()) {

                String str = (String) accountView.getSelectionModel().getSelectedItem();

                if (Integer.parseInt(str.substring(0, 4)) == a.getAccountNo()) {
                    tempAccount = a;
                    System.out.println(str);
                    showNr.setText("Number:\t" + a.getAccountNo());

                    showBalance.setText("Balance:\t" + a.getBalance());

                    showInterest.setText("Interest rate:\t" + a.getInterestRate());
                }
            }
        }
    }

    @FXML
    private void saveTxt(ActionEvent event) {
        p.writeToTxt();

    }

    @FXML
    private void editName(ActionEvent event) throws IOException {
        cust.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(getClass().getResource("editName.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    @FXML
    private void removeSavingAcc(ActionEvent event) {

        removeSavingsAcc.setVisible(false);
        depositWithdraw.setVisible(false);
        removedAccountLabel.setVisible(true);
        showBalance.setVisible(false);
        showInterest.setVisible(false);
        showNr.setVisible(false);
        removedAccountLabel.setText("You removed acc " + String.valueOf(tempAccount.getAccountNo() + "\n"
                + "From customer " + tempCust.getName() + "\n" + "Closing balance : "
                + String.valueOf(tempAccount.getClosingBalance())));

        ArrayList<String> newAccounts = new ArrayList();
        if (cust.getSelectionModel().getSelectedItem() != null) {
            p.closeAccount(tempCust.getSsn(), tempAccount.getAccountNo());

            for (SavingsAccount c : tempCust.getNumberOfAccount()) {
                newAccounts.add(c.getAccountNo() + " " + c.getAccountType());

            }

            checkAccountController.accounts = FXCollections.observableArrayList(newAccounts);
            accountView.setItems(accounts);
        }
    }

    @FXML
    private void withdraw(ActionEvent event) {
        depWithLabel.setVisible(false);
        boolean b = false;
        if (amount.getText().matches("[-+]?[0-9]*.?[0-9]+") && !amount.getText().substring(0, 1).equals("-")) {
            double amount2 = Double.parseDouble(amount.getText());
            b = p.withdraw(tempCust.getSsn(), tempAccount.getAccountNo(), amount2);
            if (b) {
                depWithLabel.setVisible(true);
                depWithLabel.setText("Withdrawal succeeded");
                depWithLabel.setTextFill(Color.web("green"));
            } else {
                depWithLabel.setVisible(true);
                depWithLabel.setText("Your balance is too low");
                depWithLabel.setTextFill(Color.web("red"));

            }
        } else {
            depWithLabel.setVisible(true);
            depWithLabel.setTextFill(Color.web("red"));
            depWithLabel.setText("Please enter a valid number");
        }

        System.out.println(tempAccount.getBalance());
        showBalance.setText("Balance:\t" + String.valueOf(tempAccount.getBalance()));
        amount.clear();
    }

    @FXML
    private void deposit(ActionEvent event) {
        depWithLabel.setVisible(false);
        boolean b = false;
        if (amount.getText().matches("[-+]?[0-9]*.?[0-9]+")) {
            double amount2 = Double.parseDouble(amount.getText());
            b = p.deposit(tempCust.getSsn(), tempAccount.getAccountNo(), amount2);
        }

        if (!b) {
            depWithLabel.setVisible(true);
            depWithLabel.setTextFill(Color.web("red"));
            depWithLabel.setText("Please enter a valid number");
        } else {

            System.out.println(tempAccount.getAccountNo());
            System.out.println(tempAccount.getBalance());
            showBalance.setText("Balance:\t" + String.valueOf(tempAccount.getBalance()));
            depWithLabel.setVisible(true);
            depWithLabel.setTextFill(Color.web("green"));
            depWithLabel.setText("Deposit succeeded");
        }
        /*
        if (!amount.getText().matches("[-+]?[0-9]*.?[0-9]+")) {
            depWithLabel.setVisible(true);
            depWithLabel.setTextFill(Color.web("red"));
            depWithLabel.setText("Please enter a valid number");
        } else {
            double amount2 = Double.parseDouble(amount.getText());
            System.out.println(tempAccount.getAccountNo());
            p.deposit(tempCust.getSsn(), tempAccount.getAccountNo(), amount2);
            System.out.println(tempAccount.getBalance());
            showBalance.setText("Balance:\t" + String.valueOf(tempAccount.getBalance()));
            depWithLabel.setVisible(true);
            depWithLabel.setTextFill(Color.web("green"));
            depWithLabel.setText("Deposit succeeded");
        }
         */
        amount.clear();

    }

    @FXML
    private void addSavingsAcc(ActionEvent event) {
        removeSavingsAcc.setVisible(false);
        depositWithdraw.setVisible(false);
        removedAccountLabel.setVisible(false);
        removeSavingsAcc.setVisible(false);
        p.addSavingsAccount(tempCust.getSsn());

        ArrayList<String> newAccounts = new ArrayList();
        for (SavingsAccount sa : tempCust.getNumberOfAccount()) {
            newAccounts.add(sa.getAccountNo() + " " + sa.getAccountType());
        }
        checkAccountController.accounts = FXCollections.observableArrayList(newAccounts);
        accountView.setItems(accounts);
        //accountView.setSelectionModel();

    }

    @FXML
    private void removeCust(ActionEvent event) throws IOException {
        closingInfo = p.removeCustomer(tempCust.getSsn());
        checkAccountController.customer = FXCollections.observableArrayList(p.getCustomers());
        cust.setItems(customer);

        Parent root = FXMLLoader.load(getClass().getResource("taBortKundInfo.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    public static Customer getTempCust() {
        return tempCust;
    }

    public static List<String> getClosingInfo() {
        return closingInfo;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(addHardCodedCostumers);
        if (addHardCodedCostumers == 0) {

            p.addCustomer("Hampus Karlsson", 199112253519L);
            p.addSavingsAccount(199112253519L);
            p.addSavingsAccount(199112253519L);
            p.addSavingsAccount(199112253519L);
            p.addCustomer("Joel Nilsson", 199112245401L);
            p.addSavingsAccount(199112245401L);
            p.addSavingsAccount(199112245401L);
            p.addCustomer("Alexiz Knöös", 199112253192L);
            p.addSavingsAccount(199112253192L);
            p.addSavingsAccount(199112253192L);
            p.addSavingsAccount(199112253192L);
            p.addSavingsAccount(199112253192L);
            p.addCustomer("Tollo Nanthanonsa", 190112031234L);
            p.addSavingsAccount(190112031234L);
            p.addSavingsAccount(190112031234L);
            p.addCustomer("Filip Stojanivski", 200001019876L);
            p.addSavingsAccount(200001019876L);
            p.addSavingsAccount(200001019876L);
            p.addCustomer("Masoud Haya", 193704216543L);
            p.addSavingsAccount(193704216543L);
            p.addSavingsAccount(193704216543L);

            System.out.println(addHardCodedCostumers);

        }
        addHardCodedCostumers++;
        changeName.setVisible(false);
        removedAccountLabel.setVisible(false);
        removeCust.setVisible(false);
        depWithLabel.setVisible(false);
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
