package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<String> toStringListArray(JSONArray array) {
        if(array==null)
            return null;

        List<String> arrList = new ArrayList<>(array.length());
        for(int i=0; i < array.length(); i++) {
            arrList.add(array.optString(i));
        }
        return arrList;
    }

    public static Sandwich parseSandwichJson(String json) {
        //public Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients)
        Log.d("WWD", "in parseSandwichJson input json is " + json);

        // first parse string into JSONObject
        JSONObject jsonOBJ = null;
        try {
            jsonOBJ = new JSONObject(json);
        } catch (JSONException e) {
            Log.d("WWD", "exception on creating JSON object from json string");
            e.printStackTrace();
        }

        // next parse name object then mainName
        JSONObject jsonName = null;
        try {
            jsonName = jsonOBJ.getJSONObject("name");
        } catch (JSONException e) {
            jsonName = null;
            Log.d("WWD", "got execption parsing name JSON Object");
            e.printStackTrace();
        }
        Log.d("WWD", "parsed name object is " +  jsonName);
        String name= "";
        try {
            name =  jsonName.getString("mainName");
        } catch (JSONException e) {
            Log.d("WWD", "got exception parsing mainName");
            e.printStackTrace();
        }
        Log.d("WWD", "mainName is " + name);

        // parse alsoKnownAs array from name JSON object
        JSONArray jsonAKAArray = new JSONArray();
        try {
            jsonAKAArray  = jsonName.getJSONArray("alsoKnownAs");
        } catch (JSONException e) {
            Log.d("WWD", "got exception parsing AKA");
            e.printStackTrace();
        }
        List<String> akaStringList = toStringListArray(jsonAKAArray);

        // next parse origin from JSON object
        String origin = "";
        try {
            origin = jsonOBJ.getString("placeOfOrigin");
        } catch (JSONException e) {
            Log.d("WWD", "got exception parsing place of origin");
            e.printStackTrace();
        }
        Log.d("WWD","place of origin is " + origin);

        // next parse description from JSON object
        String description = "";
        try {
            description = jsonOBJ.getString("description");
        } catch (JSONException e) {
            Log.d("WWD", "got exception parsing description");
            e.printStackTrace();
        }
        Log.d("WWD", "description is  " + description);

        // next parse image URL from JSON object
        String imageURL = "";
        try {
            imageURL = jsonOBJ.getString("image");
        } catch (JSONException e) {
            Log.d("WWD", "got exception parsing image URL");
            e.printStackTrace();
        }
        Log.d("WWD", "image URL is  " + imageURL);

        // next parse ingredients array from JSON object
        // parse alsoKnownAs array from name JSON object
        JSONArray jsonIngredientArray = new JSONArray();
        try {
            jsonIngredientArray  = jsonOBJ.getJSONArray("ingredients");
        } catch (JSONException e) {
            Log.d("WWD", "got exception parsing ingredients");
            e.printStackTrace();
        }
        List<String> ingredients = toStringListArray(jsonIngredientArray);
        Log.d("WWD", "the ingredients are " + ingredients);

        //finally create our delicious sandwich then return it
        //public Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients) {
        Sandwich sandwich = new Sandwich(name, akaStringList, origin, description, imageURL, ingredients);
        return sandwich;
    }
}
