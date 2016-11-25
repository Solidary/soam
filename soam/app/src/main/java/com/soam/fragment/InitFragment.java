package com.soam.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soam.R;
import com.soam.model.Category;
import com.soam.ui.CategoriesCompletionView;
import com.soam.utils.widget.CircleTransform;
import com.squareup.picasso.Picasso;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InitFragment extends Fragment implements TokenCompleteTextView.TokenListener<Category> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText description;
    CategoriesCompletionView categoriesCompletionView;

    Category[] categories;
    ArrayAdapter<Category> adapter;

    public InitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InitFragment newInstance(String param1, String param2) {
        InitFragment fragment = new InitFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }

        categories = new Category[] {
                new Category("@drawable/unknown_contact", "Restaurant", getString(R.string.lorem_ipsum)),
                new Category("@drawable/unknown_contact", "Pharmacie", getString(R.string.lorem_ipsum)),
                new Category("@drawable/unknown_contact", "Ville", getString(R.string.lorem_ipsum)),
                new Category("@drawable/unknown_contact", "Tourne Dos", getString(R.string.lorem_ipsum)),
                new Category("@drawable/unknown_contact", "Glacier", getString(R.string.lorem_ipsum))
        };

        adapter = new FilteredArrayAdapter<Category>(this.getActivity(), R.layout.category_layout, categories) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.category_layout, parent, false);
                }

                Category category = getItem(position);
                Picasso.with(getContext()).load(R.drawable.photo_male_1)
                        .resize(50, 50)
                        .transform(new CircleTransform())
                        .into((ImageView)convertView.findViewById(R.id.image));
                ((TextView)convertView.findViewById(R.id.name)).setText(category.getName());

                return convertView;  // super.getView(position, convertView, parent);
            }

            @Override
            protected boolean keepObject(Category category, String mask) {
                mask = mask.toLowerCase();
                return category.getName().toLowerCase().contains(mask);
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_init, container, false);

        categoriesCompletionView = (CategoriesCompletionView) v.findViewById(R.id.categories);
        categoriesCompletionView.setAdapter(adapter);
        categoriesCompletionView.setTokenListener(this);
        categoriesCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

        description = (EditText) v.findViewById(R.id.description);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTokenAdded(Category token) {
        Toast.makeText(getContext(), "On Token Added...", Toast.LENGTH_LONG);
    }

    @Override
    public void onTokenRemoved(Category token) {
        Toast.makeText(getContext(), "On Token Removed...", Toast.LENGTH_LONG);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
