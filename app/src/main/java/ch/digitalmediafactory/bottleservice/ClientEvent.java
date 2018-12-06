package ch.digitalmediafactory.bottleservice;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ClientEvent extends AppCompatActivity {
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    private static final String TAG = "ClientEvent";

    Dialog myDialog;


    private TextView LocationEventName;
    DatabaseReference mUsersDatabase, mEventDatabase;
    private RecyclerView mEventsList;
    ImageView img;
    String current_uid, locationame, locationid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_event);
        myDialog = new Dialog(this);
        img = (ImageView) findViewById(R.id.imageclient);
        ImageView img1 = (ImageView) findViewById(R.id.imagebar);
        ImageView img2 = (ImageView) findViewById(R.id.imagebar2);
        ImageView img3 = (ImageView) findViewById(R.id.imagebar3);
        ImageView img4 = (ImageView) findViewById(R.id.imagebar4);
        TextView EventObserver = (TextView) findViewById(R.id.EventObserver);

        mEventsList = (RecyclerView) findViewById(R.id.recycleViewClientEvents);
        mEventsList.setLayoutManager(new LinearLayoutManager(this));

        Glide.with(this)
                .asBitmap()
                .load("https://b.zmtcdn.com/data/menus/739/6104739/d0458b87931229821acf2de6b07103c6.jpg")
                .into(img1);


        Glide.with(this)
                .asBitmap()
                .load("https://s3.envato.com/files/72239763/03_Drink-Menu-Red-In.jpg")
                .into(img2);

        Glide.with(this)
                .asBitmap()
                .load("https://b.zmtcdn.com/data/menus/739/6104739/d0458b87931229821acf2de6b07103c6.jpg")
                .into(img3);


        Glide.with(this)
                .asBitmap()
                .load("https://s3.envato.com/files/72239763/03_Drink-Menu-Red-In.jpg")
                .into(img4);



        locationame = getIntent().getStringExtra("locationame");
        locationid = getIntent().getStringExtra("locationid");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("location").child("Events").child(locationid).child(locationame);
        mUsersDatabase.keepSynced(true);

        mEventDatabase = FirebaseDatabase.getInstance().getReference().child("location").child("Events").child(locationid).child(locationame).child("locationspecific");
        mEventDatabase.keepSynced(true);

        LocationEventName = (TextView) findViewById(R.id.tEventName);



        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String locationame = dataSnapshot.child("locationame").getValue().toString();
                final String locationphoto = dataSnapshot.child("locationphoto").getValue().toString();

                LocationEventName.setText(locationame);

                if(!locationphoto.equals("default")) {
                    Picasso.get().load(locationphoto).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.party).into(img, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(locationphoto).placeholder(R.drawable.party).into(img);

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        startListeningEvent();

    }

    public void startListeningEvent(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("location")
                .child("Events")
                .child(locationid)
                .child(locationame)
                .child("locationspecific")
                .limitToLast(50);
        FirebaseRecyclerOptions<SpecificLocationAreas> options =
                new FirebaseRecyclerOptions.Builder<SpecificLocationAreas>()
                        .setQuery(query, SpecificLocationAreas.class)
                        .build();
        FirebaseRecyclerAdapter adapterSpecific = new FirebaseRecyclerAdapter<SpecificLocationAreas, ClientEvent.SpecificViewHolder>(options) {
            @Override
            public ClientEvent.SpecificViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.clienteventlist, parent, false);

                return new ClientEvent.SpecificViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientEvent.SpecificViewHolder holder, int position, SpecificLocationAreas model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationspecifictitle(model.locationTitle);
                holder.setLocationspecificarea(model.specficationDescription);


                final String user_id = getRef(position).getKey();
                final String locationtitle = model.locationTitle;
                final String locationarea = model.specficationDescription;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationintent = new Intent(ClientEvent.this, clientreviewpay.class);
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
        mEventsList.setAdapter(adapterSpecific);
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
        public void setLocationspecificarea(String locationspecificarea){
            TextView specificArea = (TextView) mView.findViewById(R.id.EventInfo);
            specificArea.setText(locationspecificarea);
        }


    }




    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.popupcustom);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    public void ShowPopupMenu1(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.popupmenu1);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void buttonViewDashboard(View v){

        this.finish();
    }



    public void buttonViewClientViewPay(View v)
    {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}
