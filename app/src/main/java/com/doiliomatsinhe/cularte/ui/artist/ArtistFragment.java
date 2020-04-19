package com.doiliomatsinhe.cularte.ui.artist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.adapter.ArtistAdapter;
import com.doiliomatsinhe.cularte.data.Repository;
import com.doiliomatsinhe.cularte.databinding.FragmentArtistBinding;
import com.doiliomatsinhe.cularte.model.Artist;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ArtistAdapter.ArtistItemClickListener {

    private ArtistViewModel viewModel;
    private FragmentArtistBinding binding;
    private ArtistAdapter adapter;
    private List<Artist> artistList;


    public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArtistBinding.inflate(inflater, container, false);

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
        //TODO get the category name to filter the artists
        viewModel.readArtists("").observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                if (artists.isEmpty()) {
                    // TODO handle this
                } else {
                    adapter.setArtistList(artistList);
                    artistList = artists;
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    private void configAdapter() {
        adapter = new ArtistAdapter(this);
        binding.recyclerArtist.setAdapter(adapter);
        binding.recyclerArtist.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerArtist.setLayoutManager(layoutManager);
    }

    private void initialConfig() {
        binding.swipeRefresh.setOnRefreshListener(this);

        Repository repository = new Repository();
        ArtistViewModelFactory factory = new ArtistViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(ArtistViewModel.class);
        binding.setLifecycleOwner(this);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onArtistItemClick(int position) {

    }
}
