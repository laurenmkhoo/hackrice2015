package com.pbj.teststat;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;


public class FriendProfile extends Activity implements OnItemSelectedListener {
    public static final String PERSON = "PERSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        // Get the person for this friend profile
        Person p = (Person) getIntent().getExtras().get(PERSON);
        System.out.println(p);
        ((TextView) findViewById(R.id.headerName))
                .setText(p.getName());


        final ListView listview = (ListView) findViewById(R.id.listview);


        ListData[] values = new ListData[Person.Category.values().length];
        int i = 0;
        for (Person.Category c : Person.Category.values()) {
            values[i++] = new ListData(c.name, c.formula.calculateFor(p));
        }

        ArrayAdapter<ListData> adapter = new ArrayAdapter<ListData>(this,
                android.R.layout.simple_list_item_1, values);
        listview.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_profile, menu);
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
