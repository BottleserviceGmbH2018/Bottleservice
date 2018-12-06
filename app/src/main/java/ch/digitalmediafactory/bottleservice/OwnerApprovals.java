package ch.digitalmediafactory.bottleservice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
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

import java.util.ArrayList;
import java.util.HashMap;

public class OwnerApprovals extends AppCompatActivity {


    private DatabaseReference mLocationReqDatabase;
    private FirebaseUser mCurrentUser;


    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseClientAppOwner;
    private DatabaseReference mDatabaseAppOwner;


    DatabaseReference mUsersDatabase, mUsersDatabaseSpecific, mUsersDatabaseSpecificClient;
    DatabaseReference mAppOwnerApproveDatabase;
    private RecyclerView mAppOwnerApproveList;

    private RecyclerView mAppOwnerApproveSpecific;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_approvals);

        mLocationReqDatabase = FirebaseDatabase.getInstance().getReference().child("location_req");

        mAppOwnerApproveList = (RecyclerView) findViewById(R.id.recycleViewAppOwnerApprove);
        mAppOwnerApproveList.setLayoutManager(new LinearLayoutManager(this));


        mAppOwnerApproveSpecific = (RecyclerView) findViewById(R.id.recycleViewAppOwnerApproveSpecific);
        mAppOwnerApproveSpecific.setLayoutManager(new LinearLayoutManager(this));

        mAppOwnerApproveDatabase = FirebaseDatabase.getInstance().getReference().child("location_req").child("gYMAHaVkJRhidNaIvcN8atFtawi2");
        mAppOwnerApproveDatabase.keepSynced(true);


    }


    public void onStart() {
        super.onStart();
        startListeningAppOwnerApproval();


    }
    public void startListeningAppOwnerApproval() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("location_req")
                .child("gYMAHaVkJRhidNaIvcN8atFtawi2")
                .limitToLast(50);
        FirebaseRecyclerOptions<OwnerApprovalsRecycleview> options =
                new FirebaseRecyclerOptions.Builder<OwnerApprovalsRecycleview>()
                        .setQuery(query, OwnerApprovalsRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterOwnerApprovals = new FirebaseRecyclerAdapter<OwnerApprovalsRecycleview, OwnerApprovals.AppOwnerApprovalViewHolder>(options) {
            @Override
            public OwnerApprovals.AppOwnerApprovalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitemappownerapproval, parent, false);



//                ArrayAdapter<CharSequence> adapter;
//                final Spinner spinner;
//                spinner = view.findViewById(R.id.spinner);
//                adapter = ArrayAdapter.createFromResource(OwnerApprovals.this, R.array.AppOwnerApprovals, android.R.layout.simple_spinner_item);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(adapter);
//                spinner.setSelection(0, true);
//                View v = spinner.getSelectedView();
//                ((TextView)v).setTextColor(Color.WHITE);
//
//
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                        parent.getItemAtPosition(position);
//
//                        Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position) +" selected",Toast.LENGTH_SHORT).show();
//                        ((TextView) view).setTextColor(Color.WHITE);
//
//                        if (position == 1) {
//                            mDatabase = FirebaseDatabase.getInstance().getReference().child("location").child(locationtype).child(locationid).child(locationame);
//                            HashMap<String, String> userMap = new HashMap<>();
//                            userMap.put("locationid", locationid);
//                            userMap.put("locationame", locationame);
//                            userMap.put("locationmap", "default");
//                            userMap.put("locationphoto", locationphoto);
//                            userMap.put("locationtype", locationtype);
//                            mDatabase.setValue(userMap);
//
//                            mDatabaseClientAppOwner = FirebaseDatabase.getInstance().getReference().child("locationclient").child(locationtype).child(locationame);
//                            HashMap<String, String> userMapClientAppOwner = new HashMap<>();
//                            userMapClientAppOwner.put("locationid", locationid);
//                            userMapClientAppOwner.put("locationame", locationame);
//                            userMapClientAppOwner.put("locationmap", "default");
//                            userMapClientAppOwner.put("locationphoto", locationphoto);
//                            userMapClientAppOwner.put("locationtype", locationtype);
//                            mDatabaseClientAppOwner.setValue(userMapClientAppOwner);
//
//
//
//                            mLocationReqDatabase.child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(locationame).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (task.isSuccessful()){
//
//
//                                    }
//
//                                }
//                            });
//
//                            spinner.setSelection(0);
//
//
//                        }if (position == 2) {
//
//                            mLocationReqDatabase.child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(locationame).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (task.isSuccessful()){
//
//
//                                    }
//
//                                }
//                            });
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });




                return new OwnerApprovals.AppOwnerApprovalViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(AppOwnerApprovalViewHolder holder, int position, OwnerApprovalsRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setAppOwnerLocationname(model.locationame);
                holder.setAppOwnerLocationid(model.locationid);
                holder.setAppOwnerLocationtype(model.locationtype);
                holder.setAppOwnerLocationphoto(model.locationphoto);


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                final String locationphoto = model.locationphoto;
                final String locationtype = model.locationtype;



                final String[] items = {
                        "Approve",
                        "Release",};

                final AlertDialog.Builder builder = new AlertDialog.Builder(OwnerApprovals.this);
                builder.setTitle("Options");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item) {
                            case 0:{

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("location").child(locationtype).child(locationid).child(locationame);
                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("locationid", locationid);
                                userMap.put("locationame", locationame);
                                userMap.put("locationmap", "default");
                                userMap.put("locationphoto", locationphoto);
                                userMap.put("locationtype", locationtype);
                                mDatabase.setValue(userMap);

                                mDatabaseClientAppOwner = FirebaseDatabase.getInstance().getReference().child("locationclient").child(locationtype).child(locationame);
                                HashMap<String, String> userMapClientAppOwner = new HashMap<>();
                                userMapClientAppOwner.put("locationid", locationid);
                                userMapClientAppOwner.put("locationame", locationame);
                                userMapClientAppOwner.put("locationmap", "default");
                                userMapClientAppOwner.put("locationphoto", locationphoto);
                                userMapClientAppOwner.put("locationtype", locationtype);
                                mDatabaseClientAppOwner.setValue(userMapClientAppOwner);



                            mLocationReqDatabase.child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(locationame).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        builder.create();

                                    }

                                }
                            });
                                break;}
                            case 1:{

                                mLocationReqDatabase.child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(locationame).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){

                                            builder.create();

                                        }

                                    }
                                });

                                break;}

                        }

                    }
                });



                holder.mView.findViewById(R.id.spinner).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        builder.create();
                        builder.show();

                    }
                });
                // ...
            }

        };
        mAppOwnerApproveList.setAdapter(adapterOwnerApprovals);
        adapterOwnerApprovals.startListening();
    }

    public static class AppOwnerApprovalViewHolder extends RecyclerView.ViewHolder {
        View mView;


        public AppOwnerApprovalViewHolder(View itemView) {
            super(itemView);

            mView = itemView;




        }

        public void setAppOwnerLocationname(String locationame) {
            TextView LocationEventName = (TextView) mView.findViewById(R.id.tLoungeOwnerName);
            LocationEventName.setText(locationame);
        }

        public void setAppOwnerLocationid(String locationid) {
            TextView LocationEventName = (TextView) mView.findViewById(R.id.tAppOwnerId);
            LocationEventName.setText(locationid);
        }

        public void setAppOwnerLocationtype(String locationtype) {
            TextView LocationEventName = (TextView) mView.findViewById(R.id.tAppOwnerType);
            LocationEventName.setText(locationtype);
        }

        public void setAppOwnerLocationphoto(final String locationphoto) {
            final ImageView LocationAppOwnerImage = (ImageView) mView.findViewById(R.id.ivAppOwnerPhoto);
            Picasso.get().load(locationphoto).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.party).into(LocationAppOwnerImage, new Callback() {
                @Override
               public void onSuccess() {

              }
                @Override
               public void onError(Exception e) {
                   Picasso.get().load(locationphoto).placeholder(R.drawable.party).into(LocationAppOwnerImage);

               }
           });

       }

    }




        public void buttonDashboardBack(View v) {
            Intent Intent = new Intent(getApplicationContext(), AppOwnerDashboard.class);
            startActivity(Intent);
            this.finish();
        }
}
