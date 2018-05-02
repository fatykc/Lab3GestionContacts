package com.example.fatiha.lab3gestioncontacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    Button btIns,btLister,btChercher,btEffacer,btChanger,btAppeler;
  //  GestionContacts Mydb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btIns =(Button)findViewById(R.id.button1);
        btLister=(Button)findViewById(R.id.button2);
        btChercher= (Button)findViewById(R.id.button3);
        btEffacer=(Button)findViewById(R.id.button4);
        btChanger=(Button)findViewById(R.id.button5);
        btAppeler=(Button)findViewById(R.id.button6);

        btIns.setOnClickListener(this);
        btLister.setOnClickListener(this);
        btChercher.setOnClickListener(this);
        btEffacer.setOnClickListener(this);
        btChanger.setOnClickListener(this);
        btAppeler.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button1:
                Intent intent =new Intent(MainActivity.this,Inserer_Contact.class);
                startActivity(intent);

            break;

            case R.id.button2:
                Intent intList= new Intent(MainActivity.this,Lister_Contacts.class);
                startActivity(intList);


            break;

            case R.id.button3:
                Intent intCher= new Intent(MainActivity.this,Chercher_contact.class);
                startActivity(intCher);


            break;

            case R.id.button4:
                Intent intEfface= new Intent(MainActivity.this,Effacer_Contact.class);
                startActivity(intEfface);

            break;

            case R.id.button5:
                Intent intModifier= new Intent(MainActivity.this,ModifierTelephone.class);
                startActivity(intModifier);

            break;




            case R.id.button6:

                Intent intAppel=new Intent(MainActivity.this,Appel_Contact.class);
                startActivity(intAppel);

            break;

            default:


        }


    }
}
