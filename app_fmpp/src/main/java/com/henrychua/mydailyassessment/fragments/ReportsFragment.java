package com.henrychua.mydailyassessment.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.henrychua.mydailyassessment.R;
import com.henrychua.mydailyassessment.adapters.AssessmentDBAdapter;
import com.henrychua.mydailyassessment.adapters.ReportsViewAdapter;
import com.henrychua.mydailyassessment.helpers.MyApplication;
import com.henrychua.mydailyassessment.models.Assessment;

import java.util.List;

/**
 * A simple {\@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * \{\@link AssessmentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ReportsFragment extends NavFragment implements ReportsViewAdapter.OnReportInteractionListener {
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        public void onReportClick(Assessment assessment);
    }
    private OnFragmentInteractionListener mListener;

    /**
     * Store the list of Assessment so that we can show the list to user
     */
    private List<Assessment> listOfAssessment;

    /**
     * View reference to Assessment's recyclerView
     */
    RecyclerView mRecyclerViewList;

    View mEmptyView;
    View mAssessmentView;

    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set fragment to show actionbar items
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_report, container, false);
        mRecyclerViewList = (RecyclerView) inflatedView.findViewById(R.id.reports_List);
        mRecyclerViewList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewList.setLayoutManager(llm);
        // setup ui references
        mEmptyView = inflatedView.findViewById(R.id.emptyView);
        mAssessmentView = inflatedView.findViewById(R.id.reports_view);

        // get from db the assessments
        AssessmentDBAdapter assessmentDBAdapter = new AssessmentDBAdapter(MyApplication.getAppContext());
        listOfAssessment = assessmentDBAdapter.getAllDoneAssessments();

        onRefreshComplete(listOfAssessment);

        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // TODO: move from basemenu to here. currently in basemenu
//        MenuInflater menuInflater = getActivity().getMenuInflater();
//        menuInflater.inflate(R.menu.menu_boilerPlate, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
//            case R.id.action_refresh_boilerPlate:
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public String getFragmentTitle() {
        return "Reports";
    }


    //region callbacks

    @Override
    public void onReportViewInit(View view) {

    }

    @Override
    public void onReportViewClick(Assessment assessment) {
        if (mListener != null) {
            mListener.onReportClick(assessment);
        }
    }


    //endregion

    //region PRIVATE METHODS

    private void checkAdapterIsEmpty (ReportsViewAdapter adapter) {
        if (adapter.getItemCount() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerViewList.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerViewList.setVisibility(View.VISIBLE);
        }
    }

    /**
     * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
     * ListAdapter and turns off the progress bar.
     */
    private void onRefreshComplete(List<Assessment> result) {
        // check whether an adapter exists
        if (mRecyclerViewList.getAdapter() == null) {
            this.listOfAssessment = result;
            // Set the Adapter
            final ReportsViewAdapter va = new ReportsViewAdapter(result, this);
            va.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    //checks and show empty view when appropriate
                    checkAdapterIsEmpty(va);
                }
            });
            this.mRecyclerViewList.setAdapter(va);
            checkAdapterIsEmpty(va);
        } else {
            // Remove all items from the ListAdapter, and then replace them with the new items
//            ReportsViewAdapter va = (ReportsViewAdapter) this.mRecyclerViewList.getAdapter();
//            va.replaceList(result);
        }
    }

     //endregion

}
