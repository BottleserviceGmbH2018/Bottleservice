package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddNewLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location);
    }

    public void buttonViewLocation(View v)
    {
        this.finish();
    }
    public void buttonViewAddNewLocationGeneral(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddNewLocationGeneral.class);
        startActivity(Intent);
    }
}
