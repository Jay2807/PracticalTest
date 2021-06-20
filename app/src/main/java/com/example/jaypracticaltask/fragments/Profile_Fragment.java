package com.example.jaypracticaltask.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.jaypracticaltask.R;
import com.example.jaypracticaltask.adapters.Home_PopularAdapter;
import com.example.jaypracticaltask.model.MeditationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.jaypracticaltask.MainActivity.db;

public class Profile_Fragment extends Fragment {
    Context context;
    @BindView(R.id.icavatar)
    CircleImageView icavatar;
    @BindView(R.id.txtname)
    TextView txtname;
    @BindView(R.id.txtpracticesession)
    TextView txtpracticesession;
    @BindView(R.id.txtpracticetime)
    TextView txtpracticetime;

    @BindView(R.id.txtmeditationtime)
    TextView txtmeditationtime;
    @BindView(R.id.txtmeditationsession)
    TextView txtmeditationsession;

    @BindView(R.id.txtclockpracticetime)
    TextView txtclockpracticetime;
    @BindView(R.id.txtclockmaditationtime)
    TextView txtclockmaditationtime;

    public static Profile_Fragment newInStance() {
        Profile_Fragment fragment = new Profile_Fragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white, getActivity().getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        getUser();
        return view;
    }

    public void getUser() {
        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null && task.getResult().size() > 0) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            txtname.setText(document.getString("username"));
                            txtpracticesession.setText(document.getString("practice_sessions"));
                            txtpracticesession.setText(document.getString("practice_sessions"));
                            txtpracticetime.setText(document.getString("practice_time"));
                            txtmeditationsession.setText(document.getString("medition_session"));
                            txtmeditationtime.setText(document.getString("meditation_time"));
                            txtclockpracticetime.setText(document.getString("practice_time"));
                            txtclockmaditationtime.setText(document.getString("meditation_time"));
                            Glide.with(context).load(document.getString("user_pic")).into(icavatar);
                        }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
