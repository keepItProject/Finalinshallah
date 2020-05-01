package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class activity_log extends AppCompatActivity {
    EditText emailId, password,Email ;
    FirebaseAuth mFirebaseAuth;
    Button btnsignIn;
    TextView tvSignUp ,for_pass;
    Toolbar toolbar;
    Button bt;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    ///////////////////////////////////////////////////////////
    protected void onStart(){super.onStart();
        //mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        FirebaseUser f = mFirebaseAuth.getCurrentUser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText6);
        password = findViewById(R.id.editText7);
        btnsignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);////////////24
        Email = findViewById(R.id.email);
        for_pass=(TextView)findViewById(R.id.for_pass);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(activity_log.this, "You Are Logged In", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(activity_log.this, activity_login.class);
                    startActivity(i);
                } else {
                    Toast.makeText(activity_log.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnsignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
if(TextUtils.isEmpty(email) ||TextUtils.isEmpty(pwd) ) {
    if (TextUtils.isEmpty(email)) {
        emailId.setError("الرجاء ادخال البريد الإلكتروني");
        emailId.requestFocus();
    } else {

    }
    if (TextUtils.isEmpty(pwd)) {
        password.setError("الرجاء ادخال كلمة المرور");
        password.requestFocus();
    } else {
    }
} else{
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(activity_log.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(activity_log.this, "خطأ في تسجيل الدخول,حاول مرة أخرى..", Toast.LENGTH_SHORT).show();
                                // Intent intSignUp= new Intent (activity_log.this, MainActivity.class);///////كان كومنت
                            } else {
                                Intent intToHome = new Intent(activity_log.this, activity_homepage.class);
                                startActivity(intToHome);
                            }
                        }

                    });
                 //else {
                   // Toast.makeText(activity_log.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                //}
            }}
        });
        //For tool bar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //-----------------------------------------------------------------
        ActionBar ab = getSupportActionBar();
        bt= (Button) findViewById(R.id.button);
/*
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity_log.this, activity_homepage.class);
                startActivity(intent);
        });*/





        for_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        AlertDialog.Builder update=new AlertDialog.Builder(v.getContext());
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.forpass, null, false);
        CardView yes_card = view.findViewById(R.id.yes_card);
final EditText upemail = view.findViewById(R.id.email);

        update.setView(upemail);
        update.setView(view);
final AlertDialog alertDialog = update.show();
        CardView no_card = view.findViewById(R.id.no_card);

              yes_card.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
String emaill= upemail.getText().toString().trim();
                        mFirebaseAuth.sendPasswordResetEmail(emaill).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity_log.this, "تم ارسال رابط اعادة التعيين الى بريدك الالكتروني", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity_log.this, "فشل اعادة التعيين", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

        no_card.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    alertDialog.dismiss();
                    }
                });

            }
        });
    }
}