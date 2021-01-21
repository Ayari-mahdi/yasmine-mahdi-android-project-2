package com.example.android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1 ;
    TextView _txttext;
    EditText _txtRecherche,_txtNom,_txtAdresse,_txtTel1,_txtTel2,_txtEntreprise,_txtid;

    ImageButton _btnRecherche;

    Button _btnAjouter,_btnSupprimer,_btnModifier,_btnAppeler;
    Button _btnEnregistrer,_btnRetourner;
    Button _btnsuivant,_btnprecedent;

    LinearLayout layNavigate,laySearch,lay1,lay2;

    int x = 0;
    int j=0;
    String h="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _txttext = (TextView) findViewById(R.id.txttext) ;
        _txtNom = (EditText) findViewById(R.id.txtNom);
        _txtAdresse = (EditText) findViewById(R.id.txtAdresse);
        _txtTel1 = (EditText) findViewById(R.id.txtTel1);
        _txtTel2 = (EditText) findViewById(R.id.txtTel2);
        _txtEntreprise = (EditText) findViewById(R.id.txtEntreprise);
        _txtid = (EditText) findViewById(R.id.txtid);
        _txtRecherche = (EditText) findViewById(R.id.txtRecherche);

        _btnRecherche  = (ImageButton) findViewById(R.id.btnRecherche);
        _btnAjouter = (Button) findViewById(R.id.btnAjouter);
        _btnSupprimer = (Button) findViewById(R.id.btnSupprimer);
        _btnModifier = (Button) findViewById(R.id.btnModifier);
        _btnAppeler = (Button) findViewById(R.id.btnAppeler);
        _btnEnregistrer = (Button) findViewById(R.id.btnEnregistrer);
        _btnRetourner = (Button) findViewById(R.id.btnRetourner);
        _btnsuivant = (Button) findViewById(R.id.btnsuivant);
        _btnprecedent = (Button) findViewById(R.id.btnprecedent);

        layNavigate = (LinearLayout) findViewById(R.id.layNavigate);
        laySearch = (LinearLayout) findViewById(R.id.laySearch);
        lay1 = (LinearLayout) findViewById(R.id.lay1);
        lay2 = (LinearLayout) findViewById(R.id.lay2);
        layNavigate.setVisibility(View.INVISIBLE);


        _btnEnregistrer.setVisibility(View.INVISIBLE);
        _btnRetourner.setVisibility(View.INVISIBLE);
        _txtid.setVisibility(View.INVISIBLE);
        _txttext.setText("CONTACT INFO");


        /////////////////// recherche /////////////


        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ch = _txtRecherche.getText().toString() ;
                _txtid.setText("");
                _txtNom.setText("");
                _txtAdresse.setText("");
                _txtTel1.setText("");
                _txtTel2.setText("");
                _txtEntreprise.setText("");
                layNavigate.setVisibility(View.INVISIBLE);
                bg_recherchecontacts bgs =new bg_recherchecontacts(getApplicationContext());
                bgs.execute(ch);
            }
        });



        ///////////////// ajout ///////////////



        _btnAjouter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                x=1;
                _txtid.setText("");
                _txtNom.setText("");
                _txtAdresse.setText("");
                _txtTel1.setText("");
                _txtTel2.setText("");
                _txtEntreprise.setText("");
                _btnEnregistrer.setVisibility(View.VISIBLE);
                _btnRetourner.setVisibility(View.VISIBLE);
                _btnAjouter.setEnabled(false);
                layNavigate.setVisibility(View.INVISIBLE);
                laySearch.setVisibility(View.INVISIBLE);
                lay1.setVisibility(View.INVISIBLE);
                lay2.setVisibility(View.INVISIBLE);
                _txttext.setText("AJOUT CONTACT");
            }
        });

        ////////////////////modification ////////////

        _btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    h = _txtid.getText().toString();
                    if(h.trim().length()>0)
                    {
                    x = 2;
                    _btnEnregistrer.setVisibility(View.VISIBLE);
                    _btnRetourner.setVisibility(View.VISIBLE);
                    _btnSupprimer.setVisibility(View.INVISIBLE);
                    _btnModifier.setEnabled(false);
                    _btnAjouter.setVisibility(View.INVISIBLE);
                    layNavigate.setVisibility(View.INVISIBLE);
                    laySearch.setVisibility(View.INVISIBLE);
                    lay1.setVisibility(View.INVISIBLE);
                    lay2.setVisibility(View.INVISIBLE);
                    _txttext.setText("MODIFICATION CONTACT");
                } else{
                    Toast.makeText(getApplicationContext(),"Sélectionnez un contact pour le modifier",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ///////////////////// DELETE CONTACT ///////////////

        _btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    h=_txtid.getText().toString();
                if(h.trim().length()>0)
                {
                    x = 3;
                    AlertDialog dial = MesOptions();
                    dial.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Sélectionner un contact pour le supprimer", Toast.LENGTH_SHORT).show();
                }

            }
        });



        ////////////////// APPELER  ///////////////


        _btnAppeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }});



        //////////////////// SAVE ///////////////


        _btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  String nom= _txtNom.getText().toString();
                    String adresse= _txtAdresse.getText().toString();
                    String tel1= _txtTel1.getText().toString();
                    String tel2= _txtTel2.getText().toString();
                    String entreprise = _txtEntreprise.getText().toString();
                    if (x==1)
                    {
                             if (nom.trim().length() > 0 || tel1.trim().length() >0)
                             {
                             bg background = new bg(MainActivity.this);
                             background.execute(nom,adresse,tel1,tel2,entreprise);
                             _btnEnregistrer.setVisibility(View.INVISIBLE);
                             _btnRetourner.setVisibility(View.INVISIBLE);
                             lay1.setVisibility(View.VISIBLE);
                             lay2.setVisibility(View.VISIBLE);
                            _btnAjouter.setEnabled(true);
                            _btnModifier.setEnabled(true);
                            _btnSupprimer.setVisibility(View.VISIBLE);
                            _btnAjouter.setVisibility(View.VISIBLE);
                            laySearch.setVisibility(View.VISIBLE);
                            _btnRecherche.performClick();
                            _txttext.setText("CONTACT INFO");
                            Toast.makeText(getApplicationContext(),"contact enregistrer avec succes",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                             Toast.makeText(getApplicationContext(),"remplir au moins le NOM ou le numero de TEL 1 pour enregistrer un contact",Toast.LENGTH_SHORT).show();
                            }
                    }
                    else if (x==2)
                    {
                        String id = h;
                        bg background = new bg(MainActivity.this);
                        background.execute(id,nom,adresse,tel1,tel2,entreprise);
                        _btnEnregistrer.setVisibility(View.INVISIBLE);
                        _btnRetourner.setVisibility(View.INVISIBLE);
                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.VISIBLE);
                        _btnAjouter.setEnabled(true);
                        _btnModifier.setEnabled(true);

                        _btnSupprimer.setVisibility(View.VISIBLE);
                        _btnAjouter.setVisibility(View.VISIBLE);
                        laySearch.setVisibility(View.VISIBLE);
                        _btnRecherche.performClick();
                        _txttext.setText("CONTACT INFO");
                        Toast.makeText(getApplicationContext(),"contact modifier avec succes",Toast.LENGTH_SHORT).show();
                    }
                }
        });


        ///////////////////


        _btnRetourner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _btnEnregistrer.setVisibility(View.INVISIBLE);
                _btnRetourner.setVisibility(View.INVISIBLE);
                _btnSupprimer.setVisibility(View.VISIBLE);
                _btnAjouter.setVisibility(View.VISIBLE);
                _btnAjouter.setEnabled(true);
                _btnModifier.setEnabled(true);
                lay1.setVisibility(View.VISIBLE);
                lay2.setVisibility(View.VISIBLE);
                laySearch.setVisibility(View.VISIBLE);
                _btnRecherche.performClick();
                x = 0;
                _txttext.setText("CONTACT INFO");
            }
        });

    }


        //////////////////


    private class bg extends AsyncTask<String, Void, String> {
        AlertDialog d;
        Context c;
        public bg(Context context){
            this.c = context;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            d = new AlertDialog.Builder(c).create();
            if (x==1) {
                d.setTitle("INSERTION");
            }
            else if (x==2)
            {
                d.setTitle("modification");
            }
            else if (x==3)
            {
                d.setTitle("suppression");
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            String result = "";

            /////////////// INSERTION X==1   ///////////////

            if(x==1) {
                String nom = strings[0];
                String adresse = strings[1];
                String tel1 = strings[2];
                String tel2 = strings[3];
                String entreprise = strings[4];
                String constr = "http://addressip/phpfiles/insert-contact.php";

            try {
                URL url = new URL(constr);
                try{
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    OutputStream ops = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                    String data = URLEncoder.encode("nom","UTF-8") + "=" + URLEncoder.encode(nom,"UTF-8") +
                            "&&" + URLEncoder.encode("adresse", "UTF-8")+ "=" + URLEncoder.encode(adresse,"UTF-8")+
                            "&&" + URLEncoder.encode("tel1", "UTF-8")+ "=" + URLEncoder.encode(tel1,"UTF-8")+
                            "&&" + URLEncoder.encode("tel2", "UTF-8")+ "=" + URLEncoder.encode(tel2,"UTF-8")+
                            "&&" + URLEncoder.encode("entreprise", "UTF-8")+ "=" + URLEncoder.encode(entreprise,"UTF-8");
                    writer.write(data);
                    Log.v("MainActivity", data);
                    writer.flush();
                    writer.close();
                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                    String ligne ="";
                    while ((ligne = reader.readLine())!= null){
                        result = result + ligne;
                    }
                    reader.close();
                    ips.close();
                    http.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e ) {
                e.printStackTrace();
            }
            }

            //////////// MODIFICATION X==2  //////////////

            else if (x==2)
            {
                String id = strings[0];
                String nom = strings[1];
                String adresse = strings[2];
                String tel1 = strings[3];
                String tel2 = strings[4];
                String entreprise = strings[5];
                String constr = "http://addressip/phpfiles/modify-contact.php";
                try {
                    URL url = new URL(constr);
                    try{
                        HttpURLConnection http = (HttpURLConnection) url.openConnection();
                        http.setRequestMethod("POST");
                        http.setDoInput(true);
                        http.setDoOutput(true);
                        OutputStream ops = http.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                        String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8") +
                                "&&" + URLEncoder.encode("nom","UTF-8") + "=" + URLEncoder.encode(nom,"UTF-8") +
                                "&&" + URLEncoder.encode("adresse", "UTF-8")+ "=" + URLEncoder.encode(adresse,"UTF-8")+
                                "&&" + URLEncoder.encode("tel1", "UTF-8")+ "=" + URLEncoder.encode(tel1,"UTF-8")+
                                "&&" + URLEncoder.encode("tel2", "UTF-8")+ "=" + URLEncoder.encode(tel2,"UTF-8")+
                                "&&" + URLEncoder.encode("entreprise", "UTF-8")+ "=" + URLEncoder.encode(entreprise,"UTF-8");
                        writer.write(data);
                        Log.v("MainActivity", data);
                        writer.flush();
                        writer.close();
                        InputStream ips = http.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                        String ligne ="";
                        while ((ligne = reader.readLine())!= null){
                            result = result + ligne;
                        }
                        reader.close();
                        ips.close();
                        http.disconnect();
                        return result;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }

            //////////////// DELETE X==3 //////////////


            else if (x==3)
            {
                String id = strings[0];

                String constr = "http://addressip/phpfiles/delete-contact.php";
                try {
                    URL url = new URL(constr);
                    try{
                        HttpURLConnection http = (HttpURLConnection) url.openConnection();
                        http.setRequestMethod("POST");
                        http.setDoInput(true);
                        http.setDoOutput(true);
                        OutputStream ops = http.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                        String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8") ;

                        writer.write(data);
                        Log.v("MainActivity", data);
                        writer.flush();
                        writer.close();
                        InputStream ips = http.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                        String ligne ="";
                        while ((ligne = reader.readLine())!= null){
                            result = result + ligne;
                        }
                        reader.close();
                        ips.close();
                        http.disconnect();
                        return result;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }

            return result;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            d.setMessage(s);
            d.show();
        }
    }

        /////////////////////


    private class bg_recherchecontacts extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog dialog;
        public bg_recherchecontacts(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(MainActivity.this).create();
            dialog.setTitle("Affichage Liste contacts...");
        }

        @Override
        protected String doInBackground(String... stringss) {
            String result = "";
            String ch = stringss[0];
            String connstr = "http://addressip/phpfiles/recherche-contacts.php";
            URL url = null;
            try {
                url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("ch","UTF-8") + "=" + URLEncoder.encode(ch,"UTF-8");
                writer.write(data);
                Log.v("MainActivity", data);
                writer.flush();
                writer.close();
                InputStream ips =  http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
                String ligne ="";
                while ((ligne = reader.readLine()) != null)
                {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;
            } catch (IOException e) {
                result = e.getMessage();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("")){
                ArrayList lst = parse(s);
                List<Map<String, String>> data = new ArrayList<Map<String,String>>();
                ArrayList<com.example.android_project.contacts> contacts = new ArrayList<>();
                try {
                    JSONArray jcontactArray = new JSONArray(s);
                    for (int i = 0; i < jcontactArray.length(); i++){
                        Map<String,String> datum = new HashMap<String,String>(6);
                        datum.put("id", jcontactArray.optJSONObject(i).getString("id"));
                        datum.put("nom", jcontactArray.optJSONObject(i).getString("nom"));
                        datum.put("adresse", jcontactArray.optJSONObject(i).getString("adresse"));
                        datum.put("tel1", jcontactArray.optJSONObject(i).getString("tel1"));
                        datum.put("tel2", jcontactArray.optJSONObject(i).getString("tel2"));
                        datum.put("entreprise", jcontactArray.optJSONObject(i).getString("entreprise"));
                        data.add(datum);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
        private ArrayList<com.example.android_project.contacts> parse(final String json) {
            j=0;

            final ArrayList<com.example.android_project.contacts> contacts = new ArrayList<>();
            try {
                final JSONArray jcontactArray = new JSONArray(json);
                _txtid.setText(jcontactArray.optJSONObject(j).optString("id"));
                _txtNom.setText(jcontactArray.optJSONObject(j).optString("nom"));
                _txtAdresse.setText(jcontactArray.optJSONObject(j).optString("adresse") );
                _txtTel1.setText(jcontactArray.optJSONObject(j).optString("tel1"));
                _txtTel2.setText( jcontactArray.optJSONObject(j).optString("tel2"));
                _txtEntreprise.setText( jcontactArray.optJSONObject(j).optString("entreprise") );


                if(jcontactArray.length()==1)
                {
                    layNavigate.setVisibility(View.INVISIBLE);
                    _btnsuivant.setEnabled(false);
                    _btnprecedent.setEnabled(false);
                }
                else if (jcontactArray.length()>1)
                {
                    layNavigate.setVisibility(View.VISIBLE);
                    _btnsuivant.setEnabled(true);
                    _btnprecedent.setEnabled(false);
                }

                _btnsuivant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                             j=j+1;

                            _txtid.setText(jcontactArray.optJSONObject(j).optString("id"));
                            _txtNom.setText(jcontactArray.optJSONObject(j).optString("nom"));
                            _txtAdresse.setText(jcontactArray.optJSONObject(j).optString("adresse"));
                            _txtTel1.setText(jcontactArray.optJSONObject(j).optString("tel1"));
                            _txtTel2.setText(jcontactArray.optJSONObject(j).optString("tel2"));
                            _txtEntreprise.setText(jcontactArray.optJSONObject(j).optString("entreprise"));
                            _btnprecedent.setEnabled(true);
                           if(j == (jcontactArray.length()-1))
                        {
                            _btnsuivant.setEnabled(false);
                        }
                        }});

                _btnprecedent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                             j=j-1;

                            _txtid.setText(jcontactArray.optJSONObject(j).optString("id"));
                            _txtNom.setText(jcontactArray.optJSONObject(j).optString("nom"));
                            _txtAdresse.setText(jcontactArray.optJSONObject(j).optString("adresse"));
                            _txtTel1.setText(jcontactArray.optJSONObject(j).optString("tel1"));
                            _txtTel2.setText(jcontactArray.optJSONObject(j).optString("tel2"));
                            _txtEntreprise.setText(jcontactArray.optJSONObject(j).optString("entreprise"));
                            _btnsuivant.setEnabled(true);
                            if(j==0)
                            {
                                _btnprecedent.setEnabled(false);
                            }
                    }});

                return contacts;
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private AlertDialog MesOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("vous voulez supprimer ce contact?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = h;
                        bg background = new bg(MainActivity.this);
                        background.execute(id);
                        _txtNom.setText("");
                        _txtAdresse.setText("");
                        _txtTel1.setText("");
                        _txtTel2.setText("");
                        _txtEntreprise.setText("");
                        layNavigate.setVisibility(View.INVISIBLE);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        return MiDia;
    }


            ///////////////  PHONE CALL  ////////////



    private void makePhoneCall(){
        String number1 = _txtTel1.getText().toString();
        String number2 = _txtTel2.getText().toString();
        if (number1.trim().length() > 0 && number2.trim().length() >0)
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
            else {
                AlertDialog call = MesOptions2();
                call.show();
            }
        }
        else if (number1.trim().length() > 0 && number2.trim().length() == 0 )
        {
            String dial = "tel:" + number1;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
        else
        {
            String dial = "tel:" + number2;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

    }
    private AlertDialog MesOptions2(){

        final String number3 = _txtTel1.getText().toString();
        final String number4 = _txtTel2.getText().toString();
        AlertDialog Adiag = new AlertDialog.Builder(this)
                .setTitle("PHONE CALL")
                .setMessage("choisir le numero a appeler")
                .setIcon(R.drawable.ic_baseline)
                .setPositiveButton("tel2", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dial = "tel:" + number4;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("tel1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dial = "tel:" + number3;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        return Adiag;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
            else {
                Toast.makeText(this,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}




