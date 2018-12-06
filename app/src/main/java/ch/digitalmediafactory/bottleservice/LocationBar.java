package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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


public class LocationBar extends AppCompatActivity {

    private TextView LocationBarName;
    DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrentUser;
    ImageView imageViewBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_bar);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        String locationame = getIntent().getStringExtra("locationame");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Location_Information").child("Bar").child(current_uid).child(locationame);
        mUsersDatabase.keepSynced(true);
        LocationBarName = (TextView) findViewById(R.id.LocationBarName);
        imageViewBar = (ImageView) findViewById(R.id.imageViewBar);



        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String locationame = dataSnapshot.child("locationTitle").getValue().toString();
                final String locationphoto = dataSnapshot.child("locationSlider").getValue().toString();

                LocationBarName.setText(locationame);

                if(!locationphoto.equals("default")) {
                    Picasso.get().load(locationphoto).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.party).into(imageViewBar, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(locationphoto).placeholder(R.drawable.party).into(imageViewBar);

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void buttonBarLocationView(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeLocation.class);
        startActivity(Intent);
        this.finish();
    }

}
