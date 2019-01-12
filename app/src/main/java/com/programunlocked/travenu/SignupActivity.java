package com.programunlocked.travenu;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.programunlocked.travenu.User;
import com.programunlocked.travenu.images.Upload;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;



public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

   private Button signupbtn,choose,upload;
    private EditText fname;
    private EditText lname;
    private EditText Textemail;
    private EditText Textpassword;
    private EditText confirmpass;
    private ProgressDialog progressDialog;

    private DatabaseReference mDatabase;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private Uri filePath;
    private ArrayList<String> pathArray;
    private int array_position;
    private StorageTask mUploadTask;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            //initializing firebase auth object
            firebaseAuth = FirebaseAuth.getInstance();


            //initializing views
            Textemail = findViewById(R.id.Textemail);
            Textpassword = findViewById(R.id.Textpassword);
            confirmpass = findViewById(R.id.confirmpass);
            fname = findViewById(R.id.fname);
            signupbtn = findViewById(R.id.signup);
            choose = findViewById(R.id.choose_image);
            //upload = findViewById(R.id.upload_image);
            progressDialog = new ProgressDialog(this);
            mImageView = findViewById(R.id.show_photo);

            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            pathArray = new ArrayList<>();


            //attaching listener to button
            signupbtn.setOnClickListener(this);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = SignupActivity.this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(SignupActivity.this).load(mImageUri).into(mImageView);
        }
    }
    private void uploadFile() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(1==1) {
            final String uid = currentUser.getUid();
            mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("profile");
            //Toast.makeText(SignupActivity.this, "Uid " + uid, Toast.LENGTH_SHORT).show();

            if (mImageUri != null) {
                StorageReference fileReference = mStorageRef.child(uid).child("profile")
                        //.child(System.currentTimeMillis()
                        .child("profile_pic"
                        + "." + // getFileExtension(mImageUri));
                                "jpg");

                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                               /* handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(0);
                                    }
                                }, 500);*/

                                Toast.makeText(SignupActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                Upload upload = new Upload(mImageUri.toString().trim(),
                                      taskSnapshot.getDownloadUrl().toString());
                                String uploadId = mDatabaseRef.getKey();
                                mDatabaseRef.setValue(upload);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        /*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                mProgressBar.setProgress((int) progress);
                            }
                        });*/
            } else {
                Toast.makeText(SignupActivity.this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void OneFortyEncrypter()
    {

    }
    private void registerUser(){

            //getting email and password from edit texts
            final String email = Textemail.getText().toString().trim();
            final String password = Textpassword.getText().toString().trim();
            final String conpassword = confirmpass.getText().toString().trim();
            final String username = fname.getText().toString().trim();
            OneFortyEncrypter OFE = new OneFortyEncrypter();
            final String encryptedString = OFE.encrypt(password);


            //checking if email and passwords are empty
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(conpassword)) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "Please enter first name", Toast.LENGTH_LONG).show();
                return;
            }

            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password)

                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Toast.makeText(SignupActivity.this, "Signed In" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (currentUser != null) {

                                String uid = currentUser.getUid();
                                //Toast.makeText(SignupActivity.this, "Uid " + uid, Toast.LENGTH_SHORT).show();
                            }
                            generateUser(email, username, encryptedString);

                            if (!task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                //startActivity(new Intent(SignupActivity.this, TravenuActivity.class));
                                //Toast.makeText(SignupActivity.this, "Please sign in with email and password",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, TravenuActivity.class));
                                finish();
                            }
                            progressDialog.dismiss();
                        }
                    });

    }


    public void generateUser(String email, String username, String password)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null) {
            /*upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(SignupActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {*/

                        uploadFile();
                    //}
              //  }
            //});
            String uid = currentUser.getUid();
            DatabaseReference users = database.getReference("users").child(uid).child("info"); //users is a node in your Firebase Database.
            User user = new User(email, username, password); //ObjectClass for Users
            users.setValue(user);
        }

    }

    /*@Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (firebaseAuth.getCurrentUser() != null) {
            onAuthSuccess(firebaseAuth.getCurrentUser());
        }
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(SignupActivity.this, TravenuActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


    //[START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]
*/

    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }
    public void sign_inone(View v)
    {
        Intent i=new Intent(SignupActivity.this,MainActivity.class);
        startActivity(i);
    }

}