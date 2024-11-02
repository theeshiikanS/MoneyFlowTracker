package coe528.project;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInScreenController {

    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button LoginButton;
    @FXML
    private Label invalidLabel;

    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        User temp = new Customer (usernameText.getText(), passwordText.getText(), " ", " "); 
        if(temp.logIN() instanceof Manager) {           
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ManagerScreen.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            ManagerScreenController controller = fxmlLoader.<ManagerScreenController>getController();
            controller.setManager((Manager)temp.logIN());
            App.setRoot(root);
        } else if(temp.logIN() instanceof Customer) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("CustomerScreen.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            CustomerScreenController controller = fxmlLoader.<CustomerScreenController>getController();
            controller.setCustomer((Customer)temp.logIN());
            App.setRoot(root);
        } else {
            invalidLabel.setText("Invalid Username/Password");
        }
    }
}