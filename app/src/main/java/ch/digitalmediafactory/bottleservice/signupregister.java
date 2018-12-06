package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.lang3.StringUtils;

public class signupregister extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupregister);
        final EditText etFirstname = (EditText) findViewById(R.id.etFirstname);
        final EditText etMiddlename = (EditText) findViewById(R.id.etMiddlename);
        final EditText etLastname = (EditText) findViewById(R.id.etLastname);
        final ImageView bRegisterName = (ImageView) findViewById(R.id.bRegisterName);
        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);



        bRegisterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etFirstname.getText().toString().matches("")&& etLastname.getText().toString().matches("")) {
                    etFirstname.setError("Please Enter Your Firstname");
                    etLastname.setError("Please Enter Your Lastname");
                    etFirstname.requestFocus();
                    return;

                }else if (etFirstname.getText().toString().matches("")) {
                    etFirstname.setError("Please Enter Your Firstname");
                    etFirstname.requestFocus();
                    return;
                 }else if (etLastname.getText().toString().matches("")) {
                    etLastname.setError("Please Enter Your Lastname");
                    etLastname.requestFocus();
                    return;
                }else {
                    String firstname = etFirstname.getText().toString();
                    String middlename = etMiddlename.getText().toString();
                    String lastname = etLastname.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), signupemail.class);
                    intent.putExtra("firstname", firstname);
                    intent.putExtra("middlename", middlename);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("user_type", user_type);
                    startActivity(intent);
                }
            }
        });

    }

    public void buttonViewSignUp(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), signup.class);
        startActivity(Intent);
        this.finish();
    }
}
