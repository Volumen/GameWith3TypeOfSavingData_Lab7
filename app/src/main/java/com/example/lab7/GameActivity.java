package com.example.lab7;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Values: ";
    int r,n=0;
    Button b1,b2,b3;
    Toast StartToast, RoundToast;
    TextView tx1,tx2;
    int p1pkt,p2pkt;
    String firstplayer="Pawel";
    String secondplayer="Kinga";
    boolean canWe=false,player1=false,player2=false,toFast1=false,toFast2=false,roundOver1=false,roundOver2=false;
    String MyPreferences = "DataPlayers";
    SharedPreferences sharedPreferences;
    DatabaseHelper dbase;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        intent = new Intent(getApplicationContext(), BundleActivity.class);
        dbase = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(firstplayer, "Pawel");
        editor.putString(secondplayer, "Kinga");
        editor.putInt("Players", 2);
        editor.putInt("Rounds", 5);
        editor.apply();


        b1=findViewById(R.id.button);
        b1.setOnClickListener(this);
        b2=findViewById(R.id.button2);
        b2.setOnClickListener(this);
        b3=findViewById(R.id.button3);
        b3.setOnClickListener(this);

        tx1=findViewById(R.id.textView);
        tx2=findViewById(R.id.textView2);

        StartGame();
        n++;

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.button)  //first player button
        {
            if(toFast1 || roundOver1)
            {
                //do nothing
            }
            else
            {
                player1=true;
                if (canWe )
                {
                    if (player2 )
                    {
                        p1pkt += 3;
                        tx2.setText("PKT: " + String.valueOf(p1pkt));
                        roundOver1=true;
                    } else {
                        p1pkt += 5;
                        tx2.setText("PKT: " + String.valueOf(p1pkt));
                        roundOver1=true;
                    }
                }
                else
                {
                    p1pkt = p1pkt-1;
                    toFast1 = true;
                    player1 = false;
                    tx2.setText("PKT: " + String.valueOf(p1pkt));
                    roundOver1=true;
                }
            }
            Check();
        }
        else if(v.getId()==R.id.button2)       //Second player button
        {
            if(toFast2 || roundOver2)
            {
                //do nothing
            }
            else
            {
                player2=true;
                if (canWe )
                {
                    if (player1 )       //second click
                    {
                        p2pkt += 3;
                        tx1.setText("PKT: " + String.valueOf(p2pkt));
                        roundOver2=true;
                    } else {            //first click
                        p2pkt += 5;
                        tx1.setText("PKT: " + String.valueOf(p2pkt));
                        roundOver2=true;
                    }
                }
                else    //to fast
                {
                    p2pkt =p2pkt -1;
                    toFast2 = true;
                    player2 = false;
                    tx1.setText("PKT: " + String.valueOf(p2pkt));
                    roundOver2=true;
                }
            }
            Check();
        }
        else if(v.getId()==R.id.button3)    //showing current state of game
        {
            intent.putExtra("pkt1",p1pkt);
            intent.putExtra("firstplayername",firstplayer);
            intent.putExtra("round",n);
            intent.putExtra("pkt2",p2pkt);
            intent.putExtra("secondplayername",secondplayer);
            startActivity(intent);
        }
    }
    //starting a round
    void StartGame()
    {
            StartToast = Toast.makeText(GameActivity.this, "Start!", Toast.LENGTH_SHORT);
            r = new Random().nextInt(3) + 5; //6-8 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        StartToast.show();
                    canWe = true;
                }
            }, r * 1000);
        // now players can play
    }
    //check game over
    public void Check() {
        RoundToast = Toast.makeText(GameActivity.this, "Round nr: " + (n+1), Toast.LENGTH_SHORT);
        if (roundOver1 && roundOver2) {
            n++;
            if(n<=5) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RoundToast.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                b1.setEnabled(true);
                                b2.setEnabled(true);
                            }
                        },500);
                    }
                }, 1000);
                Reset();
                StartGame();
            }
            else if (n > 5) {
                String winner = "";
                if (p1pkt > p2pkt) {
                    winner = sharedPreferences.getString(firstplayer, "defaultvalue");
                    AddData(firstplayer,String.valueOf(p1pkt));
                } else if (p1pkt < p2pkt) {
                    winner = sharedPreferences.getString(secondplayer, "defaultvalue");
                    AddData(secondplayer,String.valueOf(p2pkt));
                }
                ShowWinner(winner);
            }
        }
    }
    void Reset()
    {
        canWe=false;
        player1=false;
        player2=false;
        toFast1=false;
        toFast2=false;
        roundOver1=false;
        roundOver2=false;
        b1.setEnabled(false);
        b2.setEnabled(false);
    }
    void ShowWinner(String win)
    {
        new AlertDialog.Builder(this)
                .setTitle("The winner is: "+win+"!")
                .setMessage("Game is over :)")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
    public void AddData(String im, String pkt)
    {
        boolean insertData = dbase.addData(im, pkt);
        if (insertData == true) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
