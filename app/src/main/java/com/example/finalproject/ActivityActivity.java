package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

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

    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        listview = (ListView) findViewById(R.id.activity_LISTVIEW);
        items = new ArrayList<>();
        handles = new HashMap<>();

        graph = (GraphView)findViewById(R.id.activity_GRAPH);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setWidth(150);

        Viewport v = graph.getViewport();
        v.setXAxisBoundsManual(true);
        v.setMinX(0);
        v.setMaxX(40);
        v.setYAxisBoundsManual(true);
        v.setMinY(0);
        v.setMaxY(160);

        getData("activities");
    }

    public void edit(View v){
        editMode=!editMode;
        refresh();
    }

    public void add(View v){
        EditText editText = (EditText)findViewById(R.id.activity_TYPENEW);
        String name = editText.getText().toString();
        if(!handles.containsKey(name)){
            int index = 0;
            while(index<items.size()&&items.get(index).getDiff()>=0)
                index++;
            items.add(index,new ActivityItem(name,0));
            handles.put(name,items.size()-1);
            while(index<items.size()){
                String itemName = items.get(index).getName();
                handles.put(itemName,handles.get(itemName));
                index++;
            }
            refresh();
        }
    }

    public void onClick(View v, ActivityItem item){
        if(!editMode) {
            putData("activities", "'name':'"+item.getName()+"','difficulty':'" +item.getDiff()+ "'");
            getData("activities");
        }
    }

    public void delete(View v, ActivityItem item){
        String name = item.getName();
        Log.d("delete","pressed "+name);
        if(handles.containsKey(name)){
            items.remove(items.get(handles.get(name)));
            handles.remove(name);
            refresh();
        }
    }

    public void set(View v, int i){

    }

    public void favorite(View v, ActivityItem item){

    }

    protected void refresh(){
        Log.d("refresh","done");
        ActivityActivityArrayAdapter adapter = new ActivityActivityArrayAdapter(this, items, editMode);
        listview.setAdapter(adapter);

        Viewport v = graph.getViewport();

        graph.removeAllSeries();
        int count = 1;
        int max = 0;
        for(ActivityItem i:items){
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{new DataPoint(count,i.getSize())});
            series.setTitle(i.getName());
            graph.addSeries(series);
            max=(i.getSize()>max)?(i.getSize()):max;
            count++;
        }
        v.setMaxX(count+1);
        v.setMaxY((int)(max*1.3));
    }

    protected void sort(){

    }

    @Override
    protected void token(String data){
        setErrorMessage("token response received");
        String op = data.split(":")[0];
        if(op.equals("load")) {
            items = new ArrayList<>();
            handles = new HashMap<>();
            try {
                JSONArray jsonArray = new JSONArray(data.substring(op.length()+1));
                //ArrayList<DataPoint> points = new ArrayList<>();
                for (int i =0;i<jsonArray.length();i++){
                    JSONObject object = (JSONObject)jsonArray.get(i);
                    String name = (String)(object.get("name"));
                    int difficulty = Integer.parseInt((String)object.get("difficulty"));
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
                }
                sort();
                refresh();
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
