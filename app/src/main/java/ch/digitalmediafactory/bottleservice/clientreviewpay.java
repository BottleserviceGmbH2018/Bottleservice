package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class clientreviewpay extends AppCompatActivity {


    DatabaseReference mUsersDatabase, mReservationDatabase, mDatabase, mDatabaseRequest;
    DatabaseReference mNotificationDatabase;
    ImageView ivSpecificArea;
    TextView SpecificTitle, SpecificArea, SpecificEventName;
    private FirebaseUser mCurrentUser;
    private String mCurrent_state;
    private String locationphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientreviewpay);
        final CheckBox itemClicked = (CheckBox) findViewById(R.id.checkbox);
        final Button bPayment = (Button) findViewById(R.id.bPayment);
        bPayment.setEnabled(false);

        ivSpecificArea = (ImageView) findViewById(R.id.ivSpecificArea);
        SpecificTitle = (TextView) findViewById(R.id.SpecificTitle);
        SpecificArea = (TextView) findViewById(R.id.SpecificArea);
        SpecificEventName = (TextView) findViewById(R.id.SpecificEventName);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();


        final String locationame = getIntent().getStringExtra("locationame");
        final String locationid = getIntent().getStringExtra("locationid");
        final String locationtitle = getIntent().getStringExtra("locationtitle");
        final String locationarea = getIntent().getStringExtra("locationarea");

        SpecificTitle.setText(locationtitle);
        SpecificArea.setText(locationarea);
        SpecificEventName.setText(locationame);


        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("location").child("Events").child(locationid).child(locationame);
        mUsersDatabase.keepSynced(true);

        mReservationDatabase = FirebaseDatabase.getInstance().getReference().child("reservation");
        mReservationDatabase.keepSynced(true);

        mNotificationDatabase = FirebaseDatabase.getInstance().getReference().child("notifications");
        mNotificationDatabase.keepSynced(true);

        mCurrent_state = "not_reserved";

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                locationphoto = dataSnapshot.child("locationphoto").getValue().toString();


                if (!locationphoto.equals("default")) {
                    Picasso.get().load(locationphoto).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.party).into(ivSpecificArea, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(locationphoto).placeholder(R.drawable.party).into(ivSpecificArea);

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        bPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mDatabase = FirebaseDatabase.getInstance().getReference().child("reservation").child(current_uid).child(locationame);
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("locationtitle", locationtitle);
                userMap.put("locationame", locationame);
                userMap.put("locationtime", "default");
                userMap.put("locationpayment", "default");
                userMap.put("locationphoto", locationphoto);
                mDatabase.setValue(userMap);

                mDatabaseRequest = FirebaseDatabase.getInstance().getReference().child("reservation_req").child(locationid).child(current_uid).child(locationame);
                HashMap<String, String> userMapRequest = new HashMap<>();
                userMapRequest.put("locationtitle", locationtitle);
                userMapRequest.put("locationame", locationame);
                userMapRequest.put("locationtime", "default");
                userMapRequest.put("locationpayment", "default");
                userMapRequest.put("locationphoto", locationphoto);
                userMapRequest.put("requested", current_uid);
                mDatabaseRequest.setValue(userMapRequest);

                HashMap<String, String> notificationData = new HashMap<>();
                notificationData.put("from", mCurrentUser.getUid());
                notificationData.put("type", "request");
                mDatabaseRequest.setValue(userMapRequest);

                mNotificationDatabase.child(locationid).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Intent Intent = new Intent(getApplicationContext(), DashboardDrawerlayout.class);
                        Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(Intent);
                        clientreviewpay.this.finish();
                    }
                });


            }
        });

        itemClicked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bPayment.setEnabled(true);
                } else {
                    bPayment.setEnabled(false);
                }

            }
        });
    }

    public void buttonViewClientEvent(View v) {

        this.finish();
    }


    public void buttonViewClientTerms(View v) {
        Intent Intent = new Intent(this, ClientTermsCondition.class);
        startActivity(Intent);
    }


}


