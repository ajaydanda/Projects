package Controller;

import Hotel.Booking;
import Hotel.Listing;
import User.Customer;
import User.Manager;
import sun.security.krb5.internal.tools.Klist;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;

public class DBManager {
    String driver ="com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/hotel";
    String user ="root";
    String password ="password";
    Connection connection ;



    public void connect(){

        try{
            System.out.println("Connect to database");
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //add a customer account
    public void  addCustomer( Customer user) throws SQLException {
        try {
            connect();
            Statement stmt = connection.createStatement();
            String sql = "insert into Customer(firstName, lastName, address,phone,userName,passWord,email ) " +
                    "values('" + user.getFirstName() + "','" + user.getLastName() + "','"  + user.getAddress() + "','"
                    + user.getPhone() + "','" + user.getUsername() + "','" + user.getPassword() + "','" + user.getEmail()+"')";
            stmt.executeUpdate(sql);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //create an manager account
    public  void addManager(Manager user){
        try {
            connect();
            Statement stmt = connection.createStatement();
            String sql = "insert into Manager(hotelName,hotelId,firstName, lastName, address,phone,userName,passWord,email ) " +
                    "values('" +user.getHotelName()+"','"+user.getHotelID()+"','"+ user.getFirstName() + "','" + user.getLastName() + "','"  + user.getAddress() + "','"
                    + user.getPhone() + "','" + user.getUsername() + "','" + user.getPassword() + "','" + user.getEmail()+"')";
            stmt.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    //verify customer，if exits:return id
    public Integer isCustomer(Customer customer){
        int id=-1;
        ResultSet rs= null;
        try{
            connect();
            String sql =" select * from customer where userName = '"+customer.getUsername()+"'and passWord = '"+customer.getPassword()+"'";
            Statement stmt=connection.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()){
                id=rs.getInt("customerId");
            }
        }catch (SQLException e){

        }
        return id;
    }

    //verify manager,if exits return manager id
    public Integer isManager(Manager manager){
        int id=-1;
        ResultSet rs= null;
        try{
            connect();
            String sql =" select * from manager where userName = '"+manager.getUsername()+"' and passWord = '"+manager.getPassword()+"'";
            System.out.println(sql);
            Statement stmt=connection.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()){
                id= rs.getInt("managerId");
            }
        }catch (SQLException e){

        }
        return id;
    }


    //get Customer based on the id
    public Customer getCustomer(Customer customer1) {
        int id =customer1.getCustID();
        Customer customer = null;
        try{
            connect();
            String sql="select * from customer where customerId ="+id;
            Statement stmt = connection.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            if (rs.next()){
                customer = new Customer(rs.getInt("customerId"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("address"),rs.getString("phone"),rs.getString("userName"),rs.getString("passWord"),rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    //getManager based on the manager id
    public Manager getManager(Manager manager){
        int id=manager.getManagerID();
        try{
            connect();
            String sql="select * from manager where managerId ="+id;
            System.out.println(sql);
            Statement stmt = connection.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            if (rs.next()){
                manager.setManagerID(rs.getInt("managerId"));
                manager.setHotelName(rs.getString("hotelName"));
                manager.setHotelID(rs.getInt("hotelId"));
                manager.setFirstName(rs.getString("firstName"));
                manager.setLastName(rs.getString("lastName"));
                manager.setAddress(rs.getString("address"));
                manager.setPhone(rs.getString("phone"));
                manager.setUsername(rs.getString("userName"));
                manager.setPassword(rs.getString("passWord"));
                manager.setEmail(rs.getString("email"));
                System.out.println("manager login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manager;
    }

    //add listing
    public String addListing(Listing listing){
        String res = "Add listing Success";
        try {
            connect();
            Statement stmt = connection.createStatement();
            String sql = "insert into listing(hotelId, roomId,roomType,roomPrice) values('"+listing.getHotelId()+"','"+listing.getRoomId()+"','"
                    +listing.getRoomType()+"','"+listing.getRoomPrice()+"')";
            int i = stmt.executeUpdate(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    //getListing in order to book a room
    public Listing getListingById(Listing tlisting){
        int id = tlisting.getListingId();
        ResultSet rs=null;
        Listing listing=null;
        try{
            connect();
            Statement stmt = connection.createStatement();
            String sql="select * from listing where listingId = "+id;
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                listing = new Listing();
                listing.setListingId(rs.getInt("listingId"));
                listing.setHotelId(rs.getInt("hotelId"));
                listing.setRoomId(rs.getInt("roomId"));
                listing.setRoomType(rs.getString("roomType"));
                listing.setRoomPrice(rs.getString("roomPrice"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listing;
    }

    //get all listing in the current hotel
    public List<Listing> getListing(Listing l){
        ResultSet rs=null;
        List<Listing> list=null;
        try{
            connect();
            Statement stmt = connection.createStatement();
            String sql="select * from listing where hotelId = "+l.getHotelId();
            rs = stmt.executeQuery(sql);
            list = new ArrayList<>();
            while (rs.next()){
                Listing listing = new Listing();
                listing.setListingId(rs.getInt("listingId"));
                listing.setHotelId(rs.getInt("hotelId"));
                listing.setRoomId(rs.getInt("roomId"));
                listing.setRoomType(rs.getString("roomType"));
                listing.setRoomPrice(rs.getString("roomPrice"));
                list.add(listing);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    //delete the hotel's listing by id
    public String deleteListing(Listing listing){
        int hotelId = listing.getHotelId();
        int listingId = listing.getListingId();
        String res = "Delete listing success";
        try {
            connect();
            Statement stmt = connection.createStatement();
            String sql = "delete from listing "+"where hotelId = "+hotelId+" and listingId = "+listingId;
            int i =stmt.executeUpdate(sql);
            if(i ==03){
                res = "delete 0 item, wrong room id";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    // modify the hotel's listing change the price of one certain type  room
    public boolean modifyListing(Listing listing){
        try{
            connect();
            String sql ="update listing "+"set roomPrice = "+listing.getRoomPrice()+
                    " where hotelId = "+listing.getHotelId()+" and roomType= '"+listing.getRoomType()+"'";
            Statement stmt=connection.createStatement();
            int i=stmt.executeUpdate(sql);
            if(i==0){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    //commit a booking
    public String addBooking(Booking booking){
        String res = "Add listing Success";
        try {
            connect();
            Statement stmt = connection.createStatement();
            String sql = "insert into booking(customerId,hotelId, roomId,roomType,roomPrice,checkInDate,checkOutDate) values('"+booking.getCustomerId()+"','"+booking.getHotelId()+"','"+booking.getRoomId()+"','"
                    +booking.getRoomType()+"','"+booking.getRoomPrice()+"','"+booking.getCheckInDate()+"','"+booking.getCheckOutDate()+"')";
            int i = stmt.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    //get all bookings that the customer had
    public List<Booking> getBookings(Customer customer){
        int id = customer.getCustID();
        ResultSet rs=null;
        List<Booking> list=new ArrayList<>();
        try{
            connect();
            String sql="select * from booking where customerId = "+id;
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            list = new ArrayList<>();
            while (rs.next()){
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("bookingId"));
                booking.setCustomerId(rs.getInt("customerId"));
                booking.setHotelId(rs.getInt("hotelId"));
                booking.setRoomId(rs.getInt("roomId"));
                booking.setRoomType(rs.getString("roomType"));
                booking.setRoomPrice(rs.getString("roomPrice"));
                booking.setCheckInDate(rs.getString("checkInDate"));
                booking.setCheckOutDate(rs.getString("checkOutDate"));
                list.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //delete a booking
    public String deleteBooking(Booking tbooking){
        int cId=tbooking.getCustomerId();
        int bookingId = tbooking.getBookingId();
        String res = "Delete listing success";
        try {
            connect();
            Statement stmt = connection.createStatement();
            String sql = "delete from booking "+"where bookingId = "+bookingId+" and customerId= "+cId;
            int i =stmt.executeUpdate(sql);
            if(i ==03){
                res = "delete 0 item, wrong room id";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    //modify customer's booking
    public boolean modifyBooking(Booking booking){
        try{
            connect();
            System.out.println(booking.getCheckInDate());
            String sql ="update booking set checkInDate = "+booking.getCheckInDate()+
                    ",checkOutDate = "+ booking.getCheckOutDate()+
                    " where bookingId = "+booking.getBookingId();
            Statement stmt = connection.createStatement();
            int i=stmt.executeUpdate(sql);
            if(i==0){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    //get the available rooms
    public List<Listing> getRooms(){
        ResultSet rs=null;
        List<Listing> list=null;
        try{
            connect();
            String sql="select * from listing";
            Statement stmt=connection.createStatement();
            rs = stmt.executeQuery(sql);
            list = new ArrayList<>();
            while (rs.next()){
                Listing listing = new Listing();
                listing.setListingId(rs.getInt("listingId"));
                listing.setHotelId(rs.getInt("hotelId"));
                listing.setRoomId(rs.getInt("roomId"));
                listing.setRoomType(rs.getString("roomType"));
                listing.setRoomPrice(rs.getString("roomPrice"));
                list.add(listing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}




















