package mk.fr.localsqlapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import fr.mk.database.DatabaseHandler;
import mk.fr.localsqlapp.model.Contact;


public class ContactArrayAdapter extends ArrayAdapter {

    private Activity context;
    private List<Contact> data;
    private int ressource;
    private LayoutInflater inflater;


    public ContactArrayAdapter(@NonNull Context context,  @NonNull List<Contact> data) {
        super(context, 0, data);

        this.data = data;
        this.context = (Activity) context;
        this.inflater = this.context.getLayoutInflater(); //transformateur d'un fichier xml en objet
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Instanciation de la vue
        View view = this.inflater.inflate(R.layout.list_view_contact, parent, false);

        //Recuperation des données d'une ligne
        Contact contactData = this.data.get(position);

        //Liaison entre les données et la vue
        TextView nameTextView = view.findViewById(R.id.listTextViewName);
        nameTextView.setText(contactData.getName().toUpperCase());

        TextView FirstNameTextView = view.findViewById(R.id.listTextViewFirstname);
        FirstNameTextView.setText(contactData.getFirst_name());

        TextView emailTextView = view.findViewById(R.id.listTextViewEmail);
        emailTextView.setText(contactData.getEmail());


        return view;
    }
}
