package ch.digitalmediafactory.bottleservice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ClientBarRequest extends AppCompatActivity {


    private TextView LocationClientBar;
    DatabaseReference mUsersDatabase, mClientSpecificDatabase;
    private RecyclerView mClientBarList;
    private RecyclerView mLocationEventList;
    private DatabaseReference mEventsDatabase;
    String current_uid, locationame, locationid;
    ImageView ivClientBar, imageViewBar;
    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_bar_request);

        //PhotoView

        mClientBarList = (RecyclerView) findViewById(R.id.recycleViewClientBarSpecific);
        mClientBarList.setLayoutManager(new LinearLayoutManager(this));
        ivClientBar = (ImageView) findViewById(R.id.imageclientbar);
        locationame = getIntent().getStringExtra("locationame");
        locationid = getIntent().getStringExtra("locationid");


        //Image View Zoom



        final Dialog settingsDialog = new Dialog(ClientBarRequest.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
                , null));
        imageViewBar = (ImageView) settingsDialog.findViewById(R.id.imageView1);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(imageViewBar);
        pAttacher.update();

        ivClientBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.show();
                Window window = settingsDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });





        //Firebase

        mEventsDatabase = FirebaseDatabase.getInstance().getReference().child("OwnerToApp").child("Events");
        mEventsDatabase.keepSynced(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Location_Information").child("Bar").child(locationid).child(locationame);
        mUsersDatabase.keepSynced(true);

        mClientSpecificDatabase = FirebaseDatabase.getInstance().getReference().child("Location_Specific").child("Bar").child(locationid).child(locationame);
        mClientSpecificDatabase.keepSynced(true);

        LocationClientBar = (TextView) findViewById(R.id.tClientBarName);

        mLocationEventList = (RecyclerView) findViewById(R.id.recycleViewEvents);
        LinearLayoutManager layoutManagerEvent = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLocationEventList.setLayoutManager(layoutManagerEvent);




        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String locationame = dataSnapshot.child("locationTitle").getValue().toString();
                final String locationphoto = dataSnapshot.child("locationSlider").getValue().toString();

                LocationClientBar.setText(locationame);

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

                    Picasso.get().load(locationphoto).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.party).into(ivClientBar, new Callback() {
                        @Override
                        public void onSuccess() {


                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(locationphoto).placeholder(R.drawable.party).into(ivClientBar);

                        }
                    });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void ClientBarDashBoard(View v){
        finish();

    }


    public void onStart() {
        super.onStart();
        //startListeningEvents();
        startListeningClient();

    }

    public void startListeningClient(){
        Query query = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Location_Specific")
                .child("Bar")
                .child(locationid)
                .child(locationame)
                .limitToLast(50);
        FirebaseRecyclerOptions<SpecificLocationAreas> options =
                new FirebaseRecyclerOptions.Builder<SpecificLocationAreas>()
                        .setQuery(query, SpecificLocationAreas.class)
                        .build();
        FirebaseRecyclerAdapter adapterSpecific = new FirebaseRecyclerAdapter<SpecificLocationAreas, ClientBarRequest.SpecificViewHolder>(options) {
            @Override
            public ClientBarRequest.SpecificViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.clienteventlist, parent, false);

                return new ClientBarRequest.SpecificViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientBarRequest.SpecificViewHolder holder, int position, SpecificLocationAreas model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationspecifictitle(model.locationTitle);
                holder.setLocationspecificarea(model.specficationDescription);


                final String user_id = getRef(position).getKey();
                final String locationtitle = model.locationTitle;
                final String locationarea = model.specficationDescription;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent( ClientBarRequest.this, clientreviewpaybar.class);
                        locationintent.putExtra("locationtitle", locationtitle);
                        locationintent.putExtra("locationarea", locationarea);
                        locationintent.putExtra("locationame", locationame);
                        locationintent.putExtra("locationid", locationid);
                        startActivity(locationintent);
                    }
                });
                // ...


            }

        };
        mClientBarList.setAdapter(adapterSpecific);
        adapterSpecific.startListening();
    }

    public static class SpecificViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public SpecificViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setLocationspecifictitle(String locationTitle){
            TextView specificTitle = (TextView) mView.findViewById(R.id.EventTitle);
            specificTitle.setText(locationTitle);
        }
        public void setLocationspecificarea(String specficationDescription){
            TextView specificArea = (TextView) mView.findViewById(R.id.EventInfo);
            specificArea.setText(specficationDescription);
        }


    }


    public void startListeningEvents(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("locationclient")
                .child("Events")
                .limitToLast(50);
        FirebaseRecyclerOptions<LocationEventRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationEventRecycleview>()
                        .setQuery(query, LocationEventRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationEventRecycleview, ClientBarRequest.LocationEventViewHolder>(options) {
            @Override
            public ClientBarRequest.LocationEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_listitem, parent, false);

                return new ClientBarRequest.LocationEventViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientBarRequest.LocationEventViewHolder holder, int position, LocationEventRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventid(model.locationid);
                holder.setLocationeventphoto(model.locationphoto);



                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent(ClientBarRequest.this, ClientEvent.class);
                        locationintent.putExtra("locationame", locationame);
                        locationintent.putExtra("locationid", locationid);
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
            TextView LocationEventName = (TextView) mView.findViewById(R.id.EventName);
            LocationEventName.setText(locationame);
        }
        public void setLocationeventid(String locationid){
            TextView LocationEventId = (TextView) mView.findViewById(R.id.EventsId);
            LocationEventId.setText(locationid);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.imageEvents);
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
