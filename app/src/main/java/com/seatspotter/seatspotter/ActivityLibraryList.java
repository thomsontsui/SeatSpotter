package com.seatspotter.seatspotter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;    //Will remove later
import com.loopj.android.http.*;

public class ActivityLibraryList extends ActionBarActivity {

    public final static String LIBRARY_NAME = "com.seatspotter.seatspotter.LIBRARYNAME";

    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Library List");
        setContentView(R.layout.activity_library_list);

        final ListView libraryList = (ListView) findViewById(R.id.libraryList);

        //Static Data before database is created
        Desk designFairDemoFloor1Desk1 = new Desk(0, 0,  0, 0);
        Desk designFairDemoFloor1Desk2 = new Desk(1, 0,  0, 1);
        Desk[] designFairDemoFloor1Desks = new Desk[]{designFairDemoFloor1Desk1, designFairDemoFloor1Desk2};

        Desk designFairDemoFloor2Desk1 = new Desk(0, 0, 0, 0);
        Desk designFairDemoFloor2Desk2 = new Desk(1, 0, 1, 0);
        Desk[] designFairDemoFloor2Desks = new Desk[]{designFairDemoFloor2Desk1, designFairDemoFloor2Desk2};

        Floor designFairDemoFloor1 = new Floor("Floor Plan A", 1, designFairDemoFloor1Desks);
        Floor designFairDemoFloor2 = new Floor("Floor Plan B", 2, designFairDemoFloor2Desks);
        Floor[] designFairDemoFloors = new Floor[]{designFairDemoFloor1, designFairDemoFloor2};

        Library designFairDemo = new Library("Design Fair Demo", designFairDemoFloors);

        Desk dcFloor1Desk1 = new Desk(0, 0, 0, 0);
        Desk dcFloor1Desk2 = new Desk(1, 0, 0, 1);
        Desk[] dcFloor1Desks = new Desk[]{dcFloor1Desk1, dcFloor1Desk2};

        Desk dcFloor2Desk1 = new Desk(0, 0, 0, 0);
        Desk dcFloor2Desk2 = new Desk(1, 0, 1, 0);
        Desk[] dcFloor2Desks = new Desk[]{dcFloor2Desk1, dcFloor2Desk2};

        Floor dcFloor1 = new Floor("Floor Plan A", 1, dcFloor1Desks);
        Floor dcFloor2 = new Floor("Floor Plan B", 2, dcFloor2Desks);
        Floor[] dcFloors = new Floor[]{dcFloor1, dcFloor2};

        Library dc = new Library("DC Library", dcFloors);

        //Static string arrays
        String[] values = new String[] {designFairDemo.name, dc.name, "DP Library"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        libraryList.setAdapter(adapter);

        libraryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent intent = new Intent(ActivityLibraryList.this, ActivityLibraryFloor.class);

                    String libraryName = ((TextView)view).getText().toString();
                    intent.putExtra(LIBRARY_NAME, libraryName);

                    startActivity(intent);
                }
                else {
                    //Will remove later
                    String item = ((TextView)view).getText().toString();

                    Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_list, menu);
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

