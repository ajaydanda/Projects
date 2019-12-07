package view;

import Controller.DBManager;
import Hotel.Booking;
import Hotel.Listing;
import User.Customer;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class bookingView{
    private DBManager dbManager;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public bookingView(DBManager dbManager){
        this.dbManager=dbManager;
    }


    // show all rooms
    public void displayRooms(Customer customer) throws SQLException {
        List<Listing> list=null;
        list = dbManager.getRooms();
        for(int i=0;i<list.size();i++){
            Listing lisitng = list.get(i);
            System.out.print("Listing Id:");
            System.out.print(lisitng.getListingId()+"");
            System.out.print("\tHotel id:");
            System.out.print(lisitng.getHotelId());
            System.out.print("\tRoom id:");
            System.out.print(lisitng.getRoomId());
            System.out.print("\tRoom type:");
            System.out.print(lisitng.getRoomType());
            System.out.print("\t Room price:");
            System.out.print(lisitng.getRoomPrice());
            System.out.print("\n");
        }

    }

    //customer book a room
    public void bookRoom(Customer customer, Booking booking) throws IOException {
        Listing listing=new Listing();
        System.out.print("Please input listingId:");
        listing.setListingId(Integer.parseInt(reader.readLine()));
        listing = dbManager.getListingById(listing);
        System.out.print("Please input checkin Date:");
        booking.setCheckInDate(reader.readLine());;
        System.out.print("Please input checkout date:");
        booking.setCheckOutDate(reader.readLine()) ;
        booking.setHotelId(listing.getHotelId());
        booking.setRoomId(listing.getRoomId());
        booking.setRoomType(listing.getRoomType());
        booking.setRoomPrice(listing.getRoomPrice());
        dbManager.addBooking(booking);

    }

    //customer delete the booking
    public void deleteBooking(Customer customer,Booking booking) throws IOException {
        System.out.print("Please input booking id:");
        int id=Integer.parseInt(reader.readLine());
        booking.setBookingId(id);
        dbManager.deleteBooking(booking);
    }

    //customer modify booking:check in and check out data
    public void modifyBooking(Customer customer,Booking booking) throws IOException {
        System.out.print("Please input booking id:");
        booking.setBookingId(Integer.parseInt(reader.readLine()));
        System.out.print("Please input new checkIn date:");
        booking.setCheckInDate(reader.readLine());
        System.out.print("Please input new checkOut date:");
        booking.setCheckOutDate(reader.readLine());;
        dbManager.modifyBooking(booking);
    }

    //get all customer's bookings
    public List<Booking> myBookings(Customer customer){
        List<Booking> bookings=dbManager.getBookings(customer);
        for(int i=0;i<bookings.size();i++){
            Booking booking=bookings.get(i);
            System.out.print("\nbooking id:"+booking.getBookingId());
            System.out.print("\thotel id: "+booking.getHotelId());
            System.out.print("\troom id :"+booking.getRoomId());
            System.out.print("\troom type :"+booking.getRoomType());
            System.out.print("\troom price:" +booking.getRoomPrice());
            System.out.print("\tcheck in date"+booking.getCheckInDate());
            System.out.print("\tck=heck out date:"+booking.getCheckOutDate());
        }
        return bookings;
    }
}