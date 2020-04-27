package com.example.myapplication.Empty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.Adaptors.calendarAdapter;
import com.example.myapplication.Data.Userinvoice;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;

public class CalenderActivity extends AppCompatActivity {
    // calendarAdapter calendarAdapter;
    DatabaseReference ref2, ref3;
    public   String expired_data;
    public   String name;

    ArrayList<String> nameslist;
    RecyclerView recyclerView;
    EditText search;

    private MCalendarView expCalendarView;
    RecyclerView.LayoutManager layoutManager;
    calendarAdapter adapter;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        nameslist=new ArrayList<>();
        search=(EditText) findViewById(R.id.searchView);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_calendar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        expCalendarView = ((MCalendarView) findViewById(R.id.calendar));
        Toolbar toolbar=findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bak);
/////////////////

        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.child("document").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               MarkedDates.getInstance().removeAdd();

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                for (DataSnapshot datashot : dataSnapshot.getChildren()) {
                    //expCalendarView.clearDisappearingChildren();

                    Userinvoice invoice = datashot.getValue(Userinvoice.class);
                    if (invoice.getUser_id().equals(uid)) {
                       String expired_data1 = datashot.child("edate").getValue(String.class);
                        if(expired_data1.equals("")){
                            expCalendarView.markDate(2080, 4, 1);

                        }
                        else {
                            String t = expired_data1.substring(5, 6);
                            String t1 = expired_data1.substring(8, 9);
                            int t12 = Integer.parseInt(t);
                            int t13 = Integer.parseInt(t1);

                            if (t12 == 0 && t13 == 0) {
                                String h = expired_data1.substring(9, 10);
                                String h1 = expired_data1.substring(6, 7);
                                String h2 = expired_data1.substring(0, 4);

                                int u = Integer.parseInt(h);
                                int u1 = Integer.parseInt(h1);
                                int u2 = Integer.parseInt(h2);
                                expCalendarView.markDate(u2, u1, u);
                            }
                            if (t12 != 0 && t13 == 0) {
                                String h = expired_data1.substring(9, 10);
                                String h1 = expired_data1.substring(5, 7);
                                String h2 = expired_data1.substring(0, 4);

                                int u = Integer.parseInt(h);
                                int u1 = Integer.parseInt(h1);
                                int u2 = Integer.parseInt(h2);
                                expCalendarView.markDate(u2, u1, u);
                            }
                            if (t12 == 0 && t13 != 0) {
                                String h = expired_data1.substring(8, 10);
                                String h1 = expired_data1.substring(6, 7);
                                String h2 = expired_data1.substring(0, 4);

                                int u = Integer.parseInt(h);
                                int u1 = Integer.parseInt(h1);
                                int u2 = Integer.parseInt(h2);
                                expCalendarView.markDate(u2, u1, u);
                            }
                            if (t12 != 0 && t13 != 0) {

                                String h = expired_data1.substring(8, 10);
                                String h1 = expired_data1.substring(5, 7);
                                String h2 = expired_data1.substring(0, 4);

                                int u = Integer.parseInt(h);
                                int u1 = Integer.parseInt(h1);
                                int u2 = Integer.parseInt(h2);
                                expCalendarView.markDate(u2, u1, u);


                            }
                        }
                    }



                }}
                @Override
                public void onCancelled (@NonNull DatabaseError databaseError){

                }
              });

        expCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, final DateData date) {
                nameslist.clear();
                recyclerView.removeAllViews();
                databaseReference.child("document").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

                        for (DataSnapshot datashot : dataSnapshot.getChildren()) {
                            Userinvoice invoice = datashot.getValue(Userinvoice.class);
                            if (invoice.getUser_id().equals(uid)) {

                            expired_data = datashot.child("edate").getValue(String.class);
                            name = datashot.child("name").getValue(String.class);


                            String g = date.getDayString();
                            String l = date.getMonthString();
                            String m = date.getYearString();
                            String n = m + "-" + l + "-" + g;

                            if (expired_data.equals(n)) {
                                nameslist.add("          "+name);
                                // expCalendarView.markDate(2020,3,20);

                            } }else {

                                // nameslist.add(name);

                            }
                        }
                            adapter = new calendarAdapter(CalenderActivity.this, nameslist);
                            recyclerView.setAdapter(adapter);



                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }


}

