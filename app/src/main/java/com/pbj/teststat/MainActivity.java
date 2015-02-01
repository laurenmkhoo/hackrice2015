package com.pbj.teststat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Main Activity.
 *
 */

public class MainActivity extends ActionBarActivity {
    private static final String PREFS_NAME = "preferences";
    public static ArrayList<Person> peopleList;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // font manipulation occurs from here
        ArrayList <View> views = getViewsByTag((ViewGroup)findViewById(R.id.activitymain), "button");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/BLANCH_CONDENSED_LIGHT.otf");
        for (int i = 0; i < views.size(); i++) {
            TextView tv = (TextView)(views.get(i));
            tv.setTypeface(tf);
        }

        // More font manipulation
        ArrayList <View> viewsBold = getViewsByTag((ViewGroup)findViewById(R.id.activitymain), "header");
        Typeface tfBold = Typeface.createFromAsset(getAssets(), "fonts/BLANCH_CONDENSED_INLINE.otf");

        for (int i = 0; i < viewsBold.size(); i++) {
            TextView tvBold = (TextView)(viewsBold.get(i));
            tvBold.setTypeface(tfBold);
        }

        // Read in tags
        loadFiles();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MessageListActivity.getUserPerson() == null) {
            Toast.makeText(getApplicationContext(), "Please run ANALYZE.", Toast.LENGTH_LONG).show();
        }
        // Check if redirected
        if (getIntent() != null && getIntent().getExtras() != null &&
                getIntent().getExtras().get(Rankings.PEOPLE_LIST) != null) {
            peopleList = (ArrayList<Person>) getIntent().getExtras().get(Rankings.PEOPLE_LIST);
        }

        // Turn off buttons when they won't work
        ((Button) findViewById(R.id.main_btn_me)).setClickable(peopleList == null);
        ((Button) findViewById(R.id.main_btn_friends)).setClickable(peopleList == null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Goes to FriendProfile with userPerson
     * @param view the view
     */
    public void goToMyProfile(View view) {
        if (MessageListActivity.getUserPerson() == null){
            Toast.makeText(getApplicationContext(), "Please run ANALYZE.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, FriendProfile.class);
            intent.putExtra(FriendProfile.PERSON, MessageListActivity.userPerson);
            intent.putExtra(Rankings.PEOPLE_LIST, peopleList); // why not?
            startActivity(intent);
        }
    }

    /**
     * Analyzes texts and goes to Rankings
     * @param view the view
     */
    public void onAnalyze(View view) {
        Intent i = new Intent(this, MessageListActivity.class);
        i.putExtra("FIRST_TIME", firstTime);
        firstTime = false;
        startActivity(i);
    }

    /**
     * Goes to FriendActivity
     * @param view
     */
    public void goToFriends(View view){
        if (MessageListActivity.getSMSPeople() == null){
            Toast.makeText(getApplicationContext(), "Please run ANALYZE.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, FriendsActivity.class);
            intent.putExtra(Rankings.PEOPLE_LIST, peopleList);
            startActivity(intent);
        }
    }

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();

        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            final int temp_id = child.getId();

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }


    private void loadFiles() {
        String result = "";
        Resources res = getResources();
        Field [] fields= R.raw.class.getFields();

        for(int count=0; count < fields.length; count++){
            try {
                //String tempField = fields[count].getName();
                //System.out.println("FIELD COUNT: " + fields[count].getInt(fields[count]));
                //System.out.println("INTEGER OF PARTY ANIMAL: " + R.raw.the_party_animal);
                String name = fields[count].getName();
                ArrayList<String> currentArrayList = Person.tagMap.get(name);

                InputStream input = res.openRawResource(fields[count].getInt(fields[count]));
                Scanner scanner = new Scanner(input);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    currentArrayList.add(line);
                }
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
                result = "Error: idk.";
            }
        }
    }

    @Override
    public void onDestroy() {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        super.onDestroy();
        String allPeople = "";
        for (Person p: peopleList) {
            allPeople += p.getStringRepresentation() + " ";
        }

        SharedPreferences settings = getSharedPreferences("preferences", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("people", allPeople);

        // Commit the edits!
        editor.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        peopleList = new ArrayList<Person>();

        SharedPreferences settings = getSharedPreferences("preferences", 0);
        String smooshedPeople = settings.getString("allPeople", "");

        String [] people = smooshedPeople.split(" ");

        for (int i = 0; i < people.length; i++) {
            String temp = people[i];
            String [] x = temp.split(";");

            Person p = new Person(x[2], x[1], x[0]);
            p.setSpecialCounts(Long.parseLong(x[3]), Long.parseLong(x[4]), Long.parseLong(x[5]), Long.parseLong(x[6]), Long.parseLong(x[7]), Long.parseLong(x[8]));
            p.setCountMessages(Long.parseLong(x[9]), Long.parseLong(x[10]));
            p.setCountWords(Long.parseLong(x[11]), Long.parseLong(x[12]));
            p.setCountChars(Long.parseLong(x[13]), Long.parseLong(x[14]));

            peopleList.add(p);
        }

    }


}
