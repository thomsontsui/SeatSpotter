package com.seatspotter.seatspotter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ActivityLibraryFloor extends ActionBarActivity {

    public final static String LIBRARY_FLOOR = "com.seatspotter.seatspotter.LIBRARYFLOOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Library Floors");
        setContentView(R.layout.activity_library_floor);
        Intent intent = getIntent();

        //Getting the library from the list View
        String name = intent.getStringExtra(ActivityLibraryList.LIBRARY_NAME);
        TextView e = (TextView)findViewById(R.id.libraryName);
        e.setText(name);

        final ListView libraryFloors = (ListView) findViewById(R.id.libraryFloors);

        String[] values = new String[] {"Floor Plan A", "Floor Plan B"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        libraryFloors.setAdapter(adapter);

        libraryFloors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ActivityLibraryFloor.this, ActivityLibraryMap.class);

                String libraryFloor = ((TextView)view).getText().toString();
                intent.putExtra(LIBRARY_FLOOR, libraryFloor);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_floor, menu);
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
