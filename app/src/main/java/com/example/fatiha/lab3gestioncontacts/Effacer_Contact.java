package com.example.fatiha.lab3gestioncontacts;

import android.database.Cursor;
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

public class Effacer_Contact extends AppCompatActivity implements View.OnClickListener{
    TextView sNom, sPrenom, sTel;
    EditText sId;

    ImageButton btChercherRetour, btChercherContact;
    Button bteffacer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_effacer_contact);

        sNom=(TextView)findViewById(R.id.editNom);
        sPrenom=(TextView)findViewById(R.id.editPrenom);
        sTel=(TextView)findViewById(R.id.txttel);

        sId = (EditText) findViewById(R.id.editId);

        btChercherRetour = (ImageButton) findViewById(R.id.btChRetour);
        btChercherRetour.setOnClickListener(this);

        btChercherContact =(ImageButton)findViewById(R.id.searchIc);
        btChercherContact.setOnClickListener(this);

        bteffacer= (Button)findViewById(R.id.btEffacerC);
        bteffacer.setOnClickListener(this);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btChRetour:
                finish();
                break;
            case R.id.searchIc:
                ficheContact();
                break;
            case R.id.btEffacerC:
                enlever();
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
                                sNom.setText(unContact.getString("nom"));
                                sPrenom.setText(unContact.getString("prenom"));
                                sTel.setText(unContact.getString("telephone"));

                                //  modif.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(Effacer_Contact.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Effacer_Contact.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "modifier");
                params.put("idcontact", sId.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }


    public void enlever() {

        String url ="http://10.0.2.2/ContactsControleur_Android/PHP/effaceContact.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                Toast.makeText(Effacer_Contact.this, "Le contact enlevé", Toast.LENGTH_SHORT).show();
                                sNom.setText(" ");
                                sPrenom.setText(" ");
                                sTel.setText(" ");
                                sId.setText(" ");
                            }
                            else{
                                Toast.makeText(Effacer_Contact.this, "Probleme avec enlever", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Effacer_Contact.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "enlever");
                params.put("idcontact",sId.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }




}







