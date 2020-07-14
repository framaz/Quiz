package com.example.framaz.qz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by framaz on 03.04.2017.
 */

public class RulesActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.instrunctions);
        } catch (Exception e) {
            Toast.makeText(this, e + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(
                com.example.framaz.qz.RulesActivity.this,
                MenuActivity.class);
        startActivity(intent);
    }
}
