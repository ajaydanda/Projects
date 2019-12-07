package display;
import Strategy.DiscountStrategy;
import Strategy.frequentCustomerStrategy;
public class PayPalAdapter implements PaymentAdapter{

    @Override
    public void proceedPayment(DiscountStrategy frequentCustomerStrategy){
        double amount = frequentCustomerStrategy.getTotal();
        redirectPaypal(amount);
    }

    public void redirectPaypal(double amount)
    {
        System.out.printf("\n Received payment of %.2f through PayPal\n",amount);
    }
}
