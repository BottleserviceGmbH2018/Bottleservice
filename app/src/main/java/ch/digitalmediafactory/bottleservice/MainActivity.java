package ch.digitalmediafactory.bottleservice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shaishavgandhi.loginbuttons.FacebookButton;
import com.shaishavgandhi.loginbuttons.GoogleButton;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ProgressDialog mLoginProgress;
//    private String LANG_CURRENT = "en";


    private EditText etUsername, etPassword;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    private DatabaseReference mUserDatabase;

    CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    int user_type = 2;
    String fullname, email, birthday, firstname, lastname, display_photo;
    private FacebookButton loginFbButton;

    //GoogleLogin
    private GoogleButton loginGoogleButton;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;
    private static final int PER_LOGIN = 1000;

    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        TextView tForgotPassword = (TextView) findViewById(R.id.tForgotPassword);

        bLogin = (Button) findViewById(R.id.bSignIn);
        mLoginProgress = new ProgressDialog(this);
//        spinner = findViewById(R.id.spinnerlanguage);
//        adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        if (LANG_CURRENT.equals("en")) {
//            spinner.setSelection(0, true);
//        } else {
//            spinner.setSelection(1, true);
//        }
//        View v = spinner.getSelectedView();
//        ((TextView) v).setTextColor(Color.WHITE);




        loginGoogleButton = (GoogleButton) findViewById(R.id.googleLogin);
        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



        //GoogleLogin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();



        //FacebookLogin
        callbackManager = CallbackManager.Factory.create();
        loginFbButton = (FacebookButton) findViewById(R.id.fbLogin);
        loginFbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        mLoginProgress.setTitle("Logging In");
                        mLoginProgress.setMessage("Please wait while we check your credentials");
                        mLoginProgress.setCanceledOnTouchOutside(false);
                        mLoginProgress.show();
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());
                                        try {
                                            email = object.getString("email");
//                                            birthday = object.getString("birthday");
                                            fullname = object.getString("name");

                                            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
                                            display_photo = profile_picture.toString();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.v("LoginActivity", "cancel");
                        loginFbButton.setClickable(true);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.v("LoginActivity", exception.getCause().toString());
                        loginFbButton.setClickable(true);
                    }
                });
            }
        });


        //Forgot Password

        tForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginForgotPassword.class);
                startActivity(intent);
            }
        });


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userid = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                if (etUsername.getText().toString().matches("") && etPassword.getText().toString().matches("")) {
                    etUsername.setError("Please Enter Your Email");
                    etPassword.setError("Please Enter Your Password");
                    etUsername.requestFocus();
                    return;
                } else if (etUsername.getText().toString().matches("")) {
                    etUsername.setError("Please Enter Your Email");
                    etUsername.requestFocus();
                    return;
                } else if (etPassword.getText().toString().matches("")) {
                    etPassword.setError("Please Enter Your Password");
                    etPassword.requestFocus();
                    return;
                } else {
                    mLoginProgress.setTitle("Logging In");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    bLogin.setClickable(false);
                    loginUser(userid, password);
                }


            }
        });


//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if (position == 0) {
//                    changeLang(MainActivity.this, "en");
//                    finish();
//                    startActivity(new Intent(MainActivity.this, MainActivity.class));
//                }
//
//                if (position == 1) {
//                    changeLang(MainActivity.this, "de");
//                    finish();
//                    startActivity(new Intent(MainActivity.this, MainActivity.class));
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        loginGoogleButton.setClickable(false);
        mLoginProgress.setTitle("Logging In");
        mLoginProgress.setMessage("Please wait while we check your credentials");
        mLoginProgress.setCanceledOnTouchOutside(false);
        mLoginProgress.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                            if (acct != null) {
                                String personName = acct.getDisplayName();
                                String personGivenName = acct.getGivenName();
                                String personFamilyName = acct.getFamilyName();
                                String personEmail = acct.getEmail();
                                Uri personPhoto = acct.getPhotoUrl();
                                String stringUri = personPhoto.toString();

                                FirebaseUser user = mAuth.getCurrentUser();
                                String userid = user.getUid();

                                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                DatabaseReference mUserDatabaseUpdate = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("name", personName);
                                userMap.put("email", personEmail);
                                userMap.put("profileImageUrl", stringUri);
                                userMap.put("type", user_type + "");
                                userMap.put("device_token", deviceToken);
                                mUserDatabaseUpdate.setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        updateUI();
                                    }
                                });
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            mLoginProgress.hide();
                            android.support.v7.app.AlertDialog.Builder connection = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                            connection.setCancelable(false);
                            connection.setMessage("Authentication Failed")
                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            loginGoogleButton.setClickable(true);
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }

                        // ...
                    }
                });
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userid = user.getUid();

                            String deviceToken = FirebaseInstanceId.getInstance().getToken();
                            DatabaseReference mUserDatabaseUpdate = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", fullname);
                            userMap.put("email", email);
                            userMap.put("profileImageUrl", display_photo);
                            userMap.put("type", user_type + "");
                            userMap.put("device_token", deviceToken);
                            mUserDatabaseUpdate.setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    updateUI();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            mLoginProgress.hide();
                            android.support.v7.app.AlertDialog.Builder connection = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                            connection.setCancelable(false);
                            connection.setMessage("Authentication Failed")
                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            loginFbButton.setClickable(true);
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }

                        // ...
                    }
                });
    }


