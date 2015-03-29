package com.henrychua.mydailyassessment.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.henrychua.mydailyassessment.R;
import com.henrychua.mydailyassessment.fragments.AssessmentsFragment;
import com.henrychua.mydailyassessment.fragments.NavDetailsFragment;
import com.henrychua.mydailyassessment.fragments.NavFragment;
import com.henrychua.mydailyassessment.fragments.NavigationDrawerFragment;
import com.henrychua.mydailyassessment.fragments.QuestionsFragment;
import com.henrychua.mydailyassessment.fragments.ReportsFragment;
import com.henrychua.mydailyassessment.models.Assessment;
import com.henrychua.mydailyassessment.models.Question;


public class BaseNavMenuActivity extends Activity  implements
        AssessmentsFragment.OnFragmentInteractionListener,
        ReportsFragment.OnFragmentInteractionListener,
        QuestionsFragment.OnFragmentInteractionListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav_menu);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // turn on the Navigation Drawer image;
        // this is called in the LowerLevelFragments
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            //if not a details fragment, show normal nav menu
            if (!(fragment instanceof NavDetailsFragment)) {
                mNavigationDrawerFragment.displayActionBarNav();
            }
            mTitle = ((NavFragment) fragment).getFragmentTitle();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                // called when the up affordance/carat in actionbar is pressed
                // if loginMainFragment, don't back
                //TODO: see what this does again?
//                Fragment f = getFragmentManager().findFragmentById(R.id.login_fragment_container);
//                if (!(f instanceof ParcelDetailsFragment)) {
                    return super.onOptionsItemSelected(item);
//                }
//                else execute onBackPressed()
//                onBackPressed();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        //  TODO: navigate to correct fragment
        FragmentManager fragmentManager = getFragmentManager();
        // Display the fragment as the main content.
        NavFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AssessmentsFragment();
                break;
            case 1:
                fragment = new ReportsFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            mTitle = fragment.getFragmentTitle();
            getActionBar().setTitle(fragment.getFragmentTitle());
        }
    }


    /**
     * Restores the actionbar to show the title
     */
    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * Sets the title of the current screen
     *
     * @param title
     */
    public void setActionBarTitle(String title) {
        mTitle = title;
        getActionBar().setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //TODO: do other things needed to show actionbar item
//            if (getFragmentManager().findFragmentById(R.id.fragment_container) instanceof ParcelsFragment) {
//                getMenuInflater().inflate(R.menu.menu_parcels, menu);
//            } else {
//                getMenuInflater().inflate(R.menu.menu_base, menu);
//            }
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    //region Listener callbacks

//TODO: add your listener callbacks


    @Override
    public void onReportClick(Assessment Assessment) {

    }

    @Override
    public void onAssessmentClick(Assessment assessment) {
        // update the actionbar to show the up carat/affordance
        mNavigationDrawerFragment.unDisplayActionBarNav();
        // fragment transit to details, add to backstack
        Toast.makeText(this, "clicked", Toast.LENGTH_LONG);
        FragmentManager fragmentManager = getFragmentManager();
        NavFragment fragment = QuestionsFragment.newInstance(assessment);
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();
            this.setActionBarTitle(fragment.getFragmentTitle());
        }
    }

    @Override
    public void onQuestionClick(Question Question) {

    }

//    @Override
//    public void onParcelClick(BumboxParcel bumboxParcel, Station station) {
//        // update the actionbar to show the up carat/affordance
//        mNavigationDrawerFragment.unDisplayActionBarNav();
//        // fragment transit to details, add to backstack
//        Toast.makeText(this, "clicked", Toast.LENGTH_LONG);
//        FragmentManager fragmentManager = getFragmentManager();
//        Fragment fragment = ParcelDetailsFragment.newInstance(bumboxParcel, station);
//        if (fragment != null) {
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//    }
//
//    @Override
//    public void onCollectParcelClicked(BumboxParcel bumboxParcel) {
//        // update the actionbar to show the up carat/affordance
//        mNavigationDrawerFragment.unDisplayActionBarNav();
//        // fragment transit to collect parcel, add to backstack
//        FragmentManager fragmentManager = getFragmentManager();
//        Fragment fragment = new QRCodeFragment();
//        if (fragment != null) {
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left);
//            transaction.replace(R.id.fragment_container, fragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//    }




    //endregion

    //region Private helper functions

    //endregion
}
