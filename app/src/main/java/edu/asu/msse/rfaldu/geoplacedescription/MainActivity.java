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
import android.widget.EditText;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText, descriptionEditText, categoryEditText, addTitleEditText, addStreetEditText, elevationEditText, latitudeEditText, longitudeEditText;
    PlaceDescription placeDescriptionObject;
    PlaceDescriptionLibrary pdlObject;

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

        Intent intent = getIntent();
        String placeTitle = intent.getStringExtra("PlaceTitle");
        pdlObject = intent.getSerializableExtra("PDLObject")!=null ? (PlaceDescriptionLibrary) intent.getSerializableExtra("PDLObject") :
                new PlaceDescriptionLibrary(this);
        placeDescriptionObject = new PlaceDescription();
        placeDescriptionObject = pdlObject.getPlaceDescription(placeTitle);

        //setting the values we got form class object in the Edit Text
        nameEditText.setText(placeDescriptionObject.name);
        descriptionEditText.setText(placeDescriptionObject.description);
        categoryEditText.setText(placeDescriptionObject.category);
        addTitleEditText.setText(placeDescriptionObject.addTitle);
        addStreetEditText.setText(placeDescriptionObject.addStreet);
        elevationEditText.setText(String.valueOf(placeDescriptionObject.elevation));
        latitudeEditText.setText(String.valueOf(placeDescriptionObject.latitude));
        longitudeEditText.setText(String.valueOf(placeDescriptionObject.longitude));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Intent i = new Intent();
                i.putExtra("PDLObject", pdlObject);
                this.setResult(RESULT_OK,i);
                finish();
                return true;

            //case R.id.action_delete:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
