package ch.digitalmediafactory.bottleservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupemail extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog mAccountProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupemail);

        mAuth = FirebaseAuth.getInstance();
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final ImageView bRegisterEmail = (ImageView) findViewById(R.id.bRegisterEmail);
        mAccountProgress = new ProgressDialog(this);

        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);
        final String firstname = intent.getStringExtra("firstname");
        final String middlename = intent.getStringExtra("middlename");
        final String lastname = intent.getStringExtra("lastname");
        bRegisterEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bRegisterEmail.setClickable(false);
                mAccountProgress.setTitle("Checking Existing Account");
                mAccountProgress.setMessage("Loading...");
                mAccountProgress.setCanceledOnTouchOutside(false);
                mAccountProgress.setCancelable(false);
                mAccountProgress.show();
                if (etEmail.getText().toString().matches("")) {
                    etEmail.setError("Please Enter Your Email Address");
                    etEmail.requestFocus();
                    bRegisterEmail.setClickable(true);
                    mAccountProgress.hide();
                    return;
                } else {
                    mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), "default")
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
                                                    etEmail.setError("Please enter a valid email address");
                                                    etEmail.requestFocus();
                                                    bRegisterEmail.setClickable(true);
                                                    mAccountProgress.hide();
                                                    return;

                                                    // TODO: Take your action
                                                } catch (FirebaseAuthUserCollisionException existEmail) {
                                                    etEmail.setError("This email address already exist");
                                                    etEmail.requestFocus();
                                                    bRegisterEmail.setClickable(true);
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
                                                                    final String email = etEmail.getText().toString();
                                                                    Intent intent = new Intent(getApplicationContext(), signuplocation.class);
                                                                    intent.putExtra("firstname", firstname);
                                                                    intent.putExtra("middlename", middlename);
                                                                    intent.putExtra("lastname", lastname);
                                                                    intent.putExtra("user_type", user_type);
                                                                    intent.putExtra("email", email);
                                                                    startActivity(intent);
                                                                    bRegisterEmail.setClickable(true);
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

    public void buttonViewSignUpRegister(View v) {
        Intent Intent = new Intent(getApplicationContext(), signupregister.class);
        startActivity(Intent);
        this.finish();
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
