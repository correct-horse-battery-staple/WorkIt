package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by panda_000 on 5/4/2017.
 */

public class ActivityActivityArrayAdapter extends ArrayAdapter<ActivityItem> {
    private Context context;
    private ArrayList<ActivityItem> items;

    public ActivityActivityArrayAdapter(Context ctx, ArrayList<ActivityItem> list) {
        super(ctx, R.layout.adapter_activity);

        context = ctx;
        items = list;
    }
    public View getView (int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.adapter_activity, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.activity_adapter_NAME);
        TextView diff = (TextView) rowView.findViewById(R.id.activity_adapter_DIFF);

        final int pos = position;
        final String text = getItem(position).getName();
        final double diffi = getItem(position).getDiff();
        name.setText(text);
        diff.setText(""+diffi);
        rowView.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                ((ActivityActivity)context).onClick(view,getItem(pos));
            }
        });
        return rowView;
    }

    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public ActivityItem getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
