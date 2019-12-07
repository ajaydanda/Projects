package view;

import Controller.DBManager;
import User.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class customerRegister {

    //register a customer account ,then log in
    public void register(Customer customer, DBManager dbManager)  throws IOException {

        //DBManager dbManager = new DBManager();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Create a new customer account");
            System.out.print("First name:");
            customer.setFirstName(reader.readLine());
            System.out.print("Last name:");
            customer.setLastName(reader.readLine());
            System.out.print("Address:");
            customer.setAddress(reader.readLine());
            System.out.print("Phone:");
            customer.setPhone(reader.readLine());
            System.out.print("Username:");
            customer.setUsername(reader.readLine());
            System.out.print("Password:");
            customer.setPassword(reader.readLine());
            System.out.print("Email:");
            customer.setEmail(reader.readLine());
            dbManager.addCustomer(customer);
            customerLogin customerLogin =new customerLogin();
            customerLogin.login(customer,dbManager);
        }catch (IOException | SQLException e){
            e.printStackTrace();}
    }


}


