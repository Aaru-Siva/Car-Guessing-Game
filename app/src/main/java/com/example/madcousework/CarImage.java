package com.example.madcousework;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CarImage extends AppCompatActivity {
    //initializing variables
    private String carImage1;
    private String carImage2;
    private String carImage3;
    private String selectedCarImgName;
    private TextView brandNameToGuess;
    private TextView resultBrandName;
    private ImageView imageView_1;
    private ImageView imageView_2;
    private ImageView imageView_3;
    private Integer imageCount;
    private CountDownTimer countDownTimer;
    private boolean isTimerSwitchOn = false;
    private TextView timer;
    private static boolean clickedSubmit = false;

    //ArrayList to store 3 random car images
    private ArrayList<String> selectedRandomBrands;

    //initialize the brands to a array list
    public ArrayList<String> carListArray = new ArrayList<>(Arrays.asList( "acura", "audi", "bentley", "bmw", "buick", "cadillac", "chevrolet",
            "citroen", "dodge", "ferrari", "fiat", "ford", "geeky", "genesis", "gmc", "honda", "jeep", "kia", "lamborghini", "lotus", "mazda", "nissan",
            "pontiac", "renault", "subaru", "suzuki", "tesla", "toyota", "volvo"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);

        //TextView is assign to timer using id
        timer = findViewById(R.id.textView_timer);

        //TextView is assign to brandNameToGuess using id
        brandNameToGuess = findViewById(R.id.textView_carBrand);

        //TextView is assign to show Correct or Wrong Message using id
        resultBrandName = findViewById(R.id.textView_correct_answer);

        //1st Image is assign to imageView_1 using id
        imageView_1 = findViewById(R.id.imageView4);

        //2nd Image is assign to imageView_2 using id
        imageView_2 = findViewById(R.id.imageView5);

        //3rd Image is assign to imageView_3 using id
        imageView_3 = findViewById(R.id.imageView6);



        Intent intent = getIntent();
        //To get the boolean value from main activity
        isTimerSwitchOn = intent.getExtras().getBoolean("switchValue");

        //Check whether switch is on or not
        if (isTimerSwitchOn) {
            switchTimer();
            begin();
        } else {
            begin();
        }

    }

    //To handle timer
    private void switchTimer() {
        //Create the countDownTimer and assigning time for 20 seconds
        countDownTimer = new CountDownTimer(21000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = millisUntilFinished / 1000 + "";
                timer.setText(seconds);
            }

            //Method for when timer finish
            @Override
            public void onFinish() {   //method for when the timer finished
                imageCount++;
                resultBrandName = findViewById(R.id.textView_correct_answer);
                String notAnswered = "NOT ANSWERED";
                resultBrandName.setTextColor(Color.RED);
                resultBrandName.setText(notAnswered);

                highlightcolourCorrectBrand();
            }
        };
        //Start the countdown
        countDownTimer.start();
    }


    public void begin() {
        //Initializing image count as 0 initially
        imageCount = 0;

        //Assigning the 3 unique car image to a array list
        selectedRandomBrands = getBrandsRandom();

        //select (1 out of 3) car images from the array list
        selectedCarImgName = selectedRandomBrands.get(getRandomNumber(0, 2));

        //The first element (car image) from the array list
        carImage1 = selectedRandomBrands.get(0) + "_" + getRandomNumber(1, 2);
        imageView_1.setImageDrawable(getResources().getDrawable(getBrandNo(carImage1, "drawable", getApplicationContext()))); // image is taken from the drawable file

        carImage2 = selectedRandomBrands.get(1) + "_" + getRandomNumber(1, 2);  // the second element (brand) from the array list and a random image( a random id ) from the brand is taken
        imageView_2.setImageDrawable(getResources().getDrawable(getBrandNo(carImage2, "drawable", getApplicationContext()))); // image is taken from the drawable file

        carImage3 = selectedRandomBrands.get(2) + "_" + getRandomNumber(1, 2); // the third element (brand) from the array list and a random image( a random id ) from the brand is taken
        imageView_3.setImageDrawable(getResources().getDrawable(getBrandNo(carImage3, "drawable", getApplicationContext()))); // image is taken from the drawable file

        brandNameToGuess.setText(selectedCarImgName.toUpperCase()); //displaying the selected brand in a text view
    }

    public ArrayList<String> getBrandsRandom() {

        ArrayList<String> uniqueBrandsList = new ArrayList<>(); // getting 3 brands from brandListArray and checking is it available in the array list and then add them to the array list
        while (uniqueBrandsList.size() != 3) { //only take 3 Unique brands
            String pickedBrand = getBrandRandom(); // take a brand
            if (uniqueBrandsList.indexOf(pickedBrand) == -1) { // check if the brand exist in the  list
                uniqueBrandsList.add(pickedBrand);      // add the unique brand to the array list
            }
        }
        return uniqueBrandsList; //the list return 3 unique brands
    }

    //https://www.educative.io/edpresso/how-to-generate-random-numbers-in-java
    // get a random integer value from a range
    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;

    }

    //https://stackoverflow.com/questions/41479017/how-to-get-id-of-images-in-drawable
    //check if the name of the image matches with the images in drawable
    protected final static int getBrandNo(final String imageName, final String imageType, final Context context) {
        final int imageCar = context.getResources().getIdentifier(imageName, imageType, context.getApplicationInfo().packageName);
        if (imageCar == 0) {
            throw new IllegalArgumentException("Image is not found with a Name " + imageName);

        } else {
            return imageCar;
        }

    }

    public String getBrandRandom() { //get a random brand
        return carListArray.get(getRandomNumber(0, (carListArray.size() - 1))); //get a random brand from brandListArray
    }

    // method for the first image
    public void OnClickFirstImage(View view) {
        imageCount++;        //increase the count when the user select the image

        String currentAnswer;           // hold the result (is the selected item is true or false)
        String Image1Name = carImage1.split("_")[0]; // get the brand name for the image by separating the Number of the image name
        if (imageCount == 1) {    //display the status(correct/wrong) only when the image is selected (by select which is count)
            if (selectedCarImgName.equals(Image1Name)) { //check if the brand name is equal to the image name
                currentAnswer = "WOW CORRECT!";    // if above condition true correct is initialize
                resultBrandName.setTextColor(Color.parseColor("#008000")); //display text in green colour
                resultBrandName.setText(currentAnswer.toUpperCase());       // display the status
                if (isTimerSwitchOn) {
                    countDownTimer.cancel();//To pause the timer
                }

            } else {
                currentAnswer = "OOPS WRONG!"; // if above condition false WRONG is initialize
                resultBrandName.setTextColor(Color.parseColor("#FF0000")); //display text in red colour
                resultBrandName.setText(currentAnswer.toUpperCase());  // display the status
                imageView_1.setBackgroundColor(Color.parseColor("#FF0000")); // the background colour of the image is change red to show the selected status is wrong
                highlightcolourCorrectBrand(); // find the correct image and background colour is change to green
                if (isTimerSwitchOn) {
                    countDownTimer.cancel(); //To pause the timer
                }

            }
        }
    }

    // method for the Second image
    public void OnClickSecondImage(View view) {
        String currentAnswer;  // hold the result (is the selected item is true or false)
        String Image2Name = carImage2.split("_")[0]; // get the brand name for the image by separating the Number of the image name
        imageCount = imageCount + 1;  //increase the count when the user select the image
        if (imageCount == 1) { //display the status(correct/wrong) only when the image is selected (by select which is count)
            if (selectedCarImgName.equals(Image2Name)) { //check if the brand name is equal to the image name
                currentAnswer = "WOW CORRECT !"; // if above condition true correct is initialize
                resultBrandName.setTextColor(Color.parseColor("#008000")); //display text in green colour
                resultBrandName.setText(currentAnswer.toUpperCase()); // display the status
                imageView_3.setBackgroundColor(Color.parseColor("#008000"));
                if (isTimerSwitchOn) {
                    countDownTimer.cancel();                   //To pause the timer
                }


            } else {
                currentAnswer = "OOPS WRONG!"; // if above condition false WRONG is initialize
                resultBrandName.setTextColor(Color.parseColor("#FF0000")); //display text in red colour
                resultBrandName.setText(currentAnswer.toUpperCase()); // display the status
                imageView_2.setBackgroundColor(Color.parseColor("#FF0000")); // the background colour of the image is change red to show the selected status is wrong
                highlightcolourCorrectBrand(); // find the correct image and background colour is change to green
                if (isTimerSwitchOn) {
                    countDownTimer.cancel(); //To pause the timer
                }

            }

        } else {
            Toast.makeText(this, "Click Next To Continue", Toast.LENGTH_SHORT).show();  //after the image is selected the only option is to select next
        }

    }


    public void OnClickThirdImage(View view) {
        String currentAnswer; // hold the result (is the selected item is true or false)
        imageCount = imageCount + 1; //increase the count when the user select the image
        String Image3Name = carImage3.split("_")[0]; // get the brand name for the image by separating the Number of the image name

        if (imageCount == 1) { //display the status(correct/wrong) only when the image is selected (by select which is count)
            if (selectedCarImgName.equals(Image3Name)) { //check if the brand name is equal to the image name
                currentAnswer = "WOW CORRECT !"; // if above condition true correct is initialize
                resultBrandName.setTextColor(Color.parseColor("#008000")); //display text in green colour
                resultBrandName.setText(currentAnswer.toUpperCase()); // display the status
                imageView_3.setBackgroundColor(Color.parseColor("#008000"));

            } else {
                currentAnswer = "OOPS WRONG!"; // if above condition false WRONG is initialize
                resultBrandName.setTextColor(Color.parseColor("#FF0000")); //display text in red colour
                resultBrandName.setText(currentAnswer.toUpperCase()); // display the status
                imageView_3.setBackgroundColor(Color.parseColor("#FF0000")); // the background colour of the image is change red to show the selected status is wrong
                highlightcolourCorrectBrand(); // find the correct image and background colour is change to green


            }

        } else {
            Toast.makeText(this, "Click Next To Continue", Toast.LENGTH_SHORT).show(); //after the image is selected the only option is to select next
        }

    }

    public void highlightcolourCorrectBrand() {
        String firstBrandName = carImage1.split("_")[0]; //split the name from the image name
        String secondBrandName = carImage2.split("_")[0];
        String thirdBrandName = carImage3.split("_")[0];

        if (selectedCarImgName.equals(firstBrandName)) { // if the image name is found equal to brand name the image background is coloured with green
            imageView_1.setBackgroundColor(Color.parseColor("#008000"));
        }

        if (selectedCarImgName.equals(secondBrandName)) {
            imageView_2.setBackgroundColor(Color.parseColor("#008000"));
        }

        if (selectedCarImgName.equals(thirdBrandName)) {
            imageView_3.setBackgroundColor(Color.parseColor("#008000"));
        }
    }

    public void OnClickNext(View view) { //method for the Next is selected
        if (imageCount != 0) {  // select is already counted if the above image is selected
            begin(); //now advance to a next screen
            resultBrandName.setText(""); //initialize the status black in the next screen

            imageView_1.setBackgroundColor(Color.parseColor("#FFFFFF")); //initial the background color of the images
            imageView_2.setBackgroundColor(Color.parseColor("#FFFFFF"));

            imageView_3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            Toast.makeText(this, "Click A Car Brand", Toast.LENGTH_SHORT).show();
        }

    }
}
