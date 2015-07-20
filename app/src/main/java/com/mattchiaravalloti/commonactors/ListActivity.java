package com.mattchiaravalloti.commonactors;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Set;


public class ListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // get the movie titles that were argued in the main activity
        final String title1 = getIntent().getStringExtra("MOVIE_TITLE_1");
        final String title2 = getIntent().getStringExtra("MOVIE_TITLE_2");

        // access the internet on a new thread
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> commonActors = URLParser.getCommonActors(title1, title2);

                // set the singleton actor store to be the common actors between these 2 movies
                ActorListStore als = ActorListStore.getInstance();
                als.setCommon(commonActors);
            }
        });

        // start the thread that finds common actors and wait for it to finsih before continuing.
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get the common actors from these 2 movies from the singleton actor store
        ActorListStore als = ActorListStore.getInstance();

        Set<String> commonActors = als.getCommon();

        RelativeLayout rView = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        TextView heading = new TextView(this);
        heading.setText("Common actors in " + title1.toUpperCase() + " and " + title2.toUpperCase());
        heading.setY(30);

        rView.addView(heading, params);


        float y = 150;

        for (String actor : commonActors) {
            TextView actorText = new TextView(this);
            actorText.setText(actor);

            actorText.setY(y);

            rView.addView(actorText);

            y += 75;
        }

        Button backButton = new Button(this);
        backButton.setText("Go back");

        final Activity realThis = this;

        backButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(realThis, MainActivity.class);
                startActivityForResult(i,0);
            }
        });

        backButton.setY(y);

        rView.addView(backButton);

        setContentView(rView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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
}
