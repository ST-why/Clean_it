package com.example.cleanit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckItemAdapter extends RecyclerView.Adapter<CheckItemAdapter.ViewHolder> {
    private List<CheckItem> items;
    private OnItemDeleteListener deleteListener;

    public interface OnItemDeleteListener {
        void onItemDelete(CheckItem item);
    }

    public CheckItemAdapter(List<CheckItem> items, OnItemDeleteListener listener) {
        this.items = items;
        this.deleteListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            deleteButton = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckItem item = items.get(position);
        holder.checkBox.setText(item.getText());
        holder.checkBox.setChecked(item.isChecked());

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onItemDelete(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CheckItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void removeItem(CheckItem item) {
        int position = items.indexOf(item);
        if (position != -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List<CheckItem> getItems() {
        return items;
    }
}
