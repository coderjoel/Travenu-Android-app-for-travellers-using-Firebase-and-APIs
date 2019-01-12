package com.programunlocked.travenu;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jchac on 20-02-2018.
 */

public class Detail {
    public String flight_name;
    public String flight_no;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Detail() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Detail(String flight_name, String flight_no) {
        this.flight_name = flight_name;
        this.flight_no = flight_no;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", flight_name);
        result.put("author", flight_no);

        return result;
    }
}
