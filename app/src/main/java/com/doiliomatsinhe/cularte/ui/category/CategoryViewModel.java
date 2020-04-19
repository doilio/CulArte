package com.doiliomatsinhe.cularte.ui.category;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doiliomatsinhe.cularte.data.Repository;
import com.doiliomatsinhe.cularte.model.Category;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<List<Category>> _categories = new MutableLiveData<>();
    //public LiveData<List<Category>> categories = _categories;
    private Repository repository;

    public CategoryViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Category>> readCategories() {

        repository.readCategories().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    _categories.setValue(null);
                    Timber.d(e);
                    return;
                }

                List<Category> categoryList = new ArrayList<>();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(queryDocumentSnapshots)) {
                    Category category = document.toObject(Category.class);
                    categoryList.add(category);
                }
                _categories.setValue(categoryList);

            }
        });

        return _categories;
    }

}