package fr.mk.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mk.fr.localsqlapp.model.Contact;

/**
 * Created by Formation on 10/01/2018.
 */

public class ContactDAO {

    private DatabaseHandler db;

    public ContactDAO(DatabaseHandler db) {
        this.db = db;
    }

    public Contact findOneById(int id) throws SQLiteException {
        String[] params = {String.valueOf(id)};
        String sql = "SELECT first_name,name,email FROM contacts WHERE id =?";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, params);
        // Cursor cursor = this.db.getReadableDatabase().rawQuery("SELECT first_name,name,email FROM contacts WHERE id ='?'", params);

        // instanciation d'un contact
        Contact contact = new Contact();

        //hydratation du contact
        if (cursor.moveToNext()) {
            hydrateContact(cursor);
        }

        //fermeture cursor
        cursor.close();

        return contact;
    }


    public List<Contact> findAll() {

        List<Contact> contactList = new ArrayList<>();

        //execution de la requete SQL
        String sql = "SELECT id,first_name,name,email FROM contacts ";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, null);
        // Cursor cursor = this.db.getReadableDatabase().rawQuery("SELECT first_name,name,email FROM contacts WHERE id ='?'", params);
        while (cursor.moveToNext()) {
            contactList.add(this.hydrateContact(cursor));
        }

        return contactList;
    }

    private Contact hydrateContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getLong(0));
        contact.setFirst_name(cursor.getString(1));
        contact.setName(cursor.getString(2));
        contact.setEmail(cursor.getString(3));
        return contact;
    }

    public String deleteSelectedContact(Contact selectedPersonn) {

        String message;
        //Instanciation de la connexion à la base de données
        //DatabaseHandler db = new DatabaseHandler(getBaseContext());

        if (selectedPersonn != null) {
            //Insertion des données
            try {
                String sql = "DELETE From contacts WHERE id=?";
                String[] params = {String.valueOf(selectedPersonn.getId())};

                // db.getWritableDatabase().execSQL(sql,params);
                db.getWritableDatabase().delete("contacts", "id" + "=" + selectedPersonn.getId(), null);
                //db.getWritableDatabase().delete("contacts", "id=?"  , params);
                message = "Contact supprimé";


            } catch (SQLiteException ex) {
                Log.e("SQL EXCEPTION", ex.getMessage());

                message = "Impossible de supprimer";

            }
        } else {
            message = "Vous devez selectionner un contact";
        }
        return message;
    }


}
