<?php
$servername="localhost";
$username="root";
$password="";
$dbname="Registerdb";
$reviewbn=$_POST["reviewbbqnation"];

$conn=mysqli_connect($servername,$username,$password,$dbname);
if(!$conn)
{
	die("Connection failed: ".mysql_connect_error());
}
else
	echo "Connection successfull"."<br>";

$sql1="Insert into reviewbnation values('','$reviewbn')";

if($conn->query($sql1) === TRUE)
{
	header("location: showbn.php");
}
else
{
	echo "Error in inserting values ".$conn->error."<br>";
}

mysqli_close($conn);

?>
