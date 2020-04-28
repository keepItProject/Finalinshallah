
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapplication.Adaptors.CategoryAdapter;
import com.example.myapplication.Data.UserCategory;
import com.example.myapplication.Data.Userinvoice;
import com.example.myapplication.Empty.mywalletActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class editInfo extends AppCompatActivity {
    Toolbar toolbar;
    UserCategory userCategory;
    ElegantNumberButton elegantNumberButton;
    public com.example.myapplication.Data.Userinvoice Userinvoice;

    private com.example.myapplication.Data.Userinvoice invoice;

    EditText h,y9,h3,h4,h5;
    TextView h2,h1;
     static String  key,nameIn,Pdate1,name,phone1,email1,num,Edate2,period,spinner12,spinner5;

    TextView textView;
    Spinner dropdown;
com.example.myapplication.Data.UserCategory userinvoice=new UserCategory();
    private static final String TAG = "editInfo";
    ArrayList<com.example.myapplication.UserCategory> categories = new ArrayList<com.example.myapplication.UserCategory>();



    public static  String expired_data;
   // public static  String name;
    Button o;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    Spinner spinner,spinner1;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        // scrollView=  (ScrollView) findViewById(R.id.s1);
        toolbar = (Toolbar) findViewById(R.id.toolbar8);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();
            }
        });
        Toolbar title = toolbar.findViewById(R.id.title);
        scrollView =findViewById(R.id.scrol);
        scrollView.setEnabled(false);
        elegantNumberButton=(ElegantNumberButton) findViewById(R.id.elegantNumberButton);
        spinner=findViewById(R.id.spinner1);
       // spinner1=findViewById(R.id.spinner1);
        //  scrollView.setPressed(true);
        //scrollView.setClickable(true);


        h=findViewById(R.id.textView42);
        textView=findViewById(R.id.textView442);
        //Pdate
        h1=findViewById(R.id.textView842);
        //Edate
        h2=findViewById(R.id.textView0042);
        //name
        h3=findViewById(R.id.textView00w42);
        //phone
        h4=findViewById(R.id.textView0a042);
        ///period

        //email
        h5=findViewById(R.id.textView142);
        /////////////////////////////////////////////////////////
        h.setText(getIntent().getStringExtra("data"));
        textView.setText(getIntent().getStringExtra("data1"));

        h1.setText(getIntent().getStringExtra("data2"));
       // h2.setText(getIntent().getStringExtra("data3"));
        h3.setText(getIntent().getStringExtra("data4"));
        h4.setText(getIntent().getStringExtra("data5"));
        h5.setText(getIntent().getStringExtra("data6"));
        h2.setText(getIntent().getStringExtra("data7"));
        ///////////////////////////////////////////////////////
        dropdown=findViewById(R.id.spinner1);
       //categories.add(0,null);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("category");
        loadAllCategories(myRef);

        h1.setOnClickListener(v ->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(editInfo.this, new editInfo.DateListener(h1), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        h2.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(editInfo.this, new editInfo.DateListener(h2), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
/////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////

        // y9=findViewById(R.id.textView18);
        final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();



    }
    public boolean onCreateOptionsMenu(Menu menue) {
        //return super.onCreateOptionsMenu(menue);
        getMenuInflater().inflate(R.menu.editcat, menue);

        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.meueline){
            final AlertDialog.Builder builder = new AlertDialog.Builder(editInfo.this);
            builder.setCancelable(true);
            View view = LayoutInflater.from(editInfo.this).inflate(R.layout.update_dailog, null, false);
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
            firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
            // String uid=databaseReference.getKey();
            databaseReference.child("document").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot datashot:dataSnapshot.getChildren() ) {
                       String expired_data=datashot.child("number").getValue(String.class);
                        String cat=datashot.child("categoryId").getValue(String.class);
                        UserCategory userCategory = datashot.getValue(com.example.myapplication.Data.UserCategory.class);

                        num=textView.getText().toString().trim();
                        if (expired_data.equals(num))
                        {

                            key=datashot.getKey();
                             nameIn=h.getText().toString().trim();
                             Pdate1=h1.getText().toString().trim();
                             Edate2=h2.getText().toString().trim();
                             name=h3.getText().toString().trim();
                             phone1=h4.getText().toString().trim();
                             email1=h5.getText().toString().trim();
                           period=elegantNumberButton.getNumber();
                            //spinner12= (String) spinner1.getSelectedItem();
                            docinfoActivity h=new docinfoActivity();
                            //UserCategory userCategory=new UserCategory();
                           // boolean j=((com.example.myapplication.UserCategory) dropdown.getSelectedItem()).getId().equals("");
                          int h123=dropdown.getSelectedItemPosition();
                           if(h123==0)
                              spinner5=cat;
                           else
                       spinner5= ((com.example.myapplication.UserCategory) dropdown.getSelectedItem()).getId();



                            // String periodChoiceTxt = (String) spinner.getSelectedItem();
                            //  String periodChoiceTxt1 = (String) spinner1.getSelectedItem();



                            HashMap<String,Object> map=new HashMap<>();
                            map.put("name",nameIn);
                            map.put("pdate",Pdate1);
                           map.put("edate",Edate2);
                            map.put("serviceProvider",name);
                            map.put("serviceProviderPhone",phone1);
                            map.put("serviceProviderWebsite",email1);
                            map.put("period",period);
                           // map.put("notify",spinner12);
                            map.put("categoryId",spinner5);



                            // map.put("serviceProviderWebsite",periodChoiceTxt);
                            //  map.put("serviceProviderWebsite",periodChoiceTxt1);

                            FirebaseDatabase.getInstance().getReference().child("document").child(key).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent1=new Intent(editInfo.this,mywalletActivity.class);
                                       /*  num=textView.getText().toString().trim();
                                         nameIn=h.getText().toString().trim();
                                         Pdate1=h1.getText().toString().trim();
                                         Edate2=h2.getText().toString().trim();
                                         name=h3.getText().toString().trim();
                                         phone1=h4.getText().toString().trim();
                                         email1=h5.getText().toString().trim();

                                        intent1.putExtra("data",nameIn);
                                        intent1.putExtra("data1",num);
                                        intent1.putExtra("data2",Pdate1);
                                        // intent.putExtra("data3",one3);
                                        intent1.putExtra("data4",name);
                                        intent1.putExtra("data5",phone1);
                                        intent1.putExtra("data6",email1);
                                        intent1.putExtra("data7",Edate2);
                                        intent1.putExtra("data8",period);
                                       // intent1.putExtra("data9",spinner12);*/

                                       // intent1.putExtra("data10", ((com.example.myapplication.UserCategory) spinner.getSelectedItem()).getName());

                                        startActivity(intent1);
                                      //  startActivity(new Intent(getApplicationContext(), activity_homepage.class));

                                        Toast.makeText(editInfo.this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
                        }else {
                        }

                        //  edit();

                        // databaseReference.child("User").child(uid).child("namme").setValue(uid);




                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
                }
            });

        }
        return super.onOptionsItemSelected(item);

    }

   /* private void loadAllCategories(DatabaseReference myRef){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
categories.add(0,"اختر التصنيف");
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                       // UserCategory userCategory=new UserCategory();
                       // categories.add(0,userCategory.getType1());


                        com.example.myapplication.UserCategory category = ds.getValue(com.example.myapplication.UserCategory.class);
                    if (category.getType().equals("static")|| category.getUser_id().equals(uid))
                        categories.add(category.getName());

                }
                //get the spinner from the xml.
                dropdown = findViewById(R.id.spinner1);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(editInfo.this, android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);
                dropdown.setSelection(0);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemvalue = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }*/
   private void loadAllCategories(DatabaseReference myRef) {
       String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               com.example.myapplication.UserCategory  i= new com.example.myapplication.UserCategory();
               categories.add(0,i);

               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   com.example.myapplication.UserCategory category = ds.getValue(com.example.myapplication.UserCategory.class);
                   if (category.getType().equals("static")|| category.getUser_id().equals(uid))
                       categories.add(category);

               }
               //get the spinner from the xml.
               dropdown = findViewById(R.id.spinner1);
               CategoryAdapter adapter = new CategoryAdapter(editInfo.this,categories);
               dropdown.setAdapter(adapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               // Failed to read value
               Log.w(TAG, "Failed to read value.", error.toException());
           }
       });
   }
    public void edit() {

    }
    Calendar myCalendar = Calendar.getInstance();

    public class DateListener implements DatePickerDialog.OnDateSetListener {
        private TextView textView;

        public DateListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            textView.setText(sdf.format(myCalendar.getTime()));
        }
    }

}
