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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class Rankings extends Activity implements OnItemSelectedListener {
    public static final String PEOPLE_LIST = "PEOPLE_LIST";

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

    private static final String[] categories = new String[Person.Category.values().length];
    static {
        int i = 0;
        for (Person.Category c : Person.Category.values()) {
            categories[i] = c.name;
        }
    }

    String selectedCategory;
    Spinner spinner1;

    private List<PersonWithRank> friends = new ArrayList<PersonWithRank>();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_rankings);

        // Get friends from intent, arbitrary ranking at first
//        List<Person> personList = (List<Person>) getIntent().getExtras().get(PEOPLE_LIST);
        int i = 0;
        System.out.println("PEOPLE_LIST: " + MainActivity.PEOPLE_LIST);
        for (Person p : MainActivity.PEOPLE_LIST) {
            PersonWithRank tempPerson = new PersonWithRank(p, i++);
            System.out.println("TEMP_PERSON: " + tempPerson);
            friends.add(tempPerson);
        }

        // Make a spinner and other stuff
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter_state);
        spinner1.setOnItemSelectedListener(this);

        final ListView listview = (ListView) findViewById(R.id.listview);

        // Make and set Adapter
        ArrayAdapter<PersonWithRank> adapter = new ArrayAdapter<PersonWithRank>(this,
                android.R.layout.simple_list_item_1, friends);
        listview.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Display Description
        Toast.makeText(getApplicationContext(), descriptions.get(
                parent.getItemAtPosition(position).toString()), Toast.LENGTH_LONG).show();

        // Select Category
        selectedCategory = parent.getItemAtPosition(position).toString();

        // Sort friends
        Collections.sort(friends, new Comparator<PersonWithRank>() {

            @Override
            public int compare(PersonWithRank lhs, PersonWithRank rhs) {
                return (int) (rhs.p.getRatingFor(selectedCategory) - lhs.p.getRatingFor(selectedCategory));
            }
        });

        // Update friends' ranks for appearance
        for (int i = 0; i < friends.size(); i++) {
            friends.get(i).rank = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * Stores a Person and their CURRENT RANK
     */
    private class PersonWithRank {
        public final Person p;
        public int rank;

        public PersonWithRank(Person p, int rank) {
            this.p = p;
            this.rank = rank;
        }


        public String toString() {
            return p.getName() + "  " + rank;
        }
    }

}
