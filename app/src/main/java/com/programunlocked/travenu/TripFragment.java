package com.programunlocked.travenu;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by Mohammed Imdad on 1/28/2018.
 */

public class TripFragment extends Fragment {

    public static final String Flight_id = "com.programunlocked.travenu.trips";

    private DatabaseReference mDatabase;
    private DatabaseReference mMessageReference;
    private FirebaseDatabase database;
    //And also a Firebase Auth object
    FirebaseAuth auth;

    DatabaseReference rootRef,demoRef;
    View myView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trip_layout, container, false);
        //getting the reference of artists node
        database = FirebaseDatabase.getInstance();
        //database.setPersistenceEnabled(true);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (1==1) {
            String uid = currentUser.getUid();


            final EditText editText1 = view.findViewById(R.id.flight_name);
            final EditText editText2 = view.findViewById(R.id.flightno);
        final EditText editText3 = view.findViewById(R.id.deptime);
        final EditText editText4 = view.findViewById(R.id.depdate);
        final EditText editText5 = view.findViewById(R.id.arrtime);
        final EditText editText6 = view.findViewById(R.id.arrdate);
        final EditText editText7 = view.findViewById(R.id.hotelname);
        final EditText editText8 = view.findViewById(R.id.checkindate);
        final EditText editText9 = view.findViewById(R.id.checkoutdate);
        final EditText editText10 = view.findViewById(R.id.address);
        final EditText editText11 = view.findViewById(R.id.hotelid);
        final EditText editText12 = view.findViewById(R.id.phoneno);
        final EditText editText13 = view.findViewById(R.id.carname);
        final EditText editText14 = view.findViewById(R.id.carno);
        final EditText editText15 = view.findViewById(R.id.pickuploc);
        final EditText editText16 = view.findViewById(R.id.pickupdate);
        final EditText editText17 = view.findViewById(R.id.time);
        final EditText editText18 = view.findViewById(R.id.dropdate);
        final EditText editText19= view.findViewById(R.id.droptime);
            final Button submit = view.findViewById(R.id.savedata);



            rootRef = FirebaseDatabase.getInstance().getReference();
            //database reference pointing to demo node
            demoRef = rootRef.child("users").child(uid).child("trips");
            /*fetch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {*/
            demoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //String value = dataSnapshot.getValue(String.class);
                    String value = (String) dataSnapshot.child("flight_name").getValue();
                    String value1 = (String) dataSnapshot.child("flight_no").getValue();
                    String value2 = (String) dataSnapshot.child("dep_time").getValue();
                    String value3 = (String) dataSnapshot.child("dep_date").getValue();
                    String value4 = (String) dataSnapshot.child("arr_time").getValue();
                    String value5 = (String) dataSnapshot.child("arr_date").getValue();
                    String value6 = (String) dataSnapshot.child("hotel_name").getValue();
                    String value7 = (String) dataSnapshot.child("check_in_date").getValue();
                    String value8 = (String) dataSnapshot.child("checkout_date").getValue();
                    String value9 = (String) dataSnapshot.child("address").getValue();
                    String value10 = (String) dataSnapshot.child("hotel_id").getValue();
                    String value11 = (String) dataSnapshot.child("phone_no").getValue();
                    String value12 = (String) dataSnapshot.child("car_name").getValue();
                    String value13 = (String) dataSnapshot.child("car_no").getValue();
                    String value14 = (String) dataSnapshot.child("pickup_loc").getValue();
                    String value15 = (String) dataSnapshot.child("pickup_date").getValue();
                    String value16 = (String) dataSnapshot.child("time").getValue();
                    String value17 = (String) dataSnapshot.child("drop_date").getValue();
                    String value18 = (String) dataSnapshot.child("drop_time").getValue();
                    editText1.setText(value);
                    editText2.setText(value1);
                    editText3.setText(value2);
                    editText4.setText(value3);
                    editText5.setText(value4);
                    editText6.setText(value5);
                    editText7.setText(value6);
                    editText8.setText(value7);
                    editText9.setText(value8);
                    editText10.setText(value9);
                    editText11.setText(value10);
                    editText12.setText(value11);
                    editText13.setText(value12);
                    editText14.setText(value13);
                    editText15.setText(value14);
                    editText16.setText(value15);
                    editText17.setText(value16);
                    editText18.setText(value17);
                    editText19.setText(value18);
                }



                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });



            //database reference pointing to root of database
            //rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef=database.getReference();
            //database reference pointing to trips node
            demoRef = rootRef.child("users").child(uid).child("trips");
            //Toast.makeText(getActivity(), "Uid  " + uid, Toast.LENGTH_SHORT).show();



            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value1 = editText1.getText().toString();
                    String value2 = editText2.getText().toString();
                String value3 = editText3.getText().toString();
                String value4 = editText4.getText().toString();
                String value5 = editText5.getText().toString();
                String value6 = editText6.getText().toString();
                String value7 = editText7.getText().toString();
                String value8 = editText8.getText().toString();
                String value9 = editText9.getText().toString();
                String value10 = editText10.getText().toString();
                String value11= editText11.getText().toString();
                String value12 = editText12.getText().toString();
                String value13 = editText13.getText().toString();
                String value14 = editText14.getText().toString();
                String value15 = editText15.getText().toString();
                String value16 = editText16.getText().toString();
                String value17 = editText17.getText().toString();
                String value18 = editText18.getText().toString();
                String value19 = editText19.getText().toString();
                    //push creates a unique id in database
                    demoRef.child("flight_name").setValue(value1);
                    demoRef.child("flight_no").setValue(value2);
                    demoRef.child("dep_time").setValue(value3);
                    demoRef.child("dep_date").setValue(value4);
                    demoRef.child("arr_time").setValue(value5);
                    demoRef.child("arr_date").setValue(value6);
                    demoRef.child("hotel_name").setValue(value7);
                    demoRef.child("check_in_date").setValue(value8);
                    demoRef.child("checkout_date").setValue(value9);
                    demoRef.child("address").setValue(value10);
                    demoRef.child("hotel_id").setValue(value11);
                    demoRef.child("phone_no").setValue(value12);
                    demoRef.child("car_name").setValue(value13);
                    demoRef.child("car_no").setValue(value14);
                    demoRef.child("pickup_loc").setValue(value15);
                    demoRef.child("pickup_date").setValue(value16);
                    demoRef.child("time").setValue(value17);
                    demoRef.child("drop_date").setValue(value18);
                    demoRef.child("drop_time").setValue(value19);
                    Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_LONG).show();
                }

            });
        /*demoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Oops! " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });*/
        }
        return view;
    }
}
