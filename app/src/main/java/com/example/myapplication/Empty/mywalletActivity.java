package com.example.myapplication.Empty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myapplication.Adaptors.ViewPagerAdapter;
import com.example.myapplication.FragmentActive;
import com.example.myapplication.FragmentExpired;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class mywalletActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
         toolbar=findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bak);

        //START HERE

        tabLayout = (TabLayout)findViewById(R.id.mytab);
        viewPager = (ViewPager)findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // ADD FRAGMENT HERE

        adapter.AddFragment(FragmentActive.newInstance("-1"),"فعّالة");
        adapter.AddFragment(FragmentExpired.newInstance("-1"),"منتهية");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}

