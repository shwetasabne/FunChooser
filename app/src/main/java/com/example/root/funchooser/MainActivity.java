package com.example.root.funchooser;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.widget.EditText;

import java.io.Serializable;

public class MainActivity extends ActionBarActivity {

    public ProgressDialog progressDialog;

    public final static String EXTRA_FOODLIST = "com.example.root.funchooser.ZIP";
   // public final static String EXTRA_CUISINE = "com.example.root.funchooser.CUISINE";
   // public final static String PD = "com.example.root.funchooser.PD";
    private Toolbar toolbar;
    Spinner spinner ;
    ArrayAdapter<CharSequence> arrayAdapter;
    ViewPager pager;
    ViewPageAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Movies","Food"};
    int Numboftabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPageAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    private class CallAPI extends AsyncTask<String, String, String> {


        protected String doInBackground(String... params) {

            Log.d("MYAPP" , params[0]+" "+params[1]);
            String term = params[0];
            String zip = params[1];


            String result_to_display = "";
            YelpApi ya = new YelpApi();
            result_to_display = ya.searchForBusinessesByLocation(term, zip);
            Log.d("MYAPP", "return is " + result_to_display);
            return result_to_display;
        }


        protected void onPostExecute(String result) {
            if(progressDialog!=null && progressDialog.isShowing()){

                progressDialog.dismiss();
            }
            Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
            intent.putExtra(EXTRA_FOODLIST, result);


            startActivity(intent);
        }

        protected void onPreExecute(){
            // instantiate new progress dialog
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(progressDialog.isShowing()){
                        Log.d("MYAPP" ,"Cancelling");
                        progressDialog.dismiss();
                    }

                }});
            // spinner (wheel) style dialog
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // better yet - use a string resource getString(R.string.your_message)
            progressDialog.setMessage("Loading data");
        // display dialog
            progressDialog.show();
        }

    } // end call api


    public void sendMessage(View v){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String zip = editText.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.food_spin);
        String cuisine = (String)spinner.getSelectedItem();
      //  intent.putExtra(EXTRA_ZIP, zip);
    //    intent.putExtra(EXTRA_CUISINE, cuisine);
      //  startActivity(intent);


        new CallAPI().execute(cuisine,zip);
    }
}
