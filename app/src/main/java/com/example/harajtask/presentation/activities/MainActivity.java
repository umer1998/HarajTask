package com.example.harajtask.presentation.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.harajtask.R;
import com.example.harajtask.info.CarDetail;
import com.example.harajtask.presentation.fragments.DetailScreenFragment;
import com.example.harajtask.presentation.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    public ImageView ivShare, ivNavigation;
    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivShare = findViewById(R.id.share);
        ivNavigation = findViewById(R.id.navigation_bar);
        searchView = findViewById(R.id.search);


        homeFragment();
    }

    public void homeFragment() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof HomeFragment)) {

            HomeFragment homeFragment = new HomeFragment(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, homeFragment)
                    .commit();
        }
    }

    public void detailScreenFragment(CarDetail carDetail) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof DetailScreenFragment)) {

            DetailScreenFragment detailScreenFragment = new DetailScreenFragment(MainActivity.this, carDetail);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, detailScreenFragment)
                    .commit();
        }
    }
}