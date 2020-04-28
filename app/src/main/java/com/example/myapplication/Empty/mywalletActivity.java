package com.example.myapplication.Empty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Adaptors.ViewPagerAdapter;
import com.example.myapplication.FragmentActive;
import com.example.myapplication.FragmentExpired;
import com.example.myapplication.R;
import com.example.myapplication.activity_category;
import com.example.myapplication.activity_homepage;
import com.google.android.material.tabs.TabLayout;

public class mywalletActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        Toolbar toolbar=findViewById(R.id.toolbar4);
        toolbar.setNavigationIcon(R.drawable.bak);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mywalletActivity.this, activity_homepage.class);
                startActivity(intent);
                finish();
            }
        });

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

