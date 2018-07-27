package com.example.android.bakingapp.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bakingapp.Model.StepsItem;
import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailFragment extends Fragment {

    String TAG = StepDetailFragment.class.getSimpleName();

    public static final String STEP_ITEM_ID = "step_item_id";

    private TextView stepDescriptionTv;
    private ImageButton prevBtn;
    private ImageButton nextBtn;

    private static StepsItem mStepItem;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;

    private PrevNextListener mPrevNextListener;

    private static long position = 0;
    private static boolean playWhenReady = false;
    public StepDetailFragment() {
    }

    interface PrevNextListener {
        void onPrevClick();

        void onNextClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(STEP_ITEM_ID)) {
            mStepItem = getArguments().getParcelable(STEP_ITEM_ID);
            Log.d(TAG, "clicked: " + mStepItem.getDescription());

            mPrevNextListener = (PrevNextListener) getContext();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        mExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.media_player);
        initializePlayer();

        stepDescriptionTv = (TextView) rootView.findViewById(R.id.step_description);
        stepDescriptionTv.setText(mStepItem.getDescription());

        prevBtn = (ImageButton) rootView.findViewById(R.id.prev_step_btn);
        nextBtn = (ImageButton) rootView.findViewById(R.id.next_step_btn);
        setBtnListeners();

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            Log.d(TAG, "setting title to: " + mStepItem.getShortDescription());
            appBarLayout.setTitle(mStepItem.getShortDescription());
        } else {
            Log.d(TAG, "app bar null");
        }

        return rootView;
    }

    private void setBtnListeners() {
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPrevNextListener.onPrevClick();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPrevNextListener.onNextClick();
            }
        });
    }

    private void initializePlayer() {

        if (mExoPlayer == null && mStepItem.getVideoURL().length() > 0) {

            Uri mediaUri = Uri.parse(mStepItem.getVideoURL());

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

            mExoPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getContext(), getClass().getSimpleName());
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if(position != 0) {
                mExoPlayer.seekTo(position);
            }

            mExoPlayer.prepare(mediaSource);

            mExoPlayer.setPlayWhenReady(playWhenReady);

        } else {
            mExoPlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }

    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}