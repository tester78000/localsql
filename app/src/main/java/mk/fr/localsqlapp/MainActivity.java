package mk.fr.localsqlapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.mk.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView contactListView;
    private List<Map<String, String>> contactList;
    private Integer selectedIndex;
    private Map<String, String> selectedPersonn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference au widget ListView sur le layout
        contactListView = findViewById(R.id.contactListView);


        //Recuperation de la liste des contacts
        contactList = this.getAllContacts();

        //Création d'un contactArrayAdapter
        ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this, contactList);

        //Definition de l'adapter de notre listView
        contactListView.setAdapter(contactAdapter);

        contactListView.setOnItemClickListener(this);

    }

    /**
     * lancement de l'activité formulaire en appuyant sur le bouton
     *
     * @param view
     */

    public void onAddContact(View view) {
        Intent formIntent = new Intent(this, FormActivity.class);
        startActivity(formIntent);

    }

    private List<Map<String, String>> getAllContacts() {
        //Instanciation de la connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(this);

        //Execution de la requête de selection
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT name, first_name, email FROM contacts", null);

        //Instanciation de la liste qui recevra les données
        List<Map<String, String>> contactList = new ArrayList<>();


        //Parcours du curseur
        while (cursor.moveToNext()) {
            Map<String, String> contactCols = new HashMap<>();
            contactCols.put("name", cursor.getString(0));
            contactCols.put("firstName", cursor.getString(1));
            contactCols.put("email", cursor.getString(2));

            //Ajout du map à la list
            contactList.add(contactCols);
        }


        return contactList;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
        
        this.selectedIndex = position;
        this.selectedPersonn = this.contactList.get(selectedIndex);

        Toast.makeText(this, position, Toast.LENGTH_SHORT).show();


        //Instanciation de la connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(view.getContext());



        //Insertion des données
        try {

            db.getWritableDatabase().delete("contacts",id + "="+position , null);


            //Reference au widget ListView sur le layout
            contactListView = findViewById(R.id.contactListView);


            //Recuperation de la liste des contacts
            contactList = this.getAllContacts();

            //Création d'un contactArrayAdapter
            ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this, contactList);

            //Definition de l'adapter de notre listView
            contactListView.setAdapter(contactAdapter);

        } catch (SQLiteException ex) {
            Log.e("SQL EXCEPTION", ex.getMessage());
        }
        Toast.makeText(this, "Contact supprimé", Toast.LENGTH_SHORT).show();


    }

    

}
