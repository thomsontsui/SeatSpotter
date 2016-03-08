package com.seatspotter.seatspotter;


import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class ActivityLibraryDesk extends ActionBarActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Desk View");
        setContentView(R.layout.activity_library_desk);

        //Legend image
        ImageView deskLegend = (ImageView)findViewById(R.id.deskLegend);
        deskLegend.setImageResource(R.drawable.desklegend2);
    }

    public void onPollButtonClick(View view){
        ViewDesk deskBlockView = (ViewDesk) findViewById(R.id.canvasDesk);
        deskBlockView.updateDeskStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_desk, menu);
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
}
