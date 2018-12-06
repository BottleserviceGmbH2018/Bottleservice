package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class AddNewLocationFeature extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location_feature);

        Intent intent = getIntent();
        final String locationtype = intent.getStringExtra("locationtype");
        final String companyname = intent.getStringExtra("companyname");
        final String locationaddress = intent.getStringExtra("locationaddress");
        final String locationaddress2 = intent.getStringExtra("locationaddress2");
        final String locationcity = intent.getStringExtra("locationcity");
        final String locationpostal = intent.getStringExtra("locationpostal");


        final Switch onoffSwitch1 = (Switch) findViewById(R.id.fullbar);
        final Switch onoffSwitch2 = (Switch) findViewById(R.id.outdoor);
        final Switch onoffSwitch3 = (Switch) findViewById(R.id.craft);
        final Switch onoffSwitch4 = (Switch) findViewById(R.id.nightlife);
        final Switch onoffSwitch5 = (Switch) findViewById(R.id.wireless);
        final Switch onoffSwitch6 = (Switch) findViewById(R.id.airconditioning);
        final Switch onoffSwitch7 = (Switch) findViewById(R.id.cashpayment);

        final TextView textView1 = (TextView) findViewById(R.id.textView1);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        final TextView textView3 = (TextView) findViewById(R.id.textView3);
        final TextView textView4 = (TextView) findViewById(R.id.textView4);
        final TextView textView5 = (TextView) findViewById(R.id.textView5);
        final TextView textView6 = (TextView) findViewById(R.id.textView6);
        final TextView textView7 = (TextView) findViewById(R.id.textView7);
        Button bNext = (Button) findViewById(R.id.bnextButton);

        onoffSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    textView1.setText("Full bar");

                } else {
                    textView1.setText("none");

                }

            }
        });


        onoffSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    textView2.setText("Outdoor Seating");

                } else {
                    textView2.setText("none");

                }

            }
        });

        onoffSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    textView3.setText("Craft seer");

                } else {
                    textView3.setText("none");

                }

            }
        });

        onoffSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    textView4.setText("Nightlife");

                } else {
                    textView4.setText("none");

                }

            }
        });


        onoffSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    textView5.setText("Wireless internet");

                } else {
                    textView5.setText("none");

                }

            }
        });


        onoffSwitch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    textView6.setText("Air conditioning");

                } else {
                    textView6.setText("none");

                }

            }
        });


        onoffSwitch7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    textView7.setText("Cash Payment");

                } else {
                    textView7.setText("none");

                }

            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationfeaturefullbar = textView1.getText().toString();
                String locationfeatureoutdoor = textView2.getText().toString();
                String locationfeaturecraft = textView3.getText().toString();
                String locationfeaturenightlife = textView4.getText().toString();
                String locationfeaturewireless = textView5.getText().toString();
                String locationfeatureair = textView6.getText().toString();
                String locationfeaturecash = textView7.getText().toString();
                Intent intent = new Intent(getApplicationContext(), AddNewLocationDescribe.class);
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
                startActivity(intent);
            }
        });
    }

}
