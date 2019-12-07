package Hotel;

import Strategy.DiscountStrategy;
import Strategy.frequentCustomerStrategy;

import java.util.List;

public class Booking {
    private int bookingId;
    private int customerId;
    private int hotelId;
    private int roomId;
    private String roomType;
    private String roomPrice;
    private String checkInDate;
    private String checkOutDate;
    private DiscountStrategy discountStrategy;
    public Booking(){}
    public Booking(int customerId,int hotelId,int roomId,String roomType,String roomPrice,String checkInDate,String checkOutDate){
        this.bookingId=bookingId;
        this.customerId=customerId;
        this.hotelId =hotelId;
        this.roomId = roomId;
        this.roomType=roomType;
        this.roomPrice=roomPrice;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
    }
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void giveFrequentCustomerDiscount(){
        this.discountStrategy = new frequentCustomerStrategy();
    }

    public DiscountStrategy getPayableAmount(List<Booking> bookings) {
        giveFrequentCustomerDiscount();
        return discountStrategy.getPayableAmount(bookings);
    }
}
