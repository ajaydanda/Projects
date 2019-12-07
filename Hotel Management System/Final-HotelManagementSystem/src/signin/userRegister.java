package signin;

import User.Customer;
import User.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class userRegister {
    public userRegister(){}

    //user register
    public static void userregister() throws IOException {
        try {
            System.out.println("Please input user type:");
            System.out.println("1:Customer");
            System.out.println("2:Manager");
            System.out.println("Input:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String op = reader.readLine();
            boolean flag = true;
            while (flag) {
                if (op.equals("1")) {
                    flag = false;
                    Customer customer=new Customer(0,null,null,null,null,null,null,null);
                    customer.register(customer);
                } else if (op.equals("2")) {
                    flag = false;
                    Manager manager=new Manager(0,null,0,null,null,null,null,null,null,null);
                    manager.register();
                } else {
                    System.out.print("Wrong input, try again");
                    op = reader.readLine();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


