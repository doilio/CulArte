package com.doiliomatsinhe.cularte.ui.category;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.doiliomatsinhe.cularte.data.Repository;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    public CategoryViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (CategoryViewModel.class.isAssignableFrom(modelClass)) {
            return (T) new CategoryViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class!");
    }
}
