package display;
import Controller.DBManager;
import Hotel.Listing;
import User.Manager;
import view.managerLogin;
import view.managerRegister;
import view.ListingView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class managerController {
    private DBManager dbmanager= new DBManager();
    private Manager manager;
    Listing listing = new Listing();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    ListingView listingView = new ListingView(dbmanager,manager);
    //manager add a listing
    public managerController(Manager manager){
        this.manager=manager;
        listing.setHotelId(manager.getHotelID());
    }
    //add listing
    public void addListing(){
        listingView.addListing(listing);
    }

    //manager delete a listing
    public void deleteListing() throws IOException {
        listingView.deleteListing();

    }

    //manager search all listings
    public void displayListing() throws Exception{
        listingView.displayListing(listing);
    }

    //manager modify one specific listing
    public void modifyListing() throws IOException {
        listingView.modifyListing(listing);
    }

    //manager login
    public void login(Manager manager) throws Exception {
        managerLogin managerLogin = new managerLogin();
        managerLogin.login(manager,dbmanager);
    }

    //manager register
    public void register() throws IOException {
        managerRegister managerRegister = new managerRegister();
        managerRegister.register(this.manager,dbmanager);
    }
}
