package com.henrychua.mydailyassessment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.henrychua.mydailyassessment.R;
import com.henrychua.mydailyassessment.models.Assessment;

import java.util.List;

public class AssessmentsViewAdapter extends RecyclerView.Adapter<AssessmentsViewAdapter.AssessmentViewHolder> {
    private static final int NUM_OF_ITEMS_IN_LIST = 2;
    //TODO: rename position and rows

    private static final int POSITION_PANAS = 0;

    private static final int VIEW_TYPE_PANAS = 0;

    public interface OnAssessmentInteractionListener {
            //TODO: change View view to the views that you need to work with
            public void onPanasViewInit(View view);
            //TODO: change View view to the views that you need to work with
            public void onPanasViewClick(Assessment assessment);
    }
    
    private OnAssessmentInteractionListener mListener;
    
    private List<Assessment> assessments;
    
    //Ensure that ids are unique
    private int itemCounter = 1;
    
    public AssessmentsViewAdapter(List<Assessment> assessments, OnAssessmentInteractionListener listener) {
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
        viewType = VIEW_TYPE_PANAS;
        return viewType;
    }
    
    @Override
    public void onBindViewHolder(AssessmentViewHolder assessmentViewHolder, final int position) {
        Assessment assessmentInCurrentPosition = assessments.get(position);
        PANASRow PANASRow = (PANASRow) assessmentViewHolder;
        //TODO: bind your model data to the views. make sure views are inited e.g.
        PANASRow.vTitle.setText(assessmentInCurrentPosition.getTitle());
        PANASRow.vNumQuestions.setText(String.valueOf(assessmentInCurrentPosition.getQuestionList().size()));

        //TODO: change null to the view that you need to work with
        mListener.onPanasViewInit(null);
        assessmentViewHolder.setOnClickListener(new AssessmentsViewAdapter.AssessmentViewHolder.IMyViewHolderClicks() {
            public void onClickAssessmentRow(View caller) {
                mListener.onPanasViewClick(assessments.get(position));
            }
        });
    }
    
    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_PANAS) {
            //TODO: see if you want to change the name of the layout
            View itemView = LayoutInflater.from(viewGroup.getContext()).
            inflate(R.layout.assessment_panas_row, viewGroup, false);
            return new PANASRow(itemView, new AssessmentsViewAdapter.AssessmentViewHolder.IMyViewHolderClicks() {
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
     * ViewHolder for PANAS
     * Putting everything in a viewholder increases smoothness of scrolling. its the basis of recyclerview
     */
    public static class PANASRow extends AssessmentViewHolder {
        //TODO: add Views you wanna work with e.g.
        protected TextView vTitle;
        protected TextView vNumQuestions;
        
        public PANASRow(View v, IMyViewHolderClicks listener) {
            super(v, listener);
            //TODO: find Views you wanna work with and assign e.g.
            vTitle = (TextView) v.findViewById(R.id.assessments_assessment_title);
            vNumQuestions = (TextView) v.findViewById(R.id.assessments_number_of_questions);
        }
    }
    
    //endregion
    
}
