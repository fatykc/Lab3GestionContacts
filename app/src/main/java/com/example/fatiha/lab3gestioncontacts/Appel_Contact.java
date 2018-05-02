package com.example.fatiha.lab3gestioncontacts;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Appel_Contact extends AppCompatActivity implements View.OnClickListener{
    Button btAppel;
    ImageButton chercheBt,modifback;
    TextView nomCherche,prenonCherche,telephoneCherhe;
    EditText   idcherche;

    public static String tel="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appel__contact);

        chercheBt = (ImageButton) findViewById(R.id.chercherIc);
        modifback = (ImageButton) findViewById(R.id.modifRetour);
        btAppel = (Button) findViewById(R.id.modifTelbt);

        nomCherche = (TextView) findViewById(R.id.iNom);
        prenonCherche = (TextView) findViewById(R.id.iPrenom);
        telephoneCherhe = (TextView) findViewById(R.id.iTelephone);
        idcherche = (EditText) findViewById(R.id.edId);


        modifback.setOnClickListener(this);
        chercheBt.setOnClickListener(this);
        btAppel.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modifRetour:
                finish();
                break;
            case R.id.chercherIc:
                ficheContact();
                break;
            case R.id.modifTelbt:

                AppelContact();


                break;
        }


    }

    public void ficheContact() {

        String url = "http://10.0.2.2/ContactsControleur_Android/PHP/chercheContact.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);

                            JSONArray jsonResponse = new JSONArray(response);

                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unContact;
                                unContact=jsonResponse.getJSONObject(1);
                                nomCherche.setText(unContact.getString("nom"));
                                prenonCherche.setText(unContact.getString("prenom"));
                                telephoneCherhe.setText(unContact.getString("telephone"));

                                //  modif.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(Appel_Contact.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Appel_Contact.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "chercher");
                params.put("idcontact", idcherche.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

   public void  AppelContact(){


       tel=telephoneCherhe.getText().toString();
       Intent intent =new Intent(Intent.ACTION_DIAL);
       intent.setData(Uri.parse("tel:"+ tel));

       nomCherche.setText(" ");
       prenonCherche.setText(" ");
       telephoneCherhe.setText(" ");
       idcherche.setText(" ");
       startActivity(intent);


   }


}
