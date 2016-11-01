/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testnewtonbank;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author skate
 */
public class FXMLDocumentController implements Initializable {
    
    public static int addHardCodedCostumers = 0;
    @FXML
    public static ObservableList<String> customer = FXCollections.observableArrayList();
    @FXML
    public static BankLogic p = new BankLogic();
    @FXML
    private Label label;
    @FXML
    private Button registerCustomer;
    @FXML
    private Button account;
    @FXML
    private TextField name;
    @FXML
    private TextField ssn;
    @FXML
    private void checkAccount(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("checkAccount.fxml"));        
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        
    }
    
    
    @FXML
        private void registerAccount(ActionEvent event) {
            
            try {
   
         if(ssn.getText().isEmpty() || name.getText().isEmpty()){
        label.setTextFill(Color.web("red"));
        label.setText("Please you need to enter a name");
        }else if(String.valueOf(ssn.getText()).length() != 12){
        label.setText("Please enter a 10 digit social security number with the format YYYYMMDDNNNN.");
        } else if(Integer.parseInt(ssn.getText().substring(0, 4)) > 2016 || Integer.parseInt(ssn.getText().substring(0, 4)) < 1900){
           label.setText("You need to enter a year between 1900 and 2016");
        } else if(Integer.parseInt(ssn.getText().substring(4, 6)) > 12 || Integer.parseInt(ssn.getText().substring(4, 6)) < 1){
           label.setText("Please enter a month between 01-12");
           System.out.println(ssn.getText().substring(4, 6));
        } else{ 
        p.addCustomer(name.getText(), Long.parseLong(ssn.getText()));
        customer.add(name.getText() + " -- " +ssn.getText());
        label.setTextFill(Color.web("green"));
        label.setText("the account has been registered!");
        }
                
            } catch (Exception e) {
         label.setText("Please enter just numbers with the format YYYYMMDDNNNN");
         } 
         
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        if(addHardCodedCostumers < 1){
//        
//        ssn.setPromptText("YYYYMMDDNNNN");
//        name.setPromptText("First and last name");
//        p.addCustomer("Hampus", 199112253519L);
//     
//        p.addCustomer("Joel", 199112245401L);
//        
//        p.addCustomer("Alexiz", 199112253192L);
//        ++addHardCodedCostumers;
//        }
//        customer = FXCollections.observableArrayList(p.getCustomers());
//        
    }    
    
}
