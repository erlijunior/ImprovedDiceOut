package com.example.erli.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

//  Field to hold the roll result text
    TextView rollResult;

//  Field to hold the score text
    TextView scoreText;

//  Field to hold the score
    int score;


//  Field to hold the random nunber
    Random rand;

//  Fields to hold the dice value
    int die1;
    int die2;
    int die3;
    int die4;

//  ArrayList to hold all 4 dice values
    List<Integer> dice;

//  ArrayList to hold all 4 dice images
    List<ImageView> diceImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

//      Set initial score
        score = 0;
        Toast.makeText(this, "Welcome to DiceOut", Toast.LENGTH_SHORT).show();

//      Link instances to widgets in the activity view
        rollResult = findViewById(R.id.rollResult);
        scoreText = findViewById(R.id.score);

//      Initiate the random number generator
        rand = new Random();

//      Create ArrayList container for the dice values
        dice = new ArrayList<>();

        ImageView dice1Image = findViewById(R.id.die1Image);
        ImageView dice2Image = findViewById(R.id.die2Image);
        ImageView dice3Image = findViewById(R.id.die3Image);
        ImageView dice4Image = findViewById(R.id.die4Image);

        diceImageViews = new ArrayList<>();

        diceImageViews.add(dice1Image);
        diceImageViews.add(dice2Image);
        diceImageViews.add(dice3Image);
        diceImageViews.add(dice4Image);

    }

    public void rollDice(View view) {
        rollResult.setText("Clicked!");

//      Roll dice
        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;
        die4 = rand.nextInt(6) + 1;

//      Set dice value into an ArrayList
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);
        dice.add(die4);

        for (int dieOfSet = 0 ; dieOfSet < 4 ; dieOfSet++) {
            String imageName = "die_" + dice.get(dieOfSet) + ".png";
            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream,null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);
            }catch (IOException e) {
                e.printStackTrace();
            }


        }

        //Build the message with the result
        String msg;
        if(die1 == die2 && die1 == die3 && die1 == die4) {
            //Quadruple
            int scoreFour = die1 * 200;
            msg = "You rolled a quadruple " + die1 + "! You score " + scoreFour + " points.";
            score += scoreFour;
        } else
            if ((die1 == die2 && (die1 == die3 ^ die1 == die4)) ||
                  (die3 == die4 &&(die3 == die1 ^ die3 == die2))) {
                    //Triple
                    msg = "You rolled a triple for 150 points!";
                    score += 150;
        } else
            if ((die1 == die2 && die3 == die4) || (die1 == die3 && die2 == die4) ||
                  (die1 == die4 && die2 == die3)) {
                   //2 Doubles
                   msg = "You rolled 2 doubles for 100 points!";
                   score += 100;
        } else
            if (die1 == die2 ^ die3 == die4 ^ die1 == die3 ^ die2 == die4 ^
                    die1 == die4 ^ die2 == die3) {
                   //A Double
                   msg = "You rolled a double for 50 points!";
                   score += 50;
        } else {
                msg = "You didn't score this roll. Try again!!";
            }

        //Update app to display the result message
        rollResult.setText(msg);
        scoreText.setText("Score: " + score);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
