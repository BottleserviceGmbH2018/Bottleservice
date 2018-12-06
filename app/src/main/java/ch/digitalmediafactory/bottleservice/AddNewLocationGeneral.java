package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewLocationGeneral extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter;
    int counter = 0;
    EditText tGuest;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location_general);


        tGuest = (EditText) findViewById(R.id.tGuests);
        ImageButton ibPositive = (ImageButton) findViewById(R.id.ibPositive);
        ImageButton ibNegative = (ImageButton) findViewById(R.id.ibNegative);
        Button bNext = (Button) findViewById(R.id.next);
        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0, true);
        View v = spinner.getSelectedView();
        ((TextView)v).setTextColor(Color.WHITE);


        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String locationtype = spinner.getSelectedItem().toString();
                String numberguest = tGuest.getText().toString();
                Intent intent = new Intent(getApplicationContext(), AddNewLocationAddress.class);
                intent.putExtra("locationtype", locationtype);
                intent.putExtra("companyname", numberguest);
                startActivity(intent);


            }
        });

        ibPositive.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub

                counter++;
                if(counter<0){
                    counter=0;
                }
                if(Integer.parseInt(tGuest.getText().toString())>counter){
                    counter=Integer.parseInt(tGuest.getText().toString());
                    counter++;
                }
                if(Integer.parseInt(tGuest.getText().toString())<counter){
                    counter=Integer.parseInt(tGuest.getText().toString());
                    counter++;
                }
                tGuest.setText(Integer.toString(counter));

                return true;
            }
        });

        ibNegative.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                counter--;
                if(counter<0){
                    counter=0;
                }
                if(Integer.parseInt(tGuest.getText().toString())>counter){
                    counter=Integer.parseInt(tGuest.getText().toString());
                    counter--;
                }
                if(Integer.parseInt(tGuest.getText().toString())<counter){
                    counter=Integer.parseInt(tGuest.getText().toString());
                    counter--;
                }
                tGuest.setText(Integer.toString(counter));

                return true;
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void countIN(View view){
        counter++;
        if(counter<0){
            counter=0;
        }
        if(Integer.parseInt(tGuest.getText().toString())>counter){
            counter=Integer.parseInt(tGuest.getText().toString());
            counter++;
        }
        if(Integer.parseInt(tGuest.getText().toString())<counter){
            counter=Integer.parseInt(tGuest.getText().toString());
            counter++;
        }
        tGuest.setText(Integer.toString(counter));
    }


    public void countDown(View view){
        counter--;
        if(counter<0){
            counter=0;
        }
        if(Integer.parseInt(tGuest.getText().toString())>counter){
            counter=Integer.parseInt(tGuest.getText().toString());
            counter--;
        }
        if(Integer.parseInt(tGuest.getText().toString())<counter){
            counter=Integer.parseInt(tGuest.getText().toString());
            counter--;
        }
        tGuest.setText(Integer.toString(counter));
    }






}
