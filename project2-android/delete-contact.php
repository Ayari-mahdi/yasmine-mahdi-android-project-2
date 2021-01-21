<?php
$db = "yasmine-mahdi-db";
$id= $_POST["id"];

$host = "localhost";
$conn = mysqli_connect($host, "root","",$db);
if ($conn)
{
	$req = "delete from contacts where id='$id' ";
        $res = mysqli_query($conn, $req);
	if ($res) {
		echo "succes suppression";
		
	} else {
		echo "echec suppression";
	}
	mysqli_close($conn);
	
} else{
	echo "probleme de connexion";
}

?>