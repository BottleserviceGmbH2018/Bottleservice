package ch.digitalmediafactory.bottleservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class signupcompanylogo extends AppCompatActivity {

    ImageView imageView;
    TextView editTextTags;
    private ProgressDialog mCompanyRegisterProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mUserDatabase;
    private TextView bRegisterCompanySkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcompanylogo);

        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        Intent intent = getIntent();
        final CheckBox itemClicked = (CheckBox) findViewById(R.id.checkbox);
        final int user_type = intent.getIntExtra("user_type", -1);
        final String companyname = intent.getStringExtra("companyname");
        final String companyownername = intent.getStringExtra("companyownername");
        final String companyemail = intent.getStringExtra("companyemail");
        final String companypassword = intent.getStringExtra("companypassword");
        final String companyaddress = intent.getStringExtra("companyaddress");
        final String companyaddress2 = intent.getStringExtra("companyaddress2");
        final String companycity = intent.getStringExtra("companycity");
        final String companypostal = intent.getStringExtra("companypostal");
        final String companyphonenumber = intent.getStringExtra("companyphonenumber");
        final String companyownerphonenumber = intent.getStringExtra("companyownerphonenumber");
        final String companyownermobilenumber = intent.getStringExtra("companyownermobilenumber");
        bRegisterCompanySkip = (TextView) findViewById(R.id.etskip);
        final Button bRegisterCompanyLogo = (Button) findViewById(R.id.bRegisterLogo);
        imageView = (ImageView) findViewById(R.id.profile_image);
        editTextTags = (TextView) findViewById(R.id.TextviewTags);
        bRegisterCompanyLogo.setEnabled(false);
        mCompanyRegisterProgress = new ProgressDialog(this);




        bRegisterCompanyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCompanyRegisterProgress.setTitle("Registering User");
                mCompanyRegisterProgress.setMessage("Please wait while we create your account");
                mCompanyRegisterProgress.setCanceledOnTouchOutside(false);
                mCompanyRegisterProgress.show();
                bRegisterCompanyLogo.setEnabled(false);
                register_company(companyownername, companyemail, companypassword, user_type);


            }
        });

        bRegisterCompanySkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCompanyRegisterProgress.setTitle("Registering User");
                mCompanyRegisterProgress.setMessage("Please wait while we create your account");
                mCompanyRegisterProgress.setCanceledOnTouchOutside(false);
                mCompanyRegisterProgress.show();
                bRegisterCompanyLogo.setEnabled(false);
                register_company(companyownername, companyemail, companypassword, user_type);
            }
        });




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if the tags edittext is empty
                //we will throw input error
                AlertDialog.Builder profilelogo = new  AlertDialog.Builder(signupcompanylogo.this);
                        profilelogo.setTitle("Photo")
                        .setMessage("Do you want to add a logo?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                getIntent.setType("image/*");

                                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickIntent.setType("image/*");

                                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                                startActivityForResult(chooserIntent, 100);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

            }
        });


        itemClicked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    bRegisterCompanyLogo.setEnabled(true);
                    bRegisterCompanySkip.setEnabled(true);
                }

                else {
                    bRegisterCompanyLogo.setEnabled(false);
                    bRegisterCompanySkip.setEnabled(true);
                }

            }
        });
    }

    private void register_company(final String companyownername, final String companyemail, String companypassword, final int user_type) {
        mAuth.createUserWithEmailAndPassword(companyemail, companypassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                    mCompanyRegisterProgress.dismiss();

                    imageView.setEnabled(false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(signupcompanylogo.this);
                    builder.setTitle("Success");
                    builder.setMessage("Congratulations on creating your account!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    signupcompanylogo.this.startActivity(intent);
                                    dialogInterface.dismiss();
                                }
                            }).show();


                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", companyownername);
                    userMap.put("email", companyemail);
                    userMap.put("profileImageUrl", "default");
                    userMap.put("type", user_type + "");

                    mDatabase.setValue(userMap);

                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    mUserDatabase.child(uid).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            sendVerificationEmail();
                        }
                    });


                } else {
                    // If sign in fails, display a message to the user.
                    AlertDialog.Builder builder = new AlertDialog.Builder(signupcompanylogo.this);
                    builder.setMessage("Your Email Address is already been registered!")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                    bRegisterCompanySkip.setEnabled(true);
                }

                // ...
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            try {

                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                //displaying selected image to imageview



                //calling the method uploadBitmap to upload image
                uploadBitmap(imgBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * The method is taking Bitmap as an argument
    * then it will return the byte[] array for the given bitmap
    * and we will send this array to the server
    * here we are using PNG Compression with 80% quality
    * you can give quality between 0 to 100
    * 0 means worse quality
    * 100 means best quality
    * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {
        final Button bRegisterCompanyLogo = (Button) findViewById(R.id.bRegisterLogo);
        //getting the tag from the edittext
        final String tags = editTextTags.getText().toString().trim();
        //waitingDialog.setCancelable(false)
                //.setTitle("Avatar updating....")
                //.setTopColorRes(R.color.colorPrimary)
                //.show();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            imageView.setImageBitmap(bitmap);
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            //progressBar1.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", tags);
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
        bRegisterCompanyLogo.setEnabled(true);

    }




    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mCompanyRegisterProgress.hide();
                        mAuth.signOut();
                    }else{
                        Toast.makeText(signupcompanylogo.this, "Couldn't send verification email.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    public void buttonViewClientTerms(View v) {
        Intent Intent = new Intent(this, OwnerTermsCondtion.class);
        startActivity(Intent);
    }


    public void buttonViewSignUpCompanyContact(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), signupcompanycontact.class);
        startActivity(Intent);
        this.finish();
    }



}
