/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author theesikaravinthan
 */
public class Manager extends User {
    
    private static Manager manager = null;
    
    public Manager(String username, String pass, String role) {
        super("admin", "admin", "manager");
    }
    
    public static Manager getManager() {
        if (manager == null) {
            manager = new Manager("admin", "admin", "manager");            
        }        
        return manager;
    }
    
    public boolean createCustomer(String user, String pass) throws IOException {
        File newCustomers = new File("src/main/java/coe528/projectFiles/" + user + ".txt");
        
        if(newCustomers.createNewFile()) {
            FileWriter fr = new FileWriter(newCustomers);
            Level tmp = new Silver("SILVER");
            fr.write(user + "," + pass + ",Customer,100.00");
            System.out.println(tmp.toString());
            fr.close();
            return true;
        } else {
            return false;
        }
    }
    
    public static ArrayList<String> customerNames() throws FileNotFoundException {
        ArrayList<String> names = new ArrayList<String>();
        
        File[] f = new File("src/main/java/coe528/projectFiles/").listFiles();
        for (File fr:f) {
            Scanner scanner = new Scanner(fr);
            String[] split = scanner.nextLine().split(",");
            names.add(split[0]);
            scanner.close();
        }       
        return names;        
    }
    
    public boolean deleteCustomer(String user) {
        File file = new File("src/main/java/coe528/projectFiles/" + user + ".txt");
        File history = new File("src\\main\\java\\transactionHistories\\" + user + "History.txt");
        if(file.exists()){
            file.delete();
            history.delete();
            return true;
        } else {
            return false;
        }
    }
}