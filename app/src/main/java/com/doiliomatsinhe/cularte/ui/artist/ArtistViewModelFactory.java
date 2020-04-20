package com.doiliomatsinhe.cularte.ui.artist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.doiliomatsinhe.cularte.data.Repository;

public class ArtistViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    public ArtistViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (ArtistViewModel.class.isAssignableFrom(modelClass)) {
           return (T) new ArtistViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class!");
    }
}
