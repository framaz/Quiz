package com.example.framaz.qz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by framaz on 03.04.2017.
 */

public class MenuActivity extends Activity {
    Button endless,blitz,rules;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.menu);
            endless=(Button)findViewById(R.id.button5);
            blitz=(Button)findViewById(R.id.button8);
            rules=(Button)findViewById(R.id.button7);
            endless.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this, com.example.framaz.qz.MainActivity.class);
                    intent.putExtra("gameMode",1);
                    startActivity(intent);
                }
            });
            blitz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this, com.example.framaz.qz.MainActivity.class);
                    intent.putExtra("gameMode",2);
                    startActivity(intent);
                }
            });
            rules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MenuActivity.this, com.example.framaz.qz.RulesActivity.class);
                    startActivity(intent);
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this, e+"", Toast.LENGTH_LONG).show();
        }
    }
}
