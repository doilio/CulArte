package com.doiliomatsinhe.cularte.ui.artistDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.data.ArtistDatabase;
import com.doiliomatsinhe.cularte.databinding.FragmentArtistDetailBinding;
import com.doiliomatsinhe.cularte.model.Artist;
import com.doiliomatsinhe.cularte.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistDetailFragment extends Fragment {

    private FragmentArtistDetailBinding binding;
    private Artist artist;
    private String instagramId, facebookId, githubId, deezerId, linkedInId, mediumId, soundCloudId, spotifyId, twitterId, youtubeId;
    private ArtistDetailViewModel viewModel;
    private static final String TEXT_PLAIN = "text/plain";
    private static final Long FACEBOOK_VERSION_CODE = 0L;
    private static final int FACEBOOK_FLAG = 0;
    private static final int FACEBOOK_VERSION_INFERIOR = 3002850;
    private static final int PHONE_NR_VALID = 800000000;

    private ArtistDatabase database;

    public ArtistDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArtistDetailBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            ArtistDetailFragmentArgs args = ArtistDetailFragmentArgs.fromBundle(getArguments());
            artist = args.getArtist();
            if (artist != null) {
                initViewModel(artist);
            }
        }

        if (getActivity() != null) {
            if (!artist.getNomeArtistico().isEmpty()) {
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(artist.getNomeArtistico());
            } else {
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(artist.getNomeCompleto());
            }

        }
        return binding.getRoot();
    }

    private void initViewModel(Artist artist) {

        ArtistDetailViewModelFactory factory = new ArtistDetailViewModelFactory(requireActivity().getApplication(), artist.getId());
        viewModel = new ViewModelProvider(this, factory).get(ArtistDetailViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (artist != null) {
            // Artist coming from Artist Fragment
            populateUI(artist);

        }

        database = ArtistDatabase.getInstance(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        final MenuItem favoriteItem = menu.findItem(R.id.ic_favorite);
        viewModel.getFavoriteArtist().observe(getViewLifecycleOwner(), new Observer<Artist>() {
            @Override
            public void onChanged(Artist artist) {
                if (artist != null) {
                    favoriteItem.setIcon(R.drawable.ic_favorite_white_24dp);
                    Timber.d("Is a Favorite");
                } else {
                    favoriteItem.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    Timber.d("Not a Favorite");
                }

            }
        });

        favoriteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                viewModel.getFavoriteArtist().observe(getViewLifecycleOwner(), new Observer<Artist>() {
                    @Override
                    public void onChanged(Artist artist) {
                        viewModel.getFavoriteArtist().removeObserver(this);
                        if (artist != null) {
                            removeFromFavorites();
                            favoriteItem.setIcon(R.drawable.ic_favorite_border_white_24dp);
                            showSnackbar(getString(R.string.removed_from_favorite));
                        } else {
                            addToFavorites();
                            favoriteItem.setIcon(R.drawable.ic_favorite_white_24dp);
                            showSnackbar(getString(R.string.added_to_favorite));
                        }

                    }
                });
                return false;
            }
        });

    }

    // Add To Favorites
    @SuppressLint("StaticFieldLeak")
    private void addToFavorites() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.artistDao().addFavorite(artist);
                return null;
            }
        }.execute();
        //database.artistDao().addFavorite(artist);
    }

    // Remove from Favorites
    @SuppressLint("StaticFieldLeak")
    private void removeFromFavorites() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.artistDao().removeArtist(artist);
                return null;
            }
        }.execute();
        //database.artistDao().removeArtist(artist);
    }

    private void populateUI(final Artist artist) {

        // Image
        if (artist.getImagensUrl().size() > 0) {
            Picasso.get().load(artist.getImagensUrl().get(0)).into(binding.detailImage);
        } else {
            Picasso.get().load(R.color.colorPrimary).into(binding.detailImage);
        }

        // Real name and Artistic Name
        if (!artist.getNomeArtistico().isEmpty()) {
            binding.artistDetailName.setText(artist.getNomeArtistico());
            if (!artist.getNomeCompleto().isEmpty()) {
                binding.artistDetailRealname.setText(String.format(getString(R.string.real_name_), artist.getNomeCompleto()));
                binding.artistDetailRealname.setVisibility(View.VISIBLE);
            }
        } else {
            binding.artistDetailName.setText(artist.getNomeCompleto());
            binding.artistDetailRealname.setVisibility(View.GONE);
        }

        // Description
        if (!artist.getCurtaDescricao().isEmpty()) {
            binding.artistDetailDescription.setText(artist.getCurtaDescricao());
        }

        // Contact - Phone
        if (!String.valueOf(artist.getContactoProfissional()).isEmpty() && artist.getContactoProfissional() > PHONE_NR_VALID) {
            binding.buttonTelephone.setVisibility(View.VISIBLE);
            binding.buttonTelephone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse(String.format(getString(R.string.telephone_nr_), artist.getContactoProfissional().toString())));

                    if (i.resolveActivity(requireActivity().getPackageManager()) != null) {
                        startActivity(i);
                    }
                }
            });
        }

        //Contact - E-mail
        if (!artist.getLinkEmail().isEmpty()) {
            binding.buttonEmail.setVisibility(View.VISIBLE);
            binding.buttonEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse(String.format(getString(R.string.mailto_), artist.getLinkEmail())));

                    if (i.resolveActivity(requireActivity().getPackageManager()) != null) {
                        startActivity(i);
                    }
                }
            });
        }

        // Story
        if (!artist.getBiografia().isEmpty()) {
            binding.artistDetailStory.setText(artist.getBiografia());
        } else {
            binding.artistDetailStory.setText(R.string.not_available);
        }

        // Social Media
        validateSocialMediaLinks(artist);

        // Icon Clicks
        handleIconClicks();

        binding.fabShare.setOnClickListener(new View.OnClickListener() {
            // When in production replace *link* with link to profile.
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                if (!artist.getNomeArtistico().isEmpty()) {
                    i.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.profile_sharing), artist.getNomeArtistico()));
                } else {
                    i.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.profile_sharing), artist.getNomeCompleto()));
                }
                i.setType(TEXT_PLAIN);

                if (i.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(Intent.createChooser(i, getString(R.string.share_profile_)));
                }
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.ic_report) {
            reportArtist();
        }

        return super.onOptionsItemSelected(item);
    }

    private void reportArtist() {
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse(getString(R.string.mail_to_)));
        if (!artist.getId().isEmpty()) {
            i.putExtra(Intent.EXTRA_SUBJECT, String.format(getString(R.string.reporting_user_), artist.getId()));
        }
        startActivity(Intent.createChooser(i, getString(R.string.report_profile)));
    }

    private void showSnackbar(String msg) {
        Snackbar.make(binding.scrollView, msg, Snackbar.LENGTH_SHORT).show();
    }

    private void handleIconClicks() {
        binding.iconFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebook(facebookId);
            }
        });
        binding.iconInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstagram(instagramId);
            }
        });
        binding.iconTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTwitter(twitterId);
            }
        });
        binding.iconYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYoutube(youtubeId);
            }
        });
        binding.iconGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithub(githubId);
            }
        });
        binding.iconSoundcloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSoundCloud(soundCloudId);
            }
        });
        binding.iconLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkedIn(linkedInId);
            }
        });
        binding.iconMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMedium(mediumId);
            }
        });
        binding.iconDeezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeezer(deezerId);
            }
        });
        binding.iconSpotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSpotify(spotifyId);
            }
        });
    }

    private void openSpotify(String spotifyId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        i.setData(Uri.parse(String.format(getString(R.string.spotify_link_), spotifyId)));
        startActivity(i);
    }

    private void openDeezer(String deezerId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (Utils.isAppInstalled(requireActivity(), getString(R.string.deezer_package_name))) {
            i.setPackage(getString(R.string.deezer_package_name));
        }
        i.setData(Uri.parse(String.format(getString(R.string.deezer_link_), deezerId)));
        startActivity(i);
    }

    private void openMedium(String mediumId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (Utils.isAppInstalled(requireActivity(), getString(R.string.medium_package_name_))) {
            i.setPackage(getString(R.string.medium_package_name_));
        }
        i.setData(Uri.parse(String.format(getString(R.string.medium_site_), mediumId)));
        startActivity(i);
    }

    private void openLinkedIn(String linkedInId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (Utils.isAppInstalled(requireActivity(), getString(R.string.linkedin_package_name_))) {
            i.setPackage(getString(R.string.linkedin_package_name_));
        }
        i.setData(Uri.parse(String.format(getString(R.string.linkedin_site_), linkedInId)));
        startActivity(i);
    }

    private void openSoundCloud(String soundCloudId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (Utils.isAppInstalled(requireActivity(), getString(R.string.soundcloud_package_name))) {
            i.setPackage(getString(R.string.soundcloud_package_name));
        }
        i.setData(Uri.parse(String.format(getString(R.string.soundcloud_site_), soundCloudId)));
        startActivity(i);
    }

    private void openGithub(String githubId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        // Keep looking for this package name
        /*        if (Utils.isAppInstalled(requireActivity(), "")) {
            i.setPackage("");
        }*/
        i.setData(Uri.parse(String.format(getString(R.string.github_site_), githubId)));
        startActivity(i);
    }

    private void openYoutube(String youtubeId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (Utils.isAppInstalled(requireActivity(), getString(R.string.youtube_package_name))) {
            i.setPackage(getString(R.string.youtube_package_name));
        }
        i.setData(Uri.parse(String.format(getString(R.string.youtube_site_), youtubeId)));
        startActivity(i);
    }

    private void openTwitter(String twitterId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (Utils.isAppInstalled(requireActivity(), getString(R.string.twitter_package_name))) {
            i.setPackage(getString(R.string.twitter_package_name));
            i.setData(Uri.parse(String.format(getString(R.string.twitter_site_one_), twitterId)));
        } else {
            i.setData(Uri.parse(String.format(getString(R.string.twitter_site_two_), twitterId)));
        }
        startActivity(i);
    }

    private void openInstagram(String instagramId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (Utils.isAppInstalled(requireActivity(), getString(R.string.instagram_package_name))) {
            i.setPackage(getString(R.string.instagram_package_name));
        }
        i.setData(Uri.parse(String.format(getString(R.string.instagram_site_), instagramId)));
        startActivity(i);
    }

    private void openFacebook(String facebookId) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        if (Utils.isAppInstalled(requireActivity(), getString(R.string.facebook_package_name))) {
            i.setPackage(getString(R.string.facebook_package_name));
            long versionCode = FACEBOOK_VERSION_CODE;
            try {
                versionCode = requireActivity().getPackageManager()
                        .getPackageInfo(getString(R.string.facebook_package_name), FACEBOOK_FLAG)
                        .versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                Timber.d(e);
            }
            if (versionCode >= FACEBOOK_VERSION_INFERIOR) {
                Uri uri = Uri.parse(String.format(getString(R.string.facebook_site_one_), facebookId));
                i.setData(uri);
            } else {
                Uri uri = Uri.parse(String.format(getString(R.string.facebook_site_two_), facebookId));
                i.setData(uri);
            }
        } else {
            i.setData(Uri.parse(String.format(getString(R.string.facebook_site_three_), facebookId)));
        }
        startActivity(i);
    }

    private void validateSocialMediaLinks(Artist artist) {
        // Switch socialMedia to a list instead of separate strings on firestore to improve the structure and remove BoilerPlate code.
        if (!artist.getLinkInstagram().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            instagramId = artist.getLinkInstagram();
        } else {
            binding.iconInstagram.setVisibility(View.GONE);
        }
        if (!artist.getLinkFacebook().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            facebookId = artist.getLinkFacebook();
        } else {
            binding.iconFacebook.setVisibility(View.GONE);
        }
        if (!artist.getLinkGithub().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            githubId = artist.getLinkGithub();
        } else {
            binding.iconGithub.setVisibility(View.GONE);
        }
        if (!artist.getLinkDeezer().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            deezerId = artist.getLinkDeezer();
        } else {
            binding.iconDeezer.setVisibility(View.GONE);
        }
        if (!artist.getLinkLinkedIn().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            linkedInId = artist.getLinkLinkedIn();
        } else {
            binding.iconLinkedin.setVisibility(View.GONE);
        }
        if (!artist.getLinkMedium().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            mediumId = artist.getLinkMedium();
        } else {
            binding.iconMedium.setVisibility(View.GONE);
        }
        if (!artist.getLinkSoundCloud().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            soundCloudId = artist.getLinkSoundCloud();
        } else {
            binding.iconSoundcloud.setVisibility(View.GONE);
        }
        if (!artist.getLinkSpotify().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            spotifyId = artist.getLinkSpotify();
        } else {
            binding.iconSpotify.setVisibility(View.GONE);
        }
        if (!artist.getLinkTwitter().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            twitterId = artist.getLinkTwitter();
        } else {
            binding.iconTwitter.setVisibility(View.GONE);
        }
        if (!artist.getLinkYoutube().isEmpty()) {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            youtubeId = artist.getLinkYoutube();
        } else {
            binding.iconYoutube.setVisibility(View.GONE);
        }
    }


}
