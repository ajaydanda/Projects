package User;
import Controller.DBManager;
import Hotel.Listing;
import display.managerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author snow
 */
//package OOADProject;
public class Manager extends User {
    private int managerID;
    private String hotelName;
    private int hotelID;
    private managerController controller=null;

    public Manager(int managerID, String hotelName, int hotelID, String firstName, String lastName, String address, String phone, String username, String password, String email) {
        super(firstName, lastName, address, phone, username, password, email);
        this.managerID = managerID;
        this.hotelName = hotelName;
        this.hotelID = hotelID;
        controller = new managerController(this);
    }

    //manager login
    public void login(Manager manager ) throws Exception {
        controller.login(manager);
    }

    //manager register
    public void register()  throws IOException {
        controller.register();

    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public boolean isManager(){
        return true;
    }

    public boolean isCustomer(){
        return false;
    }

    // manager add a listing
    public void addListing(){
        controller.addListing();
    }

    // manager delete a listing
    public void deleteListing() throws IOException {
        controller.deleteListing();
    }

    //manager modify listing
    public void modifyListing() throws IOException {
        controller.modifyListing();
    }
    //shows all listing
    public void displayListing() throws Exception {
        controller.displayListing();
    }


}
