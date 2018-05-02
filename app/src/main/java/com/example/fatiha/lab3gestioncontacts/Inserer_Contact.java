package com.example.fatiha.lab3gestioncontacts;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inserer_Contact extends AppCompatActivity {
    Button btInsererContact;
    ProgressDialog progressDialog;
    EditText edNom,edPrenom,edTelephone;
    ImageButton btefface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserer__contact);

        edNom =(EditText) findViewById(R.id.txtnom);
        edPrenom = (EditText) findViewById(R.id.txtprenom);
        edTelephone= (EditText)findViewById(R.id.txttel);

        ImageButton imb= (ImageButton) findViewById(R.id.imageButtonr);
        btInsererContact= (Button)findViewById(R.id.button);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





      btInsererContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requeteEnregistrer();
            }
        });

     }

    public void requeteEnregistrer() {

      // String url = "http://10.0.2.2/ContactsControleur_Android/PHP/contactsControleurJSON.php";

      String url = "http://10.0.2.2/ContactsControleur_Android/PHP/insereContact.php";

        //Recuperer les donnees
        final String nom= edNom.getText().toString();
        final String prenom= edPrenom.getText().toString();
        final String telephone= edTelephone.getText().toString();



        progressDialog = new ProgressDialog(Inserer_Contact.this);
        progressDialog.setMessage("Uploading, attendre SVP...");
        progressDialog.show();



        //Envoyer données au serveur

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("RESULTAT", response);
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                          //  Toast.makeText(Inserer_Contact.this,msg,Toast.LENGTH_LONG).show();

                            if(msg.equals("OK")) {
                                Toast.makeText(Inserer_Contact.this, "Contact bien enregistré", Toast.LENGTH_SHORT).show();
                                edNom.setText(" ");
                                edPrenom.setText(" ");
                                edTelephone.setText(" ");
                            }
                            else
                                Toast.makeText(Inserer_Contact.this, "Probleme pour enregistrer", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Inserer_Contact.this, "ERREUR", Toast.LENGTH_LONG).show();
                        progressDialog.hide();

                    }
                }
        ) {



            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST

                params.put("action", "enregistrer");
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("telephone", telephone);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);

    }



}
