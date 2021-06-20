package com.example.jaypracticaltask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaypracticaltask.R;
import com.example.jaypracticaltask.model.MeditationModel;

import org.w3c.dom.Text;

import java.util.List;

public class Home_PopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MeditationModel> meditationModelList;
    RecyclerView recyclerView;

    public Home_PopularAdapter(Context context, List<MeditationModel> meditationModelList, RecyclerView recyclerView) {
        super();
        this.context = context;
        this.recyclerView = recyclerView;
        this.meditationModelList = meditationModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popular, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int width = recyclerView.getWidth();
        params.width = (int) (width * 0.9);
        view.setLayoutParams(params);
        return new CategoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
        MeditationModel meditationModel = new MeditationModel();
        meditationModel = meditationModelList.get(position);
        if (meditationModel != null) {
            viewHolder.txtname.setText(meditationModel.getName());
            viewHolder.txtdesc.setText(meditationModel.getDescription());
            viewHolder.txtsteps.setText(meditationModel.getSteps() + " | " + meditationModel.getDuration());
        }
    }

    @Override
    public int getItemCount() {
        if (meditationModelList != null && meditationModelList.size() > 3) {
            return 3;
        } else {
            return meditationModelList.size();
        }
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgcontacthome;
        TextView txtname, txtdesc, txtsteps;

        public CategoryViewHolder(View view) {
            super(view);
            txtname = view.findViewById(R.id.txtname);
            txtdesc = view.findViewById(R.id.txtdescription);
            txtsteps = view.findViewById(R.id.txtsteps);
        }
    }
}
