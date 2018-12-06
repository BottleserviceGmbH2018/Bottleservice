package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddGuestLocationPrice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest_location_price);
    }

    public void buttonAddGuestLocationCalendarBack(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocationCalendar.class);
        startActivity(Intent);
        this.finish();
    }


    public void buttonAddGuestLocationBack(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocation.class);
        startActivity(Intent);
        this.finish();
    }


    public void buttonAddGuestLocationPublish(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocationPublish.class);
        startActivity(Intent);
        this.finish();
    }
}
