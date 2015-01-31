package com.pbj.teststat;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MessageListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_message_list);

        List<SMSData> smsList = new ArrayList<SMSData>();

        Uri uri = Uri.parse("content://sms/");
        Cursor c= getContentResolver().query(uri, null, null ,null,null);
        System.out.println("cursor c: " + c);

        // Read the sms data and store it in the list
        if(c.moveToFirst()) {
            Log.d("Line 43", c.getString(c.getColumnIndexOrThrow("body")).toString());
            for(int i=0; i < /*c.getCount()*/ 20; i++) {
                SMSData sms = new SMSData();
                sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
                sms.setId(c.getString(c.getColumnIndexOrThrow("_id")).toString());
                sms.setTime(c.getString(c.getColumnIndexOrThrow("date")).toString());

                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    sms.setFolderName("inbox");
                } else {
                    sms.setFolderName("sent");
                }

                smsList.add(sms);

                c.moveToNext();
            }
        }
        c.close();

        // Set smsList in the ListAdapter
        setListAdapter(new ListAdapter(this, smsList));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_list, menu);
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SMSData sms = (SMSData)getListAdapter().getItem(position);

        Toast.makeText(getApplicationContext(), sms.getBody(), Toast.LENGTH_SHORT).show();
        Log.d("onListItemClick", sms.getBody());

    }

}
