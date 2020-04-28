package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.User;
import com.example.myapplication.Empty.CalenderActivity;
import com.example.myapplication.Empty.SearchActivity;
import com.example.myapplication.Empty.mywalletActivity;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class activity_homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    Toolbar toolbar;
    ImageButton bt,wallett;
    ImageView cal, ser, ho, pro;
    Button b4;
    LinearLayout ls,lc,lp,lh;
    DrawerLayout drawerLayout;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    NavigationView navigationView;
    TextView headername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //--------------------------------------------------

        //For tool bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //--------------------------------------------------

        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.nmenu);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        headername=header.findViewById(R.id.headername);

        //--------------------------------------------------
        floatingActionButton = findViewById(R.id.orderPlus);
        bottomAppBar = findViewById(R.id.bar);
        bt = (ImageButton) findViewById(R.id.category);
        //botoom bar--------------------------------------------
        cal = (ImageView) findViewById(R.id.cale);
        ser = (ImageView) findViewById(R.id.sae);
        ho = (ImageView) findViewById(R.id.homi);
        pro = (ImageView) findViewById(R.id.userr);
        wallett = (ImageButton) findViewById(R.id.wallet);
        ls=(LinearLayout)findViewById(R.id.lse) ;
        lc=(LinearLayout)findViewById(R.id.lcal) ;
        lp=(LinearLayout)findViewById(R.id.lpro) ;
        lh=(LinearLayout)findViewById(R.id.lho) ;
//----------------------------------------------------------------
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_homepage.this, activity_category.class);
                startActivity(intent);
            }
        });

        wallett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_homepage.this, mywalletActivity.class);
                startActivity(intent);
            }
        });
//----------------------------------------------------
        //ho.bringToFront();
        lh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_homepage.this, activity_homepage.class);
                startActivity(intent);
            }
        });
        //
        //ser.bringToFront();
        ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_homepage.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        //
        // pro.bringToFront();
        lp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_homepage.this, profilActivity.class);
                startActivity(intent);
            }
        });
        //
        //cal.bringToFront();
        lc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_homepage.this, CalenderActivity.class);
                startActivity(intent);
            }
        });
        //

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


                        String name = datashot.child("name").getValue(String.class);
                        headername.setText(name);


                    }
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // For menu

    /*public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.roptions, menu);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nmenu));

        return true;
    }*/

    /*public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cond) {
            Intent intent = new Intent(activity_homepage.this, ConditionsActivity.class);
            startActivity(intent);
        }

        if (id == R.id.MCI) {

            WebView webview = new WebView(this);
            setContentView(webview);
            webview.loadUrl("https://mci.gov.sa/ar/contactus/Pages/default.aspx?t=c");
        }


        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent i1 = new Intent(activity_homepage.this, activity_log.class);
            startActivity(i1);
            Toast.makeText(this, "تم تسجيل الخروج", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);///////////////////////////
    }*/

    public void takePhoto(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)

                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent intent = new Intent(this, addDoc2Activity.class);
                intent.putExtra("imageUri", resultUri.toString());
                startActivity(intent);
                this.finish();
                // }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    //Complete menu
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.cond:
                Intent intent = new Intent(activity_homepage.this, ConditionsActivity.class);
                startActivity(intent);
                break;

            case R.id.MCI:
                WebView webview = new WebView(this);
                setContentView(webview);
                webview.loadUrl("https://mci.gov.sa/ar/eservices/Pages/ServiceDetails.aspx?sID=55");
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent i1 = new Intent(activity_homepage.this, activity_log.class);
                startActivity(i1);
                Toast.makeText(this, "تم تسجيل الخروج", Toast.LENGTH_SHORT).show();

                return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //---------------------------------------------------------------------------
}
