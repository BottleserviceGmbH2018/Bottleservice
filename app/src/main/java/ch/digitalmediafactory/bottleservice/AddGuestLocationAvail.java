package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddGuestLocationAvail extends AppCompatActivity {
    Spinner spinnerAvailDate, spinnerAvailTime;
    ArrayAdapter<CharSequence> adapterDate, adapterTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest_location_avail);
        spinnerAvailDate = (Spinner) findViewById(R.id.spinneravail);
        spinnerAvailTime = (Spinner) findViewById(R.id.spinneravailtime);


        adapterTime = ArrayAdapter.createFromResource(this, R.array.availtime, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAvailTime.setAdapter(adapterTime);


        adapterDate = ArrayAdapter.createFromResource(this, R.array.availdates, android.R.layout.simple_spinner_item);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAvailDate.setAdapter(adapterDate);

        spinnerAvailDate.setSelection(0, true);
        View viewDate = spinnerAvailDate.getSelectedView();
        spinnerAvailTime.setSelection(0, true);
        View viewTime = spinnerAvailTime.getSelectedView();
        ((TextView)viewDate).setTextColor(Color.WHITE);
        ((TextView)viewTime).setTextColor(Color.WHITE);




        spinnerAvailDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position) +" selected",Toast.LENGTH_SHORT).show();
                ((TextView) view).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerAvailTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position) +" selected",Toast.LENGTH_SHORT).show();
                ((TextView) view).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void buttonAddGuestLocationBack(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocation.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonAddGuestLocationCalendar(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocationCalendar.class);
        startActivity(Intent);
        this.finish();
    }

}
