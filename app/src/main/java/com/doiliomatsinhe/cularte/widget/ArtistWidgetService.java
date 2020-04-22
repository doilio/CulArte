package com.doiliomatsinhe.cularte.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.model.Artist;
import com.doiliomatsinhe.cularte.utils.Utils;


import static com.doiliomatsinhe.cularte.ui.favorite.FavoriteFragment.ARTIST_PREF;
import static com.doiliomatsinhe.cularte.ui.favorite.FavoriteFragment.FAVORITES;

public class ArtistWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ArtistWidgetItemFactory(getApplication(), intent);
    }

    private class ArtistWidgetItemFactory implements RemoteViewsFactory {
        private Context context;
        private Artist artist;


        ArtistWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            SystemClock.sleep(4000);
        }

        @Override
        public void onDataSetChanged() {

            SharedPreferences sharedPref = context.getSharedPreferences(ARTIST_PREF, Context.MODE_PRIVATE);
            String favoriteJSON = sharedPref.getString(FAVORITES, "");
            artist = (Artist) Utils.getObjectFromJson(favoriteJSON, Artist.class);
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.artist_widget_item);
            if (artist != null) {
                if (artist.getNomeArtistico().isEmpty()) {
                    views.setTextViewText(R.id.widget_name, artist.getNomeCompleto());
                } else {
                    views.setTextViewText(R.id.widget_name, artist.getNomeArtistico());
                }
                if (artist.getBiografia().isEmpty()){
                    views.setTextViewText(R.id.widget_story, getString(R.string.story_not_available));
                }else{
                    views.setTextViewText(R.id.widget_story, artist.getBiografia());
                }
            }

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
