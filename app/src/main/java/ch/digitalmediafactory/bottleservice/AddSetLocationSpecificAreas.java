package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;


public class AddSetLocationSpecificAreas extends AppCompatActivity {

    private RecyclerView mSpecificList;
    private DatabaseReference mUsersDatabase, mAppOwnerApproveDatabase;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_location_specific_areas);

        Intent intent = getIntent();
        final String titlelocation = intent.getStringExtra("titlelocation");
        final String locationtype = intent.getStringExtra("locationtype");
        final String specialtitle = intent.getStringExtra("specialtitle");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("location").child(locationtype).child(current_uid).child(titlelocation).child("locationspecific");
        //mAppOwnerApproveDatabase = FirebaseDatabase.getInstance().getReference().child("location_req").child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(titlelocation);
        mAuth = FirebaseAuth.getInstance();


        mSpecificList = (RecyclerView) findViewById(R.id.recycleViewSpecialAreas);
        mSpecificList.setLayoutManager(new LinearLayoutManager(this));

        Button bContinueAreas = (Button) findViewById(R.id.bContinueAreas);

        bContinueAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddSetLocationHours.class);
                intent.putExtra("titlelocation", titlelocation);
                intent.putExtra("locationtype", locationtype);
                intent.putExtra("specialtitle", specialtitle);
                startActivity(intent);
            }
        });




    }

    public void onStart() {
        super.onStart();
        startListening();
    }


    public void startListening(){
        Intent intent = getIntent();
        String titlelocation = intent.getStringExtra("titlelocation");
        String locationtype = intent.getStringExtra("locationtype");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        Query query = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Location_Specific")
                .child(locationtype)
                .child(current_uid)
                .child(titlelocation)
                .limitToLast(50);
        FirebaseRecyclerOptions<SpecificLocationAreas> options =
                new FirebaseRecyclerOptions.Builder<SpecificLocationAreas>()
                        .setQuery(query, SpecificLocationAreas.class)
                        .build();
        FirebaseRecyclerAdapter adapterSpecific = new FirebaseRecyclerAdapter<SpecificLocationAreas, AddSetLocationSpecificAreas.SpecificViewHolder>(options) {
            @Override
            public AddSetLocationSpecificAreas.SpecificViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.location_areas_specific_special, parent, false);

                return new AddSetLocationSpecificAreas.SpecificViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(AddSetLocationSpecificAreas.SpecificViewHolder holder, int position, SpecificLocationAreas model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationspecifictitle(model.locationTitle);
                holder.setLocationspecificarea(model.specficationDescription);


                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                // ...
            }

        };
        mSpecificList.setAdapter(adapterSpecific);
        adapterSpecific.startListening();
    }

    public static class SpecificViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public SpecificViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationspecifictitle(String locationTitle){
            TextView specificTitle = (TextView) mView.findViewById(R.id.AreaTitle);
            specificTitle.setText(locationTitle);
        }
        public void setLocationspecificarea(String specficationDescription){
            TextView specificArea = (TextView) mView.findViewById(R.id.AreaSpecial);
            specificArea.setText(specficationDescription);
        }


    }



    public void buttonViewAddSpecificDetails(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddSetLocationSpecificDetails.class);
        startActivity(Intent);

    }

}
