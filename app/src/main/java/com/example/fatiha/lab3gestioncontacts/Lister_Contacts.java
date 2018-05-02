package com.example.fatiha.lab3gestioncontacts;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lister_Contacts extends AppCompatActivity {

    protected  ListView listeContacts;

    protected ListAdapter adapter;
    protected ImageButton btRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_contacts);

        btRetour=(ImageButton)findViewById(R.id.imageButton);
        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listeContacts= (ListView)findViewById(R.id.ilistview);

        lister();


 /*        adapter= new SimpleCursorAdapter(this,R.layout.layout_item2_listview,cursor,new String[]{"_id","nom","prenom","telephone"},new int[]{R.id.lid,R.id.lnom,R.id.lprenom,R.id.ltel});
         listeContacts.setAdapter(adapter);*/

        }
    public void lister() {

        final ArrayList<HashMap<String, Object>> tabContacts = new ArrayList<HashMap<String, Object>>();


        String url = "http://10.0.2.2/ContactsControleur_Android/PHP/listeContacts.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            HashMap<String, Object> map;
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unContact;
                                for(i=1;i<jsonResponse.length();i++){
                                    unContact=jsonResponse.getJSONObject(i);
                                    map= new HashMap<String, Object>();


                                    map.put("idl", unContact.getString("idcontact"));
                                    map.put("mnom", unContact.getString("nom"));
                                    map.put("mprenom", unContact.getString("prenom"));
                                    map.put("mtelephone", unContact.getString("telephone"));

                                    tabContacts.add(map);
                                }

                                SimpleAdapter monAdapter = new SimpleAdapter (Lister_Contacts.this, tabContacts, R.layout.layout_item2_listview,
                                        new String[] {"idl", "mnom", "mprenom", "mtelephone"},
                                        new int[] {R.id.lid,R.id.lnom,R.id.lprenom,R.id.ltel});
                                listeContacts.setAdapter(monAdapter);
                            }
                            else{}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Lister_Contacts.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);

    }









}
