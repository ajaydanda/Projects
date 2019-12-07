package view;

import Controller.DBManager;
import User.Customer;
import view.bookingManagement;

public class customerLogin {

    //verify customer is exits
    public void login(Customer customer, DBManager dbManager){
        int id = dbManager.isCustomer(customer);

        customer.setCustID(id);
        if(id==-1){
            System.out.println("wrong usernane and password");
        }else {
            customer = dbManager.getCustomer(customer);
            bookingManagement bookingManagement = new bookingManagement();
            while (true){
                bookingManagement.manage(customer);
            }

        }
    }
}
