package ch.digitalmediafactory.bottleservice;


import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddSetLocationHours extends AppCompatActivity {


    Calendar currentTime;
    int hour, minute;
    String format;
    String titlelocation, locationtype, specialtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_location_hours);

        final TextView textview = findViewById(R.id.MondayText1);
        final TextView textview1 = findViewById(R.id.MondayText2);

        final TextView textview2 = findViewById(R.id.TuesdayText1);
        final TextView textview3 = findViewById(R.id.TuesdayText2);

        final TextView textview4 = findViewById(R.id.WednesdayText1);
        final TextView textview5 = findViewById(R.id.WednesdayText2);

        final TextView textview6 = findViewById(R.id.ThursdayText1);
        final TextView textview7 = findViewById(R.id.ThursdayText2);

        final TextView textview8 = findViewById(R.id.FridayText1);
        final TextView textview9 = findViewById(R.id.FridayText2);

        final TextView textview10 = findViewById(R.id.SaturdayText1);
        final TextView textview11 = findViewById(R.id.SaturdayText2);

        final TextView textview12 = findViewById(R.id.SundayText1);
        final TextView textview13 = findViewById(R.id.SundayText2);


        Intent intent = getIntent();
        titlelocation = intent.getStringExtra("titlelocation");
        locationtype = intent.getStringExtra("locationtype");
        specialtitle = intent.getStringExtra("specialtitle");

        currentTime = Calendar.getInstance();

        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        selectedTimeFormat(hour);

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview1.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview2.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview3.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview3.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        textview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview4.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        textview5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview5.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview6.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview7.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview8.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview9.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });



        textview10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview10.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview11.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview12.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        textview13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSetLocationHours.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        textview13.setText(hourOfDay + " : " + minute + " " + format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });



    }

    public void selectedTimeFormat(int hour){
        if(hour == 0){
            hour += 12;
            format = "AM";
        }else if(hour == 12){
            format = "PM";
        }else if(hour > 12){
            hour -= 12;
            format = "PM";
        }else {
            format = "AM";
        }

    }


    public void buttonAddGuestLocation(View v)
    {
        Intent intent = new Intent(getApplicationContext(), AddGuestLocation.class);
        intent.putExtra("titlelocation", titlelocation);
        intent.putExtra("locationtype", locationtype);
        intent.putExtra("specialtitle", specialtitle);
        startActivity(intent);
    }


}
