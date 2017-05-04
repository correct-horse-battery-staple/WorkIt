package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by panda_000 on 4/18/2017.
 */
public class ActivityActivity extends ServerActivity {
    private boolean editMode = false;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        listview = (ListView) findViewById(R.id.activity_listview);

        getData("activities");
        ArrayList<ActivityItem> items = new ArrayList<>();
        ActivityActivityArrayAdapter adapter = new ActivityActivityArrayAdapter(this, items);
        listview.setAdapter(adapter);
    }

    public void edit(View v){
        editMode=!editMode;
    }

    public void add(View v){

    }

    public void onClick(View v, ActivityItem item){
        if(!editMode) {
            putData("activities", "'name':'"+item.getName()+"','difficulty':'" +item.getDiff()+ "'");
        }
        else {

        }
    }

    @Override
    protected void token(String data){
        setErrorMessage("token response received");
        String op = data.split(":")[0];
        if(op.equals("load")) {
            try {
                JSONArray jsonArray = new JSONArray(data.substring(op.length()+1));
                DataPoint[] points = new DataPoint[jsonArray.length()];
                for (int i =0;i<jsonArray.length();i++){
                    JSONObject object = (JSONObject)jsonArray.get(i);
                    String name = (String)(object.get("name"));
                    String unit = (String)object.get("difficulty");
                    long datetime = Long.parseLong((String)object.get("datetime"));
                    //Log.d("app load",value+" "+unit+" "+datetime);
                    Date d = new Date();
                    try {
                        d = dateFormat.parse("" + datetime);
                    }
                    catch(ParseException p){
                        p.printStackTrace();
                    }
                    points[i]=new DataPoint(i+1,(unit.equals("kg")?value*2.20462:value));
                }
                Log.d("server load",data.substring(op.length()+1));
            }
            catch(JSONException i) {
                i.printStackTrace();
            }
        }
        else if(op.equals("store")){
            //setErrorMessage(data);
        }
    }
}
