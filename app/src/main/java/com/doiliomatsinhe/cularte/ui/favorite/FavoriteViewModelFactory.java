package com.doiliomatsinhe.cularte.ui.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FavoriteViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public FavoriteViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (FavoriteViewModel.class.isAssignableFrom(modelClass)) {
            return (T) new FavoriteViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class!");

    }
}
