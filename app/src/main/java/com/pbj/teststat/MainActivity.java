package com.pbj.teststat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Main Activity. Displays a list of numbers.
 *
 */
public class MainActivity extends ActionBarActivity {

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

        loadFiles();
    }

    /**
     * Called when the
     * @param view
     */
    public void goToRankings(View view) {
        startActivity(new Intent(this, Rankings.class));
    }

    public void goToMyProfile(View view) {
        startActivity(new Intent(this, MyProfile.class));
    }

    public void onMessageButtonClick(View view) {
        startActivity(new Intent(this, MessageListActivity.class));
    }

    public void goToFriends(View view){
        startActivity(new Intent(this, FriendsActivity.class));
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
