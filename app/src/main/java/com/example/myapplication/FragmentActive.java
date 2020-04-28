package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adaptors.calendarAdapter;
import com.example.myapplication.Adaptors.invoiceAdapter;
import com.example.myapplication.Data.Userinvoice;
import com.example.myapplication.Data.invoice;
import com.example.myapplication.Empty.CalenderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentActive extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<Userinvoice> activeInvoices;
    invoiceAdapter adapter;
    //Database
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    //information
    public static String name;
   static Date invoiceDate;

    public static String purchase_data;
    public static String expired_data;



    public FragmentActive() {
    }

    public  static FragmentActive newInstance(String id){
        FragmentActive fragment=new FragmentActive();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }
    invoiceAdapter invoiceAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.active_fragment,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.active_recyclerview);
         invoiceAdapter = new invoiceAdapter(getContext(),activeInvoices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(invoiceAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeInvoices = new ArrayList<>();
        // expiredInvoices.add(new invoice("bshayer","9/7/1998","9/7/1999"));
        String category_id=getArguments().getString("id");
        Log.d("MUTEE", "onCreate: "+category_id);

        //databaseReference=
        Query query;
        if(category_id.equals("-1")){
             query=FirebaseDatabase.getInstance().
                    getReference().child("document");
            firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        }else {
             query=FirebaseDatabase.getInstance().
                    getReference().child("document").orderByChild("categoryId").equalTo(category_id);
            firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        }


        // expiredInvoices.clear();
        // recyclerView.removeAllViews();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MUTEE", "onDataChange: "+dataSnapshot.getChildrenCount());
                if(dataSnapshot.getChildrenCount()>0){
                    Calendar calendar=Calendar.getInstance();
                    Date time = calendar.getTime();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                    String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    activeInvoices.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Userinvoice invoice=child.getValue(Userinvoice.class);
                        try {
                            if(invoice.getUser_id().equals(uid)){
                            if(!invoice.getEDate().equals("")){
                             invoiceDate=simpleDateFormat.parse(invoice.getEDate());

                                if(invoiceDate.after(time))
                                activeInvoices.add(invoice);}
                            else
                                activeInvoices.add(invoice);

                        }

                        } catch (ParseException e) {
                           // e.printStackTrace();
                        }


                    }
                    invoiceAdapter.notifyDataSetChanged();


                }else {
                    if(isAdded())
                    Toast.makeText(getActivity(), "لا يوجد ملفات", Toast.LENGTH_SHORT).show();
                }
                /*for (DataSnapshot datashot:dataSnapshot.getChildren()){
                    name= datashot.child("Name").getValue(String.class);
                    purchase_data=datashot.child("Expired_data").getValue(String.class);  //wrong data********
                    expired_data=datashot.child("Expired_data").getValue(String.class);

                    Calendar calendar = Calendar.getInstance();
                    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

                    if(expired_data.compareTo(currentDate)<0) { //less than currentDate

                    }

                    else if(expired_data.compareTo(currentDate)>0) { //greater than currentDate
                        expiredInvoices.add(new invoice (name,purchase_data,expired_data));
                    }

                    else if(expired_data.compareTo(currentDate)==0) { //both dates are equal

                    }

                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    } //onCreate


}
