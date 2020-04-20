package com.doiliomatsinhe.cularte.ui.artistDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doiliomatsinhe.cularte.R;
import com.doiliomatsinhe.cularte.databinding.FragmentArtistBinding;
import com.doiliomatsinhe.cularte.databinding.FragmentArtistDetailBinding;
import com.doiliomatsinhe.cularte.model.Artist;
import com.doiliomatsinhe.cularte.ui.artist.ArtistFragmentArgs;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistDetailFragment extends Fragment {

    private FragmentArtistDetailBinding binding;
    private Artist artist;
    private String instagramId, facebookId, githubId, deezerId, linkedInId, mediumId, soundCloudId, spotifyId, twitterId, youtubeId;

    public ArtistDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArtistDetailBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            ArtistDetailFragmentArgs args = ArtistDetailFragmentArgs.fromBundle(getArguments());
            artist = args.getArtist();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (artist != null) {
            // Artist coming from Artist Fragment
            populateUI(artist);
        } else {
            //TODO Populate with Artist Object coming from Room Database, when Favorites are working
        }
    }

    private void populateUI(final Artist artist) {

        // Image
        if (artist.getImagensUrl().get(0).isEmpty()) {
            Picasso.get().load(artist.getImagensUrl().get(0)).into(binding.detailImage);
            Timber.d(artist.getImagensUrl().get(0));
        }

        // Real name and Artistic Name
        if (!artist.getNomeArtistico().isEmpty()) {
            binding.artistDetailName.setText(artist.getNomeArtistico());
            if (!artist.getNomeCompleto().isEmpty()) {
                binding.artistDetailRealname.setText(String.format("real name, %s", artist.getNomeCompleto()));
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
        if (!String.valueOf(artist.getContactoProfissional()).isEmpty()) {
            binding.buttonTelephone.setVisibility(View.VISIBLE);
            binding.buttonTelephone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel: " + artist.getContactoProfissional().toString()));

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
                    i.setData(Uri.parse("mailto: " + artist.getLinkEmail()));

                    if (i.resolveActivity(requireActivity().getPackageManager()) != null) {
                        startActivity(i);
                    }
                }
            });
        }

        // Story
        if (!artist.getBiografia().isEmpty()) {
            binding.artistDetailStory.setText(artist.getBiografia());
        }


        // Social Media
        validateSocialMediaLinks(artist);

        // Icon Clicks
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

    }

    private void openDeezer(String deezerId) {

    }

    private void openMedium(String mediumId) {

    }

    private void openLinkedIn(String linkedInId) {

    }

    private void openSoundCloud(String soundCloudId) {

    }

    private void openGithub(String githubId) {

    }

    private void openYoutube(String youtubeId) {

    }

    private void openTwitter(String twitterId) {

    }

    private void openInstagram(String instagramId) {

    }

    private void openFacebook(String facebookId) {

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
