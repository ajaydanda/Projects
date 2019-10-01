<?php
$servername="localhost";
$username="root";
$password="";
$dbname="Registerdb";
$reviewcan=$_POST["reviewcarters"];

$conn=mysqli_connect($servername,$username,$password,$dbname);
if(!$conn)
{
	die("Connection failed: ".mysql_connect_error());
}
else
	echo "Connection successfull"."<br>";

$sql1="Insert into reviewcarters values('','$reviewcan')";

if($conn->query($sql1) === TRUE)
{
	header("location: showcarters.php");
}
else
{
	echo "Error in inserting values ".$conn->error."<br>";
}

mysqli_close($conn);

?>
