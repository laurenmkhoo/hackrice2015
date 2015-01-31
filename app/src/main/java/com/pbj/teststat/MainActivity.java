package com.pbj.teststat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ListView;
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
    }

    public void onMessageButtonClick(View view) {
        Intent intent = new Intent(this, MessageListActivity.class);
        startActivity(intent);
    }

}