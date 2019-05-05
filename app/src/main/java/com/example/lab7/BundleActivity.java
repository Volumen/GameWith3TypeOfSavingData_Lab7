package com.example.lab7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BundleActivity extends AppCompatActivity {
    TextView t1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bundle_layout);

        t1=findViewById(R.id.textView);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            String name = bundle.getString("firstplayername");
            int points = bundle.getInt("pkt1");
            String name2 = bundle.getString("secondplayername");
            int points2 = bundle.getInt("pkt2");
            int round = bundle.getInt("round");
            t1.setTextSize(20);
            t1.setText("It's round nr: "+round+"\nPlayer "+name+" have "+ points +" points."+"\nPlayer "+name2+" have "+ points2 +" points.");
        }
    }
}
