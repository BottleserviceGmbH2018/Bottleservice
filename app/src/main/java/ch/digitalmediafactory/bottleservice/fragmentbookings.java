package ch.digitalmediafactory.bottleservice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragmentbookings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragmentbookings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentbookings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = DashboardDrawerlayout.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    View view;


    private FirebaseUser mCurrentUser;

    private RecyclerView mLocationBookingList;
    private DatabaseReference mBookingDatabase;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragmentbookings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentbookings.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentbookings newInstance(String param1, String param2) {
        fragmentbookings fragment = new fragmentbookings();
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

        mBookingDatabase = FirebaseDatabase.getInstance().getReference().child("reservation").child(current_uid);
        mBookingDatabase.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragmentbookings, container, false);
        mLocationBookingList = (RecyclerView) view.findViewById(R.id.recycleViewClientBooking);
        LinearLayoutManager layoutManagerBookings = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLocationBookingList.setLayoutManager(layoutManagerBookings);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        startListeningBooking();

    }


    public void startListeningBooking(){
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("reservation")
                .child(current_uid)
                .limitToLast(50);
        FirebaseRecyclerOptions<ClientBookingRecycleview> options =
                new FirebaseRecyclerOptions.Builder<ClientBookingRecycleview>()
                        .setQuery(query, ClientBookingRecycleview.class)
                        .build();
        FirebaseRecyclerAdapter adapterLocationBooking = new FirebaseRecyclerAdapter<ClientBookingRecycleview, fragmentbookings.LocationBookingViewHolder>(options) {
            @Override
            public fragmentbookings.LocationBookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitembookings, parent, false);

                return new fragmentbookings.LocationBookingViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(fragmentbookings.LocationBookingViewHolder holder, int position, ClientBookingRecycleview model) {
                // Bind the Chat object to the ChatHolder
                holder.setLocationeventname(model.locationame);
                holder.setLocationeventphoto(model.locationphoto);



                final String user_id = getRef(position).getKey();
                final String locationame = model.locationame;
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                // ...
            }

        };
        mLocationBookingList.setAdapter(adapterLocationBooking);
        adapterLocationBooking.startListening();
    }

    public static class LocationBookingViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public LocationBookingViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setLocationeventname(String locationame){
            TextView LocationEventName = (TextView) mView.findViewById(R.id.tBookName);
            LocationEventName.setText(locationame);
        }

        public void setLocationeventphoto(final String locationeventphoto){
            final ImageView LocationEventImage = (ImageView) mView.findViewById(R.id.ivBooking );
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
     * This interface must be implemented by activities that contain this
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
