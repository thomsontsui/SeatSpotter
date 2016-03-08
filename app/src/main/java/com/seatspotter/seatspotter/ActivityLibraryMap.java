package com.seatspotter.seatspotter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ActivityLibraryMap extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Map View");
        setContentView(R.layout.activity_library_map);

        Intent intent = getIntent();

        //Getting the library from the list View
        String name = intent.getStringExtra(ActivityLibraryFloor.LIBRARY_FLOOR);
        TextView e = (TextView)findViewById(R.id.libraryFloor);
        e.setText(name);

        //Legend image
        ImageView mapLegend = (ImageView)findViewById(R.id.mapLegend);
        mapLegend.setImageResource(R.drawable.maplegend2);
    }

    public void onFourSeatButtonClick(View view){
        ViewMap mapView = (ViewMap) findViewById(R.id.canvasMap);
        mapView.searchFourSeatBlock();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_map, menu);
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
