package com.example.userdata.Widgets

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.userdata.MainActivity
import com.example.userdata.R
import com.example.modelresp.UserResp
import com.example.userdata.viewModel.M1ViewModel

/**
 * Implementation of App Widget functionality.
 */
class ExampleAppWidgetProvider : AppWidgetProvider() {
    lateinit var m1ViewModel: M1ViewModel
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            m1ViewModel.UserResp.value?.let { userResp ->
                updateAppWidget(context, appWidgetManager, appWidgetId, userResp)

            }
        }

    }

    @SuppressLint("RemoteViewLayout")
    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, userResp: UserResp) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
        remoteViews.setTextViewText(R.id.appwidget_text, "User Followers: ${userResp.userFollowers}")
       // remoteViews.setTextViewText(R.id.appwidget_text2, "User Following: ${userResp.userFollowing}")
        // Set click event to open the app
        remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
       // remoteViews.setOnClickPendingIntent(R.id.appwidget_text2, pendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }


    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

