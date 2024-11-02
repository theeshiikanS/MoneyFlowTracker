/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this
license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this
template
 */
package coe528.project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author theesikaravinthan
 */
public class CustomerScreenController implements Initializable {
    @FXML
    private Button enterOp;
    @FXML
    private ComboBox<String> option;
    @FXML
    private TextField amountNum;
    @FXML
    private Button logOut;

    private Customer c = null;

    @FXML
    private Text balance1;
    @FXML
    private Text cusName;
    @FXML
    private Text level;
    @FXML
    private ListView<String> transactionList;
    
    private ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    private Label amountLabel;
    @FXML
    private Label withdrawLabel;
    @FXML
    private Label validLabel;
    @FXML
    private Label onlineLabel;

    public void setCustomer(Customer customer) throws FileNotFoundException {
        c = customer;
        printBalance();
        printName();
        updateLevel();
        updateTransactionList();
    }
 /**
 * Initializes the controller class.
 * @param url
 * @param rb
 * @throws java.io.FileNotFoundException
 */
    @Override
    public void initialize (URL url, ResourceBundle rb){
        option.getItems().addAll("withdraw", "deposit", "online purchase");
      //  items = 
    }
    
    private void updateTransactionList() throws FileNotFoundException {
        File history = new File("src\\main\\java\\transactionHistories\\" + c.getUsername() + "History.txt");
        if(history.exists()){
            Scanner sc = new Scanner(history);
            while (sc.hasNextLine()) {
                items.add(sc.nextLine());
            }
            sc.close();
        }
        transactionList.setItems(items);
    }
    
    private void updateTransactionList(String amount) throws FileNotFoundException {
        items.add(amount);
        transactionList.setItems(items);
    }

    private void printBalance() throws FileNotFoundException {
        balance1.setText(Customer.getCustomer().checkBalance(c.getUsername()));
    }

    private void printName() throws FileNotFoundException {
        cusName.setText(c.getUsername());
    }

    private void updateLevel() throws FileNotFoundException {
        c.setLevel();
        Level l = c.getLevel();
        l.setTheLevel(c);
        level.setText(Customer.getCustomer().getLevel().toString());
    }

    @FXML
    private void enterOption(ActionEvent event) throws FileNotFoundException, IOException {
        String temp = option.getValue();
        onlineLabel.setText("");
        amountLabel.setText("");
        withdrawLabel.setText("");
        validLabel.setText("");
        
        if((amountNum.getText() == null || amountNum.getText().trim().isEmpty()) && option.getValue() == null) {
            validLabel.setText("Please don't leave it blank!");
            return;
        } else if (amountNum.getText() == null || amountNum.getText().trim().isEmpty()) {
            validLabel.setText("Please only enter a valid Amount!");
            return;
        } else if (option.getValue() == null) {
            validLabel.setText("Please only select a valid Option!");
            return;
        }
        
        if(amountNum.getText().matches("^[0-9]*$")) {
            if (temp.equals("withdraw")) {
                withdraw();
            } else if (temp.equals("deposit")) {
                deposit();
            } else if (temp.equals("online purchase")) {
                onlinePurchase();
            }
        } else {
            validLabel.setText("Please only enter a valid Amount!");
        }
        amountNum.setText("");
    }

    public void withdraw() throws FileNotFoundException, IOException {
        String s = Customer.getCustomer().checkBalance(c.getUsername());
        double d = Customer.getCustomer().stringToDouble(s);
        double d1 = Customer.getCustomer().stringToDouble(amountNum.getText());
        System.out.println(d);
        System.out.println(d1);
        
        if (d1 > 0 && d1 <= d) {
            updateTransactionList("-$" + amountNum.getText());
            Customer.getCustomer().withdrawMoney(c, d, d1);
            printBalance();
            updateLevel();
        } else if (d1 < 0) {
            amountLabel.setText("Amount must be greater than 0!");
        } else if (d1 > d) {
            withdrawLabel.setText("Amount can't be greater than Balance!");
        } else if (amountNum.getText().length() == 0) {
            validLabel.setText("Please enter an Amount!");
        } else {
            validLabel.setText("Please only enter a valid Amount!");
        }
    }

    public void deposit() throws FileNotFoundException, IOException {
        String s = Customer.getCustomer().checkBalance(c.getUsername());
        double d = Customer.getCustomer().stringToDouble(s);
        double d1 = Customer.getCustomer().stringToDouble(amountNum.getText());
        if (d1 > 0) {
            Customer.getCustomer().depositMoney(c, d, d1);
            updateTransactionList("+$" + amountNum.getText());
            printBalance();
            updateLevel();
        } else if (d1 < 0) {
            amountLabel.setText("Amount must be greater than 0!");
        } else {
            validLabel.setText("Please only enter a valid Amount!");
        }
    }

    public void onlinePurchase() throws FileNotFoundException, IOException {
        String s = Customer.getCustomer().checkBalance(c.getUsername());
        double accountBalance = Customer.getCustomer().stringToDouble(s);
        double transactionAmount = Customer.getCustomer().stringToDouble(amountNum.getText());

        if (transactionAmount >= 50) {
            if (Customer.getLevel() instanceof Silver) {
                if(transactionAmount+20 < accountBalance) {
                    makePurchase();
                } else {
                    withdrawLabel.setText("Amount can't be greater than Balance!");
                }
            } else if (Customer.getLevel() instanceof Gold) {
                if(transactionAmount+10 < accountBalance) {
                    makePurchase();
                } else {
                    withdrawLabel.setText("Amount can't be greater than Balance!");
                }               
            } else if (Customer.getLevel() instanceof Platinum) {
                if(transactionAmount < accountBalance) {
                    makePurchase();
                } else {
                    withdrawLabel.setText("Amount can't be greater than Balance!");
                }
            }                
        } else if (transactionAmount < 50 && transactionAmount > 0) {
            onlineLabel.setText("Online purchases must be greater than $50");
        } else if (transactionAmount < 0) {
            amountLabel.setText("Amount must be greater than 0!");
        }    else {
            validLabel.setText("Please only enter a valid Amount!");
        }
    }
    
    public void makePurchase() throws FileNotFoundException, IOException {
        String s = Customer.getCustomer().checkBalance(c.getUsername());
        double accountBalance = Customer.getCustomer().stringToDouble(s);
        double transactionAmount = Customer.getCustomer().stringToDouble(amountNum.getText());
        double amount = Customer.getCustomer().buyOnline(c, accountBalance, transactionAmount);
        updateTransactionList("-$" + String.valueOf(amount));
        printBalance();
        updateLevel();
    }
    
    @FXML
    private void logout(ActionEvent event) throws FileNotFoundException, IOException {
        App.setRoot("logInScreen");
    }
}