package ch.digitalmediafactory.bottleservice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragmenthome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragmenthome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmenthome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "fragmenthome";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;


    private FirebaseUser mCurrentUser;

    private RecyclerView mLocationBarList;
    private DatabaseReference mBarDatabase;


    private RecyclerView mLocationEventList;
    private DatabaseReference mEventsDatabase;

    private RecyclerView mLocationLoungeList;
    private DatabaseReference mLoungeDatabase;


    private FirebaseRecyclerAdapter adapterLocationBar, adapterLocationLounge;


    Calendar currentTime;
    int hour, minute;
    String format;
    TextView availLounges, availEvents, availBars;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragmenthome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmenthome.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmenthome newInstance(String param1, String param2) {
        fragmenthome fragment = new fragmenthome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mBarDatabase = FirebaseDatabase.getInstance().getReference().child("locationclient").child("Bar");
        mBarDatabase.keepSynced(true);

        mEventsDatabase = FirebaseDatabase.getInstance().getReference().child("locationclient").child("Events");
        mEventsDatabase.keepSynced(true);

        mLoungeDatabase = FirebaseDatabase.getInstance().getReference().child("locationclient").child("Lounge");
        mLoungeDatabase.keepSynced(true);









    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragmenthome, container, false);



        Button bars = (Button) view.findViewById(R.id.bBars);
        TextView textbar = (TextView) view.findViewById(R.id.BarSeeAll);
        Button lounge = (Button) view.findViewById(R.id.bLounge);
        TextView textlounge = (TextView) view.findViewById(R.id.tSeeAllLounge);
        availLounges = view.findViewById(R.id.availLounges);
        availBars = view.findViewById(R.id.availBars);
        availEvents = view.findViewById(R.id.availEvents);


        textlounge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClientLounge.class);
                startActivity(intent);
            }
        });

        lounge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClientLounge.class);
                startActivity(intent);
            }
        });

        textbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClientBar.class);
                startActivity(intent);
            }
        });

        bars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClientBar.class);
                startActivity(intent);
            }
        });


        mLocationBarList = (RecyclerView) view.findViewById(R.id.recycleViewBars);
        LinearLayoutManager layoutManagerBar = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLocationBarList.setLayoutManager(layoutManagerBar);

       mLocationEventList = (RecyclerView) view.findViewById(R.id.recycleViewEvents);
       LinearLayoutManager layoutManagerEvent = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
       mLocationEventList.setLayoutManager(layoutManagerEvent);

        mLocationLoungeList = (RecyclerView) view.findViewById(R.id.recycleViewLounge);
        LinearLayoutManager layoutManagerLounge = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLocationLoungeList.setLayoutManager(layoutManagerLounge);


        return view;




    }



    @Override
    public void onStart() {
        super.onStart();
        startListeningBar();
        startListeningEvents();
        startListeningLounge();

    }



    public void startListeningEvents(){
                Query query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("locationclient")
                        .child("Events")
                        .limitToLast(100);
                FirebaseRecyclerOptions<LocationEventRecycleview> options =
                        new FirebaseRecyclerOptions.Builder<LocationEventRecycleview>()
                                .setQuery(query, LocationEventRecycleview.class)
                                .build();
                FirebaseRecyclerAdapter adapterLocationEvent = new FirebaseRecyclerAdapter<LocationEventRecycleview, fragmenthome.LocationEventViewHolder>(options) {
                    @Override
                    public fragmenthome.LocationEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        // Create a new instance of the ViewHolder, in this case we are using a custom
                        // layout called R.layout.message for each item
                        final View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_listitem, parent, false);



                        return new fragmenthome.LocationEventViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(fragmenthome.LocationEventViewHolder holder, int position, LocationEventRecycleview model) {
                        // Bind the Chat object to the ChatHolder
                        holder.setLocationeventname(model.locationame);
                        holder.setLocationeventid(model.locationid);
                        holder.setLocationeventphoto(model.locationphoto);
                        holder.setLocationeventtime(model.locationtime);


                        final int eventitem = adapterLocationBar.getItemCount();
                        if (eventitem<=0){
                            availEvents.setVisibility(View.VISIBLE);
                        }else{
                            availEvents.setVisibility(View.GONE);
                        }

                        final TextView eventTime = holder.mView.findViewById(R.id.text3);
                        final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

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
                            lastMinute.setVisibility(View.GONE);
                        }else if(hour > locationtime) {
                            lastMinute.setVisibility(View.GONE);
                        }else if (hour < locationtime){
                            lastMinute.setVisibility(View.VISIBLE);
                        }else {
                            lastMinute.setVisibility(View.GONE);
                        }


                        final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                final String locationid = model.locationid;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent locationintent = new Intent(getContext(), ClientEvent.class);
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
        public void setLocationeventtime(int locationtime){
            TextView LocationEventTime = (TextView) mView.findViewById(R.id.EventTime);
            LocationEventTime.setText(String.valueOf(locationtime));
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
        adapterLocationBar = new FirebaseRecyclerAdapter<LocationBarRecycleview, fragmenthome.LocationBarViewHolder>(options) {
            @Override
            public fragmenthome.LocationBarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitembar, parent, false);

                return new fragmenthome.LocationBarViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(fragmenthome.LocationBarViewHolder holder, int position, LocationBarRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationbarname(model.locationTitle);
                holder.setLocationbarphoto(model.locationSlider);
                holder.setLocationbarid(model.ownerUserID);
//                holder.setLocationbartime(model.locationtime);


                final int baritem = adapterLocationBar.getItemCount();
                if (baritem<=0){
                    availBars.setVisibility(View.VISIBLE);
                }else{
                    availBars.setVisibility(View.GONE);
                }

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

//                int locationtime = model.locationtime;
//                int locationopen = model.locationtime;
//                currentTime = Calendar.getInstance();
//                hour = currentTime.get(Calendar.HOUR_OF_DAY);
//                minute = currentTime.get(Calendar.MINUTE);
//
//
//                if(locationopen == 0){
//                    locationopen += 12;
//                    format = "AM";
//                }else if(locationopen == 12){
//                    format = "PM";
//                }else if(locationopen > 12){
//                    locationopen -= 12;
//                    format = "PM";
//                }else {
//                    format = "AM";
//                }
//                String time = locationopen + ":00" + " " + format;
//                eventTime.setText("" + time);
//
//                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
//                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
//                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
//                }else {
//                    lastMinute.setVisibility(View.GONE);
//                }
//
//                final String user_id = getRef(position).getKey();
                final String locationame = model.locationTitle;
                final String locationid = model.ownerUserID;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent locationintent = new Intent(getContext(), ClientBarRequest.class);
                       locationintent.putExtra("locationame", locationame);
                       locationintent.putExtra("locationid", locationid);
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
            TextView LocationBarName = (TextView) mView.findViewById(R.id.barname);
            LocationBarName.setText(locationame);
        }

        public void setLocationbarid(String locationid){
            TextView LocationBarId = (TextView) mView.findViewById(R.id.BarId);
            LocationBarId.setText(locationid);
        }
        public void setLocationbartime(int locationtime){
            TextView LocationBarTime = (TextView) mView.findViewById(R.id.text3);
            LocationBarTime.setText(String.valueOf(locationtime));
        }

        public void setLocationbarphoto(final String locationbarphoto) {
            final ImageView LocationBarImage = (ImageView) mView.findViewById(R.id.imagebar);
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
        adapterLocationLounge = new FirebaseRecyclerAdapter<LocationLoungeRecycleview, fragmenthome.LocationLoungeViewHolder>(options) {
            @Override
            public fragmenthome.LocationLoungeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitemlounge, parent, false);


                return new fragmenthome.LocationLoungeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(fragmenthome.LocationLoungeViewHolder holder, int position, LocationLoungeRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationloungename(model.locationTitle);
                holder.setLocationloungephoto(model.locationSlider);
                holder.setLocationloungeid(model.ownerUserID);
//                holder.setLocationloungetime(model.locationtime);


                final int loungeitem = adapterLocationBar.getItemCount();
                if (loungeitem<=0){
                    availLounges.setVisibility(View.VISIBLE);
                }else{
                    availLounges.setVisibility(View.GONE);
                }

                final TextView eventTime = holder.mView.findViewById(R.id.text3);
                final TextView lastMinute = holder.mView.findViewById(R.id.lastminute);

//                int locationtime = model.locationtime;
//                int locationopen = model.locationtime;
//                currentTime = Calendar.getInstance();
//                hour = currentTime.get(Calendar.HOUR_OF_DAY);
//                minute = currentTime.get(Calendar.MINUTE);

//                Calendar cal = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("HH");
//                try {
//                    cal.setTime(sdf.parse(Integer.toString(locationtime)));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.add(Calendar.HOUR, -1);
//                int hours = cal.get(cal.HOUR_OF_DAY);


//                if(locationopen == 0){
//                    locationopen += 12;
//                    format = "AM";
//                }else if(locationopen == 12){
//                    format = "PM";
//                }else if(locationopen > 12){
//                    locationopen -= 12;
//                    format = "PM";
//                }else {
//                    format = "AM";
//                }
//                String time = locationopen + ":00" + " " + format;
//                eventTime.setText("" + time);
//
//                if(hour == locationtime){
//                    lastMinute.setVisibility(View.GONE);
//                }else if(hour > locationtime) {
//                    lastMinute.setVisibility(View.GONE);
//                }else if (hour < locationtime){
//                    lastMinute.setVisibility(View.VISIBLE);
//                }else {
//                    lastMinute.setVisibility(View.GONE);
//                }



                final String user_id = getRef(position).getKey();
                final String locationame = model.locationTitle;
                final String locationid = model.ownerUserID;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent locationintent = new Intent(getContext(), ClientLoungeRequest.class);
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

    public static class LocationLoungeViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationLoungeViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationloungename(String locationame){
            TextView LocationLoungeName = (TextView) mView.findViewById(R.id.loungename);
            LocationLoungeName.setText(locationame);
        }
        public void setLocationloungeid(String locationid){
            TextView LocationLoungeId = (TextView) mView.findViewById(R.id.LoungeId);
            LocationLoungeId.setText(locationid);
        }
        public void setLocationloungetime(int locationtime){
            TextView LocationLoungeTime = (TextView) mView.findViewById(R.id.text3);
            LocationLoungeTime.setText(String.valueOf(locationtime));
        }
        public void setLocationloungephoto(final String locationloungephoto){
            final ImageView LocationLoungeImage = (ImageView) mView.findViewById(R.id.imagelounge);
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




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain thisww
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
