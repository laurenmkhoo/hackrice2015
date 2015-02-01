package com.pbj.teststat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

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

        ArrayList <View> views = getViewsByTag((ViewGroup)findViewById(R.id.fun), "button");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/BLANCH_CONDENSED_LIGHT.otf");
        for (int i = 0; i < views.size(); i++) {
            TextView tv = (TextView)(views.get(i));
            tv.setTypeface(tf);
        }

        ArrayList <View> viewsBold = getViewsByTag((ViewGroup)findViewById(R.id.fun), "header");
        Typeface tfBold = Typeface.createFromAsset(getAssets(), "fonts/BLANCH_CONDENSED_INLINE.otf");
        for (int i = 0; i < viewsBold.size(); i++) {
            TextView tvBold = (TextView)(viewsBold.get(i));
            tvBold.setTypeface(tfBold);
        }
    }

    /**
     * Called when the
     * @param view
     */
    public void goToRankings(View view) {
        startActivity(new Intent(this, Rankings.class));
    }

    public void onMessageButtonClick(View view) {
        Intent intent = new Intent(this, MessageListActivity.class);
        startActivity(intent);
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


}
