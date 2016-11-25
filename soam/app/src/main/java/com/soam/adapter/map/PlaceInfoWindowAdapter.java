package com.soam.adapter.map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.soam.R;
import com.soam.model.Place;
import com.soam.utils.widget.CircleTransform;
import com.squareup.picasso.Picasso;

/**
 * Created by maelfosso on 11/5/16.
 */
public class PlaceInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View windows;
    private View contents;

    public PlaceInfoWindowAdapter(LayoutInflater inflater) {
        windows = inflater.inflate(R.layout.custom_info_window, null);
        contents = inflater.inflate(R.layout.custom_info_contents, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // return null;

        render(marker, contents);
        return contents;
    }

    private void render(Marker marker, View view) {
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView categories = (TextView) view.findViewById(R.id.categories);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        Place place = (Place)marker.getTag();
        name.setText(place.getName());
        categories.setText(String.valueOf(place.getCategories().size()));
        /*Picasso.with(getContext()).load(getContext().getResources().getIdentifier(element.getImage(), null, getContext().getPackageName()))
                .resize(25, 25)
                .transform(new CircleTransform())
                .into(image);*/
    }
}
