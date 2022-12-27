package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mOrigin;
    private TextView mDescription;
    private TextView mIngredients;
    private TextView mAlsoKnownAs;

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

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        mOrigin = (TextView) findViewById(R.id.origin_tv);
        mDescription = (TextView)  findViewById(R.id.description_tv);
        mIngredients = (TextView)  findViewById(R.id.ingredients_tv);
        mAlsoKnownAs = (TextView)  findViewById(R.id.also_known_tv);

        mOrigin.setText(sandwich.getPlaceOfOrigin());
        Log.d("WWD", "in DetailActivity origin is " + sandwich.getPlaceOfOrigin());
        mDescription.setText(sandwich.getDescription());

        List<String> akaList = new ArrayList<String>();
        akaList = sandwich.getAlsoKnownAs();
        for (String str : akaList) {
            mAlsoKnownAs.append(str + "  ");
        }

        List<String> ingredientList = new ArrayList<String>();
        ingredientList = sandwich.getIngredients();
        for (String str : ingredientList) {
            mIngredients.append(str + "  ");
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
