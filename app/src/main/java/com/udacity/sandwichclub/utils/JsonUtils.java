package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {

            // Create new JSONObject
            JSONObject jsonResponse = new JSONObject(json);

            // Extract JSONArray associated with the key "name"
            JSONObject nameObject = jsonResponse.getJSONObject("name");

            // Extract value for image
            String image = jsonResponse.getString("image");

            // Extract value for sandwich name
            String name = nameObject.getString("mainName");

            // Extract value for also known as
            JSONArray alsoKnownAs = nameObject.getJSONArray("alsoKnownAs");
            ArrayList<String> akaString = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                String aka = (String) alsoKnownAs.get(i);
                akaString.add(aka);
            }

            // Extract value for place of origin
            String placeOfOrigin = jsonResponse.getString("placeOfOrigin");

            // Extract value for sandwich description
            String description = jsonResponse.getString("description");

            // Extract value for ingredients
            JSONArray ingredients = jsonResponse.getJSONArray("ingredients");
            // Transform JSONArray into an ArrayList
            ArrayList<String> ingredientString = new ArrayList<>();
            // Iterate through JSONArray to add ingredient elements to ArrayList
            for (int i = 0; i < ingredients.length(); i++) {
                String ingredient = (String) ingredients.get(i);
                ingredientString.add(ingredient);
            }
            // Create a new Sandwich object with image, name, also known as, place of origin, description, and ingredients
            Sandwich sandwich = new Sandwich(name, akaString, placeOfOrigin, description, image, ingredientString);

            return sandwich;

        } catch (JSONException e) {
            /* If an error is thrown when executing any of the above statements in the "try" block,
            print a log message with the message from the exception. */
            Log.e("JsonUtils", "Problem parsing the sandwich JSON results", e);
            return null;

        }

    }

}
