package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.User;
import com.example.myapplication.Data.Userinvoice;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    String userId;
    TextView email,profilenum,profilename,verifyMsg;
    Button pass;
    FirebaseAuth fAuth;
    FirebaseUser user;
    private Context Context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        //tool bar ******
        email=findViewById(R.id.profileemail);
        pass=findViewById(R.id.profilepass);
        profilename=findViewById(R.id.profilename);
        fAuth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar999);
        setSupportActionBar(toolbar);
        userId = fAuth.getCurrentUser().getUid();
         user = fAuth.getCurrentUser();


        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.uppass, null, false);
                CardView yes_card = view.findViewById(R.id.yes_card);
                final EditText resetPassword = view.findViewById(R.id.edit_text);


                passwordResetDialog.setView(resetPassword);
                passwordResetDialog.setView(view);
                final AlertDialog alertDialog = passwordResetDialog.show();

                CardView no_card = view.findViewById(R.id.no_card);
                no_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });




                yes_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(profilActivity.this, "تم تحديث كلمة المرور بنجاح", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(profilActivity.this, "فشل تحديث كلمة المرور", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
//
                no_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });



            }
        });











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