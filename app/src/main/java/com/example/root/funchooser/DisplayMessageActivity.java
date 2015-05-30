package com.example.root.funchooser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayMessageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_message);


        Intent intent  = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Toast.makeText(getBaseContext(),   message +" is selected", Toast.LENGTH_LONG).show();
        final TextView messageTextView= (TextView) findViewById(R.id.info);

        messageTextView.setText(message);

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
