package ch.digitalmediafactory.bottleservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginForgotPasswordInstructions extends AppCompatActivity {

    private Button bResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forgot_password_instructions);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        TextView EmailAddress = (TextView) findViewById(R.id.tEmailAddress);


        EmailAddress.setText(email);
        bResetPassword = (Button) findViewById(R.id.bResetPassword);
        bResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForgotPasswordInstructions.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
