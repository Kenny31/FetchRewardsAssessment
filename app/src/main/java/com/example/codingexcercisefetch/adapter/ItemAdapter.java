package com.example.codingexcercisefetch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codingexcercisefetch.R;
import com.example.codingexcercisefetch.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        // Set data to the views
        holder.textItemId.setText("Item ID: " + currentItem.getId());
        holder.textListId.setText("List ID: " + currentItem.getListId());
        holder.textItemName.setText("Item Name: " + currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textItemId;
        TextView textListId;
        TextView textItemName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textItemId = itemView.findViewById(R.id.textItemId);
            textListId = itemView.findViewById(R.id.textListId);
            textItemName = itemView.findViewById(R.id.textItemName);
        }
    }
}
