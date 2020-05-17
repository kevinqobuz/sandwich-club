/*
 * Copyright (C) 2019 The Android Open Source Project
 */
package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        /* Name of the sandwich */
        final String NAME_SANDWICH = "name";
        /* Other known names of the sandwich */
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String INGREDIENTS = "ingredients";
        final String MAIN_NAMES = "mainName";
        final String PLACE_OF_ORIGIN ="placeOfOrigin";
        final String DESCRIPTION = "description";
        /* Downloading link for the image */
        final String IMAGE = "image";

        Sandwich sandwich = new Sandwich();

        JSONObject detailSandwich = new JSONObject(json);

        JSONObject name = detailSandwich.getJSONObject(NAME_SANDWICH);

        JSONArray alsoKnownAssJsonArray = name.getJSONArray(ALSO_KNOWN_AS);
        List<String> alsoKnownAss = new ArrayList<String>();
        for(int i=0;i<alsoKnownAssJsonArray.length();i++){
            alsoKnownAss.add(alsoKnownAssJsonArray.getString(i));
        }

        JSONArray ingredientsJsonArray = detailSandwich.getJSONArray(INGREDIENTS);
        List<String> ingredients = new ArrayList<String>();
        for(int i=0;i<ingredientsJsonArray.length();i++){
            ingredients.add(ingredientsJsonArray.getString(i));
        }

        sandwich.setMainName(name.getString(MAIN_NAMES));
        sandwich.setAlsoKnownAs(alsoKnownAss);
        sandwich.setPlaceOfOrigin(detailSandwich.getString(PLACE_OF_ORIGIN));
        sandwich.setDescription(detailSandwich.getString(DESCRIPTION));
        sandwich.setImage(detailSandwich.getString(IMAGE));
        sandwich.setIngredients(ingredients);

        return sandwich;
    }
}
