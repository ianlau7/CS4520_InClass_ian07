// Ian Lau
// Assignment 01

package com.example.practice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

@Keep
public class in_class_01 extends AppCompatActivity {

    Button calculateBMI;
    EditText weight, feet, inches;
    TextView display1, display2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class_01);
        setTitle("BMI Calculator");

        calculateBMI = findViewById(R.id.buttonCalculate);
        weight = findViewById(R.id.editTextWeight);
        feet = findViewById(R.id.editTextFeet);
        inches = findViewById(R.id.editTextInches);
        display1 = findViewById(R.id.displayText);
        display2 = findViewById(R.id.displayText2);

        // calculate BMI
        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    int w = Integer.parseInt(weight.getText().toString());
                    int f = Integer.parseInt(feet.getText().toString());
                    int i = Integer.parseInt(inches.getText().toString());

                    if (w < 0 || f < 0 || i < 0) {
                        throw new IllegalArgumentException();
                    }

                    int totalHeight = (f * 12) + i;
                    double BMI = ((w / (Math.pow(totalHeight, 2))) * 703);
                    //float BMI = (w + totalHeight);
                    Log.i("demo",w + " " + f + " " + i + " " + BMI + " " + totalHeight);
                    String display1Msg = "Your BMI: " + String.format("%.1f", BMI);

                    display1.setText(display1Msg);

                    if (BMI < 18.5) {

                        display2.setText("You are underweight");

                    } else if(BMI >= 18.5 && BMI <= 24.9) {

                        display2.setText("You are a normal weight");

                    } else if(BMI >= 25 && BMI <= 29.9) {

                        display2.setText("You are overweight");

                    } else {

                        display2.setText("You are obese");

                    }

                    Toast.makeText(in_class_01.this, "BMI Calculated",
                            Toast.LENGTH_LONG).show();

                } catch (IllegalArgumentException e) {
                    Toast.makeText(in_class_01.this, "Invalid Inputs",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
