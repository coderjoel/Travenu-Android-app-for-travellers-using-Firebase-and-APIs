package com.programunlocked.travenu.images;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.programunlocked.travenu.R;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private ProgressDialog progressDialog;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

            myView = inflater.inflate(R.layout.fragment_upload, container, false);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(1==1) {
         final String uid = currentUser.getUid();

            mButtonChooseImage = myView.findViewById(R.id.button_choose_image);
            mButtonUpload = myView.findViewById(R.id.button_upload);
            mTextViewShowUploads = myView.findViewById(R.id.text_view_show_uploads);
            mEditTextFileName = myView.findViewById(R.id.edit_text_file_name);
            mImageView = myView.findViewById(R.id.image_view);
            mProgressBar = myView.findViewById(R.id.progress_bar);
            progressDialog = new ProgressDialog(getActivity());

            mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("images");

            mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileChooser();
                }
            });

            mButtonUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                    }
                }
            });

            mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImagesActivity();
                }
            });
        }
        return myView;
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

            Picasso.with(getActivity()).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(1==1) {
            final String uid = currentUser.getUid();
            //Toast.makeText(getActivity(), "Uid " + uid, Toast.LENGTH_SHORT).show();

            if (mImageUri != null) {
                StorageReference fileReference = mStorageRef.child(uid).child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));


                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(0);
                                    }
                                }, 500);

                                Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
                                Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                        taskSnapshot.getDownloadUrl().toString());
                                String uploadId = mDatabaseRef.push().getKey();
                                mDatabaseRef.child(uploadId).setValue(upload);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                mProgressBar.setProgress((int) progress);
                            }
                        });
            } else {
                Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImagesActivity() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_travenu, new ImagesFragment()).commit();
    }
}