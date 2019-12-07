package view;

import Controller.DBManager;
import User.Manager;
import view.hotelManagement;

public class managerLogin {

    //manager login
    public void login(Manager manager, DBManager dbManager) throws Exception {
        int id = dbManager.isManager(manager);
        manager.setManagerID(id);
        if(id==-1){
            System.out.println("wrong usernane and password");
        }else {
            manager = dbManager.getManager(manager);
            hotelManagement hotelManagement =new hotelManagement();
            while (true) {
                hotelManagement.manage(manager);
            }
        }
    }
}
