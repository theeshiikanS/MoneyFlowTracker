package coe528.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ManagerScreenController implements Initializable {
    @FXML
    private TextField cusUser;
    @FXML
    private TextField cusPass;
    @FXML
    private Button logout;
    @FXML
    private Button create;
    
    private Manager m;
    private ArrayList<String> temp;
    
    @FXML
    private Button byeCustomer;
    @FXML
    private ComboBox<String> deleteDropDown;
    @FXML
    private Label incorrect;
    @FXML
    private Label label;
    @FXML
    private Label successDelete;
    @FXML
    private Label unableDelete;
    
    public void setManager(Manager manager) {
        m = manager;
    }
    public void setTemp() throws FileNotFoundException {
        temp = Manager.customerNames();
    }
    
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    private void comboBox() {
        deleteDropDown.getItems().clear();
        try {
            setTemp();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        deleteDropDown.getItems().addAll(temp);
    }
    
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        comboBox();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        App.setRoot("logInScreen");
    }

    @FXML
    private void AddCustomer() throws IOException {
        if ((cusUser.getText() == null) || cusUser.getText().trim().isEmpty() || cusPass.getText() == null || cusPass.getText().trim().isEmpty()) {
            label.setText("");
            incorrect.setText("Please enter a valid username/password!");
        } else {   
            if(cusUser.getText().matches("^[a-zA-z0-9]*$") && cusPass.getText().matches("^[a-zA-z0-9]*$")) {
                if(m.createCustomer(cusUser.getText(), cusPass.getText())) {
                    label.setText("Customer Successfully Added!");
                    incorrect.setText("");
                    cusUser.setText("");
                    cusPass.setText("");
                    comboBox();
                }
                } else {
                    label.setText("");
                    incorrect.setText("Please enter a valid username/password!");
                }
        }
    }

    @FXML
    private void deleteCustomer(ActionEvent event) throws IOException {
        if(Manager.getManager().deleteCustomer(deleteDropDown.getValue())) {
            unableDelete.setText("Customer Deleted!");
            label.setText("Only use Letters or Numbers");
            successDelete.setText("");
            incorrect.setText("");
            comboBox();
        } else {
            successDelete.setText("");
            unableDelete.setText("Sorry unable to delete!");
            label.setText("Only use Letters or Numbers");
            incorrect.setText("");
            comboBox();
        }
    }
}