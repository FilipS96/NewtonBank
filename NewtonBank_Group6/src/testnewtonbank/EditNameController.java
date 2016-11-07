package testnewtonbank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class EditNameController implements Initializable {

BankLogic p = BankLogic.getInstanceOf();

@FXML
private Label prevName;

@FXML
private Label message;

@FXML
private Label ssn;

@FXML
private TextField newName;

@FXML
private void editName(ActionEvent event) throws IOException {
    if(newName.getText().isEmpty()){
        message.setVisible(true);
        message.setTextFill(Color.web("red"));
        message.setText("Please enter a name");
    } else if(!newName.getText().matches("[a-zA-ZåäöÅÄÖ ]+")){
        message.setVisible(true);
        message.setTextFill(Color.web("red"));
        message.setText("The name can't have any numbers or symbols in it");
    } else if(newName.getText().matches("[a-zA-ZåäöÅÄÖ ]+")){
        String name = newName.getText();
    checkAccountController.getTempCust().setName(name);
    
    Parent root = FXMLLoader.load(getClass().getResource("checkAccount.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    
}


@FXML
private void cancel(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("checkAccount.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
}

    
@Override
public void initialize(URL url, ResourceBundle rb) {
    prevName.setText(prevName.getText() + " " + checkAccountController.getTempCust().getName());
    ssn.setText(ssn.getText() + " " + checkAccountController.getTempCust().getSsn());
}
    
    
}
