package com.mattchiaravalloti.commonactors;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void findCommonActors(View v) {
        EditText movie1 = (EditText)findViewById(R.id.movie1);
        EditText movie2 = (EditText)findViewById(R.id.movie2);

        String title1 = movie1.getText().toString();
        String title2 = movie2.getText().toString();

        if (title1.isEmpty() || title2.isEmpty()) {
            return;
        }

        Toast.makeText(this, "Please Wait! May take up to 20 seconds.", Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, ListActivity.class);

        i.putExtra("MOVIE_TITLE_1", title1);
        i.putExtra("MOVIE_TITLE_2", title2);

        startActivityForResult(i,1);
    }
}
