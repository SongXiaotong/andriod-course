package com.example.a12524.experimentone_week6;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

public class DynamicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("collect")) {

            Bundle bundle = intent.getExtras();
            listitem food = (listitem) bundle.getSerializable("food");
            Log.e("dynamic", food.getName());
            Bitmap bm= BitmapFactory.decodeResource(context.getResources(),R.mipmap.collect);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String channel_id = "channel_2";
            NotificationChannel channel = null;
            if(channel == null){
                String name = "my_channe2";
                String des = "channel_packet";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                channel = new NotificationChannel(channel_id, name, importance);
                channel.setDescription(des);
                channel.enableLights(true);
                manager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id);
            builder.setContentTitle("已收藏")
                    .setContentText(food.getName())
                    .setTicker("您有一条新消息")
                    .setWhen(System.currentTimeMillis())
                    .setLargeIcon(bm)
                    .setSmallIcon(R.mipmap.collect)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true);
            Intent mInent = new Intent(context,MainActivity.class);
            mInent.putExtras(bundle);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, mInent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(mPendingIntent);
            Notification notify = builder.build();
            manager.notify(1, notify);
        }
    }
}
