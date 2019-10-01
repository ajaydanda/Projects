<?php
$servername="localhost";
$username="root";
$password="";
$dbname="Registerdb";
$fname=$_POST["firstname"];
$lname=$_POST["lastname"];
$gender=$_POST["gender"];
$date=$_POST["date"];
$month=$_POST["month"];
$year=$_POST["year"];
$contact=$_POST["contact"];
$email=$_POST["email"];
$username2=$_POST["uname"];
$passw=$_POST["passwd"];
$conn=mysqli_connect($servername,$username,$password,$dbname);
if(!$conn)
{
	die("Connection failed: ".mysql_connect_error());
}
else
	echo "Connection successfull"."<br>";

$sql1="Insert into clientsdb values('','$fname','$lname','$gender','$date','$month','$year','$contact','$email','$username2','$passw')";

if($conn->query($sql1) === TRUE)
{
	header("location: login.html");
}
else
{
	echo "Error in inserting values ".$conn->error."<br>";
}

mysqli_close($conn);

?>
