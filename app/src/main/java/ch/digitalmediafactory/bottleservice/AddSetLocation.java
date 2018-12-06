package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddSetLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_location);

        Intent intent = getIntent();
        final String titlelocation = intent.getStringExtra("titlelocation");
        final String locationtype = intent.getStringExtra("locationtype");

        Button setVenue= (Button) findViewById(R.id.bSetVenue);

        setVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddSetLocationPhoto.class);
                intent.putExtra("titlelocation", titlelocation);
                intent.putExtra("locationtype", locationtype);
                startActivity(intent);

            }
        });
    }

    public void buttonViewLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeLocation.class);
        startActivity(Intent);
        this.finish();
    }
    public void buttonViewAddNewLocationGeneral(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddNewLocationGeneral.class);
        startActivity(Intent);
    }

}
