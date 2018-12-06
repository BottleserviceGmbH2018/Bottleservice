package ch.digitalmediafactory.bottleservice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class AddSetLocationSpecificPhotos extends AppCompatActivity {

    ImageView ivSpecial;
    Button bAddPhoto, bContinue;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mUserDatabase, mDatabaseSpecific, mDatabaseSpecificPhoto;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    private StorageReference mImagestorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_location_specific_photos);

        Intent intent = getIntent();
        final String titlelocation = intent.getStringExtra("titlelocation");
        final String locationtype = intent.getStringExtra("locationtype");
        final String specialtitle = intent.getStringExtra("specialtitle");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Location_Specific").child(locationtype).child(current_uid).child(titlelocation).child(specialtitle);
        mDatabaseSpecific = FirebaseDatabase.getInstance().getReference().child("location_req").child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(titlelocation).child("locationspecific").child(specialtitle);
        mDatabaseSpecificPhoto = FirebaseDatabase.getInstance().getReference().child("locationspecific").child(locationtype).child(current_uid).child(titlelocation).child(specialtitle);
        mAuth = FirebaseAuth.getInstance();


        mImagestorage = FirebaseStorage.getInstance().getReference();


        bContinue = (Button) findViewById(R.id.bContinue);
        bAddPhoto = (Button) findViewById(R.id.bAddPhoto);
        ivSpecial = (ImageView) findViewById(R.id.ivSpecial);



        bContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddSetLocationSpecificAreas.class);
                intent.putExtra("titlelocation", titlelocation);
                intent.putExtra("specialtitle", specialtitle);
                intent.putExtra("locationtype", locationtype);
                startActivity(intent);

            }
        });


        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String locationphotospecific = dataSnapshot.child("specficationSlider").getValue().toString();

                if(!locationphotospecific.equals("default")) {
                    Picasso.get().load(locationphotospecific).into(ivSpecial);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        bAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { 

                //if the tags edittext is empty
                //we will throw input error
                android.support.v7.app.AlertDialog.Builder profilelogo = new android.support.v7.app.AlertDialog.Builder(AddSetLocationSpecificPhotos.this);
                profilelogo.setTitle("Take Picture")
                        .setMessage("Take a new photo or select one from your existing photo")
                        .setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                getIntent.setType("image/*");

                                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickIntent.setType("image/*");

                                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                                startActivityForResult(chooserIntent, 100);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("GALLERY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                getIntent.setType("image/*");

                                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickIntent.setType("image/*");

                                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                                startActivityForResult(chooserIntent, 100);
                                dialogInterface.dismiss();
                            }
                        }).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            Intent intent = CropImage.activity(imageUri)
                    .setAspectRatio(2, 2)
                    .getIntent(this);
            startActivityForResult(intent, 1);
        }

                if (requestCode == 1) {

                    CropImage.ActivityResult result = CropImage.getActivityResult(data);

                    if (resultCode == RESULT_OK) {

                        bContinue.setText("Next");
                        bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
                        bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));


                        mProgressDialog = new ProgressDialog(this);
                        mProgressDialog.setTitle("Uploading image...");
                        mProgressDialog.setMessage("Please wait while we upload and process the image..");
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.show();


                        Uri resultUri = result.getUri();

                        final File thumb_filePath = new File(resultUri.getPath());


                        final Bitmap thumb_bitmap = new Compressor(this)
                                .setMaxWidth(200)
                                .setMaxWidth(200)
                                .setQuality(75)
                                .compressToBitmap(thumb_filePath);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] thumb_byte = baos.toByteArray();

                        StorageReference filepath = mImagestorage.child("location_images").child(random() + ".jpg");


                        filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {

                                    String download_url = task.getResult().getDownloadUrl().toString();

                                    mUserDatabase.child("locationspecificphoto").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {
                                                mProgressDialog.dismiss();
                                            }

                                        }
                                    });

                                    mDatabaseSpecific.child("locationspecificphoto").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {
                                                mProgressDialog.dismiss();
                                            }

                                        }
                                    });

                                    mDatabaseSpecificPhoto.child("locationspecificphoto").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {
                                                mProgressDialog.dismiss();
                                            }

                                        }
                                    });

                                }else {

                                }
                            }

                        });
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                }

        }


    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }


}
