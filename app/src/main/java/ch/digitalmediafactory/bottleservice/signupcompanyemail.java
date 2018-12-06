package ch.digitalmediafactory.bottleservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupcompanyemail extends AppCompatActivity {

    private ProgressDialog mAccountProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcompanyemail);



        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);
        final String companyname = intent.getStringExtra("companyname");
        final String companyownername = intent.getStringExtra("companyownername");
        final EditText etCompanyEmail = (EditText) findViewById(R.id.etCompanyemail);
        mAccountProgress = new ProgressDialog(this);


        final ImageView bRegisterCompanyEmail = (ImageView) findViewById(R.id.bRegisterCompanyemail);

        bRegisterCompanyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bRegisterCompanyEmail.setClickable(false);
                mAccountProgress.setTitle("Checking Existing Account");
                mAccountProgress.setMessage("Loading...");
                mAccountProgress.setCanceledOnTouchOutside(false);
                mAccountProgress.setCancelable(false);
                mAccountProgress.show();
                if (etCompanyEmail.getText().toString().matches("")) {
                    etCompanyEmail.setError("Please Enter Email");
                    etCompanyEmail.requestFocus();
                    return;
                }
                else {
                    mAuth.createUserWithEmailAndPassword(etCompanyEmail.getText().toString(), "default")
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                try {
                                                    throw task.getException();
                                                }
                                                // if user enters wrong password.
                                                catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                                    etCompanyEmail.setError("Please enter a valid email address");
                                                    etCompanyEmail.requestFocus();
                                                    bRegisterCompanyEmail.setClickable(true);
                                                    mAccountProgress.hide();
                                                    return;

                                                    // TODO: Take your action
                                                } catch (FirebaseAuthUserCollisionException existEmail) {
                                                    etCompanyEmail.setError("This email address already exist");
                                                    etCompanyEmail.requestFocus();
                                                    bRegisterCompanyEmail.setClickable(true);
                                                    mAccountProgress.hide();
                                                    return;
                                                } catch (Exception e) {

                                                }
                                            } else {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                user.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    String companyemail = etCompanyEmail.getText().toString();
                                                                    Intent intent = new Intent(getApplicationContext(), signupcompanypassword.class);
                                                                    intent.putExtra("user_type", user_type);
                                                                    intent.putExtra("companyname", companyname);
                                                                    intent.putExtra("companyownername", companyownername);
                                                                    intent.putExtra("companyemail", companyemail);
                                                                    startActivity(intent);
                                                                    bRegisterCompanyEmail.setClickable(true);
                                                                    mAccountProgress.hide();
                                                                }
                                                            }
                                                        });
                                            }

                                        }
                                    }
                            );


                }
            }
        });



    }

    public void buttonViewSignUpCompany(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), signupcompany.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonViewSignUpCompanyPassword(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), signupcompanypassword.class);
        startActivity(Intent);
        this.finish();
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
