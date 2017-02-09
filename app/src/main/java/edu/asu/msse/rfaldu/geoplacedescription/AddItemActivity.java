package edu.asu.msse.rfaldu.geoplacedescription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    PlaceDescription placeDescriptionObject;
    PlaceDescriptionLibrary pdlObject;
    String placeTitle;
    Button addPlaceButton;
    EditText nameEditText, descriptionEditText, categoryEditText, addTitleEditText, addStreetEditText, elevationEditText, latitudeEditText, longitudeEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addPlaceButton = (Button)findViewById(R.id.AddPlacebutton);

        nameEditText = (EditText)findViewById(R.id.name_editText_AddActivity);
        descriptionEditText = (EditText)findViewById(R.id.description_editText_AddActivity);
        categoryEditText = (EditText)findViewById(R.id.category_editText_AddActivity);
        addTitleEditText = (EditText)findViewById(R.id.addTitle_editText_AddActivity);
        addStreetEditText = (EditText)findViewById(R.id.addStreet_editText_AddActivity);
        elevationEditText = (EditText)findViewById(R.id.elevation_editText_AddActivity);
        latitudeEditText = (EditText)findViewById(R.id.latitude_editText_AddActivity);
        longitudeEditText = (EditText)findViewById(R.id.longitude_editText_AddActivity);

        Intent intent = getIntent();
        pdlObject = intent.getSerializableExtra("PDLObject")!=null ? (PlaceDescriptionLibrary) intent.getSerializableExtra("PDLObject") :
                new PlaceDescriptionLibrary(this);


        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeTitle = nameEditText.getText().toString().trim();
                placeDescriptionObject = new PlaceDescription();
                placeDescriptionObject.setKey_name(nameEditText.getText().toString().trim());
                placeDescriptionObject.setName(nameEditText.getText().toString().trim());
                placeDescriptionObject.setCategory(categoryEditText.getText().toString().trim());
                placeDescriptionObject.setDescription(descriptionEditText.getText().toString().trim());
                placeDescriptionObject.setAddTitle(addTitleEditText.getText().toString().trim());
                placeDescriptionObject.setAddStreet(addStreetEditText.getText().toString().trim());
                placeDescriptionObject.setElevation(Double.parseDouble(elevationEditText.getText().toString().trim()));
                placeDescriptionObject.setLatitude(Double.parseDouble(latitudeEditText.getText().toString().trim()));
                placeDescriptionObject.setLongitude(Double.parseDouble(longitudeEditText.getText().toString().trim()));

                pdlObject.addPlace(placeTitle,placeDescriptionObject);

                Intent i = new Intent();
                i.putExtra("PDLObject", pdlObject);
                setResult(RESULT_OK,i);
                finish();

            }
        });

    }
}
