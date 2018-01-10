package mk.fr.localsqlapp;

import android.app.ActionBar;
import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.mk.database.DatabaseHandler;

public class FormActivity extends AppCompatActivity {

    private String idSelected;
    private EditText editTextNom;
    private EditText editTextPrenom;
    private EditText editTextEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        this.idSelected = getIntent().getStringExtra("id");

        if (idSelected != null) {
            //Initialisation pour test
            this.editTextNom.setText(getIntent().getStringExtra("name"));
            this.editTextPrenom.setText(getIntent().getStringExtra("firstName"));
            this.editTextEmail.setText(getIntent().getStringExtra("email"));
        }
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onValid(View v) {
        //Récuperation de la saisie de l'utilisateur
        String name = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
        String firstName = ((EditText) findViewById(R.id.editTextPrenom)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();


        //Instanciation de la connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(this);


        //définition des données à inserer
        ContentValues insertValues = new ContentValues();

        insertValues.put("name", name);
        insertValues.put("first_name", firstName);
        insertValues.put("email", email);

        if (idSelected == null) {
            //Insertion des données
            try {
                db.getWritableDatabase().insert("contacts", null, insertValues);

                Toast.makeText(this, "Insertion OK", Toast.LENGTH_SHORT).show();

                editTextNom.setText("");
                editTextPrenom.setText("");
                editTextEmail.setText("");

                setResult(RESULT_OK);
                finish();


            } catch (SQLiteException ex) {
                Log.e("SQL EXCEPTION", ex.getMessage());
            }

        } else {


            try {
                String[] params = {idSelected};
                db.getWritableDatabase().update("contacts", insertValues, "id=?", params);
                //db.getWritableDatabase().update("contacts",insertValues ,"id="+idSelected, null);

                Toast.makeText(this, "Mise a jour OK", Toast.LENGTH_SHORT).show();

                editTextNom.setText("");
                editTextPrenom.setText("");
                editTextEmail.setText("");

                setResult(RESULT_OK);

                finish();

            } catch (SQLiteException ex) {
                Log.e("SQL EXCEPTION", ex.getMessage());
            }


        }
    }


}
