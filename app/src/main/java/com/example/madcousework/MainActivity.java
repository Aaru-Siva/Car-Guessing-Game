package com.example.madcousework;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    Switch switchTimer;
    boolean isTimerSwitchOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchTimer = findViewById(R.id.switch_timer);

        //To change the boolean value when the switch button clicked
        switchTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isTimerSwitchOn = true;
                }else{
                    isTimerSwitchOn = false;
                }
            }
        });

    }

    public void launchCarMakeActivity(View view) {
        Intent intent = new Intent(this, CarMake.class);
        intent.putExtra("switchValue", isTimerSwitchOn);   //To pass the boolean value to the other activity
        startActivity(intent);
    }

    public void launchHintActivity(View view) {
        Intent intent = new Intent(this, Hints.class);
        intent.putExtra("switchValue", isTimerSwitchOn);   //To pass the boolean value to the other activity
        startActivity(intent);
    }

    public void launchAdvancedLevelActivity(View view) {
        Intent intent = new Intent(this, AdvancedLevel.class);
        intent.putExtra("switchValue", isTimerSwitchOn);   //To pass the boolean value to the other activity
        startActivity(intent);
    }

    public void launchCarImageActivity(View view) {
        Intent intent = new Intent(this, CarImage.class);
        intent.putExtra("switchValue", isTimerSwitchOn);   //To pass the boolean value to the other activity
        startActivity(intent);
    }

}