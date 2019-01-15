package com.example.tanmoy.mydbproject;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.tvResult);
        radioGroup = (RadioGroup)findViewById(R.id.rgSensType);
    }

    public void onDoubleMe(View view) {

        String sensor="";
        String type = "mul";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        String result = null;
        int sensType = radioGroup.getCheckedRadioButtonId();



        if(sensType!=-1) {
            radioButton = (RadioButton) findViewById(sensType);
            String str = (String) radioButton.getText();
            if(str.equals("Soil Moisture")){
                sensor = "soil.moisture";
            }
            else if(str.equals("Temerature in Celsius")){
                sensor = "ambiance.tempc";
            }
            else if(str.equals("Temerature in Fahrenheit")){
                sensor = "ambiance.tempf";
            }
            else if(str.equals("Humidity")){
                sensor = "ambiance.humidity";
            }

            try {
                result = backgroundWorker.execute(type,sensor).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            textView.setText("Average "+str+": "+result);
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Notice");
            alertDialog.setMessage("please select an option!");
            alertDialog.show();
        }
    }
}
