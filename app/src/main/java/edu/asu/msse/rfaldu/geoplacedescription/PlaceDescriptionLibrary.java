package edu.asu.msse.rfaldu.geoplacedescription;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by rfaldu on 1/29/17.
 */
public class PlaceDescriptionLibrary implements Serializable{
    //ArrayList <PlaceDescription>
    PlaceDescription placeDescriptionObject;
    String readFile;
    Hashtable<String, PlaceDescription> placesHashTable;

    public PlaceDescriptionLibrary(Context appContext){

        placesHashTable = new Hashtable<String, PlaceDescription>();


        //InputStream is = appContext.getResources().openRawResource(R.raw.place_description);
       //BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try{
            //JSONObject placeJson = new JSONObject(new JSONTokener(br.readLine()));
            JSONObject placeJson = new JSONObject(readFromJSONFile(appContext));
            Iterator<String> it = placeJson.keys();
            while(it.hasNext()) {
                String placeTitle = it.next();
                JSONObject placeObject = placeJson.optJSONObject(placeTitle);

                if(placeTitle != null) {
                    placeDescriptionObject = new PlaceDescription(placeObject.toString(),placeTitle);
                    placesHashTable.put(placeTitle, placeDescriptionObject);
                }
            }
        }
        catch(Exception ex){
            Log.d(this.getClass().getSimpleName(),"Exception reaching Place.JSON");
        }

    }

    public String readFromJSONFile(Context context){
        String jsonString = null;
        try{
            InputStream is = context.getApplicationContext().getResources().openRawResource(R.raw.place_description);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer,"UTF-8");
        }
        catch (IOException io){
            Log.d(getClass().getSimpleName(),"Error in reading File ");
        }
        return  jsonString;
    }

    public List<String> getPlaceTitles() {
        Iterator<Map.Entry<String, PlaceDescription>> it = placesHashTable.entrySet().iterator();
        List<String> placeTitles = new ArrayList<String>();
        while (it.hasNext()) {
            Map.Entry<String, PlaceDescription> entry = it.next();
            PlaceDescription pd = entry.getValue();
            placeTitles.add(pd.key_name);
        }
        return placeTitles;
    }

    public PlaceDescription getPlaceDescription(String placeTitle){
        PlaceDescription pd = placesHashTable.get(placeTitle);
        //Log.d(getClass().getSimpleName(), pd.name );
        return pd;
    }


}
