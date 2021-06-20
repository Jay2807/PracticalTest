package com.example.jaypracticaltask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaypracticaltask.fragments.Fragment_Details;
import com.example.jaypracticaltask.fragments.Fragment_SeeAll;
import com.example.jaypracticaltask.fragments.Home_Fragment;
import com.example.jaypracticaltask.fragments.Profile_Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.imghome)
    ImageView imghome;
    @BindView(R.id.imgdetails)
    ImageView imgdetails;
    @BindView(R.id.img_flash)
    ImageView img_flash;
    @BindView(R.id.imgprofile)
    ImageView imgprofile;
    @BindView(R.id.ivpauseplay)
    ImageView ivpauseplay;
    @BindView(R.id.imgclose)
    ImageView imgclose;
    @BindView(R.id.relplayer)
    RelativeLayout relplayer;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imghome.setOnClickListener(this);
        imgdetails.setOnClickListener(this::onClick);
        imgprofile.setOnClickListener(this::onClick);
        ivpauseplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    ivpauseplay.setImageDrawable(getResources().getDrawable(R.mipmap.play, null));
                    mediaPlayer.pause();
                } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    ivpauseplay.setImageDrawable(getResources().getDrawable(R.mipmap.pause, null));
                    mediaPlayer.start();
                }
            }
        });
        relplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayAudio();
            }
        });
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relplayer.setVisibility(View.GONE);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });
        setFragment(Home_Fragment.newInStance());
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
//        playAudio();
    }

    public void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        AssetFileDescriptor afd;
        try {
            afd = getAssets().openFd("dummy.mp3");
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDetails(Fragment fragment) {
        imghome.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
        imgdetails.setColorFilter(getResources().getColor(R.color.ic_blue, null));
        imgprofile.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
        img_flash.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentmain, fragment);
        fragmentTransaction.commit();
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentmain, fragment);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentmain, fragment);
        transaction.addToBackStack("first")
                .commit();
    }

    public void PlayAudio() {
        View view = getLayoutInflater().inflate(R.layout.dialog_player, null);
        BottomSheetDialog playerDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialog);
        playerDialog.setContentView(view);
        TextView txtcurret = view.findViewById(R.id.txtcurret);
        TextView txtremaining = view.findViewById(R.id.txtremaining);
        ImageView imgclose = view.findViewById(R.id.imgclose);
        SeekBar mSeekBar = view.findViewById(R.id.seekbar);
        ImageView ivpauseplay = view.findViewById(R.id.ivpauseplay);
        playerDialog.setCancelable(false);
        playerDialog.setCancelable(true);
        if (mediaPlayer == null) {
            playAudio();
        } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            playAudio();
        }
        ivpauseplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    ivpauseplay.setImageDrawable(getResources().getDrawable(R.mipmap.play, null));
                    mediaPlayer.pause();
                } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    ivpauseplay.setImageDrawable(getResources().getDrawable(R.mipmap.pause, null));
                    mediaPlayer.start();
                }
            }
        });
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerDialog.dismiss();
            }
        });
        txtremaining.setText(getTime(mediaPlayer.getDuration()));
        mSeekBar.setMax(mediaPlayer.getDuration() / 1000);
        Handler mHandler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    mSeekBar.setProgress(mCurrentPosition);
                    txtcurret.setText(getTime(mediaPlayer.getCurrentPosition()));
                }
                mHandler.postDelayed(this, 1000);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }
        });
        playerDialog.setCanceledOnTouchOutside(false);
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 0, 0, 0, 0);
        playerDialog.getWindow().setBackgroundDrawable(inset);
        playerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    relplayer.setVisibility(View.VISIBLE);
                } else {
                    relplayer.setVisibility(View.GONE);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white, getTheme()));
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
            }
        });
        playerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_blue, getTheme()));
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_blue));
                }
                BottomSheetDialog dialogc = (BottomSheetDialog) dialogInterface;
                FrameLayout bottomSheet = dialogc.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        playerDialog.show();
    }

    public String getTime(int millis) {
        DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(new Date(millis));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imghome:
                setFragment(Home_Fragment.newInStance());
                imghome.setColorFilter(getResources().getColor(R.color.ic_blue, null));
                imgdetails.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                imgprofile.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                img_flash.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                break;
            case R.id.imgdetails:
                imghome.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                imgdetails.setColorFilter(getResources().getColor(R.color.ic_blue, null));
                imgprofile.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                img_flash.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                break;
            case R.id.imgprofile:
                setFragment(Profile_Fragment.newInStance());
                imghome.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                imgdetails.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                imgprofile.setColorFilter(getResources().getColor(R.color.ic_blue, null));
                img_flash.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.e("LOG", "COUNT=> " + count);
        if (count > 0) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentmain);
            if (fragment instanceof Home_Fragment) {
                imghome.setColorFilter(getResources().getColor(R.color.ic_blue, null));
                imgdetails.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                imgprofile.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                img_flash.setColorFilter(getResources().getColor(R.color.ic_unselect, null));
                Log.e("LOG", "Instance OF + Home");
            } else if (fragment instanceof Fragment_Details) {
                Log.e("LOG", "Instance OF + Details");
            } else if (fragment instanceof Fragment_SeeAll) {
                Log.e("LOG", "Instance OF + See All");
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}