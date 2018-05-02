package com.example.fatiha.lab3gestioncontacts;

/**
 * Created by Fatiha on 2018-03-14.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class GestionContacts extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="gestionContacts";
    public static final String TABLE_NAME="contacts";
    public static final String COL_ID="_id";
    public static final String COL_NOM="nom";
    public static final String COL_PRENOM="prenom";
    public static final String COL_TELEPHONE="telephone";


    public GestionContacts(Context context){

        super(context,DATABASE_NAME,null,2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Création de la table contacts

        String sql= "CREATE TABLE IF NOT EXISTS contacts("
                +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT,"+
                "prenom TEXT,"+
                "telephone TEXT)";

        db.execSQL(sql);

        // Initialisation de la table contacts

        ContentValues values = new ContentValues();

        values.put("nom","Kaci");
        values.put("prenom","Faty");
        values.put("telephone","514-402-1228");
        db.insert("contacts",null,values);

        values.put("nom","Hachi");
        values.put("prenom","Leila");
        values.put("telephone","514-403-1828");
        db.insert("contacts",null,values);

        values.put("nom","Peterson");
        values.put("prenom","Jordan");
        values.put("telephone","514-482-1567");
        db.insert("contacts",null,values);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("DROP TABLE IF EXISTS contacts");
     onCreate(db);
    }

    public boolean updateData(int id,String nom,String prenom,String telephone){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
       // values.put("id",id);
        values.put("nom",nom);
        values.put("prenom",prenom);
        values.put("telephone",telephone);

        db.update(TABLE_NAME,values,"_id=="+ id,null);
        return true;



    }


    public Cursor chercherContact( int id){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor= db.rawQuery("SELECT * FROM contacts WHERE "+ "_id=="+id,null);


      return cursor;
    }

    public Integer deleteData(String id){

        SQLiteDatabase db=this.getWritableDatabase();

        return db.delete(TABLE_NAME, "_id = ?",new String[]{id});

    }

    public String ListeDesContacts(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacts",null);
         if(cursor.getCount()==0){
           return "Pas de contacts";
        }else {
             StringBuffer buffer = new StringBuffer();
             while (cursor.moveToNext()){
                 buffer.append("ID : "+cursor.getString(0)+ "\n");
                 buffer.append("Nom : "+ cursor.getString(1)+ "\n");
                 buffer.append("Pénom : "+ cursor.getString(2)+ "\n");
                 buffer.append("Téléphone: "+ cursor.getString(3)+ "\n\n");


             }
             // affichage de toutes les données

             return buffer.toString();
        }



}

}
