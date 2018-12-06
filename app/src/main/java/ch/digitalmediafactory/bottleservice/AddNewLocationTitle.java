package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class AddNewLocationTitle extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseClient;
    private DatabaseReference mDatabaseAppOwner;



    private String mCurrent_state;
    private DatabaseReference mLocationReqDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location_title);




        final EditText etTitleLocation = (EditText) findViewById(R.id.etTitleLocation);


        mCurrent_state = "not_accepted";
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        mLocationReqDatabase = FirebaseDatabase.getInstance().getReference().child("location_req");


        Intent intent = getIntent();
        final String locationtype = intent.getStringExtra("locationtype");
        final String companyname = intent.getStringExtra("companyname");
        final String locationaddress = intent.getStringExtra("locationaddress");
        final String locationaddress2 = intent.getStringExtra("locationaddress2");
        final String locationcity = intent.getStringExtra("locationcity");
        final String locationpostal = intent.getStringExtra("locationpostal");
        final String locationfeaturefullbar = intent.getStringExtra("locationfeaturefullbar");
        final String locationfeatureoutdoor = intent.getStringExtra("locationfeatureoutdoor");
        final String locationfeaturecraft = intent.getStringExtra("locationfeaturecraft");
        final String locationfeaturenightlife = intent.getStringExtra("locationfeaturenightlife");
        final String locationfeaturewireless = intent.getStringExtra("locationfeaturewireless");
        final String locationfeatureair = intent.getStringExtra("locationfeatureair");
        final String locationfeaturecash = intent.getStringExtra("locationfeaturecash");
        final String describelocation = intent.getStringExtra("describelocation");
        Button bNextTitle = (Button) findViewById(R.id.bButtonTitle);
        mAuth = FirebaseAuth.getInstance();





        bNextTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titlelocation = etTitleLocation.getText().toString();
                if (etTitleLocation.getText().toString().matches("")) {
                    etTitleLocation.setError("Please Enter The Title of The Location");
                    etTitleLocation.requestFocus();
                    return;
                }if(mCurrent_state.equals("not_accepted")){
                                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = current_user.getUid();
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Location_Information").child(locationtype).child(uid).child(titlelocation);
                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("locationid", uid);
                                userMap.put("locationTitle", titlelocation);
                                userMap.put("locationMap", "default");
                                userMap.put("locationSlider", "default");
                                userMap.put("locationStatus", "Pending");
                                userMap.put("locationCity", "default");
                                userMap.put("locationDateApplied", "10/12/2018");
                                userMap.put("locationDesc", "default");
                                userMap.put("locationFeature", "default");
                                userMap.put("locationGuest", "2");
                                userMap.put("locationOwner", "David");
                                userMap.put("locationPostal", "1432");
                                userMap.put("locationProgress", "Not Finish");
                                userMap.put("locationSchedule", "default");
                                userMap.put("ownerUserID", uid);
                                userMap.put("locationType", locationtype);
                                mDatabase.setValue(userMap);

                                mDatabaseAppOwner = FirebaseDatabase.getInstance().getReference().child("location_req").child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(titlelocation);
                                HashMap<String, String> userMapAppOwner = new HashMap<>();
                                userMapAppOwner.put("ownerUserID", uid);
                                userMapAppOwner.put("locationame", titlelocation);
                                userMapAppOwner.put("locationmap", "default");
                                userMapAppOwner.put("locationphoto", "default");
                                userMapAppOwner.put("locationtype", locationtype);
                                mDatabaseAppOwner.setValue(userMapAppOwner);

                                Intent intent = new Intent(getApplicationContext(), AddSetLocation.class);
                                intent.putExtra("locationtype", locationtype);
                                intent.putExtra("companyname", companyname);
                                intent.putExtra("locationaddress", locationaddress);
                                intent.putExtra("locationaddress2", locationaddress2);
                                intent.putExtra("locationcity", locationcity);
                                intent.putExtra("locationpostal", locationpostal);
                                intent.putExtra("locationfeaturefullbar", locationfeaturefullbar);
                                intent.putExtra("locationfeatureoutdoor", locationfeatureoutdoor);
                                intent.putExtra("locationfeaturecraft", locationfeaturecraft);
                                intent.putExtra("locationfeaturenightlife", locationfeaturenightlife);
                                intent.putExtra("locationfeaturewireless", locationfeaturewireless);
                                intent.putExtra("locationfeatureair", locationfeatureair);
                                intent.putExtra("locationfeaturecash", locationfeaturecash);
                                intent.putExtra("describelocation", describelocation);
                                intent.putExtra("titlelocation", titlelocation);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                mLocationReqDatabase.child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(titlelocation).child("locationStatus").setValue("Pending").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){



                                        }

                                    }
                                });

                    }

            }
        });
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(50);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

}
