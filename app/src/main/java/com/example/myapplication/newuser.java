package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Data.User;
import com.example.myapplication.Empty.CalenderActivity;
import com.example.myapplication.Empty.activity_cloth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class newuser extends AppCompatActivity {
    DatabaseReference ref;
    Button bt;
    Toolbar toolbar;
    FirebaseAuth auth;
    DatabaseReference ref2,ref3,ref4,ref5,ref6,ref7;

    EditText Name,Email,Pass,PassCon;
    User user;
    String N,E,p ,Passc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        //For tool bar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //--------------------------------------------------

        Name=(EditText)findViewById(R.id.editText);
        Email=(EditText)findViewById(R.id.editText4);
     //  phone =(EditText)findViewById(R.id.editText5);
        Pass=(EditText)findViewById(R.id.editText2);
        PassCon=(EditText)findViewById(R.id.editText3);
        bt=(Button)findViewById(R.id.button2);
        user=new User();
        auth = FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference().child("User");





        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                N = Name.getText().toString().trim();
                E = Email.getText().toString().trim();
                //ph = phone.getText().toString().trim();
                p = Pass.getText().toString().trim();
                Passc=PassCon.getText().toString().trim();
if(N.isEmpty() || E.isEmpty() || p.isEmpty() || Passc.isEmpty() || !p.equals(Passc) || N.isEmpty() || N.length() >32 || E.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(E).matches() || p.isEmpty()||p.length()<8){
                if(!p.equals(Passc)) {
                    PassCon.setError("كلمة المرور غير متطابقة");

                }
                else{

                }
                if (N.isEmpty() || N.length() >32)
                { Name.setError("يجب إدخال الإسم");
                }
                else{}

                if(E.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(E).matches())
                { Email.setError("أدخل البريد الالكتروني بالشكل الصحيح");
                }
                else{}

                if(p.isEmpty()||p.length()<8 ) { Pass.setError("أدخل الرقم السري بالشكل صحيح");

                }
                else {

                }
              }
else{
                auth.createUserWithEmailAndPassword(E, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), activity_homepage.class));

                            Toast.makeText(newuser.this, "تم انشاء حسابك بنجاح", Toast.LENGTH_SHORT).show();
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
user.setUID(uid);
                            user.setName(N);
                            user.setEmail(E);
                            user.setPass(p);
                            ref.push().setValue(user);

                        } else {
                            Toast.makeText(newuser.this, "ادخل البيانات بالشكل الصحيح" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }}
        });
/////
    }





}
