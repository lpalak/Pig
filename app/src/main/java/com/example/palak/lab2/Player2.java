package com.example.palak.lab2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class Player2 extends ActionBarActivity {

    private FrameLayout die1;
    private FrameLayout die2;
    private Button rollDice;
    private Button hold;
    private TextView roundTextView;
    private TextView player1Score;
    private TextView player2Score;
    int roundTotal = 0;
    int gameTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2);

        die1 = (FrameLayout)findViewById(R.id.die1);
        die2 = (FrameLayout)findViewById(R.id.die2);

        roundTextView = (TextView)findViewById(R.id.count);
        player1Score = (TextView)findViewById(R.id.player1Score);
        player2Score = (TextView)findViewById(R.id.player2Score);

        // Get the intent and score from player1's activity
        Intent intent = getIntent();
        String score1 = intent.getStringExtra("Score1");
        String score2 = intent.getStringExtra("Score2");

        // set the scores in their respective text box

        player1Score.setText(score1);
        player2Score.setText(score2);

        rollDice = (Button)findViewById(R.id.roll_dice);
        rollDice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

        hold = (Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set the text box
                setGameTotal();

                // Call other player when click on hold
                callOtherPlayer();

            }
        });
    }

    public void rollDice(){
        int number1 = 1 + (int)(Math.random()*6);
        int number2 = 1 + (int)(Math.random()*6);
        setDie(number1,die1);
        setDie(number2,die2);

        if(number1 == 1 || number2 == 1){

            // Set the round total if any one dice has 1
            roundTotal=0;
            // Set the Text values of final scores
            setGameTotal();

            // Player1 Round
            callOtherPlayer();

        } else {
            roundTotal = Integer.parseInt((String) roundTextView.getText().toString());
            roundTotal = roundTotal+(number1+number2);
            roundTextView.setText(String.valueOf(roundTotal));
        }
    }

    public void setDie(int num , FrameLayout frameLayout) {

        Drawable picture;
        switch (num){
            case 1:
                picture = getResources().getDrawable(R.drawable.die_face_1);
                frameLayout.setBackground(picture);
                break;
            case 2:
                picture = getResources().getDrawable(R.drawable.die_face_2);
                frameLayout.setBackground(picture);
                break;
            case 3:
                picture = getResources().getDrawable(R.drawable.die_face_3);
                frameLayout.setBackground(picture);
                break;
            case 4:
                picture = getResources().getDrawable(R.drawable.die_face_4);
                frameLayout.setBackground(picture);
                break;
            case 5:
                picture = getResources().getDrawable(R.drawable.die_face_5);
                frameLayout.setBackground(picture);
                break;
            case 6:
                picture = getResources().getDrawable(R.drawable.die_face_6);
                frameLayout.setBackground(picture);
                break;
        }

    }

    public void callOtherPlayer(){

        // Check for game total, if > 99 then winner else call player 2
        if (gameTotal > 99) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Player2.this);
            alertDialog.setTitle("Congratulations!!!");
            alertDialog.setMessage("Player 2, you won!!");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    // reset all text box
                    reset();
                    Intent intent = new Intent(Player2.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            }).create().show();

        } else {
            Intent intent = new Intent(Player2.this, MainActivity.class);
            intent.putExtra("Score2", String.valueOf(gameTotal));
            intent.putExtra("Score1", player1Score.getText().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    public void setGameTotal(){

        gameTotal = (Integer.parseInt((String) player2Score.getText().toString()));
        gameTotal = gameTotal+roundTotal;
        player2Score.setText(String.valueOf(gameTotal));
        roundTextView.setText("0");

    }

    public void reset(){

        roundTextView.setText("0");
        player1Score.setText("");
        player2Score.setText("");
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