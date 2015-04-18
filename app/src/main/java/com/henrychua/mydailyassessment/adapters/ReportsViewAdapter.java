package com.henrychua.mydailyassessment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henrychua.mydailyassessment.R;
import com.henrychua.mydailyassessment.models.Assessment;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReportsViewAdapter extends RecyclerView.Adapter<ReportsViewAdapter.AssessmentViewHolder> {
    //TODO: rename position and rows

    private static final int POSITION_REPORT = 0;

    private static final int VIEW_TYPE_REPORT = 0;


    public interface OnReportInteractionListener {
        //TODO: change View view to the views that you need to work with
        public void onReportViewInit(View view);
        //TODO: change View view to the views that you need to work with
        public void onReportViewClick(Assessment assessment);
    }

    private OnReportInteractionListener mListener;

    private List<Assessment> assessments;

    //Ensure that ids are unique
    private int itemCounter = 1;

    public ReportsViewAdapter(List<Assessment> assessments, OnReportInteractionListener listener) {
        this.assessments = assessments;
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (position == POSITION_REPORT) {
            viewType = VIEW_TYPE_REPORT;
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder assessmentViewHolder, int position) {
        final Assessment assessmentInCurrentPosition = assessments.get(position);
        ReportRow  ReportRow  = (ReportRow ) assessmentViewHolder;
        //TODO: bind your model data to the views. make sure views are inited e.g.
        ReportRow.vAssessmentTitle.setText(assessmentInCurrentPosition.getTitle());
        ReportRow.vAssessmentDateDone.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(assessmentInCurrentPosition.getDateAnswered()));

        //TODO: change null to the view that you need to work with
        mListener.onReportViewInit(null);
        assessmentViewHolder.setOnClickListener(new AssessmentViewHolder.IMyViewHolderClicks() {
            public void onClickAssessmentRow(View caller) {
                mListener.onReportViewClick(assessmentInCurrentPosition);
            }
        });
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_REPORT) {
            //TODO: see if you want to change the name of the layout
            View itemView = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.report_row, viewGroup, false);
            return new ReportRow(itemView, new AssessmentViewHolder.IMyViewHolderClicks() {
                public void onClickAssessmentRow(View caller) {
                    //TODO: do something
                }
            });
        }
        return null;
    }

    //region ViewHolders

    /**
     * Abstract ViewHolder to constraint the adapter to hold only subclasses of this ViewHolder
     * Putting everything in a viewholder increases smoothness of scrolling. its the basis of recyclerview
     */
    public static abstract class AssessmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public static interface IMyViewHolderClicks {
            public void onClickAssessmentRow(View caller);
        }

        IMyViewHolderClicks mListener;

        public AssessmentViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);
            this.mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClickAssessmentRow(v);
            }
        }

        public void setOnClickListener(IMyViewHolderClicks iMyViewHolderClicks) {
            mListener = iMyViewHolderClicks;
        }
    }

    /**
     * ViewHolder for Report
     * Putting everything in a viewholder increases smoothness of scrolling. its the basis of recyclerview
     */
    public static class ReportRow extends AssessmentViewHolder {
        //TODO: add Views you wanna work with e.g.
        protected TextView vAssessmentTitle;
        protected TextView vAssessmentDateDone;

        public ReportRow(View v, IMyViewHolderClicks listener) {
            super(v, listener);
            //TODO: find Views you wanna work with and assign e.g.
            vAssessmentTitle = (TextView) v.findViewById(R.id.reports_assessment_name);
            vAssessmentDateDone = (TextView) v.findViewById(R.id.reports_date_answered);
        }
    }

    //endregion
    
}
