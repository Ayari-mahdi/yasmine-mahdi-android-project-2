<?php
$db = "yasmine-mahdi-db";
$nom = $_POST["nom"];
$adresse = $_POST["adresse"];
$tel1 = $_POST["tel1"];
$tel2 = $_POST["tel2"];
$entreprise = $_POST["entreprise"];
$host = "localhost";
$conn = mysqli_connect($host, "root","",$db);
if ($conn)
{
	$req = "insert into contacts (nom,adresse,tel1,tel2,entreprise) values                  ('$nom','$adresse','$tel1','$tel2','$entreprise')";
        $res = mysqli_query($conn, $req);
	if ($res) {
		echo "succes insertion";
		
	} else {
		echo "echec insertion";
	}
	mysqli_close($conn);
	
} else{
	echo "probleme de connexion";
}

?>