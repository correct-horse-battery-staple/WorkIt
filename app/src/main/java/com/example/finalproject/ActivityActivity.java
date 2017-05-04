package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by panda_000 on 4/18/2017.
 */
public class ActivityActivity extends ServerActivity {
    private boolean editMode = false;
    private ListView listview;
    private ArrayList<ActivityItem> items;
    private HashMap<String, Integer> handles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        listview = (ListView) findViewById(R.id.activity_LISTVIEW);
        items = new ArrayList<>();
        handles = new HashMap<>();

        getData("activities");
    }

    public void edit(View v){
        editMode=!editMode;
    }

    public void add(View v){
        EditText editText = (EditText)findViewById(R.id.activity_TYPENEW);
        String name = editText.getText().toString();
        if(!handles.containsKey(name)){
            items.add(new ActivityItem(name,5));
            handles.put(name,items.size()-1);
            ActivityActivityArrayAdapter adapter = new ActivityActivityArrayAdapter(this, items);
            listview.setAdapter(adapter);
        }
    }

    public void onClick(View v, ActivityItem item){
        if(!editMode) {
            putData("activities", "'name':'"+item.getName()+"','difficulty':'" +item.getDiff()+ "'");
            getData("activities");
        }
        else {
            //do stuff
        }
    }

    @Override
    protected void token(String data){
        setErrorMessage("token response received");
        String op = data.split(":")[0];
        if(op.equals("load")) {
            try {
                JSONArray jsonArray = new JSONArray(data.substring(op.length()+1));
                //DataPoint[] points = new DataPoint[jsonArray.length()];
                for (int i =0;i<jsonArray.length();i++){
                    JSONObject object = (JSONObject)jsonArray.get(i);
                    String name = (String)(object.get("name"));
                    double difficulty = Double.parseDouble((String)object.get("difficulty"));
                    long datetime = Long.parseLong((String)object.get("datetime"));
                    Date d = new Date(datetime);
                    if(!handles.containsKey(name)){
                        items.add(new ActivityItem(name,difficulty));
                        handles.put(name,items.size()-1);
                    }
                    else{
                        ActivityItem item = items.get(handles.get(name));
                        item.addPoint(datetime);
                    }
                    //points[i]=new DataPoint(i+1,1);
                }
                ActivityActivityArrayAdapter adapter = new ActivityActivityArrayAdapter(this, items);
                listview.setAdapter(adapter);
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
