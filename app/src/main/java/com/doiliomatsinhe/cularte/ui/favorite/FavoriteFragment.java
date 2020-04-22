package com.doiliomatsinhe.cularte.ui.favorite;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.adapter.ArtistAdapter;
import com.doiliomatsinhe.cularte.databinding.FragmentFavoriteBinding;
import com.doiliomatsinhe.cularte.model.Artist;
import com.doiliomatsinhe.cularte.utils.Utils;
import com.doiliomatsinhe.cularte.widget.ArtistWidget;

import java.util.List;

import timber.log.Timber;

public class FavoriteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ArtistAdapter.ArtistItemClickListener {

    private FavoriteViewModel viewModel;
    private FragmentFavoriteBinding binding;
    private ArtistAdapter adapter;
    private List<Artist> favoritesList;
    public static final String ARTIST_PREF = "artist_preference";
    public static final String FAVORITES = "my_favorites";

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

        // Clear Preferences
        clearSharedPreferences();
        updateWidget();
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
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.span_count_artists), StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerFavorites.setLayoutManager(layoutManager);
    }

    @Override
    public void onRefresh() {
        retrieveArtists();
    }

    @Override
    public void onArtistItemClick(int position) {
        Artist currentFavorite = favoritesList.get(position);

        // Save to Shared Preferences
        String myFavorite = Utils.getJsonFromObject(currentFavorite);
        saveSharedPreferences(myFavorite);

        NavDirections action = FavoriteFragmentDirections.actionFavoriteFragmentToArtistDetailFragment(currentFavorite);
        NavHostFragment.findNavController(this).navigate(action);


    }

    private void saveSharedPreferences(String myFavorite) {
        clearSharedPreferences();
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(ARTIST_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(FAVORITES, myFavorite);

        editor.apply();
        Timber.d("Saved to Preferences");

        updateWidget();
    }

    private void updateWidget() {
        // Updates the Widget
        Context context = requireActivity().getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widget = new ComponentName(context, ArtistWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_linear);
    }

    private void clearSharedPreferences() {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences(ARTIST_PREF, Context.MODE_PRIVATE);

        if (sharedPref != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();
            Timber.d("Preferences cleared");
        } else {
            Timber.d("Shared Pref is null");
        }
    }
}
