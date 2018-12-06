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

public class ClientBar extends AppCompatActivity {

    private FirebaseUser mCurrentUser;

    private RecyclerView mLocationBarList;
    private DatabaseReference mBarDatabase;
    FirebaseRecyclerAdapter adapterLocationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_bar);

        setTitle("Bar");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mBarDatabase = FirebaseDatabase.getInstance().getReference().child("OwnerToApp").child("Bar");
        mBarDatabase.keepSynced(true);


        mLocationBarList = (RecyclerView) findViewById(R.id.recycleViewClientBar);
        LinearLayoutManager layoutManagerBar = new LinearLayoutManager(ClientBar.this, LinearLayoutManager.VERTICAL, false);
        mLocationBarList.setLayoutManager(layoutManagerBar);


    }

    public void onStart() {
        super.onStart();
        startListeningBar();

    }

    public void startListeningBar(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("OwnerToApp")
                .child("Bar")
                .limitToLast(50);
        FirebaseRecyclerOptions<LocationBarRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationBarRecycleview>()
                        .setQuery(query, LocationBarRecycleview.class)
                        .build();
        adapterLocationBar = new FirebaseRecyclerAdapter<LocationBarRecycleview, ClientBar.LocationBarViewHolder>(options) {
            @Override
            public ClientBar.LocationBarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitembararea, parent, false);

                return new ClientBar.LocationBarViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientBar.LocationBarViewHolder holder, int position, LocationBarRecycleview model) {
                // Bind the Chat object to the ChatHolder

                final int baritem = adapterLocationBar.getItemCount();
                TextView LocationBarId = (TextView) findViewById(R.id.barId);
                LocationBarId.setText(Integer.toString(baritem));

                holder.setLocationbarname(model.locationTitle);
                holder.setLocationbarphoto(model.locationSlider);


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationTitle;
                final String locationid = model.locationSlider;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent(ClientBar.this, ClientBarRequest.class);
                        locationintent.putExtra("locationame", locationame);
                        locationintent.putExtra("locationid", locationid);
                        locationintent.putExtra("locationitem", baritem);
                        startActivity(locationintent);



                    }
                });
                // ...
            }

        };
        mLocationBarList.setAdapter(adapterLocationBar);
        adapterLocationBar.startListening();
    }

    public static class LocationBarViewHolder extends RecyclerView.ViewHolder {
        View mView;



        public LocationBarViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setLocationbarname(String locationame) {
            TextView LocationBarName = (TextView) mView.findViewById(R.id.ClientBarName);
            LocationBarName.setText(locationame);
        }

        public void setLocationbarid(String locationid){
            TextView LocationBarId = (TextView) mView.findViewById(R.id.BarId);
            LocationBarId.setText(locationid);
        }

        public void setLocationbarphoto(final String locationbarphoto) {
            final ImageView LocationBarImage = (ImageView) mView.findViewById(R.id.imageClientBar);
            Picasso.get().load(locationbarphoto).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.party).into(LocationBarImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(locationbarphoto).placeholder(R.drawable.party).into(LocationBarImage);

                }
            });

        }

    }

    public void ClientBarDashboard (View v){
        finish();

    }
}
