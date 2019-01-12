package com.programunlocked.travenu;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by jchac on 2/26/2017.
 */
@IgnoreExtraProperties
public class Trips {
    private String flightName;

    public Trips(){
        //this constructor is required
    }

    public Trips(String flightName) {
        this.flightName = flightName;

    }

    public String getFlightName() {
        return flightName;
    }

}