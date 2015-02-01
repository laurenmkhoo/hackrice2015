package com.pbj.teststat;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phillippan on 2/1/15.
 */
public class CustomListAdapter extends ArrayAdapter<ListData> {
    private ListData data[];
    private Typeface tf;
    private int id;
    // List context
    private final Context context;

    public CustomListAdapter(Context inputContext, int textViewResourceId, ListData []arr) {
        super(inputContext, R.layout.activity_friend_profile, arr);
        data = arr;
        context = inputContext;
        id = textViewResourceId;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/BLANCH_CONDENSED_LIGHT.otf");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_friend_profile, parent, false);

        TextView textView = (TextView)rowView.findViewById(R.id.textView);
        textView.setTypeface(tf);


        return rowView;
    }



}