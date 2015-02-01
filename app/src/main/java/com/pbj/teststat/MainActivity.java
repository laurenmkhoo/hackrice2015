package com.pbj.teststat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

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
    private ArrayList<Person> peopleList;

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

        // Check if redirected
        if (getIntent() != null && getIntent().getExtras() != null &&
                getIntent().getExtras().get(Rankings.PEOPLE_LIST) != null) {
            peopleList = (ArrayList<Person>) getIntent().getExtras().get(Rankings.PEOPLE_LIST);
        }
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
        if (peopleList == null){
            Toast.makeText(getApplicationContext(), "Please run ANALYZE first.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, FriendProfile.class);
            intent.putExtra(FriendProfile.PERSON, MessageListActivity.userPerson);
            intent.putExtra(Rankings.PEOPLE_LIST, peopleList);
            startActivity(intent);
        }
    }

    /**
     * Analyzes texts and goes to Rankings
     * @param view the view
     */
    public void onAnalyze(View view) {
        startActivity(new Intent(this, MessageListActivity.class));
    }

    /**
     * Goes to FriendActivity
     * @param view
     */
    public void goToFriends(View view){
        if (peopleList == null){
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

}
