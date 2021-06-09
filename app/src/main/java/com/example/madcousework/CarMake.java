package com.example.madcousework;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CarMake extends AppCompatActivity {
    private String brandSpinnerLabel;
    private String brandName;
    private Button identifyButton;
    private TextView resultBrandName;
    private TextView correctBrandName;
    private Spinner brand_spinner;
    private ImageView iv_car;
    private TextView timer;
    private CountDownTimer countDownTimer;
    private boolean isTimerSwitchOn = false;

    //List of brand
    public ArrayList<String> carListArray = new ArrayList<>(Arrays.asList( "acura", "audi", "bentley", "bmw", "buick", "cadillac", "chevrolet",
            "citroen", "dodge", "ferrari", "fiat", "ford", "geeky", "genesis", "gmc", "honda", "jeep", "kia", "lamborghini", "lotus", "mazda", "nissan",
            "pontiac", "renault", "subaru", "suzuki", "tesla", "toyota", "volvo"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        Intent intent = getIntent();
        //To get the boolean value from main activity
        isTimerSwitchOn = intent.getExtras().getBoolean("switchValue");

        //TextView is assign to timer
        timer = findViewById(R.id.textView_timer);
        //Image is assign to CarImage
        iv_car = findViewById(R.id.imageRandom);
        //Spinner is assign to brand Dropdown
        brand_spinner = findViewById(R.id.DropDown);
        //TextView is assign to show Correct or Wrong message
        correctBrandName = findViewById(R.id.answerLabel);
        //TextView is assign to show Correct Brand
        resultBrandName = findViewById(R.id.correctAnswer);
        //Button is assign to identifyButton
        identifyButton = findViewById(R.id.submit);
        //Check whether switch is on or not
        if (isTimerSwitchOn){
            switchTimer();
            begin();
        }else {
            begin();
        }
    }

    private void switchTimer() {
        countDownTimer = new CountDownTimer(21000,1000) {  //Create the countDownTimer
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = millisUntilFinished / 1000 + "";
                timer.setText(seconds);
            }
            @Override
            public void onFinish() {   //method for when the timer finished
                submitAuto();
            }
        };
        countDownTimer.start();       //To Start the countdown
    }

    public void begin(){
        brandName = getABrandRandom(); //get random brand name from the array list
        String imageName = brandName + "_" + getRandomNumber(1, 2);  //get image name with random image name and random Id
        iv_car.setImageDrawable( getResources().getDrawable(getBrandID(imageName, "drawable", getApplicationContext()))); // image is taken from the drawable file

        brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // get the selected name from the drop down
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandSpinnerLabel = parent.getItemAtPosition(position).toString(); //get the drop down value
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.brand_names_array, android.R.layout.simple_spinner_item); // arrayAdapter with default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //layout is set to get list of drop down
        // Apply the adapter to the spinner.
        if (brand_spinner != null) {
            brand_spinner.setAdapter(adapter);
        }

        identifyButton.setText("Identify"); //initially but is set to submit

    }

    public String getABrandRandom(){  //get a random brand
        return carListArray.get(getRandomNumber(0,(carListArray.size()-1)));
    }


    public static int getRandomNumber(int min, int max) { // get a random integer value from a range
        return (new Random()).nextInt((max - min) + 1) + min;

    }

    //https://stackoverflow.com/questions/41479017/how-to-get-id-of-images-in-drawable
    //check if the name of the image matches with the images in drawable
    protected final static int getBrandID(final String imageName, final String imageType, final Context context) {
        final int brandID = context.getResources().getIdentifier(imageName, imageType, context.getApplicationInfo().packageName);
        if (brandID == 0) {
            throw new IllegalArgumentException("There is no car with name " + imageName);
        }else{
            return brandID;
        }
    }


    //Method to auto submit when time over
    public void submitAuto(){

        identifyButton.setText("Next");
        if (brandSpinnerLabel.equals(brandName) ){
            String answer = "WOW CORRECT !";
            resultBrandName = findViewById(R.id.answerLabel);
            resultBrandName.setTextColor(Color.GREEN);
            resultBrandName.setText(answer);

        }else {
            String answer = "OOPS WRONG !";
            resultBrandName = findViewById(R.id.answerLabel);
            resultBrandName.setTextColor(Color.RED);
            resultBrandName.setText(answer);

            correctBrandName = findViewById(R.id.correctAnswer);
            correctBrandName.setTextColor(Color.YELLOW);
            correctBrandName.setText(brandName.toUpperCase());

        }

    }

    //To check the validation of the users answer by clicking the submit button
    public void identifyButton(View view) {
        if (identifyButton.getText().equals("Identify")){
            brand_spinner.setEnabled(false);
            identifyButton.setText("Next");

            if (brandSpinnerLabel.equals(brandName) ){
                String answer = "WOW! CORRECT !";
                resultBrandName = findViewById(R.id.answerLabel);
                resultBrandName.setTextColor(Color.GREEN);
                resultBrandName.setText(answer);


                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }

            }else {
                String answer = "OOPS WRONG !";
                resultBrandName = findViewById(R.id.answerLabel);
                resultBrandName.setTextColor(Color.RED);
                resultBrandName.setText(answer);

                correctBrandName = findViewById(R.id.correctAnswer);
                correctBrandName.setTextColor(Color.YELLOW);
                correctBrandName.setText(brandName.toUpperCase());


                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }
            }
        }
        else {
            //When the button label is set to Next
            begin();
            brand_spinner.setEnabled(true);
            if (isTimerSwitchOn){
                countDownTimer.cancel();   //To reset the timer
                switchTimer();
            }

            resultBrandName = findViewById(R.id.answerLabel);
            resultBrandName.setText("");

            correctBrandName = findViewById(R.id.correctAnswer);
            correctBrandName.setText("");
        }

    }

}