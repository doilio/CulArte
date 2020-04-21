package com.doiliomatsinhe.cularte.ui.favorite;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doiliomatsinhe.cularte.data.ArtistDao;
import com.doiliomatsinhe.cularte.data.ArtistDatabase;
import com.doiliomatsinhe.cularte.model.Artist;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    //private LiveData<List<Artist>> favoriteArtists;
    private ArtistDao artistDao;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        artistDao = ArtistDatabase.getInstance(application).artistDao();

       // favoriteArtists = artistDao.getAllArtists();

    }


    public LiveData<List<Artist>> getFavoriteArtists() {
        return artistDao.getAllArtists();
    }
}