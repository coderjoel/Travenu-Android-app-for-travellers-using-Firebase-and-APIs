package com.programunlocked.travenu;

import android.content.Context;
import android.media.MediaDrm;
import android.net.Uri;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class HomeFragment extends Fragment {

    View myView;
    Button fetch;
    DatabaseReference rootRef,demoRef;
    TextView demoValue;
    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19;

    private DatabaseReference mDatabase;
    private FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        final String uid = getArguments().getString("uid");
        myView = inflater.inflate(R.layout.fragment_home,container, false);
        database = FirebaseDatabase.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (1==1) {
            final String uid = currentUser.getUid();

            //Toast.makeText(getActivity(), "Uid " + uid, Toast.LENGTH_SHORT).show();
            t1 = (TextView) myView.findViewById(R.id.trip1);
            t2 = (TextView) myView.findViewById(R.id.trip2);
            t3 = (TextView) myView.findViewById(R.id.trip3);
            t4 = (TextView) myView.findViewById(R.id.trip4);
            t5 = (TextView) myView.findViewById(R.id.trip5);
            t6 = (TextView) myView.findViewById(R.id.trip6);
            t7 = (TextView) myView.findViewById(R.id.trip7);
            t8 = (TextView) myView.findViewById(R.id.trip8);
            t9 = (TextView) myView.findViewById(R.id.trip9);
            t10 = (TextView) myView.findViewById(R.id.trip10);
            t11 = (TextView) myView.findViewById(R.id.trip11);
            t12 = (TextView) myView.findViewById(R.id.trip12);
            t13 = (TextView) myView.findViewById(R.id.trip13);
            t14 = (TextView) myView.findViewById(R.id.trip14);
            t15 = (TextView) myView.findViewById(R.id.trip15);
            t16 = (TextView) myView.findViewById(R.id.trip16);
            t17 = (TextView) myView.findViewById(R.id.trip17);
            t18 = (TextView) myView.findViewById(R.id.trip18);
            t19 = (TextView) myView.findViewById(R.id.trip19);
            //fetch = (Button) myView.findViewById(R.id.b1);
            //database reference pointing to root of database
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
                            t1.setText(value);
                            t2.setText(value1);
                            t3.setText(value2);
                            t4.setText(value3);
                            t5.setText(value4);
                            t6.setText(value5);
                            t7.setText(value6);
                            t8.setText(value7);
                            t9.setText(value8);
                            t10.setText(value9);
                            t11.setText(value10);
                            t12.setText(value11);
                            t13.setText(value12);
                            t14.setText(value13);
                            t15.setText(value14);
                            t16.setText(value15);
                            t17.setText(value16);
                            t18.setText(value17);
                            t19.setText(value18);
                            }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
               // }
            //});

            mDatabase = FirebaseDatabase.getInstance().getReference().child(uid).child("trips");

        }

        return myView;
    }

}