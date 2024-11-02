/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author theesikaravinthan
 */
public abstract class Level {
    
    protected String l;
    private static Level level = null;
    
    public Level(String l) {
        this.l = l;  
    }
    
    
    public abstract void setTheLevel(Customer c);
    
    @Override
    public String toString(){//overriding the toString() method  
        return l;  
    }    
}