package com.example.android.onsitetask1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public static int z=5,l=4;
    public static float y,x;
    public void dim(View view)
    {
        EditText e1 = (EditText) findViewById(R.id.rows);
        EditText e2 = (EditText) findViewById(R.id.columns);
        if(!(e1.getText().toString().equals(""))&&!(e2.getText().toString().equals("")))
        {z=Integer.parseInt(e1.getText().toString());
            l=Integer.parseInt(e2.getText().toString());
            y=Float.valueOf(e1.getText().toString());
            x=Float.valueOf(e2.getText().toString());
            Intent intent = new Intent(this,GameActivity.class);
            startActivity(intent);
        }
    }
}
