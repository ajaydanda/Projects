package Strategy;

import Hotel.Booking;

import java.util.List;

public interface DiscountStrategy {
    abstract frequentCustomerStrategy getPayableAmount(List<Booking> bookings);
    abstract double getTotal();
}