package com.doiliomatsinhe.cularte.ui.favorite;

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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.doiliomatsinhe.cularte.adapter.ArtistAdapter;
import com.doiliomatsinhe.cularte.databinding.FragmentFavoriteBinding;
import com.doiliomatsinhe.cularte.model.Artist;

import java.util.List;

public class FavoriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ArtistAdapter.ArtistItemClickListener {

    private FavoriteViewModel viewModel;
    private FragmentFavoriteBinding binding;
    private ArtistAdapter adapter;
    private List<Artist> favoritesList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialConfig();
        configAdapter();

        retrieveArtists();
    }

    private void retrieveArtists() {
        binding.swipeRefresh.setRefreshing(true);
        viewModel.getFavoriteArtists().observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                if (artists.isEmpty()) {
                    showEmptyLayout();
                } else {
                    adapter.setArtistList(artists);
                    favoritesList = artists;
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        });

    }

    private void showEmptyLayout() {
        binding.swipeRefresh.setRefreshing(false);
        binding.exceptionsLayout.layoutFavorites.setVisibility(View.VISIBLE);
        binding.recyclerFavorites.setVisibility(View.GONE);
    }

    private void initialConfig() {
        binding.swipeRefresh.setOnRefreshListener(this);

        FavoriteViewModelFactory factory = new FavoriteViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(FavoriteViewModel.class);
        binding.setLifecycleOwner(this);
    }

    private void configAdapter() {
        adapter = new ArtistAdapter(this);
        binding.recyclerFavorites.setAdapter(adapter);
        binding.recyclerFavorites.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerFavorites.setLayoutManager(layoutManager);
    }

    @Override
    public void onRefresh() {
        retrieveArtists();
    }

    @Override
    public void onArtistItemClick(int position) {
        Toast.makeText(getActivity(), favoritesList.get(position).getNomeCompleto(), Toast.LENGTH_SHORT).show();
    }
}
