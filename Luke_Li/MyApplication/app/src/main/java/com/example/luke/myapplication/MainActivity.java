package com.example.luke.myapplication;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mShowValueText;
    private TextView mShowValueText2;
    private TextView mShowValueText3;
    private TextView mShowValueText4;
    private TextView mShowValueText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                mShowValueText.setText(value);
                mShowValueText2.setText(value);
                mShowValueText3.setText(value);
                mShowValueText4.setText(value);
                mShowValueText5.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());

        initView();
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                fragment= new HomeFragment();
                break;
            case R.id.navigation_dashboard:
                fragment = new CalendarFragment();
                break;
            case R.id.navigation_notifications:
                fragment = new AnalysisFragment();
                break;
        }
        return loadFragment(fragment);
    }

    public void skip(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,otherActivity.class);
        startActivity(intent);
    }

    private void initView() {
        mShowValueText = (TextView) findViewById(R.id.textView);
        mShowValueText2 = (TextView) findViewById(R.id.textView2);
        mShowValueText3 = (TextView) findViewById(R.id.textView3);
        mShowValueText4 = (TextView) findViewById(R.id.textView4);
        mShowValueText5 = (TextView) findViewById(R.id.textView5);
    }

}
