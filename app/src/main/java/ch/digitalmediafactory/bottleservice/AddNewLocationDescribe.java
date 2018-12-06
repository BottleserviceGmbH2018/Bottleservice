package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewLocationDescribe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location_describe);

        final EditText etdescribeLocation = (EditText) findViewById(R.id.describeLocation);

        Intent intent = getIntent();
        final String locationtype = intent.getStringExtra("locationtype");
        final String companyname = intent.getStringExtra("companyname");
        final String locationaddress = intent.getStringExtra("locationaddress");
        final String locationaddress2 = intent.getStringExtra("locationaddress2");
        final String locationcity = intent.getStringExtra("locationcity");
        final String locationpostal = intent.getStringExtra("locationpostal");
        final String locationfeaturefullbar = intent.getStringExtra("locationfeaturefullbar");
        final String locationfeatureoutdoor = intent.getStringExtra("locationfeatureoutdoor");
        final String locationfeaturecraft = intent.getStringExtra("locationfeaturecraft");
        final String locationfeaturenightlife = intent.getStringExtra("locationfeaturenightlife");
        final String locationfeaturewireless = intent.getStringExtra("locationfeaturewireless");
        final String locationfeatureair = intent.getStringExtra("locationfeatureair");
        final String locationfeaturecash = intent.getStringExtra("locationfeaturecash");
        Button bNext = (Button) findViewById(R.id.bnextButtonguest);


        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String describelocation = etdescribeLocation.getText().toString();

                if (etdescribeLocation.getText().toString().matches("")) {
                    etdescribeLocation.setError("Please Describe The Location");
                    etdescribeLocation.requestFocus();
                    return;
                } else {
                    Intent intent = new Intent(getApplicationContext(), AddNewLocationTitle.class);
                    intent.putExtra("locationtype", locationtype);
                    intent.putExtra("companyname", companyname);
                    intent.putExtra("locationaddress", locationaddress);
                    intent.putExtra("locationaddress2", locationaddress2);
                    intent.putExtra("locationcity", locationcity);
                    intent.putExtra("locationpostal", locationpostal);
                    intent.putExtra("locationfeaturefullbar", locationfeaturefullbar);
                    intent.putExtra("locationfeatureoutdoor", locationfeatureoutdoor);
                    intent.putExtra("locationfeaturecraft", locationfeaturecraft);
                    intent.putExtra("locationfeaturenightlife", locationfeaturenightlife);
                    intent.putExtra("locationfeaturewireless", locationfeaturewireless);
                    intent.putExtra("locationfeatureair", locationfeatureair);
                    intent.putExtra("locationfeaturecash", locationfeaturecash);
                    intent.putExtra("describelocation", describelocation);
                    startActivity(intent);
                }
            }
        });



    }





}
