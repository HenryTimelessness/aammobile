package com.henrychua.mydailyassessment.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.henrychua.mydailyassessment.R;

/**
 * Created by henrychua on 24/12/2014.
 */
public class UIHelper {
    public static final int SHORT_ANIMATION_TIME_MILLIS = 200;
    /**
     * @deprecated old way that screws with ui
     * Temporarily replaces the view with a loading in progress spinning icon
     * @param show show or hide the progress spinning icon
     * @param v the view to work with
     * @param context current context, use this.getActivity if called from a fragment
     */
    public static void showProgressOld(final boolean show, final View v, Context context) {
        //get parent of the view
        final ViewGroup viewParent = (ViewGroup) v.getParent();
        // show
        if (show) {
            Log.v("UI", "showing progress");
            // fade out v
            v.animate().setDuration(SHORT_ANIMATION_TIME_MILLIS).alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    v.setVisibility(View.GONE);
                    Log.v("UI", "setVisibility GONE");
                }
            });
            // loop through to find progressbar
            for (int itemPos = 0; itemPos < viewParent.getChildCount(); itemPos++) {
                final View view = viewParent.getChildAt(itemPos);
                if (view.equals(v)) {
                    Log.v("view", "found view from parent equals");
                    final int i = itemPos;
                    // attach a progressbar below/beside view v
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final ProgressBar progressBar = (ProgressBar) inflater.inflate(R.layout.progress_spinner, null);
                    viewParent.addView(progressBar, itemPos);
                    // fade in progressbar
                    progressBar.animate().setDuration(SHORT_ANIMATION_TIME_MILLIS).alpha(1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setTag("tag_pb");
                            Log.v("progressBar", "setVisibility VISIBLE");
                        }
                    });
                    break;
                }
            }
        } else {
            Log.v("view", "false for showprogress");
            // fade in
            v.animate().setDuration(SHORT_ANIMATION_TIME_MILLIS).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.v("view", "making v visible");
                    v.setVisibility(View.VISIBLE);
                }
            });
            // loop through to find progressbar
            for (int itemPos = 0; itemPos < viewParent.getChildCount(); itemPos++) {
                final View view = viewParent.getChildAt(itemPos);
                if (view instanceof ProgressBar) {
                    Log.v("view", "found progressbar frm parent");
                    final int i = itemPos;
                    // fade out and remove progressbar
                    view.animate().setDuration(SHORT_ANIMATION_TIME_MILLIS).alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Log.v("view", "removing view at i");
                            viewParent.removeViewAt(i);
                        }
                    });
                    break;
                }
            }
        }
    }

    /**
     * Temporarily replaces the view with a loading in progress spinning icon
     * @param show show or hide the progress spinning icon
     * @param v the view to work with
     * @param context current context, use this.getActivity if called from a fragment
     */
    public static void showProgress(final boolean show, final View v, Context context) {
        //get parent of the view
        final ViewGroup viewParent = (ViewGroup) v.getParent();
        // show
        if (show) {
            Log.v("UI", "showing progress");
            // fade out v
            v.animate().setDuration(SHORT_ANIMATION_TIME_MILLIS).alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    v.setVisibility(View.GONE);
                    Log.v("UI", "setVisibility GONE");
                }
            });
            // loop through to find position of v
            for (int itemPos = 0; itemPos < viewParent.getChildCount(); itemPos++) {
                final View view = viewParent.getChildAt(itemPos);
                if (view.equals(v)) {
                    Log.v("view", "found view from parent equals");
                    final int i = itemPos;
                    // attach a progressbar below/beside view v
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View progressBarView = inflater.inflate(R.layout.progress_spinner_new, viewParent);
//                    viewParent.addView(progressBarView, itemPos);
                    // fade in progressbar
                    progressBarView.animate().setDuration(SHORT_ANIMATION_TIME_MILLIS).alpha(1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            progressBarView.setVisibility(View.VISIBLE);
                            Log.v("progressBar", "setVisibility VISIBLE");
                        }
                    });
                    break;
                }
            }
        } else {
            Log.v("view", "false for showprogress");
            // fade in
            v.animate().setDuration(SHORT_ANIMATION_TIME_MILLIS).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.v("view", "making v visible");
                    v.setVisibility(View.VISIBLE);
                }
            });
            // find progressbar
            final View progressView = viewParent.findViewById(R.id.progress_spinner_container);
            if (progressView != null) {
                // fade out and remove progressbar
                progressView.animate().setDuration(SHORT_ANIMATION_TIME_MILLIS).alpha(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.v("view", "removing view at i");
                        viewParent.removeView(progressView);
                    }
                });
            }
        }
    }
}
