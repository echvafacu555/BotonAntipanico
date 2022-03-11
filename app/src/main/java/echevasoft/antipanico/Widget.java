package echevasoft.antipanico;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import echevasoft.antipanico.R;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
          int imagen= R.drawable.alerta;


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
      //  views.setTextViewText(R.id.imageView2, widgetText);
        views.setImageViewResource(R.id.widget,imagen );
    }




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            int imagen=R.drawable.alerta;


            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            //  views.setTextViewText(R.id.imageView2, widgetText);
            views.setImageViewResource(R.id.widget,imagen );



            //int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("EmitirAlerta", 1);



            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views); }

        }



    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }




}

