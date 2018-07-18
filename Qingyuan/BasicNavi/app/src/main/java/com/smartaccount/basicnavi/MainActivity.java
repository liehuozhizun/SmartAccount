package com.smartaccount.basicnavi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
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
}
