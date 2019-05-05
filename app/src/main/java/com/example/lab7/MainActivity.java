package com.example.lab7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   Button b1,b2;
   Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=findViewById(R.id.button);
        b1.setOnClickListener(this);
        b2=findViewById(R.id.button2);
        b2.setOnClickListener(this);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.button)  //Game
        {
            intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.button2)    //Game history
        {
            intent  = new Intent(MainActivity.this, ListDataActivity.class);
            startActivity(intent);
        }
    }
}
