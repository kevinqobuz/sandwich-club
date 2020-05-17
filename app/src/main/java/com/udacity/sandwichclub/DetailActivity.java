/*
 * Copyright (C) 2019 The Android Open Source Project
 */
package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import org.json.JSONException;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownTv;
    private TextView originTv;
    private TextView ingredientsTv;
    private TextView descriptionTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        alsoKnownTv = (TextView) findViewById(R.id.also_known_tv);
        originTv = (TextView) findViewById(R.id.origin_tv);
        ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);
        descriptionTv = (TextView) findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    /**
     * Display an error when the sandwich detail can't be shown
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Update UI of the sandwich details
     *
     * @param sandwich The chosen sandwich that was clicked on
     */
    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnownAss = sandwich.getAlsoKnownAs();
        for(String alsoKnownAs: alsoKnownAss){
            alsoKnownTv.append(alsoKnownAs + "\n");
        }

        originTv.append(sandwich.getPlaceOfOrigin() + "\n");

        List<String> ingredients = sandwich.getIngredients();
        for(String ingredient: ingredients){
            ingredientsTv.append(ingredient + "\n");
        }

        descriptionTv.append(sandwich.getDescription());
    }
}
