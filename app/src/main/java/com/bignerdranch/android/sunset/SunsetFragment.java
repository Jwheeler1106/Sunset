package com.bignerdranch.android.sunset;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.content.res.Resources;
import android.animation.AnimatorSet;

/**
 * Created by johnwheeler on 12/12/16.
 */

public class SunsetFragment extends Fragment
{
    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor, mSunsetSkyColor, mNightSkyColor;

    private boolean clicked;

    public static SunsetFragment newInstance()
    {
        return new SunsetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        clicked = true;

        mSceneView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (clicked == true)
                {
                    startAnimation();
                    clicked = false;
                }

                else
                {
                    sunRise();
                    clicked = true;
                }

            }
        });

        return view;
    }

    private void startAnimation()
    {
        float sunYstart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();
        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y", sunYstart, sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor).setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());
        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor).
                setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator).with(sunsetSkyAnimator).before(nightSkyAnimator);
        animatorSet.start();
        //heightAnimator.start();
        //sunsetSkyAnimator.start();
    }

    private void sunRise()
    {
        float sunYstart = mSkyView.getHeight();
        float sunYEnd = mSkyView.getTop() + 237;

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y",sunYstart, sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunRiseAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor).
                setDuration(3000);
        sunRiseAnimator.setEvaluator(new ArgbEvaluator());
        ObjectAnimator blueSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor).
                setDuration(1500);
        blueSkyAnimator.setEvaluator(new ArgbEvaluator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator).with(sunRiseAnimator).before(blueSkyAnimator);
        animatorSet.start();
        //heightAnimator.start();
        //sunRiseAnimator.start();
    }
}