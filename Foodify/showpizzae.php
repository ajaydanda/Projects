<!DOCTYPE HTML>
<head>
      <title>
Foodify
     </title>

<link href="wt1.css" rel="stylesheet" text="text/css">
</head>
<body>
<header>
<div class="logo">
<img src="Foodify-slide-1.jpg" />
</div>
<div class="nvbar">
<ul>
<li><a href="home2.html">HOME</a></li>
<li><a href="home2.html">REGISTER</a></li>
<li><a href="home2.html">LOGIN</a></li>
<li><a href="faq2.html">FAQ</a></li>
<li><a href="contact2.html">CONTACT US</a></li>
</ul>
</div>
</header>

<div id="page">
<h2 align="center">Reviews : Pizza Express</h2>


<?php
$servername="localhost";
$username="root";
$password="";
$dbname="Registerdb";

$conn=mysqli_connect($servername,$username,$password,$dbname);
if(!$conn)
{
	die("Connection failed: ".mysql_connect_error());
}


$sql1="Select * from reviewpe";
$result=mysqli_query($conn,$sql1);

if(mysqli_num_rows($result)>0)
{
	while($row=mysqli_fetch_assoc($result))
	{
		echo "Number: ".$row["id"]." Review: ".$row["review"]."<br>";
	}
}
else
{
	echo "0 results";
}

mysqli_close($conn);

?>
</div>
<footer>
<div class="logo">
<a href="https://www.twitter.com"><img src="tw.png"></a>
<a href="https://www.facebook.com"><img src="fb.png"></a>
<a href="https://www.instagram.com/?hl=en"><img src="insta.png"></a>
</div>

<p>Copyrights.All Rights Reserved.</p>
<p>&copy;</p>
</footer>

</body>
</html>

