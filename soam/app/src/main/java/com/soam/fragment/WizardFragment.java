package com.soam.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WizardFragment extends Fragment {

    private static final String ARG_IMAGE = "image";
    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";

    ImageView image;
    TextView title;
    TextView description;

    public static WizardFragment newInstance(String image, String title, String description) {
        WizardFragment wf = new WizardFragment();

        Bundle b = new Bundle();
        b.putString(ARG_IMAGE, image);
        b.putString(ARG_TITLE, title);
        b.putString(ARG_DESCRIPTION, description);

        wf.setArguments(b);
        return wf;
    }

    public WizardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wizard, container, false);

        image = (ImageView) v.findViewById(R.id.image);
        title = (TextView) v.findViewById(R.id.title);
        description = (TextView) v.findViewById(R.id.description);

        image.setImageResource(getResources().getIdentifier(getArguments().getString(ARG_IMAGE), null, getContext().getPackageName()));
        title.setText(getArguments().getString(ARG_TITLE));
        description.setText(getArguments().getString(ARG_DESCRIPTION));
        return v;
    }

}
