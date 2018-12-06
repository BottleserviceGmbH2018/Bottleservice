package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddSetLocationSpecific extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_location_specific);

        Intent intent = getIntent();
        final String titlelocation = intent.getStringExtra("titlelocation");
        final String locationtype = intent.getStringExtra("locationtype");
        ImageButton bAddSpecific = (ImageButton) findViewById(R.id.bAddSpecific);



        bAddSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddSetLocationSpecificDetails.class);
                intent.putExtra("titlelocation", titlelocation);
                intent.putExtra("locationtype", locationtype);
                startActivity(intent);
            }
        });



    }


    public void buttonViewAddSetLocationHours(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddSetLocationHours.class);
        startActivity(Intent);

    }
}
