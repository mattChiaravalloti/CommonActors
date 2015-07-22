package com.mattchiaravalloti.commonactors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;


public class ListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // get the movie titles that were argued in the main activity
        String title1 = getIntent().getStringExtra("MOVIE_TITLE_1");
        String title2 = getIntent().getStringExtra("MOVIE_TITLE_2");

        TextView headingTitle1 = (TextView)findViewById(R.id.headingTitle1);
        headingTitle1.setText(title1.toUpperCase());

        TextView headingTitle2 = (TextView)findViewById(R.id.headingTitle2);
        headingTitle2.setText(title2.toUpperCase());

        // adapted from android developer website
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(title1, title2);
        } else {
            ((TextView)findViewById(R.id.listOfActors)).setText("No network connection available.");
        }
    }

    public void onBack(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    // adapted from: http://developer.android.com/training/basics/network-ops/connecting.html
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            // params comes from the execute() call: params[0] is the url.
            Set<String> commonActors = URLParser.getCommonActors(params[0], params[1]);

            String actorList = "";

            for (String actor : commonActors) {
                actorList += actor + "\n";
            }

            return actorList;

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            ((TextView)findViewById(R.id.listOfActors)).setText(result);
        }
    }
}
