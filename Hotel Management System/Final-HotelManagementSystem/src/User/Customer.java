package User;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.DBManager;
import Hotel.Booking;
import Hotel.Listing;
import display.bookingController;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author snow
 */
//package OOADProject;
public class Customer extends User{

    private int custID;
    private bookingController controller;
    //private Map<Integer,Boolean> bookingHistory;
    public Customer(int custID, String firstName, String lastName, String address, String phone, String username, String password, String email) {
        super(firstName, lastName, address, phone, username, password, email);
        this.custID = custID;
        controller=new bookingController(this);
    }

    //customer register
    public void register(Customer customer){
        controller.register2(customer);

    }

    //customer login
    public void login(Customer customer){
        controller.login2(customer);

    }


    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public boolean isManager(){
        return false;
    }

    public boolean isCustomer(){
        return true;
    }
    public void getRooms() throws SQLException {
        controller.getRooms();

    }
    public void getBookings(){
        controller.getBookings();
    }

    //book a room
    public void bookRoom(Booking booking) throws IOException {
        controller.addBooking(booking);
        //To be implemented
    }

    //customer modify checkout date
    public void modifyBooking(Booking booking) throws IOException {
        controller.modifyBooking(booking);
    }

    //delete the customer's booking
    public void deleteBooking(Booking booking) throws IOException {
        controller.deleteBooking(booking);
    }

    // get all my bookings
    public List<Booking> getMyBookings(){
        List<Booking> bookings=controller.getBookings();
        return bookings;
    }

    //pay all bookings
    public void payBill(){
        controller.proceedPayment(getMyBookings());
    }

}