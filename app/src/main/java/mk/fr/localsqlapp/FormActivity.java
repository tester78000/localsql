package mk.fr.localsqlapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import fr.mk.database.DatabaseHandler;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
    }

    public void onValid(View v){
        DatabaseHandler db = new DatabaseHandler(this);


    }



}
