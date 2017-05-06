package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by panda_000 on 5/4/2017.
 */

public class ActivityActivityArrayAdapter extends ArrayAdapter<ActivityItem> {
    private Context context;
    private ArrayList<ActivityItem> items;
    private boolean edit;

    public ActivityActivityArrayAdapter(Context ctx, ArrayList<ActivityItem> list, boolean edit) {
        super(ctx, R.layout.adapter_activity);

        context = ctx;
        items = list;
        this.edit = edit;
    }
    public View getView (int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.adapter_activity, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.activity_adapter_NAME);
        TextView diff = (TextView) rowView.findViewById(R.id.activity_adapter_DIFF);
        TextView coun = (TextView) rowView.findViewById(R.id.activity_adapter_COUNT);

        final int pos = position;
        final String text = getItem(position).getName();
        final int diffi = getItem(position).getDiff();
        final int count = getItem(position).getSize();
        name.setText(text);
        diff.setText(String.valueOf(diffi));
        coun.setText(String.valueOf(count));
        rowView.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                ((ActivityActivity)context).onClick(view,getItem(pos));
            }
        });

        LinearLayout layout = (LinearLayout)rowView.findViewById(R.id.activity_adapter_EDITOR);
        layout.setVisibility(edit?View.VISIBLE:View.INVISIBLE);
//        Button delet = (Button)rowView.findViewById(R.id.activity_adapter_DELETE);
        Button set = (Button)rowView.findViewById(R.id.activity_adapter_SETDIFF);
        final NumberPicker pick = (NumberPicker)rowView.findViewById(R.id.activity_adapter_PICKDIFF);
        Button fave = (Button)rowView.findViewById(R.id.activity_adapter_FAVE);

//        delet.setEnabled(edit);
        fave.setEnabled(edit);
        set.setEnabled(edit);
        pick.setEnabled(edit);

        pick.setWrapSelectorWheel(false);
        pick.setMinValue(0);
        pick.setMaxValue(10);
        pick.setValue(diffi);

//        delet.setOnClickListener(new View.OnClickListener() {
//            public void onClick (View view){
//                ((ActivityActivity)context).delete(view,getItem(pos));
//            }
//        });
        set.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                ((ActivityActivity)context).set(view,pick.getValue());
            }
        });
        fave.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                ((ActivityActivity)context).favorite(view,getItem(pos));
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
