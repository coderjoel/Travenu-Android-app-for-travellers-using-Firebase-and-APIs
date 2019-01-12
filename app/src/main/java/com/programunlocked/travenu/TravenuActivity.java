package com.programunlocked.travenu;

import android.app.Dialog;
//import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.provider.FontsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programunlocked.travenu.User;
import com.squareup.picasso.Picasso;

public class TravenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        private static final String TAG = "TravenuActivity";

        private static final int ERROR_DIALOG_REQUEST = 9001;

    DatabaseReference rootRef,demoRef;
    StorageReference Imageref,Imgref;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //NavigationView navView = findViewById(R.id.nav_home);
        //navView.setNavigationItemSelectedListener(this);
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.content_travenu, new HomeFragment());
        tx.commit();

        setContentView(R.layout.activity_travenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {

            String uid = currentUser.getUid();
            //Toast.makeText(TravenuActivity.this, "Uid " + uid, Toast.LENGTH_SHORT).show();

            if (currentUser.getDisplayName() != null) {

                Uri personPhoto = currentUser.getPhotoUrl();
                ImageView show_photo = header.findViewById(R.id.show_photo);
                Picasso.with(this).load(personPhoto).into(show_photo);

                String name = currentUser.getDisplayName();
                TextView show_username = header.findViewById(R.id.show_username);
                show_username.setText(name);

                String personEmail = currentUser.getEmail();
                TextView show_email = header.findViewById(R.id.show_email);
                show_email.setText(personEmail);
            }
            else {
            final TextView show_username = header.findViewById(R.id.show_username);

            String personEmail = currentUser.getEmail();
            TextView show_email = header.findViewById(R.id.show_email);
            show_email.setText(personEmail);

            final ImageView show_photo = header.findViewById(R.id.show_photo);
            rootRef = FirebaseDatabase.getInstance().getReference();
            Imageref = FirebaseStorage.getInstance().getReference().child("uploads").child(uid);
                //database reference pointing to demo node
            demoRef = rootRef.child("users").child(uid);

            demoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String value = (String) dataSnapshot.child("info").child("username").getValue();
                    //ImageView show_photo =  dataSnapshot.child("info").child("images").getValue();
                    show_username.setText(value);
                    //Picasso.with(this).load(personPhoto).into(show_photo);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
                Imageref.child("profile").child("dp.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String link = uri.toString();
                        Glide.with(getApplicationContext() /* context */)
                                // .using(new FirebaseImageLoader())
                                .load(link).into(show_photo);
                        // Got the download URL for 'users/me/profile.png'
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            /*Imgref = Imageref.child("profile").child("dp.jpg");
                String Imaageref= Imgref.toString();
            Toast.makeText(this,"Link "+ Imaageref,Toast.LENGTH_SHORT).show();

            Glide.with(this /* context *///)
                // .using(new FirebaseImageLoader())
                //.load(Imgref).into(show_photo);
        }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

   /* private void init(){
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view){
              Intent intent = new Intent(TravenuActivity.this, MapActivity.class);
              startActivity(intent);
          }
        });
    }
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(TravenuActivity.this);

        if (available == ConnectionResult.SUCCESS){
            //everything is fine and the user an make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG,"is ServicesOK: An error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(TravenuActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this, "We can't make Map request", Toast.LENGTH_SHORT).show();
        }
        return false;*/
    }

   /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut(); //End user session
                        startActivity(new Intent(TravenuActivity.this, HomeActivity.class)); //Go back to home page
                        finish();
                        //TravenuActivity.this.finish();
                        //startActivity(new Intent(TravenuActivity.this, HomeActivity.class));
                        //finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.travenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.nav_logout) {
         //   return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_trip) {
            // Handle the camera action
            setTitle(" New Trip");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new TripFragment()).commit();
        } else if (id == R.id.nav_home) {
            setTitle("My Trips");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new HomeFragment()).commit();
        } /*else if (id == R.id.nav_friends) {
            setTitle("Friends");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new FriendFragment()).commit();
        }*/ else if (id == R.id.nav_upload) {
            setTitle("Upload");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new com.programunlocked.travenu.images.UploadFragment()).commit();
        } else if (id == R.id.nav_slideshow) {
            setTitle("Gallery");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new com.programunlocked.travenu.images.ImagesFragment()).commit();
        } else if (id == R.id.nav_map) {
            setTitle("Maps");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new MapFragment()).commit();
        } else if (id == R.id.nav_weather) {
            //fragmentManager.beginTransaction().replace(R.id.content_travenu, new WeatherFragment()).commit();
            Intent i = new Intent(TravenuActivity.this, com.programunlocked.travenu.activities.MainActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_currency) {
            //setTitle("Currency Converter");
            //fragmentManager.beginTransaction().replace(R.id.content_travenu, new CurrencyFragment()).commit();
            //Intent i = new Intent(TravenuActivity.this, com.programunlocked.travenu.currency.MainActivity.class);
            //startActivity(i);
            setTitle("Currency");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new com.programunlocked.travenu.currency.MainActivity()).commit();
        } else if (id == R.id.nav_about) {
            setTitle("About Us");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new AboutUsFragment()).commit();
        } else if (id == R.id.nav_feedback) {
            setTitle("Feedback");
            fragmentManager.beginTransaction().replace(R.id.content_travenu, new FeedbackFragment()).commit();
        } else if (id == R.id.nav_logout) {
            setTitle("Logout");
            /*fragmentManager.beginTransaction().replace(R.id.content_travenu, new LogoutFragment()).commit();*/
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseAuth.getInstance().signOut(); //End user session
                            startActivity(new Intent(TravenuActivity.this, HomeActivity.class)); //Go back to home page
                            finish();
                            //TravenuActivity.this.finish();
                            //startActivity(new Intent(TravenuActivity.this, HomeActivity.class));
                            //finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
