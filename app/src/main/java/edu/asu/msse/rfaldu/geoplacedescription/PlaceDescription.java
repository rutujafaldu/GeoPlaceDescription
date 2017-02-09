package edu.asu.msse.rfaldu.geoplacedescription;

/**
 * Created by Rutuja Faldu on 1/18/17.
 */
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Vector;


public class PlaceDescription implements Serializable {

    String key_name;
    String name;
    String description;
    String category;
    String addTitle;
    String addStreet;
    String image;
    double elevation;
    double latitude;
    double longitude;

    public PlaceDescription(){

    }

    //Constructor for initializing all the values
    public PlaceDescription(String jsonStr, String key_name){
        try {
            this.key_name = key_name;
            JSONObject jo = new JSONObject(jsonStr);
            addTitle = jo.getString("address-title");
            addStreet = jo.getString("address-street");
            elevation = jo.getDouble("elevation");
            latitude = jo.getDouble("latitude");
            longitude = jo.getDouble("longitude");
            name = jo.getString("name");
            image = jo.getString("image");
            description = jo.getString("description");
            category = jo.getString("category");

        }
        catch (Exception ex) {
            android.util.Log.w(this.getClass().getSimpleName(),
                    "error converting to/from json");
        }

    }

    public String toJsonString(){
        String ret = "";
        try{
            JSONObject jo = new JSONObject();
            jo.put("name",name);
            jo.put("description",description);
            jo.put("category",category);
            jo.put("addTitle",addTitle);
            jo.put("addStreet",addStreet);
            jo.put("elevation",elevation);
            jo.put("latitude",latitude);
            jo.put("longitude",longitude);
        }
        catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),
                    "error converting to/from json");
        }
        return ret;
    }
}