//    public void changeLang(Context context, String lang) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("Language", lang);
//        editor.apply();
//    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
//        LANG_CURRENT = preferences.getString("Language", "en");
//
//        super.attachBaseContext(MyContextWrapper.wrap(newBase, LANG_CURRENT));
//    }

    private void loginUser(String userid, String password) {
        mAuth.signInWithEmailAndPassword(userid, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (!task.isSuccessful()) {
                            mLoginProgress.hide();
                            android.support.v7.app.AlertDialog.Builder connection = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                            connection.setCancelable(false);
                            connection.setMessage("Authentication Failed")
                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            bLogin.setClickable(true);
                                            dialogInterface.dismiss();
                                        }
                                    }).show();

                        } else {
                            try {
                                if (user.isEmailVerified()) {
                                    Log.d(TAG, "onComplete: success. email is verified");
                                    updateUI();
                                } else {
                                    android.support.v7.app.AlertDialog.Builder connection = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                                    connection.setCancelable(false);
                                    connection.setTitle("Email is not verified.");
                                    connection.setMessage("Check your email inbox.")
                                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    bLogin.setClickable(true);
                                                    dialogInterface.dismiss();
                                                    mAuth.signOut();
                                                }
                                            }).show();
                                    mLoginProgress.hide();
                                }

                            } catch (NullPointerException e) {
                                Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage());
                            }

                        }

                        // ...
                    }
                });

    }


    public void buttonViewSignUp(View v) {
        Intent Intent = new Intent(getApplicationContext(), signup.class);
        startActivity(Intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            try {
                if (currentUser.isEmailVerified()) {
                    Log.d(TAG, "onComplete: success. email is verified");
                    mLoginProgress.setTitle("Logging In");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    updateUI();
                } else {

                }

            } catch (NullPointerException e) {
                Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage());
            }

        } else {
            mAuth.signOut();
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

        }
    }


    public void onStart(){
        super.onStart();

    }


    public void onStop() {
        super.onStop();
        // Check if user is signed in (non-null) and update UI accordingly.
        mLoginProgress.cancel();
    }

    private void updateUI() {
        FirebaseUser user = mAuth.getCurrentUser();
        String userid = user.getUid();
        DatabaseReference mUserDatabaseUpdate = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        mUserDatabaseUpdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int lounge = 1;
                int client = 2;

                int usertype = Integer.valueOf(dataSnapshot.child("type").getValue().toString());


                if (usertype == client) {
                    Intent intent = new Intent(MainActivity.this, DashboardDrawerlayout.class);
                    startActivity(intent);
                    signOut();
                    mLoginProgress.cancel();
                    etPassword.setText("");
                    finish();

                } else if (usertype == lounge) {
                    Intent intent = new Intent(MainActivity.this, LoungeDashboard.class);
                    startActivity(intent);
                    signOut();
                    mLoginProgress.cancel();
                    etPassword.setText("");
                    finish();


                } else {
                    Intent intent = new Intent(MainActivity.this, AppOwnerDashboard.class);
                    startActivity(intent);
                    signOut();
                    mLoginProgress.cancel();
                    etPassword.setText("");
                    finish();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void signOut() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }

}
