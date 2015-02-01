package com.pbj.teststat;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.Activity;
import android.widget.ListView;


public class Rankings extends Activity {

    Spinner spinner1;
    private String[] names = {"Profane","Vain","Hungry","Party","Slow Response"};

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_rankings);

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, names);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter_state);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Lauren", "Bao", "Jesse",
                "Cassie", "Ben", "Phillip"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        listview.setAdapter(adapter);

    }
}
