<?php
$servername="localhost";
$username="root";
$password="";
$dbname="Registerdb";
$reviewcan=$_POST["reviewcandies"];

$conn=mysqli_connect($servername,$username,$password,$dbname);
if(!$conn)
{
	die("Connection failed: ".mysql_connect_error());
}
else
	echo "Connection successfull"."<br>";

$sql1="Insert into reviewcandies values('','$reviewcan')";

if($conn->query($sql1) === TRUE)
{
	header("location: showcandies.php");
}
else
{
	echo "Error in inserting values ".$conn->error."<br>";
}

mysqli_close($conn);

?>
