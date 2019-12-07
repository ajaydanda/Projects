package view;

import Hotel.Listing;
import User.Manager;
import display.managerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

public class hotelManagement {
    public hotelManagement(){}

    //all functions of manager
    public  void manage(Manager manager) throws Exception {
        try{
            System.out.println("Select operations:");
            System.out.println("1, Display all available rooms");
            System.out.println("2, Add listing");
            System.out.println("3, Delete listing");
            System.out.println("4, Modify listing");
            System.out.println("Input:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String op = reader.readLine();
            switch (op){
                case "1":
                    manager.displayListing();
                    break;
                case "2":
                    manager.addListing();
                    break;
                case"3":
                    manager.deleteListing();
                    break;
                case"4":
                    manager.modifyListing();
                    break;
                default:
                    System.out.print("Wrong input");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
