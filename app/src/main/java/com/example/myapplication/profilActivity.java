package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Data.User;
import com.example.myapplication.Data.Userinvoice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sun.bob.mcalendarview.vo.MarkedDates;

public class profilActivity extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseUser mauth;
    TextView email,profilenum,profilename;
    Button pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        //tool bar ******
        email=findViewById(R.id.profileemail);
        pass=findViewById(R.id.profilepass);
        profilename=findViewById(R.id.profilename);

        toolbar = (Toolbar) findViewById(R.id.toolbar999);
        setSupportActionBar(toolbar);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

               for(DataSnapshot datashot:dataSnapshot.getChildren() ){
                   User invoice=datashot.getValue(User.class);
                   if(invoice.getUID().equals(uid)) {
                       String k=datashot.getKey();

                       String email1 = datashot.child("email").getValue(String.class);
                       email.setText(email1);
                       String name = datashot.child("name").getValue(String.class);
                       profilename.setText(name);


                   }
           }}
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        //*************
    }
    public boolean onCreateOptionsMenu(Menu menue) {
        //return super.onCreateOptionsMenu(menue);
        getMenuInflater().inflate(R.menu.editcat1, menue);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        int id=item.getItemId();
        if(id==R.id.meueline1){
            String one=profilename.getText().toString().trim();
            String one1=email.getText().toString().trim();
            // String one3=doc_cat.getText().toString().trim();

            Intent intent=new Intent(profilActivity.this,editprofile.class);
            intent.putExtra("data",one);
            intent.putExtra("data1",one1);



            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}