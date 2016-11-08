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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    @FXML
    private Font x1;
    
    @FXML
    private TextArea closedAccounts;
    
    @FXML
    private Label youHaveDeleted;

    @FXML
    private void goBack(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("checkAccount.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        youHaveDeleted.setText("You removed customer: " + checkAccountController.getTempCust().getName() + "\nSocial security no. " + checkAccountController.getTempCust().getSsn());
        for(String s : checkAccountController.getClosingInfo()){
            closedAccounts.appendText(s);
        }
    }    
    
}
