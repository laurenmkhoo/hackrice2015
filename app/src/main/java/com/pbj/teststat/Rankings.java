package com.pbj.teststat;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.Activity;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.view.View;


public class Rankings extends Activity implements OnItemSelectedListener {

    String selectedCategory;
    Spinner spinner1;
    private String[] categories = {Person.Category.MOST_PROFANE.name, Person.Category.MOST_VAIN.name,
           Person.Category.LONGEST_WORD.name,Person.Category.PARTY_ANIMAL.name, Person.Category.THEY_TEXT_MORE.name,
           Person.Category.THEY_TEXT_LESS.name, Person.Category.LAUGHER.name, Person.Category.KNOW_NOTHING.name,
           Person.Category.MISS_BASIC.name,Person.Category.LOTS_OF_TEXTS.name, Person.Category.LONG_TEXTS.name};

    private String[] friends =  {"Lauren", "Bao", "Jesse",
            "Cassie", "Ben", "Phillip"};;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_rankings);

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter_state);
        spinner1.setOnItemSelectedListener(this);

        final ListView listview = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, friends);
        listview.setAdapter(adapter);


    }

    private void orderPersons(){
        for(int i = 0; i < friends.length; i++){

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




}
