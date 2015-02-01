package com.pbj.teststat;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MessageListActivity extends ListActivity {

    List<SMSData> smsList;
    static HashMap<String, Person> smsPeople;
    static Person fartbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        smsList = new ArrayList<SMSData>();
        smsPeople = new HashMap<String, Person>();
        TelephonyManager tele = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        fartbox = new Person(tele.getLine1Number(), tele.getSimOperatorName());

        Uri uri = Uri.parse("content://sms/");
        Cursor c= getContentResolver().query(uri, null, null ,null,null);
        System.out.println("cursor c: " + c);

        // Read the sms data and store it in the list
        if(c.moveToFirst()) {
            Log.d("Line 43", c.getString(c.getColumnIndexOrThrow("body")).toString());
            for(int i=0; i < /*c.getCount()*/ 20; i++) {
                SMSData sms = new SMSData();
                String messageBody = c.getString(c.getColumnIndexOrThrow("body")).toString();
                sms.setBody(messageBody);
                String contactNumber = c.getString(c.getColumnIndexOrThrow("address")).toString();
                sms.setNumber(contactNumber);
                sms.setId(c.getString(c.getColumnIndexOrThrow("_id")).toString());
                sms.setTime(c.getString(c.getColumnIndexOrThrow("date")).toString());
                String contactName = getContactName(getApplicationContext(), c.getString(c.getColumnIndexOrThrow("address")));
                sms.setName(contactName);
                String contactID = getContactID(getApplicationContext(), c.getString(c.getColumnIndexOrThrow("address")));

                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    sms.setFolderName(SMSData.INBOX);
                } else {
                    sms.setFolderName(SMSData.OUTBOX);
                }

                smsList.add(sms);
                if (!smsPeople.containsKey(contactID)) {
                    Person newPerson = new Person(contactNumber, contactName);
                    smsPeople.put(contactID, newPerson);
                }
                smsPeople.get(contactID).update(sms);

                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    sms.setFolderName(SMSData.OUTBOX);
                } else {
                    sms.setFolderName(SMSData.INBOX);
                }
                fartbox.update(sms);

                c.moveToNext();
            }
        }
        System.out.println(smsPeople.keySet());
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

    private String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

    private String getContactID(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,new String[] { ContactsContract.Contacts.LOOKUP_KEY }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactID = null;
        if (cursor.moveToFirst()) {
            contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactID;
    }

    private String getMyProfile(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,new String[] { ContactsContract.Contacts.LOOKUP_KEY }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactID = null;
        if (cursor.moveToFirst()) {
            contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactID;
    }

    public static HashMap<String, Person> getSMSPeople(){
        if (smsPeople == null){
            return null;
        }
        return smsPeople;
    }

    public static Person getMe(){
        if (fartbox == null){
            return null;
        }
        return fartbox;
    }

}
