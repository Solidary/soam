package com.soam.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.soam.model.Category;
import com.tokenautocomplete.FilteredArrayAdapter;

import java.util.List;

/**
 * Created by maelfosso on 10/23/16.
 */
abstract public class CategoryFilteredArrayAdapter extends BaseAdapter {

    private List<Category> categories;

    /*
    public CategoryFilteredArrayAdapter(Context context, int resource, Category[] categories) {
        super(context, resource, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }*/
}
