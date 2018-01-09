package mk.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private Bundle savedInstanceState;

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
         if (this.selectedIndex != null) {
             this.selectedPersonn = this.contactList.get(selectedIndex);
             formIntent.putExtra("id", selectedPersonn.get("id").toString()  );
             formIntent.putExtra("name", selectedPersonn.get("name").toString()  );
             formIntent.putExtra("firstName", selectedPersonn.get("firstName").toString()  );
             formIntent.putExtra("email", selectedPersonn.get("email").toString()  );
         }
        startActivityForResult(formIntent,1);

    }

    private List<Map<String, String>> getAllContacts() {
        //Instanciation de la connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(this);

        //Execution de la requête de selection
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT id, name, first_name, email FROM contacts", null);

        //Instanciation de la liste qui recevra les données
        List<Map<String, String>> contactList = new ArrayList<>();


        //Parcours du curseur
        while (cursor.moveToNext()) {
            Map<String, String> contactCols = new HashMap<>();
            contactCols.put("id", cursor.getString(0));

            contactCols.put("name", cursor.getString(1));
            contactCols.put("firstName", cursor.getString(2));
            contactCols.put("email", cursor.getString(3));

            //Ajout du map à la list
            contactList.add(contactCols);
        }


        return contactList;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        this.selectedIndex = position;
        this.selectedPersonn = this.contactList.get(selectedIndex);


        Toast.makeText(this, "Contact supprimé " + selectedPersonn.get("firstName"), Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenuOptionDelete:
                deleteSelectedContact();
                break;
            case R.id.mainMenuOptionEdit:
                onAddContact(item.getActionView());
                break;

        }
        return true;
    }

    public void deleteSelectedContact() {

        if (this.selectedIndex != null) {



            //Instanciation de la connexion à la base de données
            DatabaseHandler db = new DatabaseHandler(getBaseContext());


            //Insertion des données
            try {
                String sql = "DELETE From contacts WHERE id=?";
                String[] params = {this.selectedPersonn.get("id")};

                // db.getWritableDatabase().execSQL(sql,params);
                db.getWritableDatabase().delete("contacts", "id" + "=" + selectedPersonn.get("id").toString(), null);
                //db.getWritableDatabase().delete("contacts", "id=?"  , params);


               contactListInit();

            } catch (SQLiteException ex) {
                Log.e("SQL EXCEPTION", ex.getMessage());
                Toast.makeText(this, "Impossible de supprimer", Toast.LENGTH_SHORT).show();
            }

        } else

        {
            Toast.makeText(this, "Vous devez selectionner un contact", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode == RESULT_OK){
            Toast.makeText(this, "Mise a jour effectué", Toast.LENGTH_SHORT).show();

            this.contactListInit();

        }
    }

    private void contactListInit() {

        //Reference au widget ListView sur le layout
        contactListView = findViewById(R.id.contactListView);


        //Recuperation de la liste des contacts
        contactList = this.getAllContacts();

        //Création d'un contactArrayAdapter
        ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this, contactList);

        //Definition de l'adapter de notre listView
        contactListView.setAdapter(contactAdapter);



    }
}
