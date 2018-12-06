package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class signupcompany extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcompany);

        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);
        final EditText etCompanyname = (EditText) findViewById(R.id.etCompanyname);
        final EditText etCompanyownername = (EditText) findViewById(R.id.etCompanyownername);
        final ImageView bRegisterCompanyName = (ImageView) findViewById(R.id.bRegisterCompanyname);

        bRegisterCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (etCompanyname.getText().toString().matches("")) {
                    etCompanyname.setError("Please Enter Company Name");
                    etCompanyname.requestFocus();
                    return;
                }else {
                    String companyname = etCompanyname.getText().toString();
                    String companyownername = etCompanyownername.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), signupcompanyemail.class);
                    intent.putExtra("companyname", companyname);
                    intent.putExtra("companyownername", companyownername);
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
