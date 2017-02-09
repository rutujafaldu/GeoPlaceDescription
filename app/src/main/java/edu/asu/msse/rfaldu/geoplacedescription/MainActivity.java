/*
 * Copyright 2017 Rutuja Faldu,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructors and the university has the right to build and
 * evaluate the software package for the purpose of determining
 * the grade and program assessment.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Purpose: To build a single view Android Application which contains
 * a class called Place Description that has attributes which stores
 * information of a place and displays it to user.
 *
 * Ser423 Mobile Applications
 * see http://pooh.poly.asu.edu/Mobile
 * @author Rutuja Faldu Rutuja.Faldu@asu.edu
 *         Software Engineering, CIDSE, ASU Poly
 * @version January 2017
 */

package edu.asu.msse.rfaldu.geoplacedescription;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class MainActivity extends AppCompatActivity {

    EditText nameEditText, descriptionEditText, categoryEditText, addTitleEditText, addStreetEditText, elevationEditText, latitudeEditText, longitudeEditText, distanceEditText;
    PlaceDescription placeDescriptionObject, placeDescriptionObject2;
    PlaceDescriptionLibrary pdlObject;
    String placeTitle, placeTitle2;
    Button editButton;
    Spinner placeTitleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEditText = (EditText)findViewById(R.id.name_editText);
        descriptionEditText = (EditText)findViewById(R.id.description_editText);
        categoryEditText = (EditText)findViewById(R.id.category_editText);
        addTitleEditText = (EditText)findViewById(R.id.addTitle_editText);
        addStreetEditText = (EditText)findViewById(R.id.addStreet_editText);
        elevationEditText = (EditText)findViewById(R.id.elevation_editText);
        latitudeEditText = (EditText)findViewById(R.id.latitude_editText);
        longitudeEditText = (EditText)findViewById(R.id.longitude_editText);
        distanceEditText = (EditText)findViewById(R.id.distance_editText);

        editButton = (Button)findViewById(R.id.EditPlacebutton);

        placeTitleSpinner = (Spinner)findViewById(R.id.spinner);

        Intent intent = getIntent();
        placeTitle = intent.getStringExtra("PlaceTitle")!=null ? intent.getStringExtra("PlaceTitle"): "unknown";
        pdlObject = intent.getSerializableExtra("PDLObject")!=null ? (PlaceDescriptionLibrary) intent.getSerializableExtra("PDLObject") :
                new PlaceDescriptionLibrary(this);
        placeDescriptionObject = new PlaceDescription();
        placeDescriptionObject = pdlObject.getPlaceDescription(placeTitle);

        if(placeTitle.equals("unknown")){

        }else {
            //setting the values we got form class object in the Edit Text
            nameEditText.setText(placeDescriptionObject.name);
            descriptionEditText.setText(placeDescriptionObject.description);
            categoryEditText.setText(placeDescriptionObject.category);
            addTitleEditText.setText(placeDescriptionObject.addTitle);
            addStreetEditText.setText(placeDescriptionObject.addStreet);
            elevationEditText.setText(String.valueOf(placeDescriptionObject.elevation));
            latitudeEditText.setText(String.valueOf(placeDescriptionObject.latitude));
            longitudeEditText.setText(String.valueOf(placeDescriptionObject.longitude));

            ArrayList<String> placeTitleList = (ArrayList<String>) pdlObject.getPlaceTitles();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, placeTitleList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            placeTitleSpinner.setAdapter(adapter);
        }

        placeTitleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                placeTitle2 = placeTitleSpinner.getSelectedItem().toString();
                placeDescriptionObject2 = pdlObject.getPlaceDescription(placeTitle2);
                calculate_distance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeDescriptionObject.setCategory(categoryEditText.getText().toString().trim());
                placeDescriptionObject.setDescription(descriptionEditText.getText().toString().trim());
                placeDescriptionObject.setAddTitle(addTitleEditText.getText().toString().trim());
                placeDescriptionObject.setAddStreet(addStreetEditText.getText().toString().trim());
                placeDescriptionObject.setElevation(Double.parseDouble(elevationEditText.getText().toString().trim()));
                placeDescriptionObject.setLatitude(Double.parseDouble(latitudeEditText.getText().toString().trim()));
                placeDescriptionObject.setLongitude(Double.parseDouble(longitudeEditText.getText().toString().trim()));

                pdlObject.editPlace(placeTitle,placeDescriptionObject);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent();
                i.putExtra("PDLObject", pdlObject);
                this.setResult(RESULT_OK,i);
                finish();
                return true;

            case R.id.action_delete:
                pdlObject.deletePlace(placeTitle);
                i = new Intent();
                i.putExtra("PDLObject", pdlObject);
                this.setResult(RESULT_OK,i);
                finish();
                return true;

            case R.id.action_edit:
                editPlaceDetails();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void editPlaceDetails(){
        descriptionEditText.setFocusable(true);
        categoryEditText.setFocusable(true);
        addStreetEditText.setFocusable(true);
        addTitleEditText.setFocusable(true);
        elevationEditText.setFocusable(true);
        latitudeEditText.setFocusable(true);
        longitudeEditText.setFocusable(true);

        descriptionEditText.setEnabled(true);
        categoryEditText.setEnabled(true);
        addStreetEditText.setEnabled(true);
        addTitleEditText.setEnabled(true);
        elevationEditText.setEnabled(true);
        latitudeEditText.setEnabled(true);
        longitudeEditText.setEnabled(true);

        editButton.setVisibility(View.VISIBLE);

    }

    public void calculate_distance(){
        double lat1, lat2, long1, long2;
        double radius = 6371;
        double long_difference, central_angle, distance;
        lat1 = placeDescriptionObject.latitude;
        lat2 = placeDescriptionObject2.latitude;
        long1 = placeDescriptionObject.longitude;
        long2 = placeDescriptionObject2.longitude;
        long_difference = abs(long1-long2);
        double temp = sin(lat1)*sin(lat2);
        double temp2 = cos(lat1)*cos(lat2)*cos(long_difference);
        central_angle= acos(temp+temp2);
        distance = radius * central_angle;

        distanceEditText.setText(""+distance);

    }
}
