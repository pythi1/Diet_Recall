package com.project.dietrecall;

import android.app.AlertDialog;
import android.app.Fragment;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FatFragmentActivity extends Fragment {

    EditText ageEt,hippsEt,wristEt,waistEt,forarmEt,thighEt,calfEt;
    TextView fatResult,floatbtxt;
    Button calculateButton;
    FloatingActionButton floatGender;
    RadioGroup genderGroup;
    LayoutInflater genderL;
    RadioButton genderValue;
    int defaultValue = 0;
    AlertDialog alertDialog;
    float fatPercentage;
    LinearLayout thighLineraL,calfLinearL,waistLineraL,forarmLinearL,resultLinearL;
    public FatFragmentActivity() {
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
        final View view =  inflater.inflate(R.layout.fat_fragment, container, false);

        final Context context = view.getContext();

        ageEt = (EditText) view.findViewById(R.id.ageValue);
        hippsEt = (EditText) view.findViewById(R.id.hipsValue);
        wristEt = (EditText) view.findViewById(R.id.wristValue);
        waistEt = (EditText) view.findViewById(R.id.waistValue);
        forarmEt = (EditText) view.findViewById(R.id.forearmValue);
        thighEt = (EditText) view.findViewById(R.id.thighValue);
        calfEt = (EditText) view.findViewById(R.id.calfValue);

        fatResult = (TextView) view.findViewById(R.id.fatValue);
        floatbtxt = (TextView) view.findViewById(R.id.fbtext);
        floatGender = (FloatingActionButton) view.findViewById(R.id.fab);
        //female
        thighLineraL = (LinearLayout) view.findViewById(R.id.thighLl);
        calfLinearL = (LinearLayout) view.findViewById(R.id.calfLl);
        //male
        waistLineraL = (LinearLayout) view.findViewById(R.id.waistLl);
        forarmLinearL = (LinearLayout) view.findViewById(R.id.forearmLl);

        resultLinearL = (LinearLayout) view.findViewById(R.id.result);

        calculateButton = (Button) view.findViewById(R.id.calculateFat);

        floatGender.setOnClickListener(new View.OnClickListener() {
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
                            floatbtxt.setText("M");
                            defaultValue = 0;
                            thighLineraL.setVisibility(View.GONE);
                            calfLinearL.setVisibility(View.GONE);
                            waistLineraL.setVisibility(View.VISIBLE);
                            forarmLinearL.setVisibility(View.VISIBLE);
                            alertDialog.dismiss();
                        } else {
                            floatbtxt.setText("F");
                            defaultValue = 1;
                            thighLineraL.setVisibility(View.VISIBLE);
                            calfLinearL.setVisibility(View.VISIBLE);
                            waistLineraL.setVisibility(View.GONE);
                            forarmLinearL.setVisibility(View.GONE);
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = ageEt.getText().toString().trim();
                String hipps = hippsEt.getText().toString().trim();
                String wrist = wristEt.getText().toString().trim();
                String waist = waistEt.getText().toString().trim();
                String forarm = forarmEt.getText().toString().trim();
                String thigh = thighEt.getText().toString().trim();
                String calf = calfEt.getText().toString().trim();
                if(isValidated(age,hipps,wrist,waist,forarm,thigh,calf)){
                    calculateFatPercentage(age,hipps,wrist,waist,forarm,thigh,calf);
                }
            }
        });
        return view;
    }

    private boolean isValidated(String age, String hipps, String wrist,String waist,String forarm,String thigh,String calf) {
        if (age.isEmpty()) {
            ageEt.setError("Age required");
            ageEt.requestFocus();
            return false;
        }
        if (hipps.isEmpty()) {
            hippsEt.setError("Hipps required");
            hippsEt.requestFocus();
            return false;
        }
        if (wrist.isEmpty()) {
            wristEt.setError("Wrist required");
            wristEt.requestFocus();
            return false;
        }
        if(defaultValue == 0){
            if (waist.isEmpty()) {
                waistEt.setError("Waist required");
                waistEt.requestFocus();
                return false;
            }
            if (forarm.isEmpty()) {
                forarmEt.setError("Forearm required");
                forarmEt.requestFocus();
                return false;
            }
        }else{
            if (thigh.isEmpty()) {
                thighEt.setError("Thigh required");
                thighEt.requestFocus();
                return false;
            }
            if (calf.isEmpty()) {
                calfEt.setError("Calf required");
                calfEt.requestFocus();
                return false;
            }
        }
        return true;
    }

    private void calculateFatPercentage(String age, String hipps, String wrist, String waist, String forarm, String thigh, String calf) {

        if(defaultValue == 0){
            if(Integer.parseInt(age) <= 30){
                fatPercentage = (float) (Integer.parseInt(waist) + (0.5 * Integer.parseInt(hipps)) -(3 * Integer.parseInt(forarm)) - (1.4 *Integer.parseInt(wrist)));
            }else{
                fatPercentage = (float) (Integer.parseInt(waist) + (0.5 * Integer.parseInt(hipps)) -(2.7 * Integer.parseInt(forarm)) - (1.4 *Integer.parseInt(wrist)));
            }

            //clasification
            if((2 <= fatPercentage && fatPercentage <= 5) || (fatPercentage > 25)){
                fatResult.setTextColor(Color.parseColor("#e20303"));
            }else if((6 <= fatPercentage && fatPercentage <= 13) || (18 < fatPercentage && fatPercentage < 25)){
                fatResult.setTextColor(Color.parseColor("#ff7b7b"));
            }else if(14 <= fatPercentage && fatPercentage <= 17){
                fatResult.setTextColor(Color.parseColor("#00796B"));
            }
        }else{
            if(Integer.parseInt(age) <= 30){
                fatPercentage = (float) (Integer.parseInt(hipps) + (0.8 * Integer.parseInt(thigh)) -(2 * Integer.parseInt(calf)) - (1.4 * Integer.parseInt(wrist)));
            }else{
                fatPercentage = (float) (Integer.parseInt(hipps) + Integer.parseInt(thigh) - (2 * Integer.parseInt(calf)) - (1.4 *Integer.parseInt(wrist)));
            }


            //clasification
            if((10 <= fatPercentage && fatPercentage <= 13) || (fatPercentage > 32)){
                fatResult.setTextColor(Color.parseColor("#e20303"));
            }else if((14 <= fatPercentage && fatPercentage <= 20) || (25 < fatPercentage && fatPercentage < 31)){
                fatResult.setTextColor(Color.parseColor("#ff7b7b"));
            }else if(21 <= fatPercentage && fatPercentage <= 24){
                fatResult.setTextColor(Color.parseColor("#00796B"));
            }
        }
        resultLinearL.setVisibility(View.VISIBLE);
        fatResult.setText(String.valueOf(String.format("%.2f",fatPercentage))+" %");
    }
}
