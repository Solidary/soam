package com.soam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soam.R;
import com.soam.model.Need;
import com.soam.utils.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maelfosso on 7/17/16.
 */
public class NeedListAdapter extends RecyclerView.Adapter<NeedListAdapter.ViewHolder> {

    public static String TAG = NeedListAdapter.class.getSimpleName();

    List<Need> needs;
    Context context;

    OnNeedClickListener onNeedClickListener;

    public interface OnNeedClickListener {
        void onItemClick(View view, Need need, int position);
    }

    public NeedListAdapter(Context context, List<Need> needs) {
        this.context = context;
        this.needs = needs;
    }

    public void setOnNeedClickListener(OnNeedClickListener onNeedClickListener) {
        this.onNeedClickListener = onNeedClickListener;
    }

    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }

    public void addNeed(Need need) {
        this.needs.add(need);
        notifyDataSetChanged();
    }

    @Override
    public NeedListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_need, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Need need = needs.get(position);

        holder.map.setImageResource(context.getResources().getIdentifier(need.getMap(), null, context.getPackageName()));;
        holder.description.setText(need.getDescription());
        holder.place.setText(need.getPlace());
        holder.elements.setText(String.valueOf(need.getElements().size()));
        holder.amount.setText(String.valueOf(need.getAmount()*need.getElements().size()));
        Picasso.with(context).load(R.drawable.photo_male_2)
                .resize(50, 50)
                .transform(new CircleTransform())
                .into(holder.photo);

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNeedClickListener != null) {
                    onNeedClickListener.onItemClick(view, need, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return needs.size();
    }

    public Need getItem(int position) {
        return needs.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView map;
        public TextView description;
        public TextView amount;
        public TextView place;
        public ImageView photo;
        public TextView elements;

        public LinearLayout lyt_parent;

        public ViewHolder(View view) {
            super(view);

            map = (ImageView) view.findViewById(R.id.map);
            description = (TextView) view.findViewById(R.id.description);
            amount = (TextView) view.findViewById(R.id.amount);
            place = (TextView) view.findViewById(R.id.place);
            photo = (ImageView) view.findViewById(R.id.photo);
            elements = (TextView) view.findViewById(R.id.elements);

            lyt_parent = (LinearLayout) view.findViewById(R.id.lyt_parent);
        }
    }
}
