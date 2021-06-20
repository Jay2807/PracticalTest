package com.example.jaypracticaltask.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaypracticaltask.R;
import com.example.jaypracticaltask.adapters.DetailBigCardAdapter;
import com.example.jaypracticaltask.adapters.SeeAllAdapter;
import com.example.jaypracticaltask.model.MeditationModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_Details extends Fragment {
    Context context;
    @BindView(R.id.recyclerdetails)
    RecyclerView recyclerdetails;
    @BindView(R.id.recyclerallpractice)
    RecyclerView recyclerallpractice;
    List<MeditationModel> meditationModelList;

    public static Fragment_Details newInStance(List<MeditationModel> meditationModelList) {
        Fragment_Details fragment = new Fragment_Details();
        fragment.meditationModelList = meditationModelList;
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white, getActivity().getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerdetails.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManagernew = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerallpractice.setLayoutManager(linearLayoutManagernew);
        DetailBigCardAdapter detailBigCardAdapter = new DetailBigCardAdapter(context, recyclerdetails);
        recyclerdetails.setAdapter(detailBigCardAdapter);
        SeeAllAdapter seeAllAdapter = new SeeAllAdapter(context, true, meditationModelList);
        recyclerallpractice.setAdapter(seeAllAdapter);
        return view;
    }
}
