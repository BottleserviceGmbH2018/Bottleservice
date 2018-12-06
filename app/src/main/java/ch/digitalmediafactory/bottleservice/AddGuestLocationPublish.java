package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddGuestLocationPublish extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest_location_publish);



       final Button mLocationSendReqBtn = (Button) findViewById(R.id.bPublish);

       mLocationSendReqBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent Intent = new Intent(getApplicationContext(), LoungeLocation.class);
               startActivity(Intent);
               finish();
               mLocationSendReqBtn.setEnabled(false);
           }
       });
    }





    public void buttonEditLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditLocation.class);
        startActivity(Intent);
        this.finish();
    }
}
