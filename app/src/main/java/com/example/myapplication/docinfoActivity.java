package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Data.UserCategory;
import com.example.myapplication.Data.Userinvoice;
import com.example.myapplication.Data.invoice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class docinfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    static String f;
    private Context mContext;
    private com.example.myapplication.Data.Userinvoice invoice;

    Button button6;
    private TextView doc_name,web100,web200;
    private TextView doc_number;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    private TextView doc_pdate;
    private TextView doc_cat;
    private TextView doc_service_provider;
    private TextView doc_service_provider_phone;
    private TextView doc_service_provider_website;


    private TextView doc_name1;
    private TextView doc_number1;
    private TextView doc_pdate1;
    private TextView doc_cat1;
    private TextView doc_service_provider1;
    private TextView doc_service_provider_phone1;
    private TextView doc_service_provider_website1;
    private TextView textView25;
    private TextView textView251;
    private TextView edate;
    private TextView edate1;
    private TextView porid;
    private TextView porid1;
    private ImageView doc_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info);
        doc_name=findViewById(R.id.doc_name);
        web100=findViewById(R.id.web100);
                web200=findViewById(R.id.web200);
        doc_image=findViewById(R.id.doc_image);
        doc_number=findViewById(R.id.doc_number);
        doc_pdate=findViewById(R.id.doc_pdate);
        doc_cat=findViewById(R.id.doc_cat);
        doc_service_provider=findViewById(R.id.doc_service_provider);
        doc_service_provider_phone=findViewById(R.id.doc_service_provider_phone);
        doc_service_provider_website=findViewById(R.id.doc_service_provider_website);
        edate=findViewById(R.id.edate);
        edate1=findViewById(R.id.edate1);
        porid=findViewById(R.id.porid);
        porid1=findViewById(R.id.porid1);


        doc_name1=findViewById(R.id.doc_name1);
        doc_number1=findViewById(R.id.doc_number1);
        doc_pdate1=findViewById(R.id.doc_pdate1);
        doc_cat1=findViewById(R.id.doc_cat1);
        doc_service_provider1=findViewById(R.id.doc_service_provider1);
        doc_service_provider_phone1=findViewById(R.id.doc_service_provider_phone1);
        doc_service_provider_website1=findViewById(R.id.doc_service_provider_website1);
        textView25=findViewById(R.id.textView25);
        textView251=findViewById(R.id.textView251);

       /* doc_name.setText(getIntent().getStringExtra("data"));
        doc_number.setText(getIntent().getStringExtra("data1"));

        doc_pdate.setText(getIntent().getStringExtra("data2"));
        // h2.setText(getIntent().getStringExtra("data3"));
        doc_service_provider.setText(getIntent().getStringExtra("data4"));
        doc_service_provider_phone.setText(getIntent().getStringExtra("data5"));
        doc_service_provider_website.setText(getIntent().getStringExtra("data6"));
        edate.setText(getIntent().getStringExtra("data7"));
        porid.setText(getIntent().getStringExtra("data8"));
       // textView25.setText(getIntent().getStringExtra("data9"));
        doc_cat.setText(getIntent().getStringExtra("data10"));
        //Glide.with(this).load(invoice.getImage()).centerCrop().into(doc_image);*/


        final Intent intent=getIntent();
        if(intent.hasExtra("invoice")){
            invoice=(com.example.myapplication.Data.Userinvoice)intent.getSerializableExtra("invoice");
            if(invoice.getCategoryId()==null){
                porid.setVisibility( View.GONE);
                porid1.setVisibility( View.GONE);

            }else {
                porid.setText( invoice.getCategoryId());
            }
            if(invoice.getName()==null){
                doc_name.setVisibility( View.GONE);
                doc_name1.setVisibility( View.GONE);

            }else {
                doc_name.setText( invoice.getName());
            }

            if(invoice.getEDate()==null){
                edate.setVisibility( View.GONE);
                edate1.setVisibility( View.GONE);

            }else {
                edate.setText( invoice.getEDate());
            }

            if(invoice.getNotify()==null){
                textView25.setVisibility( View.GONE);
                textView251.setVisibility( View.GONE);

            }else {
                textView25.setText( invoice.getNotify());
            }

            if(invoice.getNumber()==null){
                doc_number.setVisibility( View.GONE);
                doc_number1.setVisibility( View.GONE);

            }else {
                doc_number.setText( invoice.getNumber());
            }

            if(invoice.getPDate()==null){
                doc_pdate.setVisibility( View.GONE);
                doc_pdate1.setVisibility( View.GONE);

            }else {
                doc_pdate.setText( invoice.getPDate());
            }


            if(invoice.getServiceProvider()==null){
                doc_service_provider.setVisibility( View.GONE);
                doc_service_provider1.setVisibility( View.GONE);

            }else {
                doc_service_provider.setText( invoice.getServiceProvider());
            }

            if(invoice.getServiceProviderPhone()==null){
                doc_service_provider_phone.setVisibility( View.GONE);
                doc_service_provider_phone1.setVisibility( View.GONE);

            }else {
                doc_service_provider_phone.setText( invoice.getServiceProviderPhone());
            }

            if(invoice.getServiceProviderWebsite()==null){
                doc_service_provider_website.setVisibility( View.GONE);
                doc_service_provider_website1.setVisibility( View.GONE);

            }else {
                doc_service_provider_website.setText( invoice.getServiceProviderWebsite());
            }

            if(invoice.getPeriod()==null){
                porid.setVisibility( View.GONE);
                porid1.setVisibility( View.GONE);

            }else {
                porid.setText( invoice.getPeriod());
            }

            doc_service_provider_website.setMovementMethod(LinkMovementMethod.getInstance());

            RequestOptions requestOptions=new RequestOptions();
            requestOptions.placeholder(R.drawable.coverfile);
            Glide.with(this).load(invoice.getImage()).centerCrop().into(doc_image);
            try{
                FirebaseDatabase.getInstance().getReference().child("category").child(invoice.getCategoryId())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    UserCategory userCategory=dataSnapshot.getValue(com.example.myapplication.Data.UserCategory.class);
                                    doc_cat.setText(userCategory.getName());




                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

            }catch (Exception e){
                doc_cat.setVisibility(View.GONE);
                doc_cat1.setVisibility(View.GONE);
            }

            if(invoice.getImage()!=null){
                doc_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1=new Intent(docinfoActivity.this,ViewImageActivity.class);
                        intent1.putExtra("url",invoice.getImage());
                        startActivity(intent1);
                    }
                });
            }

        }

        if (intent.hasExtra("user_invoice_key")) {
            FirebaseDatabase.getInstance().getReference().child("document").child(getIntent().getStringExtra("user_invoice_key"))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            invoice = dataSnapshot.getValue(Userinvoice.class);
                            if (invoice.getName() == null) {
                                doc_name.setVisibility(View.GONE);
                                doc_name1.setVisibility(View.GONE);

                            } else {
                                doc_name.setText(invoice.getName());
                            }

                            if (invoice.getEDate() == null) {
                                edate.setVisibility(View.GONE);
                                edate1.setVisibility(View.GONE);

                            } else {
                                edate.setText(invoice.getEDate());
                            }

                            if (invoice.getNotify() == null) {
                                textView25.setVisibility(View.GONE);
                                textView251.setVisibility(View.GONE);

                            } else {
                                textView25.setText(invoice.getNotify());
                            }

                            if (invoice.getNumber() == null) {
                                doc_number.setVisibility(View.GONE);
                                doc_number1.setVisibility(View.GONE);

                            } else {
                                doc_number.setText(invoice.getNumber());
                            }

                            if (invoice.getPDate() == null) {
                                doc_pdate.setVisibility(View.GONE);
                                doc_pdate1.setVisibility(View.GONE);

                            } else {
                                doc_pdate.setText(invoice.getPDate());
                            }


                            if (invoice.getServiceProvider() == null) {
                                doc_service_provider.setVisibility(View.GONE);
                                doc_service_provider1.setVisibility(View.GONE);

                            } else {
                                doc_service_provider.setText(invoice.getServiceProvider());
                            }

                            if (invoice.getServiceProviderPhone() == null) {
                                doc_service_provider_phone.setVisibility(View.GONE);
                                doc_service_provider_phone1.setVisibility(View.GONE);

                            } else {
                                doc_service_provider_phone.setText(invoice.getServiceProviderPhone());
                            }

                            if (invoice.getServiceProviderWebsite() == null) {
                                doc_service_provider_website.setVisibility(View.GONE);
                                doc_service_provider_website1.setVisibility(View.GONE);

                            } else {
                                doc_service_provider_website.setText(invoice.getServiceProviderWebsite());
                            }

                            if (invoice.getPeriod() == null) {
                                porid.setVisibility(View.GONE);
                                porid1.setVisibility(View.GONE);

                            } else {
                                porid.setText(invoice.getPeriod());
                            }
                            doc_service_provider_website.setMovementMethod(LinkMovementMethod.getInstance());

                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.placeholder(R.drawable.coverfile);
                            Glide.with(docinfoActivity.this).load(invoice.getImage()).centerCrop().into(doc_image);
                            try {
                                FirebaseDatabase.getInstance().getReference().child("category").child(invoice.getCategoryId())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    UserCategory userCategory = dataSnapshot.getValue(com.example.myapplication.Data.UserCategory.class);
                                                    doc_cat.setText(userCategory.getName());


                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                            } catch (Exception e) {
                                doc_cat.setVisibility(View.GONE);
                                doc_cat1.setVisibility(View.GONE);
                            }

                            if (invoice.getImage() != null) {
                                doc_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent1 = new Intent(docinfoActivity.this, ViewImageActivity.class);
                                        intent1.putExtra("url", invoice.getImage());
                                        startActivity(intent1);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("TAG", "onCancelled() called with: databaseError = [" + databaseError + "]");
                            Toast.makeText(docinfoActivity.this, "error getting details", Toast.LENGTH_SHORT).show();
                        }
                    });


        }

        if (intent.hasExtra("name")) {
            String name = intent.getStringExtra("name");
            String date = intent.getStringExtra("date");

            doc_name.setText(" الاسم "+ name);

            doc_pdate.setText(" تاريخ الشراء "+date);
        }


        //tool bar ******
        toolbar = (Toolbar) findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);


        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(docinfoActivity.this);
                builder.setCancelable(true);
                View view = LayoutInflater.from(docinfoActivity.this).inflate(R.layout.deletedocument_dailod, null, false);
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
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        // String uid=databaseReference.getKey();
                        databaseReference.child("document").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot datashot : dataSnapshot.getChildren()) {
                                    String expired_data = datashot.child("number").getValue(String.class);
                                    String num = doc_number.getText().toString().trim();

                                    if (expired_data.equals(num)) {
                                        String key = datashot.getKey();


                                        FirebaseDatabase.getInstance().getReference().child("document").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    startActivity(new Intent(getApplicationContext(), activity_homepage.class));

                                                    Toast.makeText(docinfoActivity.this, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }
                                        });
                                    } else {
                                    }

                                    //  edit();

                                    // databaseReference.child("User").child(uid).child("namme").setValue(uid);


                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        ////////////////////////////////


                    }
                });

            }
        });






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
            String one=doc_name.getText().toString().trim();
            String one1=doc_number.getText().toString().trim();
            String one2=doc_pdate.getText().toString().trim();
            String web9=web200.getText().toString().trim();
           // String one3=doc_cat.getText().toString().trim();
             f=doc_cat1.getText().toString().trim();
            String one4=doc_service_provider.getText().toString().trim();
            String one5=doc_service_provider_phone.getText().toString().trim();
            String one6=doc_service_provider_website.getText().toString().trim();
            String e=edate.getText().toString().trim();
            Intent intent=new Intent(docinfoActivity.this,editInfo.class);
            intent.putExtra("data",one);
            intent.putExtra("data1",one1);
            intent.putExtra("data2",one2);
           // intent.putExtra("data3",one3);
            intent.putExtra("data4",one4);
            intent.putExtra("data5",one5);
            intent.putExtra("data6",one6);
            intent.putExtra("data7",e);
            intent.putExtra("data8",web9);

            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

}
//*************}
