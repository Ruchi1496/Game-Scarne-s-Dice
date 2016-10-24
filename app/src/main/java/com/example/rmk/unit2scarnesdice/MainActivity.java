package com.example.rmk.unit2scarnesdice;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.Random;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    TextView tvPlayer1;
    TextView tvPlayer2;
    TextView tvCurrent;
    ImageView ivDice;
    Button bHold;
    Button bRoll;
    Button bReset;
    int defaultTextColor;

   // Animation anim1,anim2;

    boolean user_turn=true;

    private int user_totalScore=0,user_currentScore=0,computer_totalScore=0,computer_currentScore=0;

    int imageArray[] = {R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay= (TextView)findViewById(R.id.tvDisplay);
        tvPlayer1= (TextView)findViewById(R.id.tvPlayer1);
        tvPlayer2= (TextView)findViewById(R.id.tvPlayer2);
        tvCurrent= (TextView)findViewById(R.id.tvCurrent);
        ivDice= (ImageView)findViewById(R.id.ivDice);
        bHold=(Button)findViewById(R.id.bHold);
        bRoll=(Button)findViewById(R.id.bRoll);
        bReset=(Button)findViewById(R.id.bReset);

        defaultTextColor = tvDisplay.getTextColors().getDefaultColor();
//        anim1= new TranslateAnimation(0.0f,200.0f,0.0f,0.0f);
//        anim1.setDuration(300);

//        anim2= new TranslateAnimation(0.0f,-200.0f,0.0f,0.0f);
//        anim2.setDuration(300);

        bRoll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RollMethod();
            }
        });
        bHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoldMethod();
            }
        });
        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetMethod();
            }
        });
    }

    private void ResetMethod(){
        user_turn=true;
        user_totalScore=0;
        user_currentScore=0;
        computer_totalScore=0;
        computer_currentScore=0;
        tvDisplay.setText("Start Again!!");
        tvPlayer2.setText("Computer Score: "+computer_totalScore);
        tvPlayer1.setText("User Score: "+user_totalScore);
        enableButtons();
    }

    private void HoldMethod(){
        user_turn=false;
        user_totalScore+=user_currentScore;
        tvPlayer2.setText("Computer Score: "+computer_totalScore);
        tvPlayer1.setText("User Score: "+user_totalScore);
        user_currentScore=0;
        computer_currentScore=0;
        computerTurn();
    }

    private void RollMethod(){
        tvDisplay.setText("Game Started!!");

        Random random=new Random();
        final int nextRoll= random.nextInt(6)+1;

        disableButtons();

        ivDice.setImageResource(imageArray[nextRoll-1]);  //path or name of the images drawn from drawable is of Integer type
    //    ivDice.setAnimation(anim1);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){

                enableButtons();
                if(nextRoll==1){
                    user_currentScore=0;
                    user_turn=false;
                    computerTurn();
                }else{
                    user_currentScore+=nextRoll;

                }
                tvPlayer2.setText("Computer Score: "+computer_totalScore);
                tvPlayer1.setText("User Score: "+user_totalScore);
                tvCurrent.setText("Current Score: "+user_currentScore);
                if((user_currentScore+user_totalScore)>=100){
                    tvPlayer1.setText("User Score: "+user_totalScore+user_currentScore);
                    Toast.makeText(MainActivity.this,"You Won!",Toast.LENGTH_LONG).show();
                    ResetMethod();
                }
            }
        },500);

    }

    private void computerTurn(){

       if(computer_currentScore<=15) {
           tvDisplay.setText("Game Started!!");
           tvPlayer2.setTextColor(Color.rgb(200,0,0));
           tvPlayer1.setTextColor(defaultTextColor);

           Random random = new Random();
           final int nextRoll = random.nextInt(6) + 1;
           disableButtons();
           ivDice.setImageResource(imageArray[nextRoll - 1]);  //path or name of the images drwan from drawable is of Integer type
           Handler handler = new Handler();
           handler.postDelayed(new Runnable() {
               public void run() {
                   if (nextRoll == 1) {
                       computer_currentScore=0;
                       finishComputerTurn();
                   } else {
                       computer_currentScore += nextRoll;
                   }
                   tvPlayer2.setText("Computer Score: " + computer_totalScore);
                   tvPlayer1.setText("User Score: " + user_totalScore);
                   tvCurrent.setText("Current Score: " + computer_currentScore);
                   if ((computer_currentScore + computer_totalScore) >= 100) {
                       tvPlayer2.setText("Computer Score: " + (computer_totalScore+computer_currentScore));
                       Toast.makeText(MainActivity.this, "Computer Won!", Toast.LENGTH_LONG).show();
                       ResetMethod();
                   }
                   if(!user_turn)
                   computerTurn();
               }
           }, 1000);
       }else{
           finishComputerTurn();
       }
    }

    private void finishComputerTurn(){
        enableButtons();
        computer_totalScore+=computer_currentScore;
        computer_currentScore=0;
        tvPlayer2.setText("Computer Score: " + computer_totalScore );
        tvPlayer1.setText("User Score: " + user_totalScore);
        tvCurrent.setText("Current Score: " + computer_currentScore);
        user_turn=true;
        tvPlayer1.setTextColor(Color.rgb(200,0,0));
        tvPlayer2.setTextColor(defaultTextColor);
    }

    private void enableButtons(){
        bRoll.setEnabled(true);
        bHold.setEnabled(true);
    }
    private void disableButtons(){
        bRoll.setEnabled(false);
        bHold.setEnabled(false);
    }
}
