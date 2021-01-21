<?php
$db = "yasmine-mahdi-db";
$id= $_POST["id"];
$nom = $_POST["nom"];
$adresse = $_POST["adresse"];
$tel1 = $_POST["tel1"];
$tel2 = $_POST["tel2"];
$entreprise = $_POST["entreprise"];
$host = "localhost";
$conn = mysqli_connect($host, "root","",$db);
if ($conn)
{
	$req = "update contacts set nom='$nom', adresse='$adresse', tel1='$tel1', tel2='$tel2',entreprise='$entreprise' where id='$id' ";
        $res = mysqli_query($conn, $req);
	if ($res) {
		echo "succes modification";
		
	} else {
		echo "echec modification";
	}
	mysqli_close($conn);
	
} else{
	echo "probleme de connexion";
}

?>