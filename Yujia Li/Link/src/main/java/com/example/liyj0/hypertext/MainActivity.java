package com.example.liyj0.hypertext;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void skip(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,otherActivity.class);
        startActivity(intent);
    }
}