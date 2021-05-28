package com.jbnu.software.foodstorage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.model.Storage;

import java.util.ArrayList;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Storage> items;

    public SearchRecyclerViewAdapter(Context context, ArrayList<Storage> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.ViewHolder holder, int position) {
        Storage storage = items.get(position);

        try {
            holder.name.setText(storage.getName());
            holder.amount.setText(Integer.toString(storage.getAmount()));
            holder.day.setText(Integer.toString(storage.getDDay()));
            holder.expiration.setText(storage.getExpiration());

        } catch (NullPointerException e) {
            Log.e("error", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView amount;
        TextView day;
        TextView expiration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            amount = itemView.findViewById(R.id.item_amount);
            day = itemView.findViewById(R.id.item_day);
            expiration = itemView.findViewById(R.id.item_expiration);
        }
    }

    public Storage getItem(int position) {
        return items.get(position);
    }

}
