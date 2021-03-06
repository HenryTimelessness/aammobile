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
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import com.henrychua.mydailyassessment.R;
import com.henrychua.mydailyassessment.adapters.ReportDetailsViewAdapter;
import com.henrychua.mydailyassessment.helpers.MyApplication;
import com.henrychua.mydailyassessment.models.Assessment;
import com.henrychua.mydailyassessment.models.Question;

/**
 * A simple {\@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * \{\@link QuestionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ReportDetailsFragment extends NavDetailsFragment implements ReportDetailsViewAdapter.OnQuestionInteractionListener {
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        public void onReportQuestionClick(Question Question);
    }
    private OnFragmentInteractionListener mListener;

    /**
     * Store the list of Question so that we can show the list to user
     */
    private List<Question> listOfQuestion;

    private Assessment mAssessment;

    Toast mToast = Toast.makeText(MyApplication.getAppContext(), "", Toast.LENGTH_SHORT);

    /**
     * View reference to Question's recyclerView
     */
    RecyclerView mRecyclerViewList;

    View mEmptyView;
    View mQuestionView;

    Button mSaveButton;

    /**
     * Use this to instantiate the QuestionsFragment
     *
     * @param assessment the parcel to show details
     * @return
     */
    public static ReportDetailsFragment newInstance(Assessment assessment) {
        ReportDetailsFragment myFragment = new ReportDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable("mAssessment", assessment);
        myFragment.setArguments(args);

        return myFragment;
    }

    public ReportDetailsFragment() {
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
        Bundle bundle = this.getArguments();
        this.mAssessment = bundle.getParcelable("mAssessment");

        View inflatedView = inflater.inflate(R.layout.fragment_report_details, container, false);
        mRecyclerViewList = (RecyclerView) inflatedView.findViewById(R.id.questionList);
        mRecyclerViewList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewList.setLayoutManager(llm);
        // setup ui references
        mEmptyView = inflatedView.findViewById(R.id.emptyView);
        mQuestionView = inflatedView.findViewById(R.id.question_view);

        onRefreshComplete(this.mAssessment.getQuestionsList());

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
        return "Report Details";
    }


    //region callbacks

    @Override
    public void onQuestionViewInit(View view) {

    }

    @Override
    public void onQuestionViewClick(Question question) {
        if (mListener != null) {
            mListener.onReportQuestionClick(question);
        }
    }

    //endregion

    //region PRIVATE METHODS

    private void checkAdapterIsEmpty (ReportDetailsViewAdapter adapter) {
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
    private void onRefreshComplete(List<Question> result) {
        // check whether an adapter exists
        if (mRecyclerViewList.getAdapter() == null) {
            this.listOfQuestion = result;
            // Set the Adapter
            final ReportDetailsViewAdapter va = new ReportDetailsViewAdapter(result, this);
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
//            ReportDetailsViewAdapter va = (ReportDetailsViewAdapter) this.mRecyclerViewList.getAdapter();
//            va.replaceList(result);
        }
    }

    //endregion

}
