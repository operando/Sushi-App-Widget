package com.os.operando.widget.sushi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.widget.RemoteViews;

public class SuShiWidget extends AppWidgetProvider {

    private static final String ACTION_TAP_SUSHI = "com.os.operando.widget.sushi.TAP_SUSHI";

    private static SparseIntArray sparseIntArray = new SparseIntArray();

    private static Intent createIntent(int appWidgetId, int textSize) {
        Intent i = new Intent(ACTION_TAP_SUSHI);
        i.putExtra("appWidgetId", appWidgetId);
        i.putExtra("textSize", textSize);
        return i;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        switch (intent.getAction()) {
            case ACTION_TAP_SUSHI:
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName myWidget = new ComponentName(context, SuShiWidget.class);
                appWidgetManager.getAppWidgetIds(myWidget);
                updateAppWidget(context, appWidgetManager,
                        intent.getIntExtra("appWidgetId", -1),
                        intent.getIntExtra("textSize", -1));
                break;
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        updateAppWidget(context, appWidgetManager, appWidgetId, -1);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int textSize) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sushi_widget);
        if (textSize != -1) {
//            views.setTextViewTextSize(R.id.sushi, TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        StringBuilder sb = new StringBuilder();
        String sushi = context.getString(R.string.sushi);
        int sushiLength2 = sparseIntArray.get(appWidgetId) + 1;
        for (int i = 0; i < sushiLength2; i++) {
            sb.append(sushi);
        }
        views.setTextViewText(R.id.sushi, sb.toString());
        sparseIntArray.append(appWidgetId, sushiLength2);

        Intent intent = createIntent(appWidgetId, textSize);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
        views.setOnClickPendingIntent(R.id.sushi, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            sparseIntArray.append(appWidgetId, 1);
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
        for (int id : appWidgetIds) {
            sparseIntArray.delete(id);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}