package ch.digitalmediafactory.bottleservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by Chris Gacu on 21/02/2018.
 */

public class LocationLoungeFragment extends Fragment {
    private static final String TAG = "LocationLoungeFragment";


    private RecyclerView mLocationLoungeList;
    private FirebaseUser mCurrentUser;
    DatabaseReference mUsersDatabase;


    public LocationLoungeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("location").child("Lounge").child(current_uid);
        mUsersDatabase.keepSynced(true);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_lounge_fragment,container,false);
        mLocationLoungeList = (RecyclerView) view.findViewById(R.id.recycleViewLocationLounge);
        mLocationLoungeList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }


    public void onStart() {
        super.onStart();
        startListening();
    }

    public void startListening(){
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Location_Information")
                .child("Lounge")
                .child(current_uid)
                .limitToLast(50);
        FirebaseRecyclerOptions<LocationLoungeRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationLoungeRecycleview>()
                        .setQuery(query, LocationLoungeRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationLounge = new FirebaseRecyclerAdapter<LocationLoungeRecycleview, LocationLoungeFragment.LocationLoungeViewHolder>(options) {
            @Override
            public LocationLoungeFragment.LocationLoungeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.location_lounge_layout, parent, false);

                return new LocationLoungeFragment.LocationLoungeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(LocationLoungeFragment.LocationLoungeViewHolder holder, int position, LocationLoungeRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationloungename(model.locationTitle);
                holder.setLocationloungephoto(model.locationSlider);


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationTitle;


                final String[] items = {
                        "Add Event to this location",
                        "Delete location",};

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Options");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item) {
                            case 0:{
                                Intent intent = new Intent(getContext(), AddNewLocation.class);
                                startActivity(intent);
                                break;}
                            case 1:{

                                break;}

                        }

                    }
                });
                holder.mView.findViewById(R.id.imageLoungeLocation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent(getContext(), LocationLounge.class);
                        locationintent.putExtra("locationame", locationame);
                        startActivity(locationintent);

                    }
                });

                holder.mView.findViewById(R.id.spinner).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        builder.create();
                        builder.show();

                    }
                });

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent(getContext(), LocationLounge.class);
                        locationintent.putExtra("locationame", locationame);
                        startActivity(locationintent);

                    }
                });
                // ...
            }

        };
        mLocationLoungeList.setAdapter(adapterLocationLounge);
        adapterLocationLounge.startListening();
    }

    public static class LocationLoungeViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationLoungeViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationloungename(String locationame){
            TextView LocationLoungeName = (TextView) mView.findViewById(R.id.locationLoungename);
            LocationLoungeName.setText(locationame);
        }
        public void setLocationloungephoto(final String locationloungephoto){
            final ImageView LocationLoungeImage = (ImageView) mView.findViewById(R.id.imageLoungeLocation);
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