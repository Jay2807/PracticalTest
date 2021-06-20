package com.example.jaypracticaltask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaypracticaltask.MainActivity;
import com.example.jaypracticaltask.R;
import com.example.jaypracticaltask.fragments.Fragment_Details;
import com.example.jaypracticaltask.model.MeditationModel;

import java.util.List;

public class SeeAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    boolean fromDetails;
    List<MeditationModel> meditationModelList;

    public SeeAllAdapter(Context context, boolean fromDetails) {
        super();
        this.context = context;
        this.fromDetails = fromDetails;
    }

    public SeeAllAdapter(Context context, boolean fromDetails, List<MeditationModel> meditationModelList) {
        super();
        this.context = context;
        this.meditationModelList = meditationModelList;
        this.fromDetails = fromDetails;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_seeall, parent, false);
        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
        MeditationModel meditationModel = new MeditationModel();
        meditationModel = meditationModelList.get(position);
        if (meditationModel != null) {
            viewHolder.txtname.setText(meditationModel.getName());
            viewHolder.txtdurations.setText(meditationModel.getDuration());
        }
    }

    @Override
    public int getItemCount() {
        if (meditationModelList != null && meditationModelList.size() > 0) {
            return meditationModelList.size();
        } else {
            return 0;
        }
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgcontacthome;
        TextView txtname, txtdurations;

        public CategoryViewHolder(View view) {
            super(view);
            txtname = view.findViewById(R.id.txtname);
            txtdurations = view.findViewById(R.id.txtdurations);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!fromDetails) {
                        ((MainActivity) context).setDetails(Fragment_Details.newInStance(meditationModelList));
                    } else {
                        ((MainActivity) context).PlayAudio();
                    }
                }
            });
        }
    }
}
