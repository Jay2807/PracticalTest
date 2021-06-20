package com.example.jaypracticaltask.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaypracticaltask.MainActivity;
import com.example.jaypracticaltask.R;
import com.example.jaypracticaltask.adapters.Home_PopularAdapter;
import com.example.jaypracticaltask.model.MeditationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jaypracticaltask.MainActivity.db;

public class Home_Fragment extends Fragment {
    Context context;
    @BindView(R.id.recycler_popular)
    RecyclerView recycler_popular;
    @BindView(R.id.recycler_new)
    RecyclerView recycler_new;
    @BindView(R.id.txtseeall)
    TextView txtseeall;
    List<MeditationModel> meditationModelList = new ArrayList<>();
    @BindView(R.id.progress_circular)
    ProgressBar progress_circular;

    public static Home_Fragment newInStance() {
        Home_Fragment fragment = new Home_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_blue, getActivity().getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_blue));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recycler_popular.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManagernew = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recycler_new.setLayoutManager(linearLayoutManagernew);
        /*Home_PopularAdapter homePopularAdapter = new Home_PopularAdapter(context);
        recycler_popular.setAdapter(homePopularAdapter);
        recycler_new.setAdapter(homePopularAdapter);*/
        txtseeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).addFragment(Fragment_SeeAll.newInStance(meditationModelList));
            }
        });
        getMeditations();
        return view;
    }

    public void getMeditations() {
        db.collection("Meditations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null && task.getResult().size() > 0) {
                        meditationModelList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MeditationModel meditationModel = new MeditationModel();
                            meditationModel.setName(document.getString("name"));
                            meditationModel.setDescription(document.getString("description"));
                            meditationModel.setSteps(document.getString("steps"));
                            meditationModel.setDuration(document.getString("duration"));
                            meditationModelList.add(meditationModel);
                        }
                        Home_PopularAdapter homePopularAdapter = new Home_PopularAdapter(context, meditationModelList, recycler_popular);
                        recycler_popular.setAdapter(homePopularAdapter);
                        recycler_new.setAdapter(homePopularAdapter);
                        progress_circular.setVisibility(View.GONE);
                    }
                } else {
                    progress_circular.setVisibility(View.GONE);
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

    }
}
