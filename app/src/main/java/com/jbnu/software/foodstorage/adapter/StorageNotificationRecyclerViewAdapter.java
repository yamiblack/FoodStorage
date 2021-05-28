package com.jbnu.software.foodstorage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbnu.software.foodstorage.R;
import com.jbnu.software.foodstorage.model.Storage;

import java.util.ArrayList;

public class StorageNotificationRecyclerViewAdapter extends RecyclerView.Adapter<StorageNotificationRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Storage> items;

    public StorageNotificationRecyclerViewAdapter(Context context, ArrayList<Storage> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public StorageNotificationRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new StorageNotificationRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageNotificationRecyclerViewAdapter.ViewHolder holder, int position) {
        Storage storage = items.get(position);

        try {
            holder.name.setText(storage.getName());
            holder.amount.setText(Integer.toString(storage.getAmount()));

            ArrayAdapter nAdapter = (ArrayAdapter) holder.nSpinner.getAdapter();

            holder.nSpinner.setSelection(storage.getNotifyDate());

            if (storage.isNotification())
                holder.nSwitch.setChecked(true);
            else
                holder.nSwitch.setChecked(false);


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
        Spinner nSpinner;
        Switch nSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            amount = itemView.findViewById(R.id.item_amount);
            nSpinner = itemView.findViewById(R.id.notify_date_spinner);
            nSwitch = itemView.findViewById(R.id.notify_switch);

            setSpinnerDefault(nSpinner);

            nSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View v, int sPosition, long id) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onSelectNotifyDate(v, position, sPosition);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            nSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onSwitchChange(v, position, isChecked);

//                            notifyItemChanged(position);
                        }
                    }
                }
            });

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
        void onSwitchChange(View v, int position, boolean isChecked);

        void onSelectNotifyDate(View v, int position, int sPosition);
    }

    public void setSpinnerDefault(Spinner spinner) {
        ArrayAdapter nAdapter = ArrayAdapter.createFromResource(context, R.array.notify_date_option, R.layout.notification_spinner_item);
        nAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(nAdapter);
    }
}
