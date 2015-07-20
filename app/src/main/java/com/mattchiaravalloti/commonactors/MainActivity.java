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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void findCommonActors(View v) {
        EditText movie1 = (EditText)findViewById(R.id.movie1);
        EditText movie2 = (EditText)findViewById(R.id.movie2);

        String title1 = movie1.getText().toString();
        String title2 = movie2.getText().toString();

        Toast.makeText(this, "Please Wait! May take up to 20 seconds.", Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, ListActivity.class);

        i.putExtra("MOVIE_TITLE_1", title1);
        i.putExtra("MOVIE_TITLE_2", title2);

        startActivityForResult(i,1);
    }
}
