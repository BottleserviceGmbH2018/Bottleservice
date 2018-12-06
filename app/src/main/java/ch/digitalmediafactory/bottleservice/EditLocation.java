package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);
    }


    public void buttonEditLocationGeneral(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddNewLocationGeneral.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonEditLocationPhoto(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddSetLocationPhoto.class);
        startActivity(Intent);
        this.finish();
    }


    public void buttonEditLocationAvail(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocationAvail.class);
        startActivity(Intent);
        this.finish();
    }


    public void buttonEditLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeLocation.class);
        startActivity(Intent);
        this.finish();
    }

}
