package edu.asu.msse.rfaldu.geoplacedescription;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class LaunchActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    ListView nameListView;
    PlaceDescriptionLibrary pdlObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        pdlObject = new PlaceDescriptionLibrary(this);

        nameListView = (ListView)findViewById(R.id.listView);

        //Initialize List View with strings
        //String[] values = new String[] {"ASU-Poly", "ASU-West", "ASU-Tempe", "ASU-Downtown Phoenix"};

        ArrayList<String> placeTitles = (ArrayList<String>) pdlObject.getPlaceTitles();
        Log.d("AAAAAAAAAAAAA",""+placeTitles.size());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.place_name,placeTitles);

        nameListView.setAdapter(adapter);

        nameListView.setOnItemClickListener(this);

        /*nameListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

                String selectedPlace = (String) nameListView.getItemAtPosition(position);
                Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
                intent.putExtra("PlaceTitle", selectedPlace);
                intent.putExtra("PDLObject",pdlObject);
                startActivity(intent);

            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(LaunchActivity.this,AddItemActivity.class);
                intent.putExtra("PDLObject", pdlObject);
                this.startActivityForResult(intent,1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),
                ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

        String selectedPlace = (String) nameListView.getItemAtPosition(position);
        Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
        intent.putExtra("PlaceTitle", selectedPlace);
        intent.putExtra("PDLObject", pdlObject);
        this.startActivityForResult(intent,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        pdlObject = data.getSerializableExtra("PDLObject")!=null ? (PlaceDescriptionLibrary) data.getSerializableExtra("PDLObject") :
                new PlaceDescriptionLibrary(this);
        ArrayList<String> placeTitles = (ArrayList<String>) pdlObject.getPlaceTitles();
        Log.d("RRRRRRRRRRRR",""+placeTitles.size());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.place_name,placeTitles);

        nameListView.setAdapter(adapter);
        nameListView.setOnItemClickListener(this);
    }
}