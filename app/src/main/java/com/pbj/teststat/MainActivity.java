package com.pbj.teststat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
 * Main Activity. Displays a list of numbers.
 *
 */

public class MainActivity extends ActionBarActivity {
    private static final String PREFS_NAME = "preferences";
    public static ArrayList<Person> PEOPLE_LIST = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // font manipulation occurs from here
        ArrayList <View> views = getViewsByTag((ViewGroup)findViewById(R.id.fun), "button");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/BLANCH_CONDENSED_LIGHT.otf");
        for (int i = 0; i < views.size(); i++) {
            TextView tv = (TextView)(views.get(i));
            tv.setTypeface(tf);
        }
        // More font manipulation
        ArrayList <View> viewsBold = getViewsByTag((ViewGroup) findViewById(R.id.fun), "header");
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
        // Turn off buttons when they won't work
        ((Button) findViewById(R.id.main_btn_me)).setClickable(
                MessageListActivity.getUserPerson() == null);
        ((Button) findViewById(R.id.main_btn_friends)).setClickable(
                MessageListActivity.getUserPerson() == null);
    }

    public void goToMyProfile(View view) {
        if (MessageListActivity.getUserPerson() == null){
            Toast.makeText(getApplicationContext(), "Please run ANALYZE.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, FriendProfile.class);
            intent.putExtra(FriendProfile.PERSON, MessageListActivity.userPerson);
            startActivity(intent);
        }
    }

    public void onAnalyze(View view) {
        startActivity(new Intent(this, MessageListActivity.class));
    }


    public void goToFriends(View view){
        if (MessageListActivity.getSMSPeople() == null){
            Toast.makeText(getApplicationContext(), "Please run ANALYZE.", Toast.LENGTH_LONG).show();
        } else {

            startActivity(new Intent(this, FriendsActivity.class));
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

        SerializationUtil serial = new SerializationUtil();
        //serialize to file
        serial.startSerialize("saved_instance.txt");
        for (Person p: MainActivity.PEOPLE_LIST) {
            try {
                serial.serialize(p);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        serial.serializeFinish();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("loadedBefore", true);

        // Commit the edits!
        editor.commit();
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean loadedBefore = settings.getBoolean("loadedBefore", false);

        if (loadedBefore) {
            SerializationUtil serial = new SerializationUtil();
            serial.startDeserialize("saved_instance.txt");

            MainActivity.PEOPLE_LIST = new ArrayList<Person>();

            try {
                while (true) {
                    MainActivity.PEOPLE_LIST.add((Person)serial.deserialize());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                serial.deserializeFinish();
            }
        }
    }

}
