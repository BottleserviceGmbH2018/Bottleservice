package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //Personal

        final Button bRegisterPersonal = (Button) findViewById(R.id.bRegisterPersonal);




        //Company

        final Button bRegisterCompany = (Button) findViewById(R.id.bRegisterCompany);
        final Switch onoffSwitch = (Switch) findViewById(R.id.switcher);

        bRegisterCompany.setEnabled(false);

        //Switch
        onoffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    onoffSwitch.setText("Lounge");
                    bRegisterPersonal.setEnabled(false);
                    bRegisterCompany.setEnabled(true);
                } else {
                    onoffSwitch.setText("Client");
                    bRegisterCompany.setEnabled(false);
                    bRegisterPersonal.setEnabled(true);
                }

            }
        });



        //Personal


        bRegisterPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_type = 2;
                if (onoffSwitch.getText().toString() == "Client") {
                    user_type = Integer.parseInt(onoffSwitch.getTextOff().toString());
                } else if (onoffSwitch.getText().toString() == "Lounge") {
                    user_type = Integer.parseInt(onoffSwitch.getTextOn().toString());
                }
                Intent intent = new Intent(getApplicationContext(), signupregister.class);
                intent.putExtra("user_type", user_type);
                startActivity(intent);

            }
        });




        //Company




        bRegisterCompany.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    int user_type = 2;
                    if (onoffSwitch.getText().toString() == "Client") {
                        user_type = Integer.parseInt(onoffSwitch.getTextOff().toString());
                    } else if (onoffSwitch.getText().toString() == "Lounge") {
                        user_type = Integer.parseInt(onoffSwitch.getTextOn().toString());
                    }
                    Intent intent = new Intent(getApplicationContext(), signupcompany.class);
                    intent.putExtra("user_type", user_type);
                    startActivity(intent);

                }
        });
    }



    public void buttonViewMain(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(Intent);
    }

}
