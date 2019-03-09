package com.example.a12524.experimentone_week6;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for(int m = -0; m < N; ++m){
            RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局
            Intent i = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 200, i, PendingIntent.FLAG_UPDATE_CURRENT);
            updateView.setOnClickPendingIntent(R.id.appwidget_text, pi); //设置点击事件
            ComponentName me = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(me, updateView);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals("start")){
            Log.e("NewAppWidget", "call-static!");
            Bundle bundle = intent.getExtras();
            listitem food = (listitem) bundle.getSerializable("food");
            Log.e("NewAppWidget", food.getName());
            RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局
            updateView.setTextViewText(R.id.appwidget_text, "今日推荐 " + food.getName());
            Intent ListIntent = new Intent(context, ListActions.class);
            ListIntent.putExtras(bundle);
            PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0, ListIntent, 0);
            updateView.setOnClickPendingIntent(R.id.appwidget_text,myPendingIntent);
            ComponentName cn = new ComponentName(context, NewAppWidget.class);
            int[]appWidgetIds = appWidgetManager.getAppWidgetIds(cn);
            for(int appwidget : appWidgetIds){
                appWidgetManager.updateAppWidget(cn, updateView);
            }
        }
        if(intent.getAction().equals("collect")){
            Log.e("NewAppWidget", "call-dyna!");
            Bundle bundle = intent.getExtras();
            listitem food = (listitem) bundle.getSerializable("food");
            Log.e("NewAppWidget", food.getName());
            RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局
            updateView.setTextViewText(R.id.appwidget_text, "已收藏 " + food.getName());
            Intent ListIntent = new Intent(context, MainActivity.class);
            ListIntent.putExtras(bundle);
            PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0, ListIntent, 0);
            ListIntent.putExtra("change", "true");
            updateView.setOnClickPendingIntent(R.id.appwidget_text,myPendingIntent);
            ComponentName cn = new ComponentName(context, NewAppWidget.class);
            int[]appWidgetIds = appWidgetManager.getAppWidgetIds(cn);
            appWidgetManager.updateAppWidget(cn, updateView);
            for(int appwidget : appWidgetIds){
                appWidgetManager.updateAppWidget(cn, updateView);
            }
        }
    }
}

