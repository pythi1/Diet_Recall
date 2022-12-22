package com.project.dietrecall;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class BmiFragmentActivity extends Fragment {


    EditText age, weight, height;
    Button calculate;
    TextView result, fatPerc, floatBt, bmiStatus, bmitext;
    LinearLayout resultLayout;
    LayoutInflater genderL;
    RadioGroup genderGroup;
    RadioButton genderValue;
    FloatingActionButton floatingActionButton;
    AlertDialog alertDialog;
    int defaultValue = 0;
    float fat;
    public BmiFragmentActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bmi_fragment, container, false);

        final Context context = view.getContext();


        resultLayout = (LinearLayout) view.findViewById(R.id.result);
        age = (EditText) view.findViewById(R.id.ageValue);
        weight = (EditText) view.findViewById(R.id.weightValue);
        height = (EditText) view.findViewById(R.id.heightValue);
        result = (TextView) view.findViewById(R.id.bmiValue);
        bmiStatus = (TextView) view.findViewById(R.id.status);
        bmitext = (TextView) view.findViewById(R.id.bmitxt);
        floatBt = (TextView) view.findViewById(R.id.fbtext);
        fatPerc = (TextView) view.findViewById(R.id.fatPercentage);
        calculate = (Button) view.findViewById(R.id.calculate);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderL = LayoutInflater.from(context);
                View genderDialogView = genderL.inflate(R.layout.gender, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setView(genderDialogView);


                genderGroup = genderDialogView.findViewById(R.id.gender);
                if (defaultValue == 0) {
                    genderValue = genderGroup.findViewById(R.id.maleButton);
                    genderValue.setChecked(true);
                } else {
                    genderValue = genderGroup.findViewById(R.id.femaleButton);
                    genderValue.setChecked(true);
                }
                genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.maleButton) {
                            floatBt.setText("M");
                            defaultValue = 0;
                            alertDialog.dismiss();
                        } else {
                            floatBt.setText("F");
                            defaultValue = 1;
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    calculateBMI();
                }
            }
        });

        return view;
    }

    private boolean validate() {
        if (age.getText().toString().trim().isEmpty()) {
            age.setError("Age required");
            age.requestFocus();
            return false;
        }
        if (weight.getText().toString().trim().isEmpty()) {
            weight.setError("Weight required");
            weight.requestFocus();
            return false;
        }
        if (height.getText().toString().trim().isEmpty()) {
            height.setError("Height required");
            height.requestFocus();
            return false;
        }
        return true;
    }

    private void calculateBMI() {
        String ageValue = age.getText().toString().trim();
        String weightValue = weight.getText().toString().trim();
        String heightValue = height.getText().toString().trim();
        float age = Float.parseFloat(ageValue);
        float weight = Float.parseFloat(weightValue);
        float height = Float.parseFloat(heightValue);

        int resultValue = (int) ((int) (weight * 10000) / (height * height));

        if (defaultValue == 0) {
            fat = (float) ((1.20 * ((int) (weight * 10000) / (height * height))) + (0.23 * age) - 16.2); //male
        } else {
            fat = (float) ((1.20 * ((int) (weight * 10000) / (height * height))) + (0.23 * age) - 5.4); //female
        }
        resultLayout.setVisibility(View.VISIBLE);
        result.setText(String.valueOf(resultValue));

        result.setTextColor(Color.parseColor("#e10000"));
        bmiStatus.setTextColor(Color.parseColor("#e10000"));
        bmitext.setTextColor(Color.parseColor("#e10000"));
        if (resultValue < 18.5) {
            //underweighted
            bmiStatus.setText("Under weighted");
        } else if (18.5 <= resultValue && resultValue <= 24.9) {
            //Normal
            result.setTextColor(Color.parseColor("#00796B"));
            bmiStatus.setTextColor(Color.parseColor("#00796B"));
            bmitext.setTextColor(Color.parseColor("#00796B"));
            bmiStatus.setText("Normal");
        } else if (25.0 <= resultValue && resultValue <= 29.9) {
            bmiStatus.setText("Overweight");
        } else if (30.0 <= resultValue && resultValue <= 34.9) {
            bmiStatus.setText("class I obesity");
        } else if (35.0 <= resultValue && resultValue <= 39.9) {
            bmiStatus.setText("class II obesity");
        } else {
            bmiStatus.setText("class III obesity");
        }
        fatPerc.setText(String.valueOf(String.format("%.2f", fat)) + " %");
    }
}
