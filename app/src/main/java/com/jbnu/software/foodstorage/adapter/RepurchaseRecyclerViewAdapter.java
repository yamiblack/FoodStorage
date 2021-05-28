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
import com.jbnu.software.foodstorage.model.Repurchase;

import java.util.ArrayList;

public class RepurchaseRecyclerViewAdapter extends RecyclerView.Adapter<RepurchaseRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Repurchase> items;

    public RepurchaseRecyclerViewAdapter(Context context, ArrayList<Repurchase> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RepurchaseRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repurchase, parent, false);
        return new RepurchaseRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Repurchase repurchase = items.get(position);

        try {
            holder.tvNumbers.setText(repurchase.getNumber());
            holder.tvProductName.setText(repurchase.getName());
        } catch (NullPointerException e) {
            Log.e("error ", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumbers;
        TextView tvProductName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumbers = itemView.findViewById(R.id.tv_repurchase_numbers);
            tvProductName = itemView.findViewById(R.id.tv_repurchase_product);

        }
    }

    public Repurchase getItem(int position) {
        return items.get(position);
    }
}
