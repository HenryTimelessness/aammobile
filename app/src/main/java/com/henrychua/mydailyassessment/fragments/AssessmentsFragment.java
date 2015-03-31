package com.henrychua.mydailyassessment.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.henrychua.mydailyassessment.R;
import com.henrychua.mydailyassessment.adapters.AssessmentsViewAdapter;
import com.henrychua.mydailyassessment.fragments.NavFragment;
import com.henrychua.mydailyassessment.models.Assessment;
import com.henrychua.mydailyassessment.models.Customer;
import com.henrychua.mydailyassessment.models.Question;
import com.orm.query.Condition;
import com.orm.query.Select;

/**
 * A simple {\@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * \{\@link AssessmentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AssessmentsFragment extends NavFragment implements AssessmentsViewAdapter.OnAssessmentInteractionListener {
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        public void onAssessmentClick(Assessment Assessment);
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

    public AssessmentsFragment() {
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
        View inflatedView = inflater.inflate(R.layout.fragment_assessment, container, false);
        mRecyclerViewList = (RecyclerView) inflatedView.findViewById(R.id.assessmentList);
        mRecyclerViewList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewList.setLayoutManager(llm);
        // setup ui references
        mEmptyView = inflatedView.findViewById(R.id.emptyView);
        mAssessmentView = inflatedView.findViewById(R.id.assessment_view);

        //create test data
        listOfAssessment = new ArrayList<Assessment>();
        List<Question> questions = new ArrayList<Question>();

        questions.add(new Question("Titlez", "Some qns", Question.ANSWER_RATING, 10.0, 5.0, null, null, 0, false, null));
        questions.add(new Question("Titlez2", "Some qns2", Question.ANSWER_RATING, 10.0, 5.0, null, null, 0, false, null));
        Assessment assessment1 = new Assessment("PANAS", questions, true, true, new Customer("Chua", 123456789, "string@string.com", null), new Date());
        Assessment assessment2 = new Assessment("PANASShort", questions, true, true, new Customer("Chua", 123456789, "string@string.com", null), new Date());
        listOfAssessment.add(assessment1);
        listOfAssessment.add(assessment2);
        listOfAssessment.get(0).save();
        List<Assessment> savedAssessments = Select.from(Assessment.class)
//                .where(Condition.prop("is_answered").eq(true))
                .list();
        listOfAssessment.add(savedAssessments.get(0));
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
        return "Assessment";
    }


    //region callbacks

    @Override
    public void onPanasViewClick(Assessment assessment) {
        if (mListener != null) {
            mListener.onAssessmentClick(assessment);
        }
    }

    @Override
    public void onPanasViewInit(View view) {

    }


    //endregion

    //region PRIVATE METHODS

    private void checkAdapterIsEmpty (AssessmentsViewAdapter adapter) {
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
            final AssessmentsViewAdapter va = new AssessmentsViewAdapter(result, this);
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
//            AssessmentsViewAdapter va = (AssessmentsViewAdapter) this.mRecyclerViewList.getAdapter();
//            va.replaceList(result);
        }
    }

     //endregion

}
