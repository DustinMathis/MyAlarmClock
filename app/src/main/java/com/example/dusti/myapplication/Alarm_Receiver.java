package com.example.dusti.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("In the reciever", "yay");

        //fetch extra strings from the intent
        String get_your_string = intent.getExtras().getString("extra");

        //create an intent to the rington service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        //pass the extra string from Main Activity to the RIntone playing service
        service_intent.putExtra("extra", get_your_string);

        //start the rington service
        context.startService(service_intent);
    }
}
