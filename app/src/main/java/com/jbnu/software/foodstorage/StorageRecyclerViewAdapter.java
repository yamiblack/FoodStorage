package com.jbnu.software.foodstorage;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StorageRecyclerViewAdapter extends RecyclerView.Adapter<StorageRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Storage> items;
    private int exAmount;

    public StorageRecyclerViewAdapter(Context context, ArrayList<Storage> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public StorageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storage, parent, false);
        return new StorageRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageRecyclerViewAdapter.ViewHolder holder, int position) {
        Storage storage = items.get(position);
        exAmount = storage.getAmount();

        holder.bind(storage);
        try {
            holder.name.setText(storage.getName());
            holder.amount.setText(Integer.toString(storage.getAmount()));
            holder.day.setText(Integer.toString(storage.getDDay()));
            holder.expiration.setText(storage.getExpiration());

            holder.expandedAmount.setText(Integer.toString(storage.getAmount()));

            holder.itemView.setOnClickListener(v -> {
                boolean expanded = storage.isExpanded();
                storage.setExpanded(!expanded);
                notifyItemChanged(position);
            });

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
        RelativeLayout expend;
        TextView expandedAmount;
        Button check;
        Button increment;
        Button decrement;
        Button clear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            amount = itemView.findViewById(R.id.item_amount);
            day = itemView.findViewById(R.id.item_day);
            expiration = itemView.findViewById(R.id.item_expiration);

            expend = itemView.findViewById(R.id.expand_item);
            expandedAmount = itemView.findViewById(R.id.expanded_amount);
            check = itemView.findViewById(R.id.check);
            increment = itemView.findViewById(R.id.increment);
            decrement = itemView.findViewById(R.id.decrement);
            clear = itemView.findViewById(R.id.clear);

            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exAmount--;
                    expandedAmount.setText(Integer.toString(exAmount));
                }
            });

            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exAmount++;
                    expandedAmount.setText(Integer.toString(exAmount));
                }
            });

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            if(mListener !=null){
                                mListener.onClearClick(v,position);
                                notifyItemChanged(position);
                            }
                        }
                }
            });

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        if(mListener !=null){
                            mListener.onCheckClick(v,position);
                            notifyItemChanged(position);
                        }
                    }
                }
            });

        }

        private void bind(Storage storage) {
            boolean expanded = storage.isExpanded();

            expend.setVisibility(expanded ? View.VISIBLE : View.GONE);

        }
    }

    public Storage getItem(int position) {
        return items.get(position);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onClearClick(View v, int position);
        void onCheckClick(View v, int position);
    }

    public int getExAmount() {
        return exAmount;
    }
}
