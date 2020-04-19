package com.doiliomatsinhe.cularte.ui.artist;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doiliomatsinhe.cularte.data.Repository;
import com.doiliomatsinhe.cularte.model.Artist;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class ArtistViewModel extends ViewModel {

    private MutableLiveData<List<Artist>> _artists = new MutableLiveData<>();
    //public LiveData<List<Category>> categories = _categories;
    private Repository repository;

    public ArtistViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Artist>> readArtists(String categoryName) {

        repository.readArtists(categoryName).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    _artists.setValue(null);
                    Timber.d(e);
                    return;
                }

                List<Artist> artistList = new ArrayList<>();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(queryDocumentSnapshots)) {
                    Artist artist = document.toObject(Artist.class);
                    artistList.add(artist);
                }
                _artists.setValue(artistList);

            }
        });

        return _artists;
    }
}
