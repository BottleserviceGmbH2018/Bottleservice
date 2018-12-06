package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ClientLocationAddress extends AppCompatActivity {

    private RecyclerView mLocationBarList;
    private DatabaseReference mLocationBarDatabase;

    private RecyclerView mLocationLoungeList;
    private DatabaseReference mLocationLoungeDatabase;

    private RecyclerView mLocationEventsList;
    private DatabaseReference mLocationEventsDatabase;


    private EditText messageSearch;

    private Calendar currentTime;
    private int hour, minute;
    private String format;

    private TextView locationsearch;
    private TextView locationplace;
    private TextView locationevents;
    private TextView locationlounges;
    private TextView locationbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_location_address);

        mLocationBarDatabase = FirebaseDatabase.getInstance().getReference().child("locationclient").child("Bar");
        mLocationBarDatabase.keepSynced(true);


        mLocationBarList = (RecyclerView) findViewById(R.id.recycleViewLocationSearchBar);
        LinearLayoutManager layoutManagerBar = new LinearLayoutManager(ClientLocationAddress.this, LinearLayoutManager.VERTICAL, false);
        mLocationBarList.setLayoutManager(layoutManagerBar);


        mLocationLoungeDatabase = FirebaseDatabase.getInstance().getReference().child("locationclient").child("Lounge");
        mLocationLoungeDatabase.keepSynced(true);


        mLocationLoungeList = (RecyclerView) findViewById(R.id.recycleViewLocationSearchLounge);
        LinearLayoutManager layoutManagerLounge = new LinearLayoutManager(ClientLocationAddress.this, LinearLayoutManager.VERTICAL, false);
        mLocationLoungeList.setLayoutManager(layoutManagerLounge);


        mLocationEventsDatabase = FirebaseDatabase.getInstance().getReference().child("locationclient").child("Events");
        mLocationEventsDatabase.keepSynced(true);


        mLocationEventsList = (RecyclerView) findViewById(R.id.recycleViewLocationSearchEvent);
        LinearLayoutManager layoutManagerEvents = new LinearLayoutManager(ClientLocationAddress.this, LinearLayoutManager.VERTICAL, false);
        mLocationEventsList.setLayoutManager(layoutManagerEvents);


        messageSearch = (EditText) findViewById(R.id.editText);
        messageSearch.addTextChangedListener(searchTextWatcher);

         locationsearch = (TextView) findViewById(R.id.locationsearch);
         locationplace = (TextView) findViewById(R.id.locationplace);
         locationevents = (TextView) findViewById(R.id.locationevents);
         locationlounges = (TextView) findViewById(R.id.locationlounges);
         locationbar = (TextView) findViewById(R.id.locationbar);
    }

    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            startListeningLocationBar();
            startListeningLocationEvents();
            startListeningLocationLounge();

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchText = messageSearch.getText().toString().trim();
            startListeningSearchBar(searchText);
            startListeningSearchLounge(searchText);
            startListeningSearchEvent(searchText);
            locationsearch.setVisibility(View.GONE);
            locationplace.setVisibility(View.GONE);
            locationevents.setVisibility(View.GONE);
            locationlounges.setVisibility(View.GONE);
            locationbar.setVisibility(View.GONE);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void startListeningSearchLounge(String searchText) {
        Query firebaseSearchQueryLounge = mLocationLoungeDatabase.orderByChild("locationame").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerOptions<LocationSearchRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationSearchRecycleview>()
                        .setQuery(firebaseSearchQueryLounge, LocationSearchRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationSearchRecycleview, ClientLocationAddress.LocationLoungeSearchViewHolder>(options) {
            @Override
            public ClientLocationAddress.LocationLoungeSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_search_layout, parent, false);



                return new ClientLocationAddress.LocationLoungeSearchViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientLocationAddress.LocationLoungeSearchViewHolder holder, int position, LocationSearchRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventid(model.locationid);
                holder.setLocationeventphoto(model.locationphoto);
                holder.setLocationeventtime(model.locationtime);
                holder.setLocationtype(model.locationtype);

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
//                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

                int locationtime = model.locationtime;
                int locationopen = model.locationtime;
                currentTime = Calendar.getInstance();
                hour = currentTime.get(Calendar.HOUR_OF_DAY);
                minute = currentTime.get(Calendar.MINUTE);

//                Calendar cal = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH");
//                try {
//                    cal.setTime(sdf.parse(Integer.toString(locationtime)));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.add(Calendar.HOUR, -1);
//                int hours = cal.get(cal.HOUR_OF_DAY);


                if(locationopen == 0){
                    locationopen += 12;
                    format = "AM";
                }else if(locationopen == 12){
                    format = "PM";
                }else if(locationopen > 12){
                    locationopen -= 12;
                    format = "PM";
                }else {
                    format = "AM";
                }
                String time = locationopen + ":00" + " " + format;
                eventTime.setText("" + time);

                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
                }else {
//                    lastMinute.setVisibility(View.GONE);
                }


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                final String locationtype = model.locationtype;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(locationtype == "Events") {
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientEvent.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                        else if(locationtype == "Lounge"){
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientLounge.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }else{
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientBar.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                    }
                });
                // ...
            }

        };
        mLocationLoungeList.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class LocationLoungeSearchViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationLoungeSearchViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationEventName = (TextView) mView.findViewById(R.id.placeName);
            LocationEventName.setText(locationame);
        }
        public void setLocationeventid(String locationid){
            TextView LocationEventId = (TextView) mView.findViewById(R.id.placeId);
            LocationEventId.setText(locationid);
        }
        public void setLocationeventtime(int locationtime){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.text3);
            LocationEventTime.setText(String.valueOf(locationtime));
        }
        public void setLocationtype(String locationtype){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.placeType);
            LocationEventTime.setText(locationtype);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.location_image);
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


    public void startListeningSearchEvent(String searchText) {
        Query firebaseSearchQueryEvents = mLocationEventsDatabase.orderByChild("locationame").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerOptions<LocationSearchRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationSearchRecycleview>()
                        .setQuery(firebaseSearchQueryEvents, LocationSearchRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationSearchRecycleview, ClientLocationAddress.LocationEventSearchViewHolder>(options) {
            @Override
            public ClientLocationAddress.LocationEventSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_search_layout, parent, false);



                return new ClientLocationAddress.LocationEventSearchViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientLocationAddress.LocationEventSearchViewHolder holder, int position, LocationSearchRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventid(model.locationid);
                holder.setLocationeventphoto(model.locationphoto);
                holder.setLocationeventtime(model.locationtime);
                holder.setLocationtype(model.locationtype);

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
//                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

                int locationtime = model.locationtime;
                int locationopen = model.locationtime;
                currentTime = Calendar.getInstance();
                hour = currentTime.get(Calendar.HOUR_OF_DAY);
                minute = currentTime.get(Calendar.MINUTE);

//                Calendar cal = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH");
//                try {
//                    cal.setTime(sdf.parse(Integer.toString(locationtime)));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.add(Calendar.HOUR, -1);
//                int hours = cal.get(cal.HOUR_OF_DAY);


                if(locationopen == 0){
                    locationopen += 12;
                    format = "AM";
                }else if(locationopen == 12){
                    format = "PM";
                }else if(locationopen > 12){
                    locationopen -= 12;
                    format = "PM";
                }else {
                    format = "AM";
                }
                String time = locationopen + ":00" + " " + format;
                eventTime.setText("" + time);

                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
                }else {
//                    lastMinute.setVisibility(View.GONE);
                }


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                final String locationtype = model.locationtype;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(locationtype == "Events") {
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientEvent.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                        else if(locationtype == "Lounge"){
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientLounge.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }else{
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientBar.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                    }
                });
                // ...
            }

        };
        mLocationEventsList.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class LocationEventSearchViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationEventSearchViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationEventName = (TextView) mView.findViewById(R.id.placeName);
            LocationEventName.setText(locationame);
        }
        public void setLocationeventid(String locationid){
            TextView LocationEventId = (TextView) mView.findViewById(R.id.placeId);
            LocationEventId.setText(locationid);
        }
        public void setLocationeventtime(int locationtime){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.text3);
            LocationEventTime.setText(String.valueOf(locationtime));
        }
        public void setLocationtype(String locationtype){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.placeType);
            LocationEventTime.setText(locationtype);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.location_image);
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


    public void startListeningSearchBar(String searchText) {
        Query firebaseSearchQueryBar = mLocationBarDatabase.orderByChild("locationame").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerOptions<LocationSearchRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationSearchRecycleview>()
                        .setQuery(firebaseSearchQueryBar, LocationSearchRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationSearchRecycleview, ClientLocationAddress.LocationBarSearchViewHolder>(options) {
            @Override
            public ClientLocationAddress.LocationBarSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_search_layout, parent, false);



                return new ClientLocationAddress.LocationBarSearchViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientLocationAddress.LocationBarSearchViewHolder holder, int position, LocationSearchRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventid(model.locationid);
                holder.setLocationeventphoto(model.locationphoto);
                holder.setLocationeventtime(model.locationtime);
                holder.setLocationtype(model.locationtype);

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
//                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

                int locationtime = model.locationtime;
                int locationopen = model.locationtime;
                currentTime = Calendar.getInstance();
                hour = currentTime.get(Calendar.HOUR_OF_DAY);
                minute = currentTime.get(Calendar.MINUTE);

//                Calendar cal = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH");
//                try {
//                    cal.setTime(sdf.parse(Integer.toString(locationtime)));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.add(Calendar.HOUR, -1);
//                int hours = cal.get(cal.HOUR_OF_DAY);


                if(locationopen == 0){
                    locationopen += 12;
                    format = "AM";
                }else if(locationopen == 12){
                    format = "PM";
                }else if(locationopen > 12){
                    locationopen -= 12;
                    format = "PM";
                }else {
                    format = "AM";
                }
                String time = locationopen + ":00" + " " + format;
                eventTime.setText("" + time);

                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
                }else {
//                    lastMinute.setVisibility(View.GONE);
                }


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                final String locationtype = model.locationtype;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(locationtype == "Events") {
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientEvent.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                        else if(locationtype == "Lounge"){
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientLounge.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }else{
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientBar.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                    }
                });
                // ...
            }

        };
        mLocationBarList.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class LocationBarSearchViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationBarSearchViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationEventName = (TextView) mView.findViewById(R.id.placeName);
            LocationEventName.setText(locationame);
        }
        public void setLocationeventid(String locationid){
            TextView LocationEventId = (TextView) mView.findViewById(R.id.placeId);
            LocationEventId.setText(locationid);
        }
        public void setLocationeventtime(int locationtime){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.text3);
            LocationEventTime.setText(String.valueOf(locationtime));
        }
        public void setLocationtype(String locationtype){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.placeType);
            LocationEventTime.setText(locationtype);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.location_image);
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


    @Override
    public void onResume() {
        super.onResume();
        startListeningLocationBar();
        startListeningLocationEvents();
        startListeningLocationLounge();

    }


    public void startListeningLocationSearch(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("locationclient")
                .child("Bar")
                .limitToLast(5);
        FirebaseRecyclerOptions<LocationSearchRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationSearchRecycleview>()
                        .setQuery(query, LocationSearchRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationSearchRecycleview, ClientLocationAddress.LocationSearchViewHolder>(options) {
            @Override
            public ClientLocationAddress.LocationSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_search_layout, parent, false);



                return new ClientLocationAddress.LocationSearchViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientLocationAddress.LocationSearchViewHolder holder, int position, LocationSearchRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventid(model.locationid);
                holder.setLocationeventphoto(model.locationphoto);
                holder.setLocationeventtime(model.locationtime);
                holder.setLocationtype(model.locationtype);

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
//                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

                int locationtime = model.locationtime;
                int locationopen = model.locationtime;
                currentTime = Calendar.getInstance();
                hour = currentTime.get(Calendar.HOUR_OF_DAY);
                minute = currentTime.get(Calendar.MINUTE);

//                Calendar cal = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH");
//                try {
//                    cal.setTime(sdf.parse(Integer.toString(locationtime)));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.add(Calendar.HOUR, -1);
//                int hours = cal.get(cal.HOUR_OF_DAY);


                if(locationopen == 0){
                    locationopen += 12;
                    format = "AM";
                }else if(locationopen == 12){
                    format = "PM";
                }else if(locationopen > 12){
                    locationopen -= 12;
                    format = "PM";
                }else {
                    format = "AM";
                }
                String time = locationopen + ":00" + " " + format;
                eventTime.setText("" + time);

                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
                }else {
//                    lastMinute.setVisibility(View.GONE);
                }


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                final String locationtype = model.locationtype;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(locationtype == "Events") {
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientEvent.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                        else if(locationtype == "Lounge"){
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientLounge.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }else{
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientBar.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                    }
                });
                // ...
            }

        };
        mLocationBarList.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class LocationSearchViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationSearchViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationEventName = (TextView) mView.findViewById(R.id.placeName);
            LocationEventName.setText(locationame);
        }
        public void setLocationeventid(String locationid){
            TextView LocationEventId = (TextView) mView.findViewById(R.id.placeId);
            LocationEventId.setText(locationid);
        }
        public void setLocationeventtime(int locationtime){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.text3);
            LocationEventTime.setText(String.valueOf(locationtime));
        }
        public void setLocationtype(String locationtype){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.placeType);
            LocationEventTime.setText(locationtype);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.location_image);
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


    public void startListeningLocationBar(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("locationclient")
                .child("Bar")
                .limitToFirst(5);
        FirebaseRecyclerOptions<LocationSearchRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationSearchRecycleview>()
                        .setQuery(query, LocationSearchRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationSearchRecycleview, ClientLocationAddress.LocationBarViewHolder>(options) {
            @Override
            public ClientLocationAddress.LocationBarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_search_layout, parent, false);



                return new ClientLocationAddress.LocationBarViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientLocationAddress.LocationBarViewHolder holder, int position, LocationSearchRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventid(model.locationid);
                holder.setLocationeventphoto(model.locationphoto);
                holder.setLocationeventtime(model.locationtime);
                holder.setLocationtype(model.locationtype);

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
//                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

                int locationtime = model.locationtime;
                int locationopen = model.locationtime;
                currentTime = Calendar.getInstance();
                hour = currentTime.get(Calendar.HOUR_OF_DAY);
                minute = currentTime.get(Calendar.MINUTE);

//                Calendar cal = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH");
//                try {
//                    cal.setTime(sdf.parse(Integer.toString(locationtime)));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.add(Calendar.HOUR, -1);
//                int hours = cal.get(cal.HOUR_OF_DAY);


                if(locationopen == 0){
                    locationopen += 12;
                    format = "AM";
                }else if(locationopen == 12){
                    format = "PM";
                }else if(locationopen > 12){
                    locationopen -= 12;
                    format = "PM";
                }else {
                    format = "AM";
                }
                String time = locationopen + ":00" + " " + format;
                eventTime.setText("" + time);

                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
                }else {
//                    lastMinute.setVisibility(View.GONE);
                }


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                final String locationtype = model.locationtype;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(locationtype == "Events") {
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientEvent.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                        else if(locationtype == "Lounge"){
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientLounge.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }else{
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientBar.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                    }
                });
                // ...
            }

        };
        mLocationBarList.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class LocationBarViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationBarViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationEventName = (TextView) mView.findViewById(R.id.placeName);
            LocationEventName.setText(locationame);
        }
        public void setLocationeventid(String locationid){
            TextView LocationEventId = (TextView) mView.findViewById(R.id.placeId);
            LocationEventId.setText(locationid);
        }
        public void setLocationeventtime(int locationtime){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.text3);
            LocationEventTime.setText(String.valueOf(locationtime));
        }
        public void setLocationtype(String locationtype){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.placeType);
            LocationEventTime.setText(locationtype);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.location_image);
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


    public void startListeningLocationLounge(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("locationclient")
                .child("Lounge")
                .limitToFirst(5);
        FirebaseRecyclerOptions<LocationSearchRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationSearchRecycleview>()
                        .setQuery(query, LocationSearchRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationSearchRecycleview, ClientLocationAddress.LocationLoungeViewHolder>(options) {
            @Override
            public ClientLocationAddress.LocationLoungeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_search_layout, parent, false);



                return new ClientLocationAddress.LocationLoungeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientLocationAddress.LocationLoungeViewHolder holder, int position, LocationSearchRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventid(model.locationid);
                holder.setLocationeventphoto(model.locationphoto);
                holder.setLocationeventtime(model.locationtime);
                holder.setLocationtype(model.locationtype);

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
//                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

                int locationtime = model.locationtime;
                int locationopen = model.locationtime;
                currentTime = Calendar.getInstance();
                hour = currentTime.get(Calendar.HOUR_OF_DAY);
                minute = currentTime.get(Calendar.MINUTE);

//                Calendar cal = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH");
//                try {
//                    cal.setTime(sdf.parse(Integer.toString(locationtime)));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.add(Calendar.HOUR, -1);
//                int hours = cal.get(cal.HOUR_OF_DAY);


                if(locationopen == 0){
                    locationopen += 12;
                    format = "AM";
                }else if(locationopen == 12){
                    format = "PM";
                }else if(locationopen > 12){
                    locationopen -= 12;
                    format = "PM";
                }else {
                    format = "AM";
                }
                String time = locationopen + ":00" + " " + format;
                eventTime.setText("" + time);

                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
                }else {
//                    lastMinute.setVisibility(View.GONE);
                }


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                final String locationtype = model.locationtype;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(locationtype == "Events") {
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientEvent.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                        else if(locationtype == "Lounge"){
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientLounge.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }else{
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientBar.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                    }
                });
                // ...
            }

        };
        mLocationLoungeList.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class LocationLoungeViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationLoungeViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationEventName = (TextView) mView.findViewById(R.id.placeName);
            LocationEventName.setText(locationame);
        }
        public void setLocationeventid(String locationid){
            TextView LocationEventId = (TextView) mView.findViewById(R.id.placeId);
            LocationEventId.setText(locationid);
        }
        public void setLocationeventtime(int locationtime){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.text3);
            LocationEventTime.setText(String.valueOf(locationtime));
        }
        public void setLocationtype(String locationtype){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.placeType);
            LocationEventTime.setText(locationtype);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.location_image);
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


    public void startListeningLocationEvents(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("locationclient")
                .child("Events")
                .limitToFirst(5);
        FirebaseRecyclerOptions<LocationSearchRecycleview> options =
                new FirebaseRecyclerOptions.Builder<LocationSearchRecycleview>()
                        .setQuery(query, LocationSearchRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationSearchRecycleview, ClientLocationAddress.LocationEventViewHolder>(options) {
            @Override
            public ClientLocationAddress.LocationEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_search_layout, parent, false);



                return new ClientLocationAddress.LocationEventViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ClientLocationAddress.LocationEventViewHolder holder, int position, LocationSearchRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventid(model.locationid);
                holder.setLocationeventphoto(model.locationphoto);
                holder.setLocationeventtime(model.locationtime);
                holder.setLocationtype(model.locationtype);

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
//                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

                int locationtime = model.locationtime;
                int locationopen = model.locationtime;
                currentTime = Calendar.getInstance();
                hour = currentTime.get(Calendar.HOUR_OF_DAY);
                minute = currentTime.get(Calendar.MINUTE);

//                Calendar cal = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH");
//                try {
//                    cal.setTime(sdf.parse(Integer.toString(locationtime)));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.add(Calendar.HOUR, -1);
//                int hours = cal.get(cal.HOUR_OF_DAY);


                if(locationopen == 0){
                    locationopen += 12;
                    format = "AM";
                }else if(locationopen == 12){
                    format = "PM";
                }else if(locationopen > 12){
                    locationopen -= 12;
                    format = "PM";
                }else {
                    format = "AM";
                }
                String time = locationopen + ":00" + " " + format;
                eventTime.setText("" + time);

                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
                }else {
//                    lastMinute.setVisibility(View.GONE);
                }


                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                final String locationtype = model.locationtype;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(locationtype == "Events") {
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientEvent.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                        else if(locationtype == "Lounge"){
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientLounge.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }else{
                            Intent locationintent = new Intent(ClientLocationAddress.this, ClientBar.class);
                            locationintent.putExtra("locationame", locationame);
                            locationintent.putExtra("locationid", locationid);
                            startActivity(locationintent);
                        }
                    }
                });
                // ...
            }

        };
        mLocationEventsList.setAdapter(adapterLocationEvent);
        adapterLocationEvent.startListening();
    }

    public static class LocationEventViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationEventViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationEventName = (TextView) mView.findViewById(R.id.placeName);
            LocationEventName.setText(locationame);
        }
        public void setLocationeventid(String locationid){
            TextView LocationEventId = (TextView) mView.findViewById(R.id.placeId);
            LocationEventId.setText(locationid);
        }
        public void setLocationeventtime(int locationtime){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.text3);
            LocationEventTime.setText(String.valueOf(locationtime));
        }
        public void setLocationtype(String locationtype){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.placeType);
            LocationEventTime.setText(locationtype);
        }
        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.location_image);
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
