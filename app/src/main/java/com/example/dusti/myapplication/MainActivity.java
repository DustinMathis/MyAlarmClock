package com.example.dusti.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static java.util.Calendar.getInstance;


public class MainActivity extends AppCompatActivity {
//make our alarm manager variable
    AlarmManager alarm_manager;
    TimePicker  alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        //initialize alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //intialize our timepicker

        alarm_timepicker = findViewById(R.id.timePicker);

        //initalize our text update box

        update_text = findViewById(R.id.update_text);

        //create an instance of a calendar
        final Calendar calendar = Calendar.getInstance();

        //intialize the start button
        Button alarm_on = findViewById(R.id.alarm_on);


        // create an intent to the alarm receiver class
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

        //initalize the stop button
        Button alarm_off = findViewById(R.id.alarm_off);
        //create an onclick listener to undo alarm

        //create an onclick listener to start the alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // set the calendar with the hour and minute that we picked on the timepicker
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                //get the int value of the hour and minute

                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                //change the int to string

                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                //convert 24 hour to 12 hour time

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                //convert minutes to add the 0

                if (minute < 10){
                    minute_string = "0" + String.valueOf(minute);
                }

                //set the text box to show time of alarm on


                set_alarm_text("Alarm set to " + hour_string + ":" + minute_string);

                //put in extra string into my intent, tells the clock you pressed the alarm on

                my_intent.putExtra("extra", "alarm on");

                //create a pending intent that delays that delays the intent until the specified time
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0 ,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);
            }
        });

        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // method that changes update text box
                set_alarm_text("alarm off");
                //cancel the alarm
                alarm_manager.cancel(pending_intent);

                //put extra string into my_intent tells the clock you pressed the "alarm off" button
                my_intent.putExtra("extra", "alarm off");

                //stop the ringtone
                sendBroadcast(my_intent);
            }
        });



    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }
}
