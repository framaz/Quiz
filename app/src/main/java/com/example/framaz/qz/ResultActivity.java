package com.example.framaz.qz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by framaz on 18.03.2017.
 */

public class ResultActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.result_layout);
            int asd = getIntent().getIntExtra("right", 0);
            TextView tw = findViewById(R.id.textView7);
            String lol = Integer.toString(asd);
            tw.setText(lol);
        } catch (Exception e) {
            Toast.makeText(this, e + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
