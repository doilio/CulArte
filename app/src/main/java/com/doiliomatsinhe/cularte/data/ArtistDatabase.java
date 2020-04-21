package com.doiliomatsinhe.cularte.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.model.Artist;
import com.doiliomatsinhe.cularte.utils.Converters;

import timber.log.Timber;

@TypeConverters(Converters.class)
@Database(entities = {Artist.class}, version = 1, exportSchema = false)
public abstract class ArtistDatabase extends RoomDatabase {

    public abstract ArtistDao artistDao();

    private static ArtistDatabase INSTANCE;
    private static final String DATABASE_NAME = "favorite_artists";

    public static ArtistDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ArtistDatabase.class) {
                Timber.d(context.getString(R.string.creating_database_instance));
                INSTANCE = Room.databaseBuilder(context,
                        ArtistDatabase.class,
                        DATABASE_NAME)
                        //.allowMainThreadQueries() // just for testing, remove when done
                        .build();
            }
        }
        Timber.d(context.getString(R.string.fetching_database_instance));
        return INSTANCE;
    }
}
