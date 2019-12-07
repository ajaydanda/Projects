package view;

import Hotel.Booking;
import Hotel.Listing;
import User.Customer;
import display.managerController;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class bookingManagement {

    public bookingManagement(){}

    //select options to perform functions
    public void manage(Customer customer){
        try {
            Booking booking = new Booking();
            booking.setCustomerId(customer.getCustID());
            System.out.println("Select operations:");
            System.out.println("1, Display all available rooms");
            System.out.println("2, Booking room");
            System.out.println("3, Delete booking");
            System.out.println("4, Modify booking");
            System.out.println("5, My bookings");
            System.out.println("6, pay my bookings");
            System.out.println("Input:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String op = reader.readLine();
            switch (op){
                case "1":
                    customer.getRooms();
                    break;
                case "2":
                    customer.bookRoom(booking);
                    break;
                case"3":
                    customer.deleteBooking(booking);
                    break;
                case"4":
                    customer.modifyBooking(booking);
                    break;
                case"5":
                    customer.getMyBookings();
                    break;
                case "6":
                    customer.payBill();
                    break;
                default:
                    System.out.print("Wrong input");
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
