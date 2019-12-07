package display;

import Controller.DBManager;
import Hotel.Booking;
import Hotel.Listing;
import Strategy.DiscountStrategy;
import User.Customer;
import view.customerLogin;
import view.customerRegister;
import view.bookingManagement;
import view.bookingView;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class bookingController {
    private DBManager dbManager=new DBManager();
    private bookingView bookingView = new bookingView(dbManager);
    private PaymentAdapter paymentAdapter = new PayPalAdapter();
    private Booking booking =new Booking();
    private Customer customer;
    public bookingController(Customer customer){
        this.customer=customer;
    }

    //get all available rooms
    public void getRooms() throws SQLException {
        bookingView.displayRooms(customer);

    }

    //add a specific room
    public void addBooking(Booking booking1) throws IOException {
        bookingView.bookRoom(customer,booking1);
    }

    //modify customer's specific room
    public void modifyBooking(Booking booking) throws IOException {
        bookingView.modifyBooking(this.customer,booking);
    }

    //get the customer's all bookings
    public List<Booking> getBookings(){
        List<Booking> bookings= new LinkedList<>();
        bookings=bookingView.myBookings(customer);
        return bookings;
    }


    //delete one booking
    public void deleteBooking(Booking booking) throws IOException {
        bookingView.deleteBooking(customer,booking);
    }

    //customer register
    public void register2(Customer customer) {
        customerRegister cusreg = new customerRegister();
        try {
            cusreg.register(customer, dbManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //customer login
    public void login2(Customer customer) {
        customerLogin cuslog = new customerLogin();
        cuslog.login(customer, dbManager);

    }

    //show the functions
    public void manage(Customer customer){
        bookingManagement bookingManagement = new bookingManagement();
        bookingManagement.manage(customer);
    }
    //Get payable amount of booking - requires that addBooking has been already called
    public DiscountStrategy getPayableAmount(List<Booking> bookings){
        return booking.getPayableAmount(bookings);
    }
    //Redirect to payment adapter to pay for booking - requires that addBooking has already been called
    public void proceedPayment(List<Booking> bookings){
        paymentAdapter.proceedPayment(getPayableAmount(bookings));
    }
}
