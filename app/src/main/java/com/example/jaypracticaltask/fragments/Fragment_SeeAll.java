package com.example.jaypracticaltask.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaypracticaltask.R;
import com.example.jaypracticaltask.adapters.SeeAllAdapter;
import com.example.jaypracticaltask.model.MeditationModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_SeeAll extends Fragment {
    Context context;
    @BindView(R.id.recycerlall)
    RecyclerView recycerlall;
    @BindView(R.id.imgback)
    ImageView imgback;
    List<MeditationModel> meditationModelList = new ArrayList<>();

    public static Fragment_SeeAll newInStance(List<MeditationModel> meditationModelList) {
        Fragment_SeeAll fragment = new Fragment_SeeAll();
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
        View view = inflater.inflate(R.layout.frament_seeall, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white, getActivity().getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recycerlall.setLayoutManager(linearLayoutManager);
        SeeAllAdapter seeAllAdapter = new SeeAllAdapter(context, false, meditationModelList);
        recycerlall.setAdapter(seeAllAdapter);
        return view;
    }
}
