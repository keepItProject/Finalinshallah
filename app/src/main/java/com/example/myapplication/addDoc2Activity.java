package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapplication.Adaptors.CategoryAdapter;
import com.example.myapplication.Data.Category;
import com.example.myapplication.Data.Userinvoice;
import com.example.myapplication.alarm.AlarmData;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addDoc2Activity extends AppCompatActivity {
    DatabaseReference ref;
    FirebaseAuth auth;
    DatabaseReference ref2, ref3, ref4, ref5, ref6, ref7;
    Toolbar toolbar;
    //////
    private static final String TAG = "addDoc2Activity";
    private ImageView receipt;
    public Userinvoice Userinvoice;
    ElegantNumberButton elegantNumberButton;
    Date date1;
    private ArrayList<Float> prices;
    Spinner spinner;
    private TextView name, date, phone, email, invoiceNumber;
    private ArrayList<UserCategory> categories = new ArrayList<>();
    private CheckBox mCheckBox;
    Button bt , bt1;
    private Spinner mSpinner;
    String Name1, number1, Pdate1, Edate1, Etime1, period1, serviceprovider1, numserviceprovider1, websiteserviceprovider1;
    EditText Name, number, period, serviceprovider, numserviceprovider, websiteserviceprovider;
    TextView  Pdate, Edate, Etime;
    TextView end_time;
    private Uri uri;
    private int mHourDay, mMinutes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doc2);
