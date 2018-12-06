package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class AddGuestLocationCalendar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest_location_calendar);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);


        CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                String selectedDate = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                        + " " + (calSelected.get(Calendar.MONTH) + 1)
                        + " " + calSelected.get(Calendar.YEAR);

                Toast.makeText(AddGuestLocationCalendar.this, selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public void buttonAddGuestLocationBack(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocation.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonAddGuestLocationPrice(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocationPrice.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonAddGuestLocationAvailBack(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddGuestLocationAvail.class);
        startActivity(Intent);
        this.finish();
    }

}
