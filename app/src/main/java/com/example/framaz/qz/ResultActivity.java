package com.example.framaz.qz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by framaz on 18.03.2017.
 */

public class ResultActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.result_layout);
            int asd=getIntent().getIntExtra("wrong",0);
            Toast.makeText(this,asd,Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Toast.makeText(this, e+"", Toast.LENGTH_LONG).show();
        }
    }
}
