package com.example.venky.capstoneproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import com.example.venky.capstoneproject.Activities.MainActivity;
import com.example.venky.capstoneproject.Activities.MealDetailsActivity;
import com.squareup.picasso.Picasso;

/**
 * Implementation of App Widget functionality.
 */
public class MealAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MealDetailsActivity.PREFERENCE_KEY,0);
        String image = sharedPreferences.getString(MealDetailsActivity.MEAL_IMAGE_KEY,"DATA NOT FOUND");
        String meal_name = sharedPreferences.getString(MealDetailsActivity.MEAL_NAME_KEY,"RECIPE_NAME NOT AVAILABLE");

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.meal_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.appwidget_text,meal_name);
        Picasso.with(context).load(image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(views, R.id.appwidget_image, new int[]{appWidgetId});
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widget_host,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

