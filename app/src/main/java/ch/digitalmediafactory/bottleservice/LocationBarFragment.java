package ch.digitalmediafactory.bottleservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chris Gacu on 21/02/2018.
 */

public class LocationBarFragment extends Fragment {
    private static final String TAG = "LocationBarFragment";

    private RecyclerView mLocationBarList;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mUsersDatabase;


    public LocationBarFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Location_Information").child("Bar").child(current_uid);
        mUsersDatabase.keepSynced(true);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_bar_fragment,container,false);
        mLocationBarList = (RecyclerView) view.findViewById(R.id.recycleViewLocationBar);
        mLocationBarList.setLayoutManager(new LinearLayoutManager(container.getContext()));

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
                .child("Bar")
                .child(current_uid)
                .limitToLast(50);
        FirebaseRecyclerOptions<LocationBarRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationBarRecycleview>()
                        .setQuery(query, LocationBarRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationBar = new FirebaseRecyclerAdapter<LocationBarRecycleview, LocationBarFragment.LocationBarViewHolder>(options) {
            @Override
            public LocationBarFragment.LocationBarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.location_bar_layout, parent, false);

                return new LocationBarFragment.LocationBarViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(LocationBarFragment.LocationBarViewHolder holder, int position, LocationBarRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationbarname(model.locationTitle);
                holder.setLocationbarphoto(model.locationSlider);


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
                holder.mView.findViewById(R.id.imageBarLocation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent(getContext(), LocationBar.class);
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

                        Intent locationintent = new Intent(getContext(), LocationBar.class);
                        locationintent.putExtra("locationame", locationame);
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
        public void setLocationbarname(String locationame){
            TextView LocationBarName = (TextView) mView.findViewById(R.id.locationBarname);
            LocationBarName.setText(locationame);
        }
        public void setLocationbarphoto(final String locationbarphoto){
            final ImageView LocationBarImage = (ImageView) mView.findViewById(R.id.imageBarLocation);
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

}