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
import android.widget.Toast;

import java.util.HashMap;


public class Rankings extends Activity implements OnItemSelectedListener {

    String selectedCategory;
    Spinner spinner1;
    private String[] categories = {Person.Category.MOST_PROFANE.name, Person.Category.MOST_VAIN.name,
           Person.Category.LONGEST_WORD.name,Person.Category.PARTY_ANIMAL.name, Person.Category.THEY_TEXT_MORE.name,
           Person.Category.THEY_TEXT_LESS.name, Person.Category.LAUGHER.name, Person.Category.KNOW_NOTHING.name,
           Person.Category.MISS_BASIC.name,Person.Category.LOTS_OF_TEXTS.name, Person.Category.LONG_TEXTS.name};

    private String[] friends =  {"Lauren", "Bao", "Jesse",
            "Cassie", "Ben", "Phillip"};;

    private static HashMap<String, String> descriptions = new HashMap<String, String>();

    static {
        descriptions.put(Person.Category.MOST_PROFANE.name, "Definitely does not kiss their mother with their mouth.");
        descriptions.put(Person.Category.MOST_VAIN.name, "I don't think these friends are aware that the Earth revolves around the sun and not them.");
        descriptions.put(Person.Category.LONGEST_WORD.name, "These friends are trying to compensate for something with their large-word-knowing");
        descriptions.put(Person.Category.PARTY_ANIMAL.name, "This friend is probably most likely to get insurance on their liver");
        descriptions.put(Person.Category.THEY_TEXT_MORE.name, "You should probably text these people a bit more...");
        descriptions.put(Person.Category.THEY_TEXT_LESS.name, "I wouldn't be surprised if these people had shrines dedicated to you.");
        descriptions.put(Person.Category.LAUGHER.name, "They're not actually laughing out loud, just so you know.");
        descriptions.put(Person.Category.KNOW_NOTHING.name, "If you were on a trivia show, don't bother calling these people");
        descriptions.put(Person.Category.MISS_BASIC.name, "Don't get too close to these people, I hear basic-ness is contagious");
        descriptions.put(Person.Category.LOTS_OF_TEXTS.name, "The killer of limited text customers world-wide");
        descriptions.put(Person.Category.LONG_TEXTS.name, "They've probably spent an hour constructing each text");
    }
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
        Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        selectedCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




}
