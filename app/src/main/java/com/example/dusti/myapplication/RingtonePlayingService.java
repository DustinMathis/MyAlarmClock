package com.example.dusti.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import static android.app.Notification.*;


public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        //fetch the extra string values
        String state = intent.getExtras().getString("extra");









        //this converts the extra strings from the intent to start IDS, values 0 or 1

        assert state != null;
        switch(state){
            case "alarm on":
                startId = 1;
            break;
            case "alarm off":
                startId = 0;
            break;
            default:
                startId = 0;
                break;
        }

        //if else statements
        // if there is no music playing and the user pressed alarm on
        //music should start playing
        if(!this.isRunning && startId == 1) {
            media_song = MediaPlayer.create(this, R.raw.zapsplat_cartoon_bird_woodpecker_002_17783);
            media_song.start();
            this.isRunning = true;
            this.startId = 0;

            //NOTIFICATION

            //set up the notification manager
            NotificationManager notify_manager = (NotificationManager)
                    this.getSystemService(this.NOTIFICATION_SERVICE);

            //set up an intent that goes to the main activity
            Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);

            //set up a pending intent
            PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0 , intent_main_activity, 0);


            //make the notification parameters

            Notification notification_popup = new Builder(this)
                    .setContentTitle("Your alarm is going off!!")
                    .setContentText("Click me!")
                    .setContentIntent(pending_intent_main_activity)
                    .setAutoCancel(true)
                    .build();

            //set up the notification call command
            notify_manager.notify(0, notification_popup);

        }
        // if there is music playing and the user pressed alarm off
        //music should stop playing

        else if(this.isRunning && startId == 0){
            //stop the ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;
        }
        //these are if the user presses random buttons
        //if there is no music playing and user presses alarm off
        //do nothing
        else if (!this.isRunning && startId == 0){
            this.isRunning = false;
            this.startId = 0;


        }
        //if there is music playing and alarm on button is pressed
        //do nothing
        else if(this.isRunning && startId ==1){
        this.isRunning = true;
        this.startId = 1;
        }
        else{
        Log.e("i have no idea", "Im not sure how you got here.");
        }







        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.e("on destroy called", "it worked!");
        super.onDestroy();
        this.isRunning = false;

    }

}

