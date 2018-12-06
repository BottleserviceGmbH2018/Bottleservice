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
import com.squareup.picasso.Picasso;

public class LocationLounge extends AppCompatActivity {
    private TextView LocationLoungeName;
    DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrentUser;
    ImageView imageViewLounge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_lounge);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        String locationame = getIntent().getStringExtra("locationame");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Location_Information").child("Lounge").child(current_uid).child(locationame);
        mUsersDatabase.keepSynced(true);

        LocationLoungeName = (TextView) findViewById(R.id.LocationLoungeName);
        imageViewLounge = (ImageView) findViewById(R.id.imageViewLounge);



        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String locationame = dataSnapshot.child("locationTitle").getValue().toString();
                String locationphoto = dataSnapshot.child("locationSlider").getValue().toString();

                LocationLoungeName.setText(locationame);

                if(!locationphoto.equals("default")) {
                    Picasso.get().load(locationphoto).placeholder(R.drawable.party).into(imageViewLounge);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void buttonLoungeLocationView(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeLocation.class);
        startActivity(Intent);
        this.finish();
    }
}
