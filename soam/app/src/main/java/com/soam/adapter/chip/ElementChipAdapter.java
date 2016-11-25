package com.soam.adapter.chip;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.soam.R;
import com.soam.model.Element;
import com.soam.utils.widget.CircleTransform;
import com.squareup.picasso.Picasso;

/**
 * Created by maelfosso on 11/4/16.
 */
public class ElementChipAdapter extends ChipViewAdapter {

    OnElementChipClickListener onElementChipClickListener;

    public ElementChipAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutRes(int position) {
        return R.layout.chip_element;
    }

    @Override
    public int getBackgroundRes(int position) {
        return 0;
    }

    @Override
    public int getBackgroundColor(int position) {
        return R.color.colorPrimary;
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return R.color.colorPrimaryDark;
    }

    @Override
    public void onLayout(View view, final int position) {
        Element element = (Element) getChip(position);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView price = (TextView) view.findViewById(R.id.price);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        ImageButton close = (ImageButton) view.findViewById(R.id.close_action);

        name.setText(element.getName());
        price.setText(String.valueOf(element.getPrice()));
        Picasso.with(getContext()).load(getContext().getResources().getIdentifier(element.getImage(), null, getContext().getPackageName()))
                .resize(25, 25)
                .transform(new CircleTransform())
                .into(image);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onElementChipClickListener.onCloseElementChipClick(view, (Element) getChip(position), position);
            }
        });
    }

    public void setOnElementChipClickListener(OnElementChipClickListener onElementChipClickListener) {
        this.onElementChipClickListener = onElementChipClickListener;
    }

    public interface OnElementChipClickListener {

        void onCloseElementChipClick(View view, Element element, int position);
    }
}
