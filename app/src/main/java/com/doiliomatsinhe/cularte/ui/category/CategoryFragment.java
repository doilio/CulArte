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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.adapter.CategoryAdapter;
import com.doiliomatsinhe.cularte.data.Repository;
import com.doiliomatsinhe.cularte.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private CategoryViewModel viewModel;
    private FragmentCategoryBinding binding;
    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialConfig();
        adapter = new CategoryAdapter();
        binding.recyclerCategory.setAdapter(adapter);
        binding.recyclerCategory.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.recyclerCategory.setLayoutManager(layoutManager);
    }

    private void initialConfig() {
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
