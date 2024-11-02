/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author theesikaravinthan
 */
public class Customer extends User {
    
    private static Customer customer = null;
    private String username, password, role, acctBalance;
    private static Level currLevel = new Silver("SILVER");
   
    public Customer(String username, String password, String role, String acctBalance) {
        super(username, password, role);
        this.username = username;
        this.password = password;
        this.role = role;
        this.acctBalance = acctBalance;   
    }
    
    public String getUsername () {
        return username;
    }
   
    public String getPassword () {
        return password;
    }
    
    public void setLevel() throws FileNotFoundException {
    	if (stringToDouble(checkBalance(this.username)) < 10000) {
           currLevel = new Silver("SILVER"); 
        } else if (stringToDouble(checkBalance(this.username)) > 10000 && stringToDouble(checkBalance(this.username)) < 20000) {
            currLevel = new Gold("GOLD"); 
        } else {
           currLevel = new Platinum("PLATINUM");
        }
    }
    
    public void setLevel(Level level) {
        currLevel = level;
    }
    
    public static Level getLevel() {
    	return currLevel;
    }
    
    public static Customer getCustomer() {
        if (customer == null) {
            customer = new Customer("", "", "Customer", "");            
        }        
        return customer;
    }
    
    public String checkBalance(String username) throws FileNotFoundException{
        File file = new File("src/main/java/coe528/projectFiles/" + username + ".txt");

        if(file.exists()) {
            Scanner scanner = new Scanner(file);
            String[] split = scanner.nextLine().split(",");
            scanner.close();
            return split[3];
        }
    return null;
    }
    
    public double stringToDouble(String s) {
        double d = Double.parseDouble(s);
        return d;
    }
    
    public void depositMoney(Customer c, double balance, double amount) throws IOException {
        balance = balance + amount;
        transactionHistory(c.getUsername(), c.getPassword(), "+" + String.valueOf(amount));
        fileWriting(c, balance);        
    }
    
    public void withdrawMoney(Customer c, double balance, double amount) throws IOException {
        balance = balance - amount;
        transactionHistory(c.getUsername(), c.getPassword(), "-" + String.valueOf(amount));
        fileWriting(c, balance);      
    }
    
    public double buyOnline(Customer c, double balance, double amount) throws IOException {
        System.out.println(getLevel());
        if (getLevel() instanceof Silver) {
            balance = balance - 20 - amount;
            fileWriting(c, balance);
            transactionHistory(c.getUsername(), c.getPassword(), "-" + String.valueOf(amount+20));
            return amount+20;
            
        } else if(getLevel() instanceof Gold) {
            balance = balance - 10 - amount;
            fileWriting(c, balance);
            transactionHistory(c.getUsername(), c.getPassword(), "-" + String.valueOf(amount+10));
            return amount+10;
        } else if(getLevel() instanceof Platinum) {
            balance = balance - amount;
            fileWriting(c, balance);
            transactionHistory(c.getUsername(), c.getPassword(), "-" + String.valueOf(amount));
            return amount;
        }
        return 0;
    }  
    
    public void fileWriting(Customer c, double newBalance) throws FileNotFoundException, IOException {
        File file = new File("src/main/java/coe528/projectFiles/" + c.getUsername() + ".txt");         
        try (FileWriter f2 = new FileWriter(file, false)) {
            f2.write(c.getUsername() + "," + c.getPassword() + ",Customer," + newBalance);
            f2.close();
        }
    }
    
    public void transactionHistory(String user, String pass, String list) throws IOException {
        File history = new File("src\\main\\java\\transactionHistories\\" + user + "History.txt");
        if(history.createNewFile()) {
            FileWriter fr = new FileWriter(history, true);
            fr.write(list);
            fr.close();
        } else {
            FileWriter fr = new FileWriter(history, true);
            fr.write("\n" + list);
            fr.close();
        }
    }   
}