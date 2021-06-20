package com.example.jaypracticaltask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaypracticaltask.R;

public class DetailBigCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    RecyclerView recyclerView;

    public DetailBigCardAdapter(Context context, RecyclerView recyclerView) {
        super();
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detailbigcard, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int width = recyclerView.getWidth();
        params.width = (int) (width * 0.9);
        view.setLayoutParams(params);
        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgcontacthome;

        public CategoryViewHolder(View view) {
            super(view);
        }
    }
}
