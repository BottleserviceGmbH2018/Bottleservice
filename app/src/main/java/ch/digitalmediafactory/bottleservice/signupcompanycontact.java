package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class signupcompanycontact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcompanycontact);


        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);
        final String companyname = intent.getStringExtra("companyname");
        final String companyownername = intent.getStringExtra("companyownername");
        final String companyemail = intent.getStringExtra("companyemail");
        final String companypassword = intent.getStringExtra("companypassword");
        final String companyaddress = intent.getStringExtra("companyaddress");
        final String companyaddress2 = intent.getStringExtra("companyaddress2");
        final String companycity = intent.getStringExtra("companycity");
        final String companypostal = intent.getStringExtra("companypostal");
        final EditText etCompanyPhonenumber = (EditText) findViewById(R.id.etCompanyphonenumber);
        final EditText etCompanyOwnerPhonenumber = (EditText) findViewById(R.id.etCompanyownerphonenumber);
        final EditText etCompanyOwnerMobilenumber = (EditText) findViewById(R.id.etCompanyownermobilenumber);
        final ImageView bRegisterCompanyContact = (ImageView) findViewById(R.id.bRegisterCompanyContact);


        bRegisterCompanyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etCompanyPhonenumber.getText().toString().matches("") && etCompanyOwnerMobilenumber.getText().toString().matches("")) {
                    etCompanyPhonenumber.setError("Please Enter Company Phone Number");
                    etCompanyOwnerMobilenumber.setError("Please Enter Owners Mobile Number");
                    etCompanyPhonenumber.requestFocus();
                    return;
                }else if(etCompanyPhonenumber.getText().toString().matches("")) {
                    etCompanyPhonenumber.setError("Please Enter Company Phone Number");
                    etCompanyPhonenumber.requestFocus();
                    return;
                }
                else if(etCompanyOwnerMobilenumber.getText().toString().matches("")) {
                    etCompanyOwnerMobilenumber.setError("Please Enter Owners Mobile Number");
                    etCompanyOwnerMobilenumber.requestFocus();
                    return;
                }else {
                    String companyphonenumber = etCompanyPhonenumber.getText().toString();
                    String companyownerphonenumber = etCompanyOwnerPhonenumber.getText().toString();
                    String companyownermobilenumber = etCompanyOwnerMobilenumber.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), signupcompanylogo.class);
                    intent.putExtra("user_type", user_type);
                    intent.putExtra("companyname", companyname);
                    intent.putExtra("companyownername", companyownername);
                    intent.putExtra("companyemail", companyemail);
                    intent.putExtra("companypassword", companypassword);
                    intent.putExtra("companyaddress", companyaddress);
                    intent.putExtra("companyaddress2", companyaddress2);
                    intent.putExtra("companycity", companycity);
                    intent.putExtra("companypostal", companypostal);
                    intent.putExtra("companyphonenumber", companyphonenumber);
                    intent.putExtra("companyownerphonenumber", companyownerphonenumber);
                    intent.putExtra("companyownermobilenumber", companyownermobilenumber);
                    startActivity(intent);
                }
            }
        });
    }

    public void buttonViewSignUpCompanyLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), signupcompanylocation.class);
        startActivity(Intent);
        this.finish();
    }

}
