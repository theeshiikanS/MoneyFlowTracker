/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author theesikaravinthan
 */

/*
    Overview:User is mutable and an object that can defines the type of user (Customer or Manager) 
    Abstration Function:The user object represents the user of the program. They all have a password, username and role written onto their specified file.
    Rep Invariant: Username, Password and role are Strings that can be null or blank.
*/

public class User {
    protected String username;
    String password;
    private String role;
    
    //Requires:Require three parameters: username, password and role
    //Modifies:Sets the instance variables to the corresponding variables in the methods signature
    //Effects: Intializes the instacne vairables
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    //Requires: 
    //Modifies:
    //Effects: Returns the username of the object
    public String getUsername() {
        return this.username;
    }
    
    //Requires: 
    //Modifies:
    //Effects: Returns the password of the object
    public String getPassword() {
        return this.password;
    }
    
    //Requires: 
    //Modifies:
    //Effects: Returns the role of the object
    public String getRole() {
        return this.role;
    }
    
    //Requires: A string with the speicifed role of the object
    //Modifies: Changes the objects role to the new one given
    //Effects: Gives the object a new role
    public void setRole(String r) {
        this.role = r;
    }
    
    //Requires: 
    //Modifies: 
    //Effects: Allows the object to log in based on the entered username and password
    public User logIN() throws IOException {
        
        File file = new File("src/main/java/coe528/projectFiles/" + username + ".txt");
        
        if(file.exists()) {
            Scanner scanner = new Scanner(file);
            String[] split = scanner.nextLine().split(",");
            
            if (username.equals(split[0]) && password.equals(split[1])) {
                scanner.close();
                return new Customer(split[0], split[1], split[2], split[3]);
            }
        }                      
        else if (username.equals("admin") && password.equals("admin")) {         
            return new Manager(username, password, "Manager");
        }   
        return null;
    }
    
    //Requires: 
    //Modifies:
    //Effects: Makes sure that the username, password and role is not blank or null
    public boolean repOK(){
        if(username.isBlank() || role.isBlank() || password.isBlank()){
            return false;
        }
        return true;
    }
    public String toString () {
        return("Username: " + username + " Password: " + password + " Role: " + role);
    }
}