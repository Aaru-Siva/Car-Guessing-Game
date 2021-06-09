package com.example.madcousework;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import static android.view.View.VISIBLE;

public class Hints extends AppCompatActivity {
    //initializing variables
    private final int MAX_GUESSES = 3;

    // for other functions to see the answer
    private String answer;

    // to store the textView underscores
    private String clue;

    // keeps attempts count
    private int attemptCount = 0;

    // various views
    private Db dataBase;
    private ImageView imgHint2;
    private int lastRand;
    private Button btn;
    private EditText editText;
    private TextView  hintScores;
    private TextView hintCarName;
    private TextView hintFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);
        start();
    }

    // find views by ids
    public void findViews() {
        imgHint2 = findViewById(R.id.imgHint2);
        hintScores = findViewById(R.id.hintTextView1);
        btn = findViewById(R.id.hintSubmitButton);
        editText = findViewById(R.id.hintEditText);
        hintCarName = findViewById(R.id.hintsCarName);
        hintFeedback = findViewById(R.id.hintFeedBack);
    }

    // stores the answer
    public void storeAnswer() {
        lastRand = Db.getLastRandomIndex();
        answer = dataBase.getCarName(lastRand);
        answer = answer.toUpperCase();
    }

    //setting the underScore
    public void setUnderscores() {
        clue = "";
        for (int i = 0; i < answer.length(); i++) {
            if (!(answer.charAt(i) == (' '))) {
                clue = clue + "_ ";
            } else
                clue = clue + "   ";
        }
        System.out.println( hintScores.toString());
        System.out.println(answer);
        System.out.println(clue);
        hintScores.setText(clue);
        hintScores.setVisibility(VISIBLE);
    }

    public void start() {
        dataBase = new Db();
        findViews();

        // Set random image
        imgHint2.setImageResource(dataBase.getRandomBrand());
        imgHint2.setVisibility(VISIBLE);

        storeAnswer();

        //test line
        System.out.println(answer);

        // puts the right amount of underscores into TextView
        setUnderscores();

        // forces the keyboard out, as sometimes it seems to be stuck.
        final EditText editText2 = findViewById(R.id.hintEditText);
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imm.showSoftInput(editText2, InputMethodManager.SHOW_IMPLICIT);
                    editText2.setHint("");
                } else
                    editText2.setHint("Enter a Letter!");
            }
        });
    }

    public void newBrand() {
        editText.setVisibility(VISIBLE);
        btn.setText(getString(R.string.submit));
        attemptCount = 0;
        hintCarName.setVisibility(View.INVISIBLE);
        hintScores.setTextColor(Color.rgb(255, 64, 129));
        start();
    }

    public void onClickListener(View v) {
        //Referred from https://stackoverflow.com/questions/13593069/androidhide-keyboard-after-button-click
        //Hide Virtual Key Board When Clicking Button
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btn.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        //Hide Virtual Keyboard
        boolean found = false;

        //if already all guessed well, reset values and start again from beginning
        if (btn.getText().equals(getString(R.string.next))) {
            hintFeedback.setVisibility(View.INVISIBLE);
            newBrand();
        } else if ((editText.getText().toString().equals("")
                || (editText.getText().toString().equals(getString(R.string.enter_a_letter))))) {
            //do nothing -- prevents user from empty input giving error
            System.out.println("No letter entered!");

        } else {
            // it gets the last char of editText
            // and its fine, because max 1 letter can be inserted in editText
            char guess = editText.getText().toString().toUpperCase().charAt(0);

            StringBuilder newClue = new StringBuilder(clue);
            // need to remake the answer with the same amount of spaces as there is in String clue
            String answerRemake = "";

            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == ' ') { // if empty space, put three empty spaces

                    answerRemake = answerRemake + "   ";
                } else if (answer.charAt(i) == ',') { // if comma, leave comma
                    answerRemake = answerRemake + ",";
                } else { // else remake = answer + space
                    answerRemake = answerRemake + answer.charAt(i) + " ";
                }

            }

            // for the length of the clue, if answer Remake at specific char equals the guessed letter
            // reshape newClue with the letter
            for (int j = 0; j < clue.length(); j++) {
                if (answerRemake.charAt(j) == guess) {
                    newClue.setCharAt(j, guess);
                    found = true;
                }

            }

            // so when user enters correct letter, it does not say that guess is incorrect
            if (found) {
                hintFeedback.setText("");
                hintFeedback.setVisibility(View.INVISIBLE);
            }
            // if it was guess was correct, change the feedback to tell that guess is incorrect
            // if it was not found, count it as wrong attempt
            else {
                attemptCount++;

                hintFeedback.setTextColor(Color.rgb(192, 192, 192)); //yellow colour
                hintFeedback.setText(getString(R.string.incorrect_guess));
                hintFeedback.setVisibility(VISIBLE);
            }

            // exporting out of the function so modified clue can be accessed by another guessing action
            clue = newClue.toString();

            hintScores.setText(clue);
            editText.setText("");

            // if no underscores are found, change text view to CORRECT! in green + change button
            if (!(clue.contains("_"))) {
                hintScores.setText(getString(R.string.CORRECT));
                hintScores.setTextColor(Color.rgb(0, 255, 0)); //green colour
                editText.setVisibility(View.INVISIBLE);
                btn.setText(getString(R.string.next));
            }

            //if all the guesses are used, change to WRONG in red + change button
            if (attemptCount >= MAX_GUESSES) {
                guessesUsed();
            }
        }
    }

    public void guessesUsed() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                hintCarName.setText(answer);
                hintCarName.setVisibility(VISIBLE);
                hintScores.setText(getString(R.string.WRONG));
                hintScores.setTextColor(Color.rgb(255, 0, 0)); //red color
                editText.setVisibility(View.INVISIBLE);
                btn.setText(getString(R.string.next));
            }
        });
    }

}