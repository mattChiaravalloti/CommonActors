package com.mattchiaravalloti.commonactors;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;
import java.util.Set;


public class ListActivity extends ActionBarActivity {
    private static final String BASE_URL = "http://www.imdb.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // get the movie titles that were argued in the main activity
        String name1 = getIntent().getStringExtra("ENTITY_NAME_1");
        String name2 = getIntent().getStringExtra("ENTITY_NAME_2");

        boolean getCommonActors = getIntent().getBooleanExtra("GET_COMMON_ACTORS", true);

        if (!getCommonActors) {
            ((TextView)findViewById(R.id.heading)).setText("Common movies for");
            setTitle("Common Movies");
        } else {
            setTitle("Common Actors");
        }

        TextView headingTitle1 = (TextView)findViewById(R.id.headingTitle1);
        headingTitle1.setText(name1.toUpperCase());

        TextView headingTitle2 = (TextView)findViewById(R.id.headingTitle2);
        headingTitle2.setText(name2.toUpperCase());

        // adapted from android developer website
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask(getCommonActors).execute(name1, name2);
        } else {
            ((TextView)findViewById(R.id.listOfResults)).setText("No network connection available.");
        }
    }

    public void onBack(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    // adapted from: http://developer.android.com/training/basics/network-ops/connecting.html
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        private boolean getCommonActors;

        public DownloadWebpageTask(boolean getCommonActors) {
            super();
            this.getCommonActors = getCommonActors;
        }

        @Override
        protected String doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the first entity name
            Map<String,String> commonResults;

            if (this.getCommonActors) {
                commonResults = IMDBParser.getCommonActors(params);
            } else {
                commonResults = IMDBParser.getCommonMovies(params);
            }

            StringBuilder resultsList = new StringBuilder();

            for (Map.Entry<String,String> result : commonResults.entrySet()) {
                resultsList.append("<a href='")
                        .append(BASE_URL)
                        .append(result.getValue())
                        .append("'>")
                        .append(result.getKey())
                        .append("</a>")
                        .append("<br>");
            }

            return resultsList.toString();

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            TextView resultsList = (TextView) findViewById(R.id.listOfResults);
            resultsList.setText(Html.fromHtml(result));
            resultsList.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
