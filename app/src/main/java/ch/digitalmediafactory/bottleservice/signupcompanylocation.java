package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class signupcompanylocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcompanylocation);


        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);
        final String companyname = intent.getStringExtra("companyname");
        final String companyownername = intent.getStringExtra("companyownername");
        final String companyemail = intent.getStringExtra("companyemail");
        final String companypassword = intent.getStringExtra("companypassword");
        final EditText etCompanyAddress = (EditText) findViewById(R.id.etCompanyaddress);
        final EditText etCompanyAddress2 = (EditText) findViewById(R.id.etCompanyaddress2);
        final EditText etCompanyCity = (EditText) findViewById(R.id.etCompanycity);
        final EditText etCompanyPostal = (EditText) findViewById(R.id.etCompanypostal);
        final ImageView bRegisterCompanyLocation = (ImageView) findViewById(R.id.bRegisterCompanylocation);


        bRegisterCompanyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etCompanyAddress.getText().toString().matches("") && etCompanyCity.getText().toString().matches("") && etCompanyPostal.getText().toString().matches("")) {
                    etCompanyAddress.setError("Please Enter Company Address");
                    etCompanyCity.setError("Please Enter City Address");
                    etCompanyPostal.setError("Please Enter Postal Code");
                    etCompanyAddress.requestFocus();
                    return;
                }else if(etCompanyAddress.getText().toString().matches("")) {
                    etCompanyAddress.setError("Please Enter Company Address");
                    etCompanyAddress.requestFocus();
                    return;
                }
                else if(etCompanyCity.getText().toString().matches("")) {
                    etCompanyCity.setError("Please Enter City Address");
                    etCompanyCity.requestFocus();
                    return;
                }
                else if(etCompanyPostal.getText().toString().matches("")) {
                    etCompanyPostal.setError("Please Enter Postal Code");
                    etCompanyPostal.requestFocus();
                    return;
                }else {
                    String companyaddress = etCompanyAddress.getText().toString();
                    String companyaddress2 = etCompanyAddress2.getText().toString();
                    String companycity = etCompanyCity.getText().toString();
                    String companypostal = etCompanyPostal.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), signupcompanycontact.class);
                    intent.putExtra("user_type", user_type);
                    intent.putExtra("companyname", companyname);
                    intent.putExtra("companyownername", companyownername);
                    intent.putExtra("companyemail", companyemail);
                    intent.putExtra("companypassword", companypassword);
                    intent.putExtra("companyaddress", companyaddress);
                    intent.putExtra("companyaddress2", companyaddress2);
                    intent.putExtra("companycity", companycity);
                    intent.putExtra("companypostal", companypostal);
                    startActivity(intent);
                }
            }
        });
    }


    public void buttonViewSignUpCompanyPassword(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), signupcompanypassword.class);
        startActivity(Intent);
        this.finish();
    }
}