////////////////////
         toolbar=findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.bak);
        uri =Uri.parse(getIntent().getStringExtra("imageUri"));
        bt1=(Button)findViewById(R.id.button8);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addDoc2Activity.this, activity_homepage.class);
                startActivity(intent);
            }
        });
        /////
        elegantNumberButton=findViewById(R.id.textView0);
        elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num=elegantNumberButton.getNumber();
                Log.e("num",num);

            }
        });
        Name = (EditText) findViewById(R.id.name55);
        number = (EditText) findViewById(R.id.number);
        //period = (EditText) findViewById(R.id.textView0);
        Pdate = (TextView) findViewById(R.id.date);
        Edate = (TextView) findViewById(R.id.end_date);
        Etime = (TextView) findViewById(R.id.end_time);
        end_time= (TextView) findViewById(R.id.end_time);
        serviceprovider = (EditText) findViewById(R.id.name);
        numserviceprovider = (EditText) findViewById(R.id.phone);
        websiteserviceprovider = (EditText) findViewById(R.id.email);
        receipt= findViewById(R.id.receipt);
        spinner = findViewById(R.id.spinner4);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);

        ////
        List<String> list = new ArrayList<>();
       // list.add("خ");
        list.add("قبل اسبوع");
        list.add("قبل شهر");

        Pdate.setOnClickListener(v ->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(addDoc2Activity.this, new DateListener(Pdate), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        Edate.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(addDoc2Activity.this, new DateListener(Edate), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        Etime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(addDoc2Activity.this, new TimeListener(Etime),
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            //timePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            timePickerDialog.show();
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemvalue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setEnabled(false);
        end_time.setEnabled(false);
        Pdate.setError(null);
        end_time.setBackgroundResource(R.drawable.round100);
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()){

                    spinner.setEnabled(true);
                    end_time.setEnabled(true);
                    end_time.setBackgroundResource(R.drawable.round4);

                }

                else{

                    spinner.setEnabled(false);
                    end_time.setEnabled(false);
                    Pdate.setError(null);
                    end_time.setBackgroundResource(R.drawable.round100);
                }
            }
        });

        /////
        bt = (Button) findViewById(R.id.button7);
        auth = FirebaseAuth.getInstance();
        ref2 = FirebaseDatabase.getInstance().getReference().child("document");
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Name1 = Name.getText().toString().trim();
                number1 = number.getText().toString().trim();
                Pdate1 = Pdate.getText().toString().trim();
                Edate1 = Edate.getText().toString().trim();
                Etime1 = Etime.getText().toString().trim();

                String priod = elegantNumberButton.getNumber();
                serviceprovider1 = serviceprovider.getText().toString().trim();
                numserviceprovider1 = numserviceprovider.getText().toString().trim();
                websiteserviceprovider1 = websiteserviceprovider.getText().toString().trim();
                // String t=receipt.

                FirebaseDatabase.getInstance().getReference().child("document").orderByChild("number").equalTo(number1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                        if (dataSnapshot2.exists()) {

                            showExisitDialog();


                        }
                        else{}
                        if(Name1.equals(""))
                            Name.setError("جب تعئة اسم الفاتورة");
                        else {}
                        if(number1.equals(""))
                            number.setError("جب تعئة رقم الفاتورة");
                        else {}
                        if (mCheckBox.isChecked()){
                            if(Pdate1.equals("")){
                                Pdate.setError("جب تعئة تاريخ الشراء");
                            }
                        }
                        else{
                        }

                        if(!Name1.equals("") && !number1.equals(""))
                        {
                            if(mCheckBox.isChecked()&& Pdate1.equals("")) {
                            }
                            else{

                            Calendar calendar = Calendar.getInstance();
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference media = storage.getReference("photos");
                            final StorageReference child = media.child("img" + calendar.getTimeInMillis());
                            UploadTask uploadTask = child.putFile(uri);


                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return child.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    if (task.isSuccessful()) {

                                        Uri downloadUri = task.getResult();

                                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        DatabaseReference push = FirebaseDatabase.getInstance().getReference().child("document").push();
                                        String key = push.getKey();
                                        Userinvoice = new Userinvoice();
                                        Userinvoice.setId(key);
                                        Userinvoice.setUser_id(uid);
                                        Userinvoice.setType("user");
                                        Userinvoice.setName(Name1);
                                        Userinvoice.setNumber(number1);
                                        Userinvoice.setPDate(Pdate1);
                                        Userinvoice.setEDate(Edate1);
                                        Userinvoice.setPeriod(priod);
                                        Userinvoice.setCategoryId(((UserCategory) dropdown.getSelectedItem()).getId());
                                        Userinvoice.setServiceProvider(serviceprovider1);
                                        Userinvoice.setNotify((String) spinner.getSelectedItem());
                                        Userinvoice.setServiceProviderPhone(numserviceprovider1);
                                        Userinvoice.setServiceProviderWebsite(websiteserviceprovider1);
                                        Userinvoice.setImage(downloadUri.toString());
                                        // Userinvoice.setImage(receipt);
                                        push.setValue(Userinvoice).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    Toast.makeText(addDoc2Activity.this, "تمت إضافة الفاتورة بنجاح", Toast.LENGTH_SHORT).show();
                                                    String myFormat = "yyyy-MM-dd HH:mm"; //In which you need put here
                                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                                    try {
                                                        ////if
                                                        if(Edate1.equals("") && !Pdate1.equals("")){
                                                            if(!priod.equals("")){
                                                                String h2 = Pdate1.substring(0, 4);
                                                                int t12 = Integer.parseInt(h2);
                                                                int t1 = Integer.parseInt(priod);
                                                                int i=t12+t1;
                                                                String j= String.valueOf(i);
                                                     String PuDate=Pdate1.replace(h2,j);


                                                                handleAlarm(key, sdf.parse(String.format(Locale.US,"%s %02d:%02d", PuDate, mHourDay, mMinutes)));
                                                        }}
                                                        else{
                                                        handleAlarm(key, sdf.parse(String.format(Locale.US,"%s %02d:%02d", Edate1, mHourDay, mMinutes)));}
                                                    } catch (Exception ex) {
                                                        Toast.makeText(addDoc2Activity.this, "Error in parse date", Toast.LENGTH_SHORT).show();
                                                    }
                                                    Intent intent = new Intent(addDoc2Activity.this, activity_homepage.class);
                                                    startActivity(intent);
                                                } //////////////////////////
                                                else {
                                                    Toast.makeText(addDoc2Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });


                                    } else {
                                        Toast.makeText(addDoc2Activity.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        // Handle failures
                                        // ...
                                    }
                                }
                            });

                        }}
                        ////
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        //////////////////////////
        // Init Views
        receipt = findViewById(R.id.receipt);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        invoiceNumber = findViewById(R.id.number);
      //  mSpinner = (Spinner) findViewById(R.id.spinner4);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("category");
        loadAllCategories(myRef);


        // hold data from another activity
        Uri myUri = Uri.parse(Objects.requireNonNull(getIntent().getExtras()).getString("imageUri"));
        extractTextFromImage(myUri);

    }

    private void showExisitDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        View view = LayoutInflater.from(this).inflate(R.layout.document_dailog, null, false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.show();

        CardView repeat_card = view.findViewById(R.id.repeat_card);
        repeat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });


    }

    private void handleAlarm(String key, Date date) {
        if (mCheckBox.isChecked()) {
            String periodChoiceTxt = (String) spinner.getSelectedItem().toString().trim();
            long offset = 0;
            if (getString(R.string.before_week).equalsIgnoreCase(periodChoiceTxt)) {
                offset = TimeUnit.DAYS.toMillis(7);
            } else if (getString(R.string.before_month).equalsIgnoreCase(periodChoiceTxt)) {
                offset = TimeUnit.DAYS.toMillis(30);
            }


            date.setTime(date.getTime() - offset);
            setAlarmAt(key, date);

        }
    }

    private void setAlarmAt(String key, Date date) {
        AlarmData alarm = ((Application) getApplication()).getMyAlarmManager().newAlarm(this);
        alarm.time.setTime(date);
        alarm.setKey(this, key);
        alarm.setTime(this, alarm.time.getTimeInMillis());
    }

    private void loadAllCategories(DatabaseReference myRef) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserCategory category = ds.getValue(UserCategory.class);
                    if (category.getType().equals("static")|| category.getUser_id().equals(uid))
                        categories.add(category);

                }
                //get the spinner from the xml.
                dropdown = findViewById(R.id.spinner);
                CategoryAdapter adapter = new CategoryAdapter(addDoc2Activity.this,categories);
                dropdown.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    Spinner dropdown;


   /* // call from XML file
    public void takePhoto(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }
*/

    // @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void extractTextFromImage(Uri resultUri) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultUri != null) {
            // return Image from crop
            //CropImage.ActivityResult result = CropImage.getActivityResult(data);
            // if (resultCode == RESULT_OK) {
            // Uri resultUri = result.getUri();
            FirebaseVisionImage image = null;
            try {
                image = FirebaseVisionImage.fromBitmap(MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(),
                        resultUri
                ));
                receipt.setImageBitmap(MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(),
                        resultUri));

            } catch (IOException e) {
                e.printStackTrace();
            }
            //Firebase Init
            InitFirebase initFirebase = new InitFirebase().invoke();
            FirebaseVisionBarcodeDetector detector = initFirebase.getDetector();
            FirebaseVisionTextRecognizer textRecognizer = initFirebase.getTextRecognizer();
            assert image != null;
            //Barcode Number
            Task<List<FirebaseVisionBarcode>> barCodeNumber = detector.detectInImage(image)
                    .addOnSuccessListener(barcodes -> {
                        Log.d("BarCode Number", barcodes.toString());
                        for (FirebaseVisionBarcode barcode : barcodes) {
                            Rect bounds = barcode.getBoundingBox();
                            Point[] corners = barcode.getCornerPoints();

                            String rawValue = barcode.getRawValue();
                            invoiceNumber.setText(rawValue);
                            Log.w("rawValue", rawValue + "");

                            int valueType = barcode.getValueType();
                            Log.w("valueType", valueType + "");
                        }

                    })
                    .addOnFailureListener(e -> Log.d("BarCode Number", e.getMessage()));
            //OCR text
            textRecognizer.processImage(image)
                    .addOnSuccessListener(result1 -> {
                        boolean getName = false;
                        prices = new ArrayList<Float>();
                        String resultText = result1.getText();
                        // all Text in receipt
                        for (FirebaseVisionText.TextBlock block : result1.getTextBlocks()) {
                            String blockText = block.getText();
                            // check for date
                           // System.out.println(blockText);
                            if (date.getText().length() == 0)
                                date.setText(dateChecker(blockText) ? changeDateFormat(blockText) : "");
                            // check for phone number invoicenum
                            if (phone.getText().length() == 0)
                                phone.setText(mobileNumber(blockText));

                            if (invoiceNumber.getText().length() == 0)
                                invoiceNumber.setText(invoicenum(blockText));
                            // loop in all lines in Text
                            for (FirebaseVisionText.Line line : block.getLines()) {
                                String lineText = line.getText();
                                //Log.d("lineText ", lineText);
                                if (!getName) {
                                    name.setText(lineText);
                                    getName = true;
                                }
                                if (phone.getText().length() == 0)
                                    phone.setText(mobileNumber(lineText));
                                if (date.getText().length() == 0)
                                    date.setText(dateChecker(lineText) ? lineText : "");
                                if (email.getText().length() == 0)
                                    email.setText(web(lineText));

                                if (invoiceNumber.getText().length() == 0)
                                    invoiceNumber.setText(invoicenum(lineText));
                                /// loop in all words in one line
                                for (FirebaseVisionText.Element element : line.getElements()) {
                                    String elementText = element.getText();
                                    if (phone.getText().length() == 0)
                                        phone.setText(mobileNumber(elementText));
                                    if (date.getText().length() == 0)
                                        date.setText(dateChecker(elementText) ? changeDateFormat(elementText) : "");

                                    if (invoiceNumber.getText().length() == 0)
                                        invoiceNumber.setText(invoicenum(elementText));
                                }
                            }
                        }

                    })
                    .addOnFailureListener(
                            e -> Log.w(TAG, Objects.requireNonNull(e.getMessage())));
        }/* else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }*/
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    private static Integer returnInvoiceNumber(ArrayList<Integer> invoiceNumbers) {
        for (Integer number :
                invoiceNumbers) {
            if (mobileNumber(number.toString()).length() != 0) {
                return number;
            }

        }
        return 0;
    }

    // saudi arabia number
    private static String mobileNumber(String text) {
        final String regex = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$";
        final String regex1 = "^((?:[+?0?0?966]+)(?:\\s?\\d{2})(?:\\s?\\d{7}))$";
        if (Pattern.compile(regex + "|" + regex1).matcher(text.replaceAll(" ", "")).matches()) {
            return text;
        }
        return "";
    }

    // website & email
    private static String companyWebsite(String text) {
        final String regex1 = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
        final String regex = "^(http\\:\\/\\/|https\\:\\/\\/)?([a-z0-9][a-z0-9\\-]\\.)+[a-z0-9][a-z0-9\\-]$";
        if (Pattern.compile(regex + "|" + regex1).matcher(text.replaceAll(" ", "")).matches()) {
            return text;
        }
        return "";
    }


    private static  String web(String text)
    {
        //String ocrResult = "- I tax INVOICE 550092002018021020 KINGDOM CENTER";

        String regex = "^(http\\:\\/\\/|https\\:\\/\\/)?([a-z0-9][a-z0-9\\-]\\.)+[a-z0-9][a-z0-9\\-]$";
                //"\\b(https://?|ftp://|file://|www.)*[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

        // Set a pattern
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        // Set a matcher
        Matcher matches = pattern.matcher(text);

        if (matches.find())
        {
            return matches.group(1);
        }
        else
        {
            return "";
        }}




    private static  String invoicenum(String text)
    {
        //String ocrResult = "- I tax INVOICE 550092002018021020 KINGDOM CENTER";

        String regex = "(?:.*)(?:TAX INVOICE)[\\s\\r\\t\\n]*(\\d{1,20})(?:.*)";

        // Set a pattern
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        // Set a matcher
        Matcher matches = pattern.matcher(text);

        if (matches.find())
        {
            return matches.group(1);
        }
        else
        {
            return "";
        }
    }

    // extract date from text
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean dateChecker(String text) {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("[yyyy-MM-dd][MM/dd/yy][MMMM dd, yyyy][yyyy/MM/dd][dd-MMM-yyyy]", Locale.ENGLISH);
        try {
            LocalDate.parse(text, DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // extract date from text
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String changeDateFormat(String text) {
        //Create formatter
        DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //Local date instance
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("[yyyy-MM-dd][MM/dd/yy][MMMM dd, yyyy][yyyy/MM/dd][dd-MMM-yyyy]", Locale.ENGLISH);
        //Get formatted String
        String dateString = FOMATTER.format(LocalDate.parse(text, DATE_FORMATTER));
        System.out.println(dateString);        //07/15/2018
        return dateString;
    }


    private static class InitFirebase {
        private FirebaseVisionBarcodeDetector detector;
        private FirebaseVisionTextRecognizer textRecognizer;

        public FirebaseVisionBarcodeDetector getDetector() {
            return detector;
        }

        public FirebaseVisionTextRecognizer getTextRecognizer() {
            return textRecognizer;
        }

        public InitFirebase invoke() {
            FirebaseVisionBarcodeDetectorOptions barcodeDetectorOptions =
                    new FirebaseVisionBarcodeDetectorOptions.Builder()
                            .build();
            detector = FirebaseVision.getInstance()
                    .getVisionBarcodeDetector();
            FirebaseVisionCloudTextRecognizerOptions options =
                    new FirebaseVisionCloudTextRecognizerOptions.Builder()
                            .setLanguageHints(Arrays.asList("en", "ar"))
                            .build();
            textRecognizer = FirebaseVision.getInstance()
                    .getCloudTextRecognizer(options);
            return this;
        }
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

    public class TimeListener implements TimePickerDialog.OnTimeSetListener {
        private TextView textView;

        public TimeListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            // TODO Auto-generated method stub
            mHourDay = i;
            mMinutes = i1;
            textView.setText(String.format(Locale.US, "%02d:%02d", mHourDay, mMinutes));
        }
    }
}

