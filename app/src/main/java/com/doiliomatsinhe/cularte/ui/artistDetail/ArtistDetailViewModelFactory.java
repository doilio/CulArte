package com.doiliomatsinhe.cularte.ui.artistDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ArtistDetailViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String id;

    public ArtistDetailViewModelFactory(Application application, String id) {
        this.application = application;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ArtistDetailViewModel.class)) {
            return (T) new ArtistDetailViewModel(application, id);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class!");
    }
}
