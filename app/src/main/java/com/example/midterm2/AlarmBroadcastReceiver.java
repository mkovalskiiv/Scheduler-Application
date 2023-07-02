package com.example.midterm2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public AlarmBroadcastReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Notification worked.", Toast.LENGTH_LONG).show();
        String nameToPass = intent.getStringExtra("intentName");    //get name of appointment
        createNotif(context, nameToPass);
    }//onReceive

    public void createNotif(Context context, String name) {
        //Toast.makeText(context, "Appt: " + name, Toast.LENGTH_LONG).show(); //DEBUG

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "myNotifChannelID")    //
                .setSmallIcon(R.drawable.mk_watermark)                                                              //
                .setContentTitle("Appointment notification:")                                                       // initialize
                .setContentText(name)                                                                               // notification
                .setStyle(new NotificationCompat.BigTextStyle().bigText(name))                                      //
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);                                                  //

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "myNotifChannel";
            String description = "what's to say?";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("myNotifChannelID", channelName, importance);
            channel.setDescription(description);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(117, builder.build());
        }// create notification channel

    }//createNotif

}//AlarmBroadcastReceiver
