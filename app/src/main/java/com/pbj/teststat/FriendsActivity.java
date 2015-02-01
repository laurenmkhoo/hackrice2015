package com.pbj.teststat;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FriendsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_friends);


        setListAdapter(new FriendAdapter(this, MessageListActivity.getSMSPeople()));
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

    public void goToFriendProfile(View view) {
        Intent intent = new Intent(this, FriendProfile.class);
        System.out.println("\n\n\n\n\n\n\n\n\n\nActivity: " + ((Button) view).getContentDescription());
        intent.putExtra(FriendProfile.PERSON, MessageListActivity.getSMSPeople().get(((Button) view).getContentDescription()));
        startActivity(intent);
    }

}
