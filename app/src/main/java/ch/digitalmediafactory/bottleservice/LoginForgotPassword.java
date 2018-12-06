package ch.digitalmediafactory.bottleservice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginForgotPassword extends AppCompatActivity {

    private Button bResetPassword;
    private ProgressDialog mLoginProgress;
    private EditText etEmailAddress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forgot_password);

        bResetPassword = (Button) findViewById(R.id.bResetPassword);
        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        mLoginProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        bResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginProgress.setTitle("Checking Existing Account");
                mLoginProgress.setMessage("Loading...");
                mLoginProgress.setCanceledOnTouchOutside(false);
                mLoginProgress.setCancelable(false);
                mLoginProgress.show();
                bResetPassword.setClickable(false);
                if (!etEmailAddress.getText().toString().matches("")) {
                    mAuth.createUserWithEmailAndPassword(etEmailAddress.getText().toString(), "default")
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                try {
                                                    throw task.getException();
                                                }
                                                catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                                {
                                                    etEmailAddress.setError("No account found with that email address");
                                                    etEmailAddress.requestFocus();
                                                    bResetPassword.setClickable(true);
                                                    mLoginProgress.hide();
                                                    return;
                                                }
                                                catch (FirebaseAuthUserCollisionException existEmail) {
                                                    FirebaseAuth.getInstance().sendPasswordResetEmail(etEmailAddress.getText().toString())
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        android.support.v7.app.AlertDialog.Builder connection = new android.support.v7.app.AlertDialog.Builder(LoginForgotPassword.this);
                                                                        connection.setCancelable(false);
                                                                        connection.setMessage("We have sent you instructions to reset your password!")
                                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                                        String email = etEmailAddress.getText().toString();
                                                                                        bResetPassword.setClickable(true);
                                                                                        Intent intent = new Intent(LoginForgotPassword.this, LoginForgotPasswordInstructions.class);
                                                                                        intent.putExtra("email", email);
                                                                                        startActivity(intent);
                                                                                        dialogInterface.dismiss();

                                                                                    }
                                                                                }).show();

                                                                    } else {
                                                                        android.support.v7.app.AlertDialog.Builder connection = new android.support.v7.app.AlertDialog.Builder(LoginForgotPassword.this);
                                                                        connection.setCancelable(false);
                                                                        connection.setMessage("Failed to send request.")
                                                                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                                        bResetPassword.setClickable(true);
                                                                                        dialogInterface.dismiss();
                                                                                    }
                                                                                }).show();
                                                                    }
                                                                    mLoginProgress.hide();
                                                                }
                                                            });

                                                } catch (Exception e) {

                                                }
                                            } else {
                                                etEmailAddress.setError("No account found with that email address");
                                                etEmailAddress.requestFocus();
                                                bResetPassword.setClickable(true);
                                                mLoginProgress.hide();
                                                return;
                                            }
                                        }
                                    }
                            );


                } else {
                    mLoginProgress.hide();
                    etEmailAddress.setError("Please Enter Your Email");
                    etEmailAddress.requestFocus();
                    bResetPassword.setClickable(true);
                    mLoginProgress.hide();
                    return;
                }
            }
        });

    }


}
