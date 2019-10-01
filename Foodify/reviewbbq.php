<?php
$servername="localhost";
$username="root";
$password="";
$dbname="Registerdb";
$reviewbbq=$_POST["reviewbbq"];

$conn=mysqli_connect($servername,$username,$password,$dbname);
if(!$conn)
{
	die("Connection failed: ".mysql_connect_error());
}
else
	echo "Connection successfull"."<br>";

$sql1="Insert into reviewbombay values('','$reviewbbq')";

if($conn->query($sql1) === TRUE)
{
	header("location: showbbq.php");
}
else
{
	echo "Error in inserting values ".$conn->error."<br>";
}

mysqli_close($conn);

?>
