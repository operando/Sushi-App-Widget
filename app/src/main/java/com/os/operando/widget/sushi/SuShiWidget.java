package com.os.operando.widget.sushi;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;

public class SuShiWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        updateAppWidget(context, appWidgetManager, appWidgetId, -1);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int textSize) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sushi_widget);
        if (textSize != -1) {
            views.setTextViewTextSize(R.id.sushi, TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        int maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        updateAppWidget(context, appWidgetManager, appWidgetId, Math.min(maxWidth, maxHeight));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}