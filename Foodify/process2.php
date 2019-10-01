<?php
define('DB_SERVER', 'localhost');
   define('DB_USERNAME', 'root');
   define('DB_PASSWORD', '');
   define('DB_DATABASE', 'Registerdb');
   $db = mysqli_connect(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);
$username=$_POST["username"];
$passw=$_POST["password"];

if(!$db)
{
	die("Connection failed: ".mysql_connect_error());
}
else
	echo "Connection successfull"."<br>";

   session_start();
   if($_SERVER["REQUEST_METHOD"] == "POST") 
   {
	   $myusername = mysqli_real_escape_string($db,$username);
      $mypassword = mysqli_real_escape_string($db,$passw); 
	   $sql = "SELECT id FROM clientsdb WHERE Username = '$myusername' and Password = '$mypassword'";
	   $result = mysqli_query($db,$sql);
      $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
    //  $active = $row['active'];
      
      $count = mysqli_num_rows($result);
	  if($count == 1) {
      //   session_register("myusername");
         $_SESSION['login_user'] = $myusername;
         
         header("location: home2.html");
      }else 
	  {
         echo "Your Login Name or Password is invalid";
	  }
   }

mysqli_close($db);

?>
<html>
<br>
<a style="text-decoration:none; color:black;" href="login.html">Click here to try again</a>
</html>