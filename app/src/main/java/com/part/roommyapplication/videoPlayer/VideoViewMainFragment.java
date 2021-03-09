package com.part.roommyapplication.videoPlayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.part.roommyapplication.R;


public class VideoViewMainFragment extends Fragment {

    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btnFullscreen;

    public static SimpleExoPlayer simpleExoPlayer;

    private static final String VIDEO_TEST_URL="https:\\/\\/rkmshillong.online\\/public\\/media\\/BEG\\/lectures\\/BEG-01\\/videos\\/BEG-01-03\\/136596e36bc0b4e51c9edae4f36d3823.mp4";

    String videoUrl;
    long seek=0;



    public VideoViewMainFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video_view_main, container, false);

        //Initialization
        playerView = v.findViewById(R.id.player_view);
        progressBar = v.findViewById(R.id.progress_bar);
        btnFullscreen = v.findViewById(R.id.bt_fullscreen);

        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            videoUrl = bundle.getString("path");
            seek = bundle.getLong("seek");
            //Log.i("SAURABH",videoUrl);


            if (simpleExoPlayer != null) {
                Log.i("SAURABH","NEW LOG");
                //simpleExoPlayer.setPlayWhenReady(false);
                //simpleExoPlayer.stop();
                //simpleExoPlayer.seekTo(0);
                simpleExoPlayer.release();
           }

            iniExoplayer(videoUrl,seek);
            //Log.i("SAURABH","skdnvsdknvds");

            //Stop any previous video on clicking new video
//            if (simpleExoPlayer != null) {
//                Log.i("SAURABH","NEW LOG");
//                simpleExoPlayer.setPlayWhenReady(false);
//                simpleExoPlayer.stop();
//                //simpleExoPlayer.seekTo(0);
//            }
        }
        return v;
    }

    //To Initialise Simple Exoplayer
    public void iniExoplayer(String url,long seek) {


        //Stop any previous video on clicking new video
        if (simpleExoPlayer != null) {
            //Log.i("SAURABH","NEW LOG");
            //simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.stop();
            //simpleExoPlayer.release();
            //simpleExoPlayer.seekTo(0);
        }

//        Initialize load control
        LoadControl loadControl = new DefaultLoadControl();
        //Initialize band width meter
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //Initialize track selector
        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );
//
//        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
//
//        //Initialize data source factory
//        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");
//        //Initialize extractors factory
//        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//        //Initialize media source
//        //MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(VIDEO_TEST_URL),factory, extractorsFactory, null, null);
//        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),factory, extractorsFactory, null, null);
//
//        playerView.setPlayer(simpleExoPlayer);
//        playerView.setKeepScreenOn(true);
//
//        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(),"appname"));
        MediaSource videosource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url));
        simpleExoPlayer.prepare(videosource);
        //if(seek!=0){
            simpleExoPlayer.seekTo(seek-2);
        //}
        simpleExoPlayer.setPlayWhenReady(true);

        simpleExoPlayer.addListener(new com.google.android.exoplayer2.Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                super.onPlayerStateChanged(playWhenReady, playbackState);

                //Check condition
                if (playbackState == com.google.android.exoplayer2.Player.STATE_BUFFERING) {
                    //Show progress bar
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    //hide progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView= view.findViewById(R.id.player_view);
        btnFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), FullScreenActivity.class);
                intent.putExtra("seek",simpleExoPlayer.getCurrentPosition());
                intent.putExtra("path",videoUrl.toString());
                simpleExoPlayer.release();
                startActivity(intent);

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
//        simpleExoPlayer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
//        simpleExoPlayer.release();
    }


}