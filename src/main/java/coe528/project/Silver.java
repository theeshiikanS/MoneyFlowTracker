/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.FileNotFoundException;

/**
 *
 * @author theesikaravinthan
 */
public class Silver extends Level{

    public Silver(String l) {
        super(l);
    }

    @Override
    public void setTheLevel(Customer c) {
        double d;
        try {
            d = Customer.getCustomer().stringToDouble(Customer.getCustomer().checkBalance(c.getUsername()));
            if ((d >= 10000) && d < 20000) {
                c.setLevel(new Gold("GOLD"));
            }
            if (d > 20000) {
                c.setLevel(new Platinum("PLATINUM"));
            }
            if (d < 10000) {
                c.setLevel(new Silver("SILVER"));
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }  
}
