package ch.digitalmediafactory.bottleservice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class signupbirthday extends AppCompatActivity {

    private static final String TAG = "signupbirthday";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mUserDatabase;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    PDFView pdfView;
    Dialog myDialog;
    private ProgressDialog mRegisterProgress;
    Button bRegisterBirthdate;
    String address, address2, city, postal;
    TextView mDisplayDated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupbirthday);


        mAuth = FirebaseAuth.getInstance();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        Intent intent = getIntent();
        final CheckBox itemClicked = (CheckBox) findViewById(R.id.checkbox);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        final int user_type = intent.getIntExtra("user_type", -1);
        final String firstname = intent.getStringExtra("firstname");
        final String middlename = intent.getStringExtra("middlename");
        final String lastname = intent.getStringExtra("lastname");
        final String password = intent.getStringExtra("password");
        final String email = intent.getStringExtra("email");
        address = intent.getStringExtra("address");
        address2 = intent.getStringExtra("address2");
        city = intent.getStringExtra("city");
        postal = intent.getStringExtra("postal");
        progressBar.setVisibility(View.INVISIBLE);
        final EditText mDisplayDate = (EditText) findViewById(R.id.tvDate);
        mDisplayDated = (TextView) findViewById(R.id.tvDated);
        bRegisterBirthdate = (Button) findViewById(R.id.bRegisterBirthdate);
        bRegisterBirthdate.setEnabled(false);
        mRegisterProgress = new ProgressDialog(this);
        itemClicked.setClickable(false);



        bRegisterBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mRegisterProgress.setTitle("Registering User");
                mRegisterProgress.setMessage("Please wait while we create your account");
                mRegisterProgress.setCanceledOnTouchOutside(false);
                mRegisterProgress.show();

                progressBar.setVisibility(View.VISIBLE);
                bRegisterBirthdate.setEnabled(false);
                register_user(firstname, lastname, email ,password, user_type);
            }
        });


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDisplayDate.setEnabled(true);
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        signupbirthday.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });



        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyyy/mm/dd: " + year + "/" + month + "/" + day);
                String date = month + " / " + day + " / " + year;
                String dated = year + "-" + month + "-" + day;
                mDisplayDate.setText(date);
                mDisplayDated.setText(dated);
                Calendar userAge = new GregorianCalendar(year,month,day);
                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (!minAdultAge.before(userAge)) {
                    itemClicked.setClickable(true);
                    itemClicked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                        {
                            if ( isChecked )
                            {
                                bRegisterBirthdate.setEnabled(true);
                            }

                            else {
                                bRegisterBirthdate.setEnabled(false);
                            }

                        }
                    });
                }else{
                    itemClicked.setClickable(false);
                    mDisplayDated.setError(signupbirthday.this.getString(R.string.you_must_be_at_least_18_years_old_to_use_bottleservice_others_won_t_see_your_birthday));
                    mDisplayDated.requestFocus();
                    Toast.makeText(signupbirthday.this, signupbirthday.this.getString(R.string.you_must_be_at_least_18_years_old_to_use_bottleservice_others_won_t_see_your_birthday), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        };
    }

    private void register_user(final String firstname, final String lastname, final String email, String password, final int user_type) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mRegisterProgress.hide();
                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();
                            String date = mDisplayDated.getText().toString();
                            String first = firstname;
                            String last = lastname;
                            String name = first + " " + last;

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", name);
                            userMap.put("email", email);
                            userMap.put("profileImageUrl", "default");
                            userMap.put("type", user_type + "");
                            userMap.put("userAdd1", address);
                            userMap.put("userAdd2", address2);
                            userMap.put("userCity", city);
                            userMap.put("birthdate", date);
                            userMap.put("userPostal", postal);
                            mDatabase.setValue(userMap);


                            String deviceToken = FirebaseInstanceId.getInstance().getToken();

                            mUserDatabase.child(uid).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                sendVerificationEmail();

                                }
                            });


                            android.support.v7.app.AlertDialog.Builder connection = new  android.support.v7.app.AlertDialog.Builder(signupbirthday.this);
                            connection.setCancelable(false);
                            connection.setTitle("Success");
                            connection.setMessage("Please verify your account.")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(signupbirthday.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                            dialogInterface.dismiss();
                                        }
                                    }).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            String loginfailed = task.getException().toString();
                            Toast.makeText(signupbirthday.this, loginfailed, Toast.LENGTH_SHORT).show();
                            bRegisterBirthdate.setEnabled(true);

                        }

                        // ...
                    }
                });


    }

    public void sendVerificationEmail(){
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    if(user != null){
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mRegisterProgress.hide();
                    mAuth.signOut();
                }else{
                    Toast.makeText(signupbirthday.this, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    }


    public void buttonViewSignUpPassword(View v) {
        Intent Intent = new Intent(this, signuppassword.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonViewClientTerms(View v) {
        Intent Intent = new Intent(this, ClientTermsCondition.class);
        startActivity(Intent);
    }

}
