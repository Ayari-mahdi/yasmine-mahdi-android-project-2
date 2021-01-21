package com.example.android_project;
import org.json.JSONObject;

public class contacts {
    private String id, nom,adresse  ,tel1,tel2,entreprise;

    public contacts(JSONObject jObject) {
        this.id = jObject.optString("id");
        this.nom = jObject.optString("nom");
        this.adresse = jObject.optString("adresse");
        this.tel1 = jObject.optString("tel1");
        this.tel2 = jObject.optString("tel2");
        this.entreprise = jObject.optString("entreprise");
    }

    public String getid() {
        return id;
    }

    public String getnom() {
        return nom;
    }

    public String getadresse() {
        return adresse;
    }

    public String gettel1() {
        return tel1;
    }

    public String gettel2() {
        return tel2;
    }

    public String getentreprise() {
        return entreprise;
    }
}
