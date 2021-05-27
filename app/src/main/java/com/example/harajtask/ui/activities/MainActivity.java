package com.example.harajtask.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.harajtask.R;
import com.example.harajtask.info.CarDetail;
import com.example.harajtask.ui.fragments.DetailScreenFragment;
import com.example.harajtask.ui.fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public ImageView ivShare, ivNavigation;
    public SearchView searchView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivShare = findViewById(R.id.share);
        ivNavigation = findViewById(R.id.navigation_bar);
        ivNavigation.setOnClickListener(this);
        searchView = findViewById(R.id.search);

        //drawer is implemeted so user can swipe from left so drawer open
        //or user can click naviagtion drawer icon on top left side
        drawerLayout = findViewById(R.id.drawer);

        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);


        homeFragment();
    }

    //calling home fragment which is basically 1st screen of app
    //home frament show list of cars

    public void homeFragment() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof HomeFragment)) {

            HomeFragment homeFragment = new HomeFragment(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, homeFragment)
                    .commit();
        }
    }

    //calling Detail screen fragment which is basically 2nd screen of app
    //home frament shows the detail of the car selected

    public void detailScreenFragment(CarDetail carDetail) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof DetailScreenFragment)) {

            DetailScreenFragment detailScreenFragment = new DetailScreenFragment(MainActivity.this, carDetail);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, detailScreenFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    //on back button press handler

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragment instanceof HomeFragment) {
            MainActivity.this.finish();
            System.exit(0);
        } else if (fragment instanceof DetailScreenFragment) { //from car detail screen to car list screen we make invisible share icon
            searchView.setVisibility(View.VISIBLE);            // and visible navigation icon and search view
            ivNavigation.setVisibility(View.VISIBLE);
            ivNavigation.setOnClickListener(this);
            ivShare.setVisibility(View.GONE);
            homeFragment();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_home:
                homeFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
            return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigation_bar:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
    }
}