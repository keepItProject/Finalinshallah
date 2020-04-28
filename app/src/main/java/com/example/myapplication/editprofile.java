package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Data.User;
import com.example.myapplication.Empty.mywalletActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class editprofile extends AppCompatActivity {
    EditText name,email;
    Toolbar toolbar;

    DatabaseReference databaseReference;
    Task<Void> firebaseUser;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        toolbar = (Toolbar) findViewById(R.id.toolbar990);
        setSupportActionBar(toolbar);

                email=findViewById(R.id.profileemail1);
                name=findViewById(R.id.profilename1);
                name.setText(getIntent().getStringExtra("data"));
                email.setText(getIntent().getStringExtra("data1"));
            }
            public boolean onCreateOptionsMenu(Menu menue) {
                //return super.onCreateOptionsMenu(menue);
                getMenuInflater().inflate(R.menu.editcat, menue);

                return true;
            }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.meueline)
        {

            final AlertDialog.Builder builder = new AlertDialog.Builder(editprofile.this);
            builder.setCancelable(true);
            View view = LayoutInflater.from(editprofile.this).inflate(R.layout.updateprofile, null, false);
            CardView yes_card = view.findViewById(R.id.yes_card);
            builder.setView(view);
            final AlertDialog alertDialog = builder.show();
            CardView no_card = view.findViewById(R.id.no_card);
            no_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();

                }
            });


            yes_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference= FirebaseDatabase.getInstance().getReference();
                    // String uid=databaseReference.getKey();
                    String e=email.getText().toString().trim();
                    FirebaseDatabase.getInstance().getReference().child("User").orderByChild("email").equalTo(e).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot datashot:dataSnapshot.getChildren() ) {
                                String key = datashot.getKey();


                                    //email.setError("الايميل موجود مسبقا");

                                    String e = email.getText().toString().trim();
                                    String name11 = name.getText().toString().trim();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("name", name11);
                                updateRecord(e);

                                    FirebaseDatabase.getInstance().getReference().child("User").child(key).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseUser= FirebaseAuth.getInstance().getCurrentUser().updateEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                });
                                                Intent intent1 = new Intent(editprofile.this, activity_homepage.class);

                                                startActivity(intent1);
                                                //  startActivity(new Intent(getApplicationContext(), activity_homepage.class));

                                                Toast.makeText(editprofile.this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                                                finish();

                                            }
                                        }
                                    });

                                /////////////
                            }
                        }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
                /////
            });
                    alertDialog.dismiss();

                }

            });

        }
        return super.onOptionsItemSelected(item);

    }
    private void updateRecord(String Email) {
        // User data change listener
        String userId = databaseReference.push().getKey();
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setEmail(Email);
                databaseReference.child(userId).setValue(user);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.e( "Failed to read user");
            }
        });
    }

}
