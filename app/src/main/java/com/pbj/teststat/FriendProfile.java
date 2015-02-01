package com.pbj.teststat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class FriendProfile extends ActionBarActivity implements OnItemSelectedListener {
    public static final String PERSON = "PERSON";

    private ArrayList<Person> peopleList;
    private Person p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        // Save people list for future redirections
        peopleList = (ArrayList<Person>) getIntent().getExtras().get(Rankings.PEOPLE_LIST);

        // Get the person for this friend profile
        p = (Person) getIntent().getExtras().get(PERSON);
        ((TextView) findViewById(R.id.headerName)).setText(p.getName());


        ListData[] values = new ListData[Person.Category.values().length];
        int i = 0;
        for (Person.Category c : Person.Category.values()) {
            values[i++] = new ListData(c.name, c.formula.calculateFor(p));
        }

        ArrayAdapter<ListData> adapter = new ArrayAdapter<ListData>(this,
                android.R.layout.simple_list_item_1, values);
        ((ListView) findViewById(R.id.listview)).setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (p == null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {

            // Go Home
            case R.id.friend_profile_home:
                intent = new Intent(this, MainActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        // Always add people list
        intent.putExtra(Rankings.PEOPLE_LIST, peopleList);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class ListData {
        public final String categoryName;
        public final double value;

        public ListData(String name, Double stat){
            categoryName = name;
            value = stat;
        }


        public String toString() {
            return this.categoryName + " " + "[" +  this.value + "]";
        }
    }
}
