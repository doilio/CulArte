package com.doiliomatsinhe.cularte.ui.artistDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.doiliomatsinhe.cularte.data.ArtistDao;
import com.doiliomatsinhe.cularte.data.ArtistDatabase;
import com.doiliomatsinhe.cularte.model.Artist;

public class ArtistDetailViewModel extends AndroidViewModel {

    private LiveData<Artist> favoriteArtist;

    public LiveData<Artist> getFavoriteArtist() {
        return favoriteArtist;
    }

    public ArtistDetailViewModel(@NonNull Application application, String id) {
        super(application);

        ArtistDao database = ArtistDatabase.getInstance(application).artistDao();

        favoriteArtist = database.getArtistById(id);
    }

}
