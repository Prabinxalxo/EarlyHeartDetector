package com.heartdisease.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewAge;
    private TextView textViewSex;
    private TextView textViewBP;
    private TextView textViewCholesterol;
    private TextView textViewChestPain;
    private TextView textViewResult;
    private LinearLayout resultContainer;
    private LinearLayout dietRecommendationsContainer;
    private Button buttonStartOver;
    private Button buttonDownloadReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Initialize UI components
        textViewName = findViewById(R.id.textViewName);
        textViewAge = findViewById(R.id.textViewAge);
        textViewSex = findViewById(R.id.textViewSex);
        textViewBP = findViewById(R.id.textViewBP);
        textViewCholesterol = findViewById(R.id.textViewCholesterol);
        textViewChestPain = findViewById(R.id.textViewChestPain);
        textViewResult = findViewById(R.id.textViewResult);
        resultContainer = findViewById(R.id.resultContainer);
        dietRecommendationsContainer = findViewById(R.id.dietRecommendationsContainer);
        buttonStartOver = findViewById(R.id.buttonStartOver);
        buttonDownloadReport = findViewById(R.id.buttonDownloadReport);

        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        int age = intent.getIntExtra("AGE", 0);
        String sex = intent.getStringExtra("SEX");
        int bp = intent.getIntExtra("BP", 0);
        int cholesterol = intent.getIntExtra("CHOLESTEROL", 0);
        String chestPainType = intent.getStringExtra("CHEST_PAIN_TYPE");
        boolean hasDisease = intent.getBooleanExtra("HAS_DISEASE", false);

        // Set text values
        textViewName.setText("Name: " + name);
        textViewAge.setText("Age: " + String.valueOf(age));
        textViewSex.setText("Sex: " + sex);
        textViewBP.setText("Blood Pressure: " + String.valueOf(bp) + " mm Hg");
        textViewCholesterol.setText("Cholesterol: " + String.valueOf(cholesterol) + " mg/dL");
        textViewChestPain.setText("Chest Pain Type: " + chestPainType);

        // Set result and background color
        if (hasDisease) {
            textViewResult.setText("Heart disease detected");
            textViewResult.setTextColor(getResources().getColor(R.color.colorRed));
            resultContainer.setBackgroundResource(R.drawable.red_background);
        } else {
            textViewResult.setText("No heart disease detected");
            textViewResult.setTextColor(getResources().getColor(R.color.colorGreen));
            resultContainer.setBackgroundResource(R.drawable.green_background);
        }

        // Display diet recommendations
        displayDietRecommendations(hasDisease);

        // Set up start over button
        buttonStartOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set up download report button
        buttonDownloadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PDFGenerator pdfGenerator = new PDFGenerator(ResultsActivity.this);
                pdfGenerator.generateAndSavePDF(name, age, sex, bp, cholesterol, chestPainType, hasDisease);
            }
        });
    }

    private void displayDietRecommendations(boolean hasDisease) {
        // Clear previous recommendations
        dietRecommendationsContainer.removeAllViews();
        
        // Create a new instance of DietRecommendations
        DietRecommendations dietRecommendations = new DietRecommendations();
        
        // Get recommendations based on disease status
        DietPlan dietPlan = dietRecommendations.getDietRecommendation(hasDisease);
        
        // Display each category and its recommendations
        for (String category : dietPlan.getCategories()) {
            // Add category header
            TextView categoryHeader = new TextView(this);
            categoryHeader.setText(category + ":");
            categoryHeader.setTextSize(16);
            categoryHeader.setPadding(0, 16, 0, 8);
            categoryHeader.setTextColor(getResources().getColor(R.color.colorAccent));
            dietRecommendationsContainer.addView(categoryHeader);
            
            // Add items for this category
            for (String item : dietPlan.getItemsForCategory(category)) {
                TextView itemText = new TextView(this);
                itemText.setText("• " + item);
                itemText.setTextSize(14);
                itemText.setPadding(16, 4, 0, 4);
                dietRecommendationsContainer.addView(itemText);
            }
        }
    }
}