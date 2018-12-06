package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class LoungeOwnerReservation extends AppCompatActivity {

    private DatabaseReference mLocationReqDatabase;
    private FirebaseUser mCurrentUser;



    DatabaseReference mUserDatabase;


    DatabaseReference mUsersDatabase, mUsersDatabaseSpecific, mUsersDatabaseSpecificClient;
    DatabaseReference mAppOwnerApproveDatabase;
    private RecyclerView mLoungeOwnerReservation;

    private RecyclerView mAppOwnerApproveSpecific;
    Spinner spinner;
    String locationkey;
    String locationid, locationame, locationtype, current_uid, name;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_owner_reservation);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();

        mLocationReqDatabase = FirebaseDatabase.getInstance().getReference().child("reservation_req");

        mLoungeOwnerReservation = (RecyclerView) findViewById(R.id.recycleViewLoungeOwnerReservation);
        mLoungeOwnerReservation.setLayoutManager(new LinearLayoutManager(this));








    }

    public void onStart() {
        super.onStart();
        startListeningClientOwnerReservation();


    }
    public void startListeningClientOwnerReservation() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("reservation_req")
                .child(current_uid)
                .limitToLast(50);
        FirebaseRecyclerOptions<LoungeOwnerReservationRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LoungeOwnerReservationRecycleview>()
                        .setQuery(query, LoungeOwnerReservationRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LoungeOwnerReservationRecycleview, LoungeOwnerReservation.AppOwnerApprovalViewHolder>(options) {
            @Override
            public LoungeOwnerReservation.AppOwnerApprovalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitemloungereservation, parent, false);



                return new LoungeOwnerReservation.AppOwnerApprovalViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final LoungeOwnerReservation.AppOwnerApprovalViewHolder holder, int position, LoungeOwnerReservationRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLoungeOwnerReservationAddress(model.locationame);
                holder.setLoungeOwnerReservationRequest(model.requested);



                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(model.requested);
                mUserDatabase.addValueEventListener(new ValueEventListener() {
                   @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {

                        name = dataSnapshot.child("name").getValue().toString();

                       TextView ClientName = holder.mView.findViewById(R.id.tLoungeOwnerName);
                       ClientName.setText(name);

                   }
                    @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
             });




            }

        };
        mLoungeOwnerReservation.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class AppOwnerApprovalViewHolder extends RecyclerView.ViewHolder {
        View mView;



        public AppOwnerApprovalViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setLoungeOwnerReservationAddress(String locationame) {
            TextView LocationEventName = (TextView) mView.findViewById(R.id.tLoungeAddress);
            LocationEventName.setText(locationame);

        }

        public void setLoungeOwnerReservationRequest(String requested) {
            TextView LocationEventName = (TextView) mView.findViewById(R.id.tLoungeRequest);
            LocationEventName.setText(requested);
        }





    }


    public void buttonLoungeDashboardBack(View v){
        Intent StartIntent = new Intent(LoungeOwnerReservation.this, LoungeDashboard.class);
        startActivity(StartIntent);
        finish();
    }
}
