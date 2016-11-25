package com.soam.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.soam.R;
import com.soam.adapter.ElementListAdapter;
import com.soam.adapter.chip.ElementChipAdapter;
import com.soam.model.Element;
import com.soam.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElementsFragment extends Fragment implements ElementListAdapter.OnItemClickListener,
            ElementChipAdapter.OnElementChipClickListener {

    public static String TAG = ElementsFragment.class.getName();

    private static final String ARG_DESCRIPTION = "com.soam.params.description";
    private static final String ARG_KEY_WORDS = "com.soam.params.keywords";

    String argDescription;
    List<String> argKeywords;

    ChipView chipView;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    public ElementListAdapter mAdapter;

    private OnElementFragmentListener mListener;

    public ElementsFragment() {
        // Required empty public constructor
    }

    public static ElementsFragment newInstance(String description, ArrayList<String> keywords) {
        ElementsFragment fragment = new ElementsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESCRIPTION, description);
        args.putStringArrayList(ARG_KEY_WORDS, keywords);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // According to the description and the keywords, try to get the list of items available
        if (getArguments() != null) {
            argDescription = getArguments().getString(ARG_DESCRIPTION);
            argKeywords = getArguments().getStringArrayList(ARG_KEY_WORDS);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_elements, container, false);
        chipView = (ChipView) v.findViewById(R.id.chipview);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClick();
            }
        });

        ElementChipAdapter adapter = new ElementChipAdapter(getContext());
        adapter.setOnElementChipClickListener(this);
        chipView.setAdapter(adapter);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        // specify an adapter (see also next example)
        mAdapter = new ElementListAdapter(getActivity(), Constants.getElementsData(getActivity()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        return v;
    }


    public void onFabClick() {
        if (mListener != null) {
            mListener.onFabSearchClick();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnElementFragmentListener) {
            mListener = (OnElementFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View view, Element elt, int position) {
        elt.setPosition(position);

        if (chipView.getChipList().size() == 0) {
            // ViewGroup.MarginLayoutParams params = ViewGroup.MarginLayou
            // chipView.setLayoutParams();
        }
        chipView.add(elt);

        mAdapter.removeItem(position);
    }

    @Override
    public void onCloseElementChipClick(View view, Element element, int position) {
        // chipView.getChipList().remove(position);
        chipView.remove(element);

        mAdapter.addItem(element);
        Log.d(TAG, "OnCloseElementChipClick -- " + element.getName() + " -- " + position + " +++ " + chipView.getChipList().size());
    }

    public interface OnElementFragmentListener {
        void onFabSearchClick();
    }
}
