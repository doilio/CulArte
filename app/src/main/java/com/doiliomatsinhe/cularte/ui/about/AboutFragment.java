package com.doiliomatsinhe.cularte.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doiliomatsinhe.cularte.R;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Element versionElement = new Element();
        versionElement.setTitle(getString(R.string.version) + getString(R.string.version_nr));

        return new AboutPage(getActivity())
                .addItem(versionElement)
                .setDescription(getString(R.string.about_description))
                .addGroup(getString(R.string.about_contact_us))
                .addEmail(getString(R.string.about_dev_email), getString(R.string.about_send_mail))
                .addGroup(getString(R.string.about_social_networks))
                .addInstagram(getString(R.string.about_instagram_id), getString(R.string.about_instagram))
                .addFacebook(getString(R.string.about_facebook_id), getString(R.string.about_facebook))
                .addGitHub(getString(R.string.about_github_id), getString(R.string.about_github))
                .addTwitter(getString(R.string.about_twitter_id), getString(R.string.about_twitter))
                .addItem(createCopyright())
                .create();
    }

    private Element createCopyright() {
        Element copyright = new Element();
        String copyrightString = String.format(getString(R.string.about_copyright), Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setGravity(Gravity.CENTER);
        return copyright;
    }
}
