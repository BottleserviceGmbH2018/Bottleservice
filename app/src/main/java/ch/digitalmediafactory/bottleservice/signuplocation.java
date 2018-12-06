package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class signuplocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuplocation);



        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);
        final String firstname = intent.getStringExtra("firstname");
        final String middlename = intent.getStringExtra("middlename");
        final String lastname = intent.getStringExtra("lastname");
        final String email = intent.getStringExtra("email");
        final EditText etAddress = (EditText) findViewById(R.id.etaddress);
        final EditText etAddress2 = (EditText) findViewById(R.id.etaddress2);
        final EditText etCity = (EditText) findViewById(R.id.etcity);
        final EditText etPostal = (EditText) findViewById(R.id.etpostal);
        final ImageView bRegisterLocation = (ImageView) findViewById(R.id.bRegisterlocation);


        bRegisterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etAddress.getText().toString().matches("") && etCity.getText().toString().matches("") && etPostal.getText().toString().matches("")) {
                    etAddress.setError("Please Enter Address");
                    etCity.setError("Please Enter City Address");
                    etPostal.setError("Please Enter Postal Code");
                    etAddress.requestFocus();
                    return;
                }else if(etAddress.getText().toString().matches("")) {
                    etAddress.setError("Please Enter Address");
                    etAddress.requestFocus();
                    return;
                }
                else if(etCity.getText().toString().matches("")) {
                    etCity.setError("Please Enter City Address");
                    etCity.requestFocus();
                    return;
                }
                else if(etPostal.getText().toString().matches("")) {
                    etPostal.setError("Please Enter Postal Code");
                    etPostal.requestFocus();
                    return;
                }else {
                    String address = etAddress.getText().toString();
                    String address2 = etAddress2.getText().toString();
                    String city = etCity.getText().toString();
                    String postal = etPostal.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), signuppassword.class);
                    intent.putExtra("user_type", user_type);
                    intent.putExtra("firstname", firstname);
                    intent.putExtra("middlename", middlename);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("user_type", user_type);
                    intent.putExtra("email", email);
                    intent.putExtra("address", address);
                    intent.putExtra("address2", address2);
                    intent.putExtra("city", city);
                    intent.putExtra("postal", postal);
                    startActivity(intent);
                }
            }
        });
    }
}
