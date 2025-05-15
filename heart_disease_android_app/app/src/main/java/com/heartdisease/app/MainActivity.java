package com.heartdisease.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private RadioGroup radioGroupSex;
    private EditText editTextBP;
    private EditText editTextCholesterol;
    private Spinner spinnerChestPain;
    private Button buttonPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        radioGroupSex = findViewById(R.id.radioGroupSex);
        editTextBP = findViewById(R.id.editTextBP);
        editTextCholesterol = findViewById(R.id.editTextCholesterol);
        spinnerChestPain = findViewById(R.id.spinnerChestPain);
        buttonPredict = findViewById(R.id.buttonPredict);

        // Set up the chest pain type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.chest_pain_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChestPain.setAdapter(adapter);

        // Set up predict button click listener
        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Get all input values
                    String name = editTextName.getText().toString();
                    int age = Integer.parseInt(editTextAge.getText().toString());
                    int sexValue = (radioGroupSex.getCheckedRadioButtonId() == R.id.radioButtonMale) ? 1 : 0;
                    int bp = Integer.parseInt(editTextBP.getText().toString());
                    int cholesterol = Integer.parseInt(editTextCholesterol.getText().toString());
                    int chestPainType = spinnerChestPain.getSelectedItemPosition();
                    
                    // Create an instance of HeartDiseasePredictor
                    HeartDiseasePredictor predictor = new HeartDiseasePredictor();
                    boolean hasDiseaseRisk = predictor.predictHeartDisease(age, sexValue, chestPainType, bp, cholesterol);
                    
                    // Create diet recommendations
                    DietRecommendations dietRecommendations = new DietRecommendations();
                    
                    // Pass the data to the results activity
                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    intent.putExtra("NAME", name);
                    intent.putExtra("AGE", age);
                    intent.putExtra("SEX", sexValue == 1 ? "Male" : "Female");
                    intent.putExtra("BP", bp);
                    intent.putExtra("CHOLESTEROL", cholesterol);
                    intent.putExtra("CHEST_PAIN_TYPE", spinnerChestPain.getSelectedItem().toString());
                    intent.putExtra("HAS_DISEASE", hasDiseaseRisk);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInputs() {
        if (editTextName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextAge.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (radioGroupSex.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select your sex", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextBP.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your blood pressure", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextCholesterol.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your cholesterol", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        // Validate numeric ranges
        int age = Integer.parseInt(editTextAge.getText().toString());
        if (age < 1 || age > 120) {
            Toast.makeText(this, "Please enter a valid age (1-120)", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        int bp = Integer.parseInt(editTextBP.getText().toString());
        if (bp < 70 || bp > 250) {
            Toast.makeText(this, "Please enter a valid blood pressure (70-250 mm Hg)", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        int cholesterol = Integer.parseInt(editTextCholesterol.getText().toString());
        if (cholesterol < 100 || cholesterol > 600) {
            Toast.makeText(this, "Please enter a valid cholesterol (100-600 mg/dL)", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
}