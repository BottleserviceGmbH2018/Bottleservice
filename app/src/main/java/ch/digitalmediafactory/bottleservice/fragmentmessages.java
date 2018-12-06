package ch.digitalmediafactory.bottleservice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragmentmessages.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragmentmessages#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentmessages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = DashboardDrawerlayout.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView mUserList;

    private DatabaseReference mUsersDatabase;

    private EditText messageSearch;
    private LinearLayoutManager layoutManagerUser;

    private FirebaseRecyclerAdapter adapter;


    private Parcelable recyclerViewState;

    private SwipyRefreshLayout mSwipyRefreshLayout;


    boolean loadingState = true;
    Query deviceListQuery, scrollQuery;
    private String oldestPostId, FirstPostId;




    public fragmentmessages() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentmessages.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentmessages newInstance(String param1, String param2) {
        fragmentmessages fragment = new fragmentmessages();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragmentmessages, container, false);
        mUserList = (RecyclerView) view.findViewById(R.id.recycleViewUsers);
        layoutManagerUser = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        layoutManagerUser.setReverseLayout(true);
//        layoutManagerUser.setStackFromEnd(true);
        mUserList.setLayoutManager(layoutManagerUser);

        messageSearch = (EditText) view.findViewById(R.id.editText);
        messageSearch.addTextChangedListener(searchTextWatcher);

        mSwipyRefreshLayout = (SwipyRefreshLayout)view.findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);

        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.BOTTOM ? "top" : "bottom"));
                fetchdata();
            }
        });
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.stopListening();
    }



    @Override
    public void onStart(){
        super.onStart();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mUsersDatabase.keepSynced(true);
        mUsersDatabase.orderByKey().limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    oldestPostId = child.getKey(); ////HERE WE ARE SAVING THE LAST POST_ID FROM URS POST

                    dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mUsersDatabase.orderByKey().limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    FirstPostId = child.getKey(); ////HERE WE ARE SAVING THE LAST POST_ID FROM URS POST

                    dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        startListening();




    }


    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            startListening();

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchText = messageSearch.getText().toString().trim();
            startListeningSearch(searchText);
            adapter.stopListening();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void startListeningSearch(String searchText){
        Query firebaseSearchQuery = mUsersDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerOptions<UsersChat> options =
                new FirebaseRecyclerOptions.Builder<UsersChat>()
                        .setQuery(firebaseSearchQuery, UsersChat.class)
                        .build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<UsersChat, SearchViewHolder>(options) {
            @Override
            public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_single_layout, parent, false);

                return new SearchViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(SearchViewHolder holder, int position, UsersChat model) {
                // Bind the Chat object to the ChatHolder
                holder.setDisplayName(model.name);
                holder.setDisplayEmail(model.email);
                holder.setUserImage(model.profileImageUrl);

                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                // ...
            }

        };
        mUserList.setAdapter(adapter);
        adapter.startListening();

    }



    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public SearchViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDisplayName(String name){
            TextView userNameView = (TextView) mView.findViewById(R.id.userName);
            userNameView.setText(name);
        }
        public void setDisplayEmail(String email){
            TextView userNameView = (TextView) mView.findViewById(R.id.userEmail);
            userNameView.setText(email);
        }
        public void setUserImage(String profileImageUrl){
            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.profile_image);
            Picasso.get().load(profileImageUrl).placeholder(R.drawable.default_avatar).into(userImageView);
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


    private void fetchdata(){
                mUsersDatabase.orderByKey().startAt(oldestPostId).limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            loadingState = false;
                            oldestPostId = child.getKey(); ////HERE WE ARE SAVING THE LAST POST_ID FROM URS POST
                            dataSnapshot.getChildrenCount();
                            startListening();
                            mSwipyRefreshLayout.setRefreshing(false);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    public void startListening(){
        if(!loadingState){
            deviceListQuery = mUsersDatabase.orderByKey().endAt(oldestPostId);
        }else{
            deviceListQuery = mUsersDatabase.orderByKey().limitToFirst(10);
        }
            FirebaseRecyclerOptions<UsersChat> options =
                new FirebaseRecyclerOptions.Builder<UsersChat>()
                        .setQuery(deviceListQuery, UsersChat.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<UsersChat, UserViewHolder>(options) {
            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_single_layout, parent, false);

                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(UserViewHolder holder, int position, UsersChat model) {
                // Bind the Chat object to the ChatHolder
                holder.setDisplayName(model.name);
                holder.setDisplayEmail(model.email);
                holder.setUserImage(model.profileImageUrl);

                final String user_id = getRef(position).getKey();

                int Pos = holder.getAdapterPosition();
                holder.mView.  setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                // ...
            }

        };


        mUserList.setAdapter(adapter);
        adapter.startListening();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = adapter.getItemCount();
                int lastVisiblePosition = layoutManagerUser.findLastCompletelyVisibleItemPosition();

                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mUserList.scrollToPosition(positionStart);

                }
            }
        });



    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDisplayName(String name){
            TextView userNameView = (TextView) mView.findViewById(R.id.userName);
            userNameView.setText(name);
        }
        public void setDisplayEmail(String email){
            TextView userNameView = (TextView) mView.findViewById(R.id.userEmail);
            userNameView.setText(email);
        }
        public void setUserImage(String profileImageUrl){
            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.profile_image);
            Picasso.get().load(profileImageUrl).placeholder(R.drawable.default_avatar).into(userImageView);
        }
    }


    public void startListeningScroll(){
        Query query = mUsersDatabase
                .orderByKey()
                .startAt(oldestPostId)
                .limitToFirst(10);
        FirebaseRecyclerOptions<UsersChat> options =
                new FirebaseRecyclerOptions.Builder<UsersChat>()
                        .setQuery(query, UsersChat.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<UsersChat, UserViewHolderScroll>(options) {
            @Override
            public UserViewHolderScroll onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_single_layout, parent, false);

                return new UserViewHolderScroll(view);
            }

            @Override
            protected void onBindViewHolder(UserViewHolderScroll holder, int position, UsersChat model) {
                // Bind the Chat object to the ChatHolder
                holder.setDisplayName(model.name);
                holder.setDisplayEmail(model.email);
                holder.setUserImage(model.profileImageUrl);

                final String user_id = getRef(position).getKey();



                if(position == adapter.getItemCount() - 0){
                    fetchdata();
                }

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                // ...
            }

        };

        mUserList.setAdapter(adapter);
        adapter.startListening();




    }

    public static class UserViewHolderScroll extends RecyclerView.ViewHolder {
        View mView;
        public UserViewHolderScroll(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDisplayName(String name){
            TextView userNameView = (TextView) mView.findViewById(R.id.userName);
            userNameView.setText(name);
        }
        public void setDisplayEmail(String email){
            TextView userNameView = (TextView) mView.findViewById(R.id.userEmail);
            userNameView.setText(email);
        }
        public void setUserImage(String profileImageUrl){
            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.profile_image);
            Picasso.get().load(profileImageUrl).placeholder(R.drawable.default_avatar).into(userImageView);
        }
    }





}
