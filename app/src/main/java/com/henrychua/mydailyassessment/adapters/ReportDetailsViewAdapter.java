package com.henrychua.mydailyassessment.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.henrychua.mydailyassessment.R;
import com.henrychua.mydailyassessment.models.Question;

import java.util.List;

/**
 * Created by henrychua on 18/4/15.
 */
public class ReportDetailsViewAdapter extends RecyclerView.Adapter<ReportDetailsViewAdapter.QuestionViewHolder>{
    private static final int NUM_OF_ITEMS_IN_LIST = 1;
    //TODO: rename position and rows

    private static final int POSITION_QUESTION = 0;

    private static final int VIEW_TYPE_QUESTION_OPEN_ENDED = 0;
    private static final int VIEW_TYPE_QUESTION_SCALE = 1;


    public interface OnQuestionInteractionListener {
        //TODO: change View view to the views that you need to work with
        public void onQuestionViewInit(View view);
        //TODO: change View view to the views that you need to work with
        public void onQuestionViewClick(Question question);
    }

    private OnQuestionInteractionListener mListener;

    private List<Question> questions;

    //Ensure that ids are unique
    private int itemCounter = 1;

    public ReportDetailsViewAdapter(List<Question> questions, OnQuestionInteractionListener listener) {
        this.questions = questions;
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        Question questionInRow = questions.get(position);
        if (questionInRow.getAnswerType().equals(Question.ANSWER_OPEN_ENDED)) {
            viewType = VIEW_TYPE_QUESTION_OPEN_ENDED;
        } else if (questionInRow.getAnswerType().equals(Question.ANSWER_SCALE)) {
            viewType = VIEW_TYPE_QUESTION_SCALE;
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder questionViewHolder, final int position) {
        final Question questionInRow = questions.get(position);
        if (questionInRow.getAnswerType().equals(Question.ANSWER_OPEN_ENDED)) {
            QuestionRow questionOpenEndedRow = (QuestionRow) questionViewHolder;
            //TODO: bind your model data to the views. make sure views are inited e.g.
            questionOpenEndedRow.vQuestionContent.setText(questionInRow.getContent());
            questionOpenEndedRow.vQuestionAnswer.setText(questionInRow.getOpenEndedAnswer());

            //TODO: change null to the view that you need to work with
            mListener.onQuestionViewInit(null);
            questionViewHolder.setOnClickListener(new ReportDetailsViewAdapter.QuestionViewHolder.IMyViewHolderClicks() {
                public void onClickQuestionRow(View caller) {
                    mListener.onQuestionViewClick(questions.get(position));
                }
            });
        } else if (questionInRow.getAnswerType().equals(Question.ANSWER_SCALE)) {
            QuestionRow questionScaleRow = (QuestionRow) questionViewHolder;
            //TODO: bind your model data to the views. make sure views are inited e.g.
            questionScaleRow.vQuestionContent.setText(questionInRow.getContent());
            questionScaleRow.vQuestionAnswer.setText(String.valueOf(questionInRow.getRatingAnswer()) + "/" + String.valueOf(questionInRow.getRatingLimitHigher()));
            //TODO: change null to the view that you need to work with
            mListener.onQuestionViewInit(null);
            questionViewHolder.setOnClickListener(new ReportDetailsViewAdapter.QuestionViewHolder.IMyViewHolderClicks() {
                public void onClickQuestionRow(View caller) {
                    mListener.onQuestionViewClick(questions.get(position));
                }
            });
        }


    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_QUESTION_OPEN_ENDED) {
            //TODO: see if you want to change the name of the layout
            View itemView = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.report_details_row, viewGroup, false);
            return new QuestionRow(itemView, new QuestionViewHolder.IMyViewHolderClicks() {
                public void onClickQuestionRow(View caller) {
                    //TODO: do something
                }
            });
        } else {
            //TODO: see if you want to change the name of the layout
            View itemView = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.report_details_row, viewGroup, false);
            return new QuestionRow(itemView, new QuestionViewHolder.IMyViewHolderClicks() {
                public void onClickQuestionRow(View caller) {
                    //TODO: do something
                }
            });
        }
//        return null;
    }

    //region ViewHolders

    /**
     * Abstract ViewHolder to constraint the adapter to hold only subclasses of this ViewHolder
     * Putting everything in a viewholder increases smoothness of scrolling. its the basis of recyclerview
     */
    public static abstract class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public static interface IMyViewHolderClicks {
            public void onClickQuestionRow(View caller);
        }

        IMyViewHolderClicks mListener;

        public QuestionViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);
            this.mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClickQuestionRow(v);
            }
        }

        public void setOnClickListener(IMyViewHolderClicks iMyViewHolderClicks) {
            mListener = iMyViewHolderClicks;
        }
    }

    /**
     * ViewHolder for Open Ended Question
     * Putting everything in a viewholder increases smoothness of scrolling. its the basis of recyclerview
     */
    public static class QuestionRow extends QuestionViewHolder {
        //TODO: add Views you wanna work with e.g.
        protected TextView vQuestionContent;
        protected TextView vQuestionAnswer;

        public QuestionRow(View v, IMyViewHolderClicks listener) {
            super(v, listener);
            //TODO: find Views you wanna work with and assign e.g.
            vQuestionContent = (TextView) v.findViewById(R.id.report_question_content);
            vQuestionAnswer = (TextView) v.findViewById(R.id.report_question_answer);
        }
    }

    //endregion
}
