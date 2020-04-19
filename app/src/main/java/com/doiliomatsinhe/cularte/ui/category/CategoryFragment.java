package com.doiliomatsinhe.cularte.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.data.Repository;
import com.doiliomatsinhe.cularte.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private CategoryViewModel viewModel;
    private FragmentCategoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeRefresh.setOnRefreshListener(this);

        Repository repository = new Repository();
        CategoryViewModelFactory factory = new CategoryViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(CategoryViewModel.class);
        binding.setLifecycleOwner(this);
    }

    @Override
    public void onRefresh() {

    }
}
