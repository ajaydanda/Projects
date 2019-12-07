package display;
import Strategy.DiscountStrategy;
import Strategy.frequentCustomerStrategy;

public interface PaymentAdapter {
    abstract void proceedPayment(DiscountStrategy frequentCustomerStrategy);
}