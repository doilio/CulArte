package com.doiliomatsinhe.cularte.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.adapter.CategoryAdapter;
import com.doiliomatsinhe.cularte.data.Repository;
import com.doiliomatsinhe.cularte.databinding.FragmentCategoryBinding;
import com.doiliomatsinhe.cularte.model.Category;

import java.util.List;

public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CategoryAdapter.CategoryItemClickListener {

    private CategoryViewModel viewModel;
    private FragmentCategoryBinding binding;
    private CategoryAdapter adapter;
    private List<Category> categoryList;


    public CategoryFragment() {
        // Required empty public constructor
    }

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
        configAdapter();

        retrieveCategories();

    }

    private void retrieveCategories() {
        binding.swipeRefresh.setRefreshing(true);
        viewModel.readCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories.isEmpty()) {
                    // TODO handle this
                } else {
                    adapter.setCategoryList(categories);
                    categoryList = categories;
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    private void configAdapter() {
        adapter = new CategoryAdapter(this);
        binding.recyclerCategory.setAdapter(adapter);
        binding.recyclerCategory.setHasFixedSize(true);
        //TODO replace with GridLayout to control the span on configuration changes
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
        retrieveCategories();
    }

    @Override
    public void onCategoryItemClick(int position) {
        Category category = categoryList.get(position);
        // Toast.makeText(getActivity(), category.getNome(), Toast.LENGTH_SHORT).show();
        //  Pass an argument categoryName.
        String categoryName = category.getNome();
        NavDirections action = CategoryFragmentDirections.actionCategoryFragmentToArtistFragment(categoryName);
        NavHostFragment.findNavController(this).navigate(action);
    }
}
