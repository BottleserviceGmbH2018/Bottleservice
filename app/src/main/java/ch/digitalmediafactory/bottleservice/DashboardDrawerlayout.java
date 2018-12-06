package ch.digitalmediafactory.bottleservice;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;
import me.toptas.fancyshowcase.OnViewInflateListener;


public class DashboardDrawerlayout extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToogle;
    FragmentTransaction fragmentTransaction;
    TextView textEmail, textLast, textName;
    NavigationView nvDrawer;
    private FirebaseAuth mAuth;


    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mLogoutProgress;

    private StorageReference mImagestorage;

    private CircleImageView mDisplayImage;
    private ProgressDialog mProgressDialog;
    private ProgressBar loading;
    private SwipeRefreshLayout swipeRefresh;
    private MenuItem itemsearch;
    private Class fragmentClass;
    private String name;
    private GoogleApiClient mGoogleApiClient;
    private FancyShowCaseView mFancyShowCaseViewWelcome, mFancyShowCaseViewSearch, mFancyShowCaseViewToggle, mFancyShowCaseViewDrawer, mFancyShowCaseViewFinish;
    private FancyShowCaseView mFancyShowCaseViewReservationHome, mFancyShowCaseViewReservation, mFancyShowCaseViewReservationRequest, mFancyShowCaseViewReservationReview, mFancyShowCaseViewReservationTerms, mFancyShowCaseViewReservationBooking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_drawerlayout);



        //Firebase

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mUserDatabase.keepSynced(true);

        //
        mAuth = FirebaseAuth.getInstance();
        mImagestorage = FirebaseStorage.getInstance().getReference();

        //Value Database
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String profileimage = dataSnapshot.child("profileImageUrl").getValue().toString();
                String emailaddress = dataSnapshot.child("email").getValue().toString();
                textName.setText(name);
                textEmail.setText(emailaddress);

                if(!profileimage.equals("default")) {
                    Picasso.get().load(profileimage).placeholder(R.drawable.default_avatar).into(mDisplayImage);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




//       swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);

//      swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
//                connectedRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//                        final boolean connected = snapshot.getValue(Boolean.class);
//                        if (connected) {
//                            Toast.makeText(DashboardDrawerlayout.this, "Reloading..", Toast.LENGTH_SHORT).show();
//                            swipeRefresh.setRefreshing(false);
//                        } else {
//                            Toast.makeText(DashboardDrawerlayout.this, "Please check your connection", Toast.LENGTH_SHORT).show();
//                            swipeRefresh.setRefreshing(true);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//                        swipeRefresh.setRefreshing(true);
//                    }
//                });
//
//            }
//        });




        //Get intents
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");


        //HeaderView
        nvDrawer = (NavigationView) findViewById(R.id.nv);
        View headerView = nvDrawer.getHeaderView(0);
        textName = headerView.findViewById(R.id.textName);
        textEmail = headerView.findViewById(R.id.textEmail);
        setupDrawerContent(nvDrawer);



        //CircleView
        mDisplayImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
        mLogoutProgress = new ProgressDialog(this);




        //Nav Drawer
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToogle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flcontent, new fragmenthome());
        fragmentTransaction.commit();
        setTitle("Bottleservice");




        //
        mDisplayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder profilelogo = new  android.support.v7.app.AlertDialog.Builder(DashboardDrawerlayout.this);
                profilelogo.setTitle("Take Picture")
                        .setMessage("Take a new photo or select one from your existing photo")
                        .setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                getIntent.setType("image/*");

                                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickIntent.setType("image/*");

                                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                                startActivityForResult(chooserIntent, 100);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("GALLERY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                getIntent.setType("image/*");

                                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickIntent.setType("image/*");

                                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
                                startActivityForResult(chooserIntent, 100);
                                dialogInterface.dismiss();

                            }
                        }).show();

            }
        });








        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();


            Intent intent = CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .getIntent(this);
            startActivityForResult(intent, 1);

        }
            if (requestCode == 1) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {


                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setTitle("Uploading image...");
                    mProgressDialog.setMessage("Please wait while we upload and process the image..");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();


                    Uri resultUri = result.getUri();

                    final File thumb_filePath = new File(resultUri.getPath());

                    String current_user_id = mCurrentUser.getUid();

                    final Bitmap thumb_bitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxWidth(200)
                            .setQuality(75)
                            .compressToBitmap(thumb_filePath);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] thumb_byte = baos.toByteArray();

                    StorageReference filepath = mImagestorage.child("profile_images").child(current_user_id + ".jpg");


                    filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {

                                String download_url = task.getResult().getDownloadUrl().toString();

                                mUserDatabase.child("profileImageUrl").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()) {
                                            mProgressDialog.dismiss();
                                        }

                                    }
                                });

                            }else {

                            }
                        }

                    });
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }


        }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (mToogle.onOptionsItemSelected(item)) {

            return true;
        }
        switch(item.getItemId()) {
            case R.id.action_search:

                Intent locationintent = new Intent(DashboardDrawerlayout.this, ClientLocationAddress.class);
                startActivity(locationintent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        itemsearch = menu.findItem(R.id.action_search);

        String tutorialKey = "SOME_KEY";
        Boolean firstTime = getPreferences(MODE_PRIVATE).getBoolean(tutorialKey, true);
        if (firstTime) {


            // here you do what you want to do - an activity tutorial in my case


        //Toggle

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewToggle = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .focusOn(findViewById(android.support.v7.appcompat.R.id.action_bar))
                                .customView(R.layout.user_manual_toggle, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonToggle).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewDrawer)
                                                        .show();
                                                mFancyShowCaseViewToggle.removeView();
                                                mDrawerlayout.openDrawer(Gravity.START);
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewToggle.show();
                    }
                }, 50
        );


        //Search

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewSearch = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .focusOn(findViewById(R.id.action_search))
                                .customView(R.layout.user_manual_search, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonSearch).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewSearch.hide();
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewToggle)
                                                        .show();
                                                mFancyShowCaseViewSearch.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewSearch.show();
                    }
                }, 50
        );


        //ReservationHome

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewReservationHome = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .customView(R.layout.user_manual_reservationhome, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonReservationHome).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewReservationHome.hide();
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewReservation)
                                                        .show();
                                                mFancyShowCaseViewReservationHome.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewReservationHome.show();
                    }
                }, 50
        );



        //Reservation

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewReservation = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .customView(R.layout.user_manual_reservation, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonReservation).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewReservation.hide();
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewReservationRequest)
                                                        .show();
                                                mFancyShowCaseViewReservation.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewReservation.show();
                    }
                }, 50
        );

        //ReservationRequest

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewReservationRequest = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .customView(R.layout.user_manual_reservationrequest, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonReservationRequest).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewReservationRequest.hide();
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewReservationReview)
                                                        .show();
                                                mFancyShowCaseViewReservationRequest.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewReservationRequest.show();
                    }
                }, 50
        );


        //ReservationReview


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewReservationReview = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .customView(R.layout.user_manual_reservationreview, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonReservationReview).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewReservationReview.hide();
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewReservationTerms)
                                                        .show();
                                                mFancyShowCaseViewReservationReview.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewReservationReview.show();
                    }
                }, 50
        );



        //ReservationTerms


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewReservationTerms = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .customView(R.layout.user_manual_terms, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonReservationTerms).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewReservationTerms.hide();
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewReservationBooking)
                                                        .show();
                                                mFancyShowCaseViewReservationTerms.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewReservationTerms.show();
                    }
                }, 50
        );



        //ReservationBooking


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewReservationBooking = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .customView(R.layout.user_manual_booking, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonReservationBooking).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewReservationBooking.hide();
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewFinish)
                                                        .show();
                                                mFancyShowCaseViewReservationBooking.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewReservationBooking.show();
                    }
                }, 50
        );


        //Drawer

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewDrawer = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .customView(R.layout.user_manual_drawer, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonDrawer).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewDrawer.hide();
                                                mDrawerlayout.closeDrawer(Gravity.START);
                                                new FancyShowCaseQueue()
                                                        .add(mFancyShowCaseViewReservationHome)
                                                        .show();
                                                mFancyShowCaseViewDrawer.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewDrawer.show();
                    }
                }, 50
        );


        //Finish


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mFancyShowCaseViewFinish = new FancyShowCaseView.Builder(DashboardDrawerlayout.this)
                                .customView(R.layout.user_manual_finish, new OnViewInflateListener() {
                                    @Override
                                    public void onViewInflated(@NonNull View view) {
                                        view.findViewById(R.id.bButtonFinish).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mFancyShowCaseViewFinish.hide();

                                                mFancyShowCaseViewFinish.removeView();
                                            }
                                        });
                                    }
                                }).closeOnTouch(false).build();
                        mFancyShowCaseViewFinish.show();
                    }
                }, 50
        );


            getPreferences(MODE_PRIVATE).edit().putBoolean(tutorialKey, false).apply();
        }

            return super.onCreateOptionsMenu(menu);

    }




    //Selective Drawer


    public void selecItemDrawer(MenuItem menuItem) {
        Fragment myFragment = null;



        switch (menuItem.getItemId()){
            case R.id.home:
                setTitle("Bottleservice");
                fragmentClass = fragmenthome.class;
                itemsearch.setVisible(true);

                break;
            case R.id.msg:
                fragmentClass = fragmentmessages.class;
                itemsearch.setVisible(false);

                break;
            case R.id.book:
                fragmentClass = fragmentbookings.class;
                itemsearch.setVisible(true);
                break;

            case R.id.pay:
                fragmentClass = fragmentpayment.class;
                itemsearch.setVisible(true);
                break;

            case R.id.setting:
                fragmentClass = fragmentsettings.class;
                itemsearch.setVisible(true);
                break;

            case R.id.logout:
                fragmentClass = fragmentlogout.class;
                android.support.v7.app.AlertDialog.Builder connection = new  android.support.v7.app.AlertDialog.Builder(DashboardDrawerlayout.this);
                connection.setCancelable(false);
                connection.setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mLogoutProgress.setTitle("Logging Out");
                                mLogoutProgress.setMessage("Saving your data...");
                                mLogoutProgress.setCanceledOnTouchOutside(false);
                                mLogoutProgress.show();
                                sendToStart();
                                FirebaseAuth.getInstance().signOut();
                                mAuth.signOut();
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                }).show();
                break;

            default:
                fragmentClass = fragmenthome.class;


        }
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerlayout.closeDrawers();



    }
    private void setupDrawerContent(final NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selecItemDrawer(item);
                return true;
            }
        });
    }

    private void sendToStart(){
        Intent intent = new Intent(DashboardDrawerlayout.this, MainActivity.class);
        startActivity(intent);
        mAuth.signOut();
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            sendToStart();
            mProgressDialog.dismiss();

        }
    }


    @Override
    public void onResume(){
        super.onResume();



        String tutorialKey = "SOME_KEY";
        Boolean firstTime = getPreferences(MODE_PRIVATE).getBoolean(tutorialKey, true);
        if (firstTime) {

            mFancyShowCaseViewWelcome = new FancyShowCaseView.Builder(this)
                    .customView(R.layout.user_manual_welcome, new OnViewInflateListener() {
                        @Override
                        public void onViewInflated(@NonNull View view) {
                            view.findViewById(R.id.bButtonWelcome).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mFancyShowCaseViewWelcome.removeView();
                                    new FancyShowCaseQueue()
                                            .add(mFancyShowCaseViewSearch)
                                            .show();

                                }
                            });
                        }
                    }).closeOnTouch(false).build();
            mFancyShowCaseViewWelcome.show();

            // here you do what you want to do - an activity tutorial in my case

        }
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Toast.makeText(DashboardDrawerlayout.this, "Loading..", Toast.LENGTH_SHORT).show();
//                    swipeRefresh.setRefreshing(false);
                } else {
//                    swipeRefresh.setRefreshing(true);
                    Toast.makeText(DashboardDrawerlayout.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.action_search:
                new FancyShowCaseView.Builder(this)
                        .title("Focus on Actionbar items")
                        .build()
                        .show();
                break;

        }
    }



    @Override
    public void onStop(){
        super.onStop();
        mLogoutProgress.dismiss();

    }

    @Override
    public void onBackPressed(){
        android.support.v7.app.AlertDialog.Builder connection = new  android.support.v7.app.AlertDialog.Builder(DashboardDrawerlayout.this);
                connection.setCancelable(false);
                connection.setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        mAuth.signOut();
                        finish();
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();

    }




}
