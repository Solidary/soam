package com.soam.ui;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soam.R;
import com.soam.model.Category;
import com.soam.utils.widget.CircleTransform;
import com.squareup.picasso.Picasso;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * Created by maelfosso on 10/23/16.
 */
public class CategoriesCompletionView extends TokenCompleteTextView<Category> {

    public CategoriesCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(Category category) {
        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // LinearLayout v = (LinearLayout) l.inflate(R.layout.category_completion, (ViewGroup) getParent(), false);
        View v = l.inflate(R.layout.category_completion, (ViewGroup) getParent(), false);

        ImageView image = (ImageView) v.findViewById(R.id.image);
        TextView name = (TextView) v.findViewById(R.id.name);

        Picasso.with(getContext()).load(R.drawable.photo_female_2)
                .resize(50, 50)
                .transform(new CircleTransform())
                .into(image);
        name.setText(category.getName());
        return v;
    }

    @Override
    protected Category defaultObject(String text) {
        return new Category("@drawable/unknown_contact", text, "Joiuygght");
    }
}
