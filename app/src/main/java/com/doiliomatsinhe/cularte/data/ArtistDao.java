package com.doiliomatsinhe.cularte.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.doiliomatsinhe.cularte.model.Artist;

import java.util.List;

@Dao
public interface ArtistDao {

    @Query("SELECT * FROM artists ORDER BY id")
    LiveData<List<Artist>> getAllArtists();

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert //Test without conflictStrategy
    void addFavorite(Artist artist);

    @Delete
    void removeFavorite(Artist artist);

}
