<?php
$db = "yasmine-mahdi-db";

$ch = $_POST["ch"];

$host = "localhost";
$conn = mysqli_connect($host, "root","",$db);
if ($conn)
{
	mysqli_set_charset($conn,"utf8");
	
	$req= "select * from contacts where nom like '%$ch%'  or tel1 like '%$ch%' or tel2 like '%$ch%' or entreprise like '%$ch%' ";
	$result=mysqli_query($conn, $req);
	
	while($row = mysqli_fetch_assoc($result)){
		$output[] = $row;
		
	}
	
	echo json_encode($output);
	
	
		
	mysqli_close($conn);
	
} else{
	echo "probleme de connexion";
}



?>