package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewLocationAddress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location_address);


        Intent intent = getIntent();
        final String locationtype = intent.getStringExtra("locationtype");
        final String companyname = intent.getStringExtra("companyname");
        Button bNext = (Button) findViewById(R.id.bNext);
        final EditText etLocationAddress = (EditText) findViewById(R.id.Address1);
        final EditText etLocationAddress2 = (EditText) findViewById(R.id.Address2);
        final EditText etLocationCity = (EditText) findViewById(R.id.City);
        final EditText etLocationPostal = (EditText) findViewById(R.id.PostalCode);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationaddress = etLocationAddress.getText().toString();
                String locationaddress2 = etLocationAddress2.getText().toString();
                String locationcity = etLocationCity.getText().toString();
                String locationpostal = etLocationPostal.getText().toString();

                if (etLocationAddress.getText().toString().matches("") && etLocationCity.getText().toString().matches("") && etLocationPostal.getText().toString().matches("")) {
                    etLocationAddress.setError("Please Enter The Location Address");
                    etLocationCity.setError("Please Enter The City");
                    etLocationPostal.setError("Please Enter The Postal Code");
                    etLocationAddress.requestFocus();
                    return;
                }
                else if (etLocationAddress.getText().toString().matches("")){
                    etLocationAddress.setError("Please Enter The Location Address");
                    etLocationAddress.requestFocus();
                    return;
                }
                else if (etLocationCity.getText().toString().matches("")){
                    etLocationCity.setError("Please Enter The City");
                    etLocationCity.requestFocus();
                    return;
                }
                else if (etLocationPostal.getText().toString().matches("")){

                    etLocationPostal.setError("Please Enter The Postal Code");
                    etLocationPostal.requestFocus();
                    return;
                }else {
                    Intent intent = new Intent(getApplicationContext(), AddNewLocationFeature.class);
                    intent.putExtra("locationtype", locationtype);
                    intent.putExtra("companyname", companyname);
                    intent.putExtra("locationaddress", locationaddress);
                    intent.putExtra("locationaddress2", locationaddress2);
                    intent.putExtra("locationcity", locationcity);
                    intent.putExtra("locationpostal", locationpostal);
                    startActivity(intent);
                }

            }
        });
    }


}
