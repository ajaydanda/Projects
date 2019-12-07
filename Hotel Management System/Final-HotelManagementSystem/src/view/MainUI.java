package view;
import java.io.IOException;
import java.util.Scanner;
import signin.userLogin;
import signin.userRegister;



//Both types of user accounts will be created here and sent to dbmanager
public class MainUI {

    static userRegister userReg = new userRegister();//UserRegister singleton
    static userLogin userLog = new userLogin();//UserLogin singleton

    public static void main(String[] args) throws IOException {
        //DBManager dbm = new DBManager();
        //dbm.connect();
        Scanner input = new Scanner(System.in);

        System.out.print("Welcome to the Hotel Management System.\n1 for Register\n2 for Login\n9 to exit Hotel Management System\n");


        while(true){
            int user_input = input.nextInt();
            if(user_input==1)
            {
                //Register
                userReg.userregister();
				/*System.out.println("1 for Manager account\n2 for Customer account\n");
				user_input = input.nextInt();

				if(user_input == 1)
				{
					System.out.println("Selected Manager account");
				}
				else if(user_input == 2)
				{
					System.out.println("Selected Customer account");
				}
				else
					System.out.println("Bad input, please try again");
				*/
            }
            else if(user_input==2)
            {
                //Login


                userLog.userlogin();
            }
            else if(user_input==9)
            {
                //Terminate
                break;
            }
            else
            {
                //Unrecognized input
                System.out.println("Command not recognized, please try again.");
            }
        }
        //End execution
        System.out.println("Thank you for using the Hotel Management System");


    }


}