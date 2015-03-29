package com.henrychua.mydailyassessment.fragments;


import android.os.Bundle;
import android.view.MenuItem;

/**
 * An Abstract class for lower level details fragments when using nav drawer,
 * helps change the nav icon to up button when navigating inside this fragment
 */
public abstract class NavDetailsFragment extends NavFragment {
    public NavDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // needed to indicate that the fragment would
        // like to add items to the Options Menu
        setHasOptionsMenu(true);
        // update the actionbar to show the up carat/affordance
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        switch (item.getItemId()) {
            case android.R.id.home:
                //called when the up affordance/carat in actionbar is pressed
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
