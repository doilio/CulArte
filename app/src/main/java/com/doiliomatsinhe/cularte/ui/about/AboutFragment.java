package com.doiliomatsinhe.cularte.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doiliomatsinhe.cularte.R;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Element versionElement = new Element();
        versionElement.setTitle("Version" + "1.0.0");

        return new AboutPage(getActivity())
                .addItem(versionElement)
                .setDescription("It is a platform that comes to unify art in Mozambique.\nGetting closer and closer to our artists, showing what our country has to offer when it comes to Art and Culture, helping to publish our artists and disseminate information related to them.")
                .addGroup("Contact-Us")
                .addEmail("doiliomatsinhe@gmail.com", "Send an e-mail")
                .addGroup("Social Networks")
                .addInstagram("doiliomatsinhe", "Instagram")
                .addFacebook("doilio.matsinhe", "Facebook")
                .addGitHub("doilio", "Github")
                .addTwitter("DoilioMatsinhe", "Twitter")
                .addItem(createCopyright())
                .create();
    }

    private Element createCopyright() {
        Element copyright = new Element();
        String copyrightString = String.format("Copyright %d by Doilio Matsinhe", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setGravity(Gravity.CENTER);
        return copyright;
    }
}
