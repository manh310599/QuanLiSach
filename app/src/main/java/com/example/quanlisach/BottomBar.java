package com.example.quanlisach;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class BottomBar extends AppCompatActivity {

    private BottomNavigationView mBottomBar;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_bottom_bar);


        mBottomBar = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);


        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        mBottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeId) {
                    viewPager.setCurrentItem(0);
                }
                if (id == R.id.Search) {
                    viewPager.setCurrentItem(1);
                }

                if (id == R.id.Setting) {
                    viewPager.setCurrentItem(2);
                }
                if (id == R.id.Profile) {
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position)
                {
                    case 0:
                        mBottomBar.getMenu().findItem(R.id.homeId).setChecked(true);
                        break;
                    case 1:
                        mBottomBar.getMenu().findItem(R.id.Search).setChecked(true);
                        break;

                    case 2:
                        mBottomBar.getMenu().findItem(R.id.Setting).setChecked(true);
                        break;
                    case 3:
                        mBottomBar.getMenu().findItem(R.id.Profile).setChecked(true);
                        break;
                }
            }
        });

    }

}