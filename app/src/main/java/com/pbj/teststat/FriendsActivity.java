package com.pbj.teststat;

import android.app.ListActivity;
import android.graphics.Typeface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;


public class FriendsActivity extends ListActivity {
    private ArrayList<String> peopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_friends); //new
        View header = getLayoutInflater().inflate(R.layout.header, null);
        ListView listView = getListView();
        listView.addHeaderView(header);
        setListAdapter(new FriendAdapter(this, MessageListActivity.getSMSPeople()));

        //font manipulation occurs  here
        TextView tv = (TextView)findViewById(R.id.topheader);
        Typeface tfBold = Typeface.createFromAsset(getAssets(), "fonts/BLANCH_CONDENSED_LIGHT.otf");
        tv.setTypeface(tfBold);

        // Save people list for future
        peopleList = (ArrayList<String>) getIntent().getExtras().get(Rankings.PEOPLE_LIST);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
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

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }
    public void goToFriendProfile(View view) {
        Intent intent = new Intent(this, FriendProfile.class);
        intent.putExtra(FriendProfile.PERSON,
                MessageListActivity.getSMSPeople().get(((Button) view).getContentDescription()));
        intent.putExtra(Rankings.PEOPLE_LIST, peopleList);
        startActivity(intent);
    }

}
