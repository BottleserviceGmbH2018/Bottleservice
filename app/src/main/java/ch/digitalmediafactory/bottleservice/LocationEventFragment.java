package ch.digitalmediafactory.bottleservice;

/**
 * Created by Chris Gacu on 21/02/2018.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Chris Gacu on 21/02/2018.
 */

public class LocationEventFragment extends Fragment {
    private static final String TAG = "LocationEventFragment";


    private RecyclerView mLocationEventList;
    private FirebaseUser mCurrentUser;
    DatabaseReference mUsersDatabase;


    public LocationEventFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("location").child("Events").child(current_uid);
        mUsersDatabase.keepSynced(true);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_event_fragment,container,false);
        mLocationEventList = (RecyclerView) view.findViewById(R.id.recycleViewLocationEvents);
        mLocationEventList.setLayoutManager(new LinearLayoutManager(container.getContext()));
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
                .child("location")
                .child("Events")
                .child(current_uid)
                .limitToLast(50);
        FirebaseRecyclerOptions<LocationEventRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationEventRecycleview>()
                        .setQuery(query, LocationEventRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationEventRecycleview, LocationEventFragment.LocationEventViewHolder>(options) {
            @Override
            public LocationEventFragment.LocationEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.location_event_layout, parent, false);

                return new LocationEventFragment.LocationEventViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(LocationEventFragment.LocationEventViewHolder holder, int position, LocationEventRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventphoto(model.locationphoto);


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;


                holder.mView.findViewById(R.id.imageEventLocation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent(getContext(), LocationEvent.class);
                        locationintent.putExtra("locationame", locationame);
                        startActivity(locationintent);

                    }
                });

                // ...
            }

        };
        mLocationEventList.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class LocationEventViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationEventViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationBarName = (TextView) mView.findViewById(R.id.locationEventname);
            LocationBarName.setText(locationame);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.imageEventLocation);
            Picasso.get().load(locationeventphoto).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.party).into(LocationEventImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(locationeventphoto).placeholder(R.drawable.party).into(LocationEventImage);

                }
            });

        }



    }
}
