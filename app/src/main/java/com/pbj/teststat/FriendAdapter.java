package com.pbj.teststat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendAdapter extends ArrayAdapter<String> {
    private final HashMap<String, Person> personMap;
    private final List<String> numberList;

    // List context
    private final Context context;

    public FriendAdapter(Context inputContext,HashMap<String, Person> map) {
        super(inputContext, R.layout.activity_main, new ArrayList(map.keySet()));
        personMap = map;
        numberList = new ArrayList(map.keySet());
        context = inputContext;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_friends, parent, false);

        Button senderPerson = (Button) rowView.findViewById(R.id.friendPerson);

        senderPerson.setText(personMap.get(numberList.get(position)).getName());
        senderPerson.setContentDescription(personMap.get(numberList.get(position)).getID());

        return rowView;
    }
}