package com.example.root.funchooser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class DisplayMessageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_display_message);


        Intent intent  = getIntent();

        final String foodList = intent.getStringExtra(MainActivity.EXTRA_FOODLIST);
        JSONParser parser = new JSONParser();
        JSONObject response = null;

        try {
            response = (JSONObject) parser.parse(foodList);
        } catch (ParseException e) {
            Log.d("MYAPP", e.toString());
            e.printStackTrace();
        }
        JSONArray businesses = (JSONArray) response.get("businesses");
        JSONObject firstBusiness = (JSONObject) businesses.get(0);
        String firstBusinessID = firstBusiness.get("name").toString();
        Log.d("MYAPP" , firstBusinessID);

        final TextView messageTextView= (TextView) findViewById(R.id.info);

        messageTextView.setText(firstBusinessID);


    //    Log.d("MYAPP", response);
        final Button b = (Button) findViewById(R.id.got);
        b.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                messageTextView.setText("Happy Eating");
                b.setVisibility(View.GONE);
            }
        });
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


}

