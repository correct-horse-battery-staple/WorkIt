package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by panda_000 on 4/18/2017.
 */
public class ActivityActivity extends ServerActivity {
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
    }

    public void edit(View v){
        editMode=!editMode;
    }

    public void add(View v){

    }

    @Override
    protected void token(String data){
        setErrorMessage("token response received");
        String op = data.split(":")[0];
        if(op.equals("load")) {
            try {
                JSONArray jsonArray = new JSONArray(data.substring(op.length()+1));
                for (int i =0;i<jsonArray.length();i++){
                    Log.d("app json",jsonArray.get(i).toString());
                }
                setErrorMessage(data);
                Log.d("server load",data.substring(op.length()+1));
            }
            catch(JSONException i) {
                i.printStackTrace();
            }
        }
        else if(op.equals("store")){
            setErrorMessage(data);
        }
    }
}
