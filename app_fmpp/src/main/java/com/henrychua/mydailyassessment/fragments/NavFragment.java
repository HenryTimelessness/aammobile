package com.henrychua.mydailyassessment.fragments;


import android.app.Fragment;
import android.os.Bundle;

/**
 * A simple Abstract fragment that has helper methods to
 */

/**
 * Override this in fragment subclasses so that nav menu can change titles according to the fragment showing
 */
public abstract class NavFragment extends Fragment {
    public abstract String getFragmentTitle();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set fragment to show actionbar items
        setHasOptionsMenu(true);
    }
}
