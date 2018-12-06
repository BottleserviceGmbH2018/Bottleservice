package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddGuestLocation extends AppCompatActivity {

    String titlelocation, locationtype, specialtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest_location);

        Intent intent = getIntent();
        titlelocation = intent.getStringExtra("titlelocation");
        locationtype = intent.getStringExtra("locationtype");
        specialtitle = intent.getStringExtra("specialtitle");
    }

    public void buttonAddGuestLocationAvail(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocationAvail.class);
        startActivity(Intent);

    }

    public void buttonBarLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeLocation.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonAddLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddNewLocationGeneral.class);
        startActivity(Intent);

    }

    public void buttonSetLocation(View v)
    {
        Intent intent = new Intent(getApplicationContext(), AddSetLocationPhoto.class);
        intent.putExtra("titlelocation", titlelocation);
        intent.putExtra("locationtype", locationtype);
        intent.putExtra("specialtitle", specialtitle);
        startActivity(intent);

    }





}
