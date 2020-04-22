package com.doiliomatsinhe.cularte.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.model.Artist;
import com.doiliomatsinhe.cularte.utils.Utils;

import timber.log.Timber;

import static com.doiliomatsinhe.cularte.ui.favorite.FavoriteFragment.ARTIST_PREF;
import static com.doiliomatsinhe.cularte.ui.favorite.FavoriteFragment.FAVORITES;

/**
 * Implementation of App Widget functionality.
 */
public class ArtistWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent serviceIntent = new Intent(context, ArtistWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.artist_widget);
        views.setRemoteAdapter(R.id.widget_linear, serviceIntent);
        views.setEmptyView(R.id.widget_linear, R.id.empty_widget);

        SharedPreferences sharedPref = context.getSharedPreferences(ARTIST_PREF, Context.MODE_PRIVATE);
        String artistJSON = sharedPref.getString(FAVORITES, "");
        Artist artist = (Artist) Utils.getObjectFromJson(artistJSON, Artist.class);
        if (artist != null) {
            if (artist.getNomeArtistico().isEmpty()) {
                Timber.d(artist.getNomeCompleto());
            } else {
                Timber.d(artist.getNomeArtistico());
            }
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            // Ingredients
            Intent serviceIntent = new Intent(context, ArtistWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            // Get the Views
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.artist_widget);
            views.setRemoteAdapter(R.id.widget_linear, serviceIntent);
            views.setEmptyView(R.id.widget_linear, R.id.empty_widget);


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
}

