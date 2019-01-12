package com.programunlocked.travenu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Mohammed Imdad on 2/4/2018.
 */

public class FeedbackFragment extends Fragment {

    View myView,v;
    EditText f;
    private FirebaseDatabase database;
    DatabaseReference ratingRef;
    RatingBar r;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.feedback_layout,container, false);
        database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (1==1) {
            String uid = currentUser.getUid();
            r = myView.findViewById(R.id.ratingBar);
            f = myView.findViewById(R.id.feedback);
            final Button submit = myView.findViewById(R.id.send_feedback);
            ratingRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("feedback");
            //String feedback = f.getText().toString();
            /*r.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser)*/
            submit.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              ratingRef.child("Feedback").setValue(f.getText().toString());
                                              ratingRef.child("Rating").setValue(r.getRating());
                                          }
                                          //}
                                          //});
                                      });

            /*ratingRef.child("Rating").addValueEventListener(new ValueEventListener() {
               @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    /*if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        float rating = Float.parseFloat(dataSnapshot.getValue().toString());
                        r.setRating(rating);
                    }
                    int total = 0,
                            count = 0;
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        int rating = dataSnapshot.child("Rating").getValue(Integer.class);
                        total = total + rating;
                        count = count + 1;
                    }
                    r.setRating(total);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });*/
        }
        return myView;
    }

}
