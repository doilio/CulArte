package com.doiliomatsinhe.cularte.ui.artist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doiliomatsinhe.cularte.adapter.ArtistAdapter;
import com.doiliomatsinhe.cularte.data.Repository;
import com.doiliomatsinhe.cularte.databinding.FragmentArtistBinding;
import com.doiliomatsinhe.cularte.model.Artist;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ArtistAdapter.ArtistItemClickListener {

    private ArtistViewModel viewModel;
    private FragmentArtistBinding binding;
    private ArtistAdapter adapter;
    private List<Artist> artistList;
    private String categoryName;


    public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArtistBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            ArtistFragmentArgs args = ArtistFragmentArgs.fromBundle(getArguments());
            categoryName = args.getCategoryName();
        }

        if (getActivity() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(categoryName);
        }


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
        viewModel.readArtists(categoryName).observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                if (artists.isEmpty()) {
                    showEmptyLayout();

                } else {
                    adapter.setArtistList(artists);
                    artistList = artists;
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    private void showEmptyLayout() {
        binding.swipeRefresh.setRefreshing(false);
        binding.exceptionsLayout.layoutEmpty.setVisibility(View.VISIBLE);
        binding.recyclerArtist.setVisibility(View.GONE);
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
        retrieveArtists();
    }

    @Override
    public void onArtistItemClick(int position) {
        Artist currentArtist = artistList.get(position);
        NavDirections action = ArtistFragmentDirections.actionArtistFragmentToArtistDetailFragment(currentArtist);
        NavHostFragment.findNavController(this).navigate(action);
    }
}
