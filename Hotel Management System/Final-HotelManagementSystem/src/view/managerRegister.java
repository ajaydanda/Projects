package view;

import Controller.DBManager;
import User.Customer;
import User.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class managerRegister {
    public managerRegister(){}
    //manager register
    public void register(Manager manager, DBManager dbManager)  throws IOException {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Create a new manager account");
            System.out.print("Hotel name:");
            manager.setHotelName(reader.readLine());
            System.out.print("Hotel id:");
            manager.setHotelID(Integer.parseInt(reader.readLine()));
            System.out.print("First name:");
            manager.setFirstName(reader.readLine());
            System.out.print("Last name:");
            manager.setLastName(reader.readLine());
            System.out.print("Address:");
            manager.setAddress(reader.readLine());
            System.out.print("Phone:");
            manager.setPhone(reader.readLine());
            System.out.print("Username:");
            manager.setUsername(reader.readLine());
            System.out.print("Password:");
            manager.setPassword(reader.readLine());
            System.out.print("Email:");
            manager.setEmail(reader.readLine());
            dbManager.addManager(manager);
            managerLogin managerLogin=new managerLogin();
            managerLogin.login(manager,dbManager);
        }catch (IOException e){
            e.printStackTrace();} catch (Exception e) {
            e.printStackTrace();
        }
    }




}


