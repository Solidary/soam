package com.soam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soam.R;
import com.soam.model.Element;
import com.soam.model.Need;
import com.soam.utils.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maelfosso on 10/24/16.
 */
public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Element> original_items;
    List<Element> filtered_items;

    ItemFilter mFilter = new ItemFilter();

    private OnItemClickListener mOnItemClickListener;


    public ElementListAdapter(Context context, List<Element> items) {
        this.context = context;

        this.filtered_items = items;
        this.original_items = items;
    }

    @Override
    public ElementListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_element, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Element element = filtered_items.get(position);
        String name = position + " - " + element.getName();
        Double price = element.getPrice() - (position * 100);

        Log.d("ELEMENTLISTADAPTER", name);
        Picasso.with(context).load(context.getResources().getIdentifier(element.getImage(), null, context.getPackageName()))
                .resize(50, 50)
                .transform(new CircleTransform())
                .into(holder.image);
        holder.name.setText(name);
        holder.price.setText(String.valueOf(price));
    }

    @Override
    public int getItemCount() {
        return filtered_items.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(Element element) {
        filtered_items.add(element.getPosition(), element);
    }

    public void removeItem(int position) {
        filtered_items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filtered_items.size());
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout lyt;
        public ImageView image;
        public TextView name;
        public TextView price;

        public ViewHolder(View view) {
            super(view);

            lyt = (LinearLayout) view.findViewById(R.id.lyt);
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);

            lyt.setOnClickListener(this);
            // lyt.setTag(getAdapterPosition());
        }

        @Override
        public void onClick(View view) {
            // removeItem(getAdapterPosition());
            mOnItemClickListener.onItemClick(view, filtered_items.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<Element> list = original_items;
            final List<Element> result_list = new ArrayList<>();

            for(int i = 0; i < list.size(); i++) {
                String str_name = list.get(i).getName();
                if (str_name.toLowerCase().contains(query)) {

                    Log.d("Elementlistadapter", list.get(i).getName() + " --- " + list.get(i).getPrice());
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filtered_items = (List<Element>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Element elt, int position);
    }

}
