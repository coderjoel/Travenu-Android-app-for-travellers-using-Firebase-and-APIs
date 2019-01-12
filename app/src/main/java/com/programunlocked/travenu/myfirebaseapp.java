package com.programunlocked.travenu;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jchac on 20-02-2018.
 */

public class myfirebaseapp extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
