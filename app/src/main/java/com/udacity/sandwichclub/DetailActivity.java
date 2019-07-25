package com.udacity.sandwichclub;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Sandwich Name
        TextView name = findViewById(R.id.sandwich_name_tv);
        name.setText(sandwich.getMainName());

        // Also Known As
        // View an ArrayList in a TextView
        TextView akaTitle = findViewById(R.id.aka_title);
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        String alsoKnownAsString = "";
        for (String s : sandwich.getAlsoKnownAs()) {
            if (!alsoKnownAsString.isEmpty()){
                alsoKnownAsString += "\n";
            }
            alsoKnownAsString += s;
        }
        alsoKnownAs.setText(alsoKnownAsString);

        // Ingredients
        TextView ingredientsTitle = findViewById(R.id.ingredients_title);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        String ingredientsString = "";
        for (String s : sandwich.getIngredients()) {
            if (!ingredientsString.isEmpty()) {
                ingredientsString += "\n";
            }
            ingredientsString += s;
        }
        ingredients.setText(ingredientsString);

        // Place of Origin
        TextView originTitle = findViewById(R.id.origin_title);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        // Sandwich Description
        TextView descriptionTitle = findViewById(R.id.description_title);
        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

        // Hide any TextViews that are missing data
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAs.setVisibility(View.GONE);
            akaTitle.setVisibility(View.GONE);
        }

        if (sandwich.getIngredients().isEmpty()) {
            ingredients.setVisibility(View.GONE);
            ingredientsTitle.setVisibility(View.GONE);
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOrigin.setVisibility(View.GONE);
            originTitle.setVisibility(View.GONE);
        }

        if (sandwich.getDescription().isEmpty()) {
            description.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.GONE);
        }

    }
}
