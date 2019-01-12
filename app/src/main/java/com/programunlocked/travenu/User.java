package com.programunlocked.travenu;


import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
/*public class User {

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}*/

public class User {

    String email;
    String username;
    String password;
    //ImageView imageView;

    public User() {
        //Empty Constructor For Firebase
    }


    public User(String email, String username, String password)
    {
        this.email = email;
        this.username = username; //Parameterized for Program-Inhouse objects.
        this.password = password;
        //this.imageView = imageView;
    }

    //Getters and Setters
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = password;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

}
// [END blog_user_class]