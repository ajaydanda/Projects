package view;

import Controller.DBManager;
import Hotel.Listing;
import User.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ListingView {
    private Listing listing = new Listing();
    private DBManager dbManager ;
    private Manager manager;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ListingView(DBManager dbManager,Manager manager){
        this.dbManager=dbManager;
        this.manager=manager;
    }

    //manager add a listing
    public void addListing(Listing temp){
        int hotelId = temp.getHotelId();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Add a new list:");
            System.out.println("Hotel id:"+hotelId);
            listing.setHotelId(hotelId);
            System.out.print("Room id:");
            listing.setRoomId(Integer.parseInt(reader.readLine()));
            System.out.print("Room type:");
            listing.setRoomType(reader.readLine());
            System.out.print("Room price:");
            listing.setRoomPrice(reader.readLine());
            String info =dbManager.addListing(listing);
            System.out.print(info);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //manager delete a listing
    public void deleteListing() throws IOException {

        try{
            System.out.print("Input listing id:");
            listing.setListingId(Integer.parseInt(reader.readLine()));
            String info =dbManager.deleteListing(listing);
            System.out.print(info);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    //manager search all listings
    public void displayListing(Listing temp) throws Exception{
        listing.setHotelId(temp.getHotelId());
        List<Listing> list= dbManager.getListing(listing);
        for(int i =0;i<list.size();i++){
            Listing listing = list.get(i);
            System.out.print("\n listing id:"+listing.getListingId());
            System.out.print("\t hotel id: "+listing.getHotelId());
            System.out.print("\t room id: "+listing.getRoomId());
            System.out.print("\t room type: "+listing.getRoomType());
            System.out.print("\t room price: "+listing.getRoomPrice());
        }
    }

    //manager modify one specific listing
    public void modifyListing(Listing listing) throws IOException {
        System.out.println("please input room type:");
        listing.setRoomType(reader.readLine());
        System.out.print("please input room price");
        listing.setRoomPrice( reader.readLine());
        dbManager.modifyListing(listing);

    }
}
