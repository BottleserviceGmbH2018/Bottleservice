package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ClientLounge extends AppCompatActivity {


    private FirebaseUser mCurrentUser;

    private RecyclerView mLocationLoungeList;
    private DatabaseReference mLoungeDatabase;
    FirebaseRecyclerAdapter adapterLocationLounge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_lounge);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mLoungeDatabase = FirebaseDatabase.getInstance().getReference().child("OwnerToApp").child("Lounge");
        mLoungeDatabase.keepSynced(true);

        mLocationLoungeList = (RecyclerView) findViewById(R.id.recycleViewClientLounge);
        LinearLayoutManager layoutManagerBar = new LinearLayoutManager(ClientLounge.this, LinearLayoutManager.VERTICAL, false);
        mLocationLoungeList.setLayoutManager(layoutManagerBar);

    }

    public void onStart() {
        super.onStart();
        startListeningLounge();

    }

    public void startListeningLounge(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("OwnerToApp")
                .child("Lounge")
                .limitToLast(50);
        FirebaseRecyclerOptions<LocationLoungeRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationLoungeRecycleview>()
                        .setQuery(query, LocationLoungeRecycleview.class)
                        .build();
        adapterLocationLounge = new FirebaseRecyclerAdapter<LocationLoungeRecycleview, ClientLounge.LocationLoungeViewHolder>(options) {
            @Override
            public ClientLounge.LocationLoungeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitemclientlounge, parent, false);

                return new ClientLounge.LocationLoungeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientLounge.LocationLoungeViewHolder holder, int position, LocationLoungeRecycleview model) {
                // Bind the Chat object to the ChatHolder

                final int baritem = adapterLocationLounge.getItemCount();
                TextView LocationBarId = (TextView) findViewById(R.id.LoungeId);
                LocationBarId.setText(Integer.toString(baritem));


                holder.setLocationloungename(model.locationTitle);
                holder.setLocationloungephoto(model.locationSlider);

                final String user_id = getRef(position).getKey();
                final String locationame = model.locationTitle;
                final String locationid = model.locationSlider;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent(ClientLounge.this, ClientLoungeRequest.class);
                        locationintent.putExtra("locationame", locationame);
                        locationintent.putExtra("locationid", locationid);
                        startActivity(locationintent);

                    }
                });
                // ...
            }

        };
        mLocationLoungeList.setAdapter(adapterLocationLounge);
        adapterLocationLounge.startListening();
    }

    public void ClientLoungeDashBoard(View v){
        finish();

    }

    public static class LocationLoungeViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationLoungeViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationloungename(String locationame){
            TextView LocationLoungeName = (TextView) mView.findViewById(R.id.ClientBarName);
            LocationLoungeName.setText(locationame);
        }

        public void setLocationloungephoto(final String locationloungephoto){
            final ImageView LocationLoungeImage = (ImageView) mView.findViewById(R.id.imageClientLounge);
            Picasso.get().load(locationloungephoto).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.party).into(LocationLoungeImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(locationloungephoto).placeholder(R.drawable.party).into(LocationLoungeImage);

                }
            });
        }



    }
}
