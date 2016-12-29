package com.mattchiaravalloti.commonactors;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioButton actorOption = (RadioButton)findViewById(R.id.commonActorsOption);
        actorOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((TextView)findViewById(R.id.entity1Label)).setText("Movie 1");
                    ((TextView)findViewById(R.id.entity2Label)).setText("Movie 2");
                    ((Button)findViewById(R.id.findButton)).setText("Find Common Actors!");
                }
            }
        });

        RadioButton movieOption = (RadioButton)findViewById(R.id.commonMoviesOption);
        movieOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((TextView)findViewById(R.id.entity1Label)).setText("Actor 1");
                    ((TextView)findViewById(R.id.entity2Label)).setText("Actor 2");
                    ((Button)findViewById(R.id.findButton)).setText("Find Common Movies!");
                }
            }
        });
    }

    public void findCommonActors(View v) {
        EditText entity1 = (EditText)findViewById(R.id.entity1);
        EditText entity2 = (EditText)findViewById(R.id.entity2);

        String name1 = entity1.getText().toString();
        String name2 = entity2.getText().toString();

        if (name1.isEmpty() || name2.isEmpty()) {
            return;
        }

        RadioGroup options = (RadioGroup)findViewById(R.id.optionGroup);
        boolean getCommonActors = options.getCheckedRadioButtonId() == R.id.commonActorsOption;

        Toast.makeText(this, "Please Wait! May take up to 20 seconds.", Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, ListActivity.class);

        i.putExtra("ENTITY_NAME_1", name1);
        i.putExtra("ENTITY_NAME_2", name2);
        i.putExtra("GET_COMMON_ACTORS", getCommonActors);

        startActivityForResult(i,1);
    }
}
