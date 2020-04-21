package com.doiliomatsinhe.cularte.ui.favorite;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.doiliomatsinhe.cularte.data.ArtistDao;
import com.doiliomatsinhe.cularte.data.ArtistDatabase;
import com.doiliomatsinhe.cularte.model.Artist;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {


    private ArtistDao artistDao;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        artistDao = ArtistDatabase.getInstance(application).artistDao();

    }


    public LiveData<List<Artist>> getFavoriteArtists() {
        return artistDao.getAllArtists();
    }
}