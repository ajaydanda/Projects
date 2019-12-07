package Strategy;

import Hotel.Booking;

import java.util.List;

/**
 *
 * @author snow
 */
public class frequentCustomerStrategy implements DiscountStrategy{
    private int currentBookings;
    private double total;


    public frequentCustomerStrategy() {

    }
    @Override
    public frequentCustomerStrategy getPayableAmount(List<Booking> bookings){
        total=0;
        for(int i=0;i<bookings.size();i++){
            Booking booking=bookings.get(i);
            total+=Integer.parseInt(booking.getRoomPrice());
        }
        this.currentBookings = bookings.size();
        if(currentBookings >= 5){
            total= total * (0.9);
        }
        return this;

    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}