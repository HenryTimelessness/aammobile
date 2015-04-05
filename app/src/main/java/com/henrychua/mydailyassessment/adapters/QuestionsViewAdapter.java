package com.henrychua.mydailyassessment.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import com.henrychua.mydailyassessment.R;
import com.henrychua.mydailyassessment.models.Question;

import java.util.List;

public class QuestionsViewAdapter extends RecyclerView.Adapter<QuestionsViewAdapter.QuestionViewHolder> {
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
        public void onQuestionAnswerUpdated(Question question, String answerOpenEnded);
        public void onQuestionAnswerUpdated(Question question, double answerScale);
    }
    
    private OnQuestionInteractionListener mListener;
    
    private List<Question> questions;
    
    //Ensure that ids are unique
    private int itemCounter = 1;
    
    public QuestionsViewAdapter(List<Question> questions, OnQuestionInteractionListener listener) {
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
            QuestionOpenEndedRow questionOpenEndedRow = (QuestionOpenEndedRow) questionViewHolder;
            //TODO: bind your model data to the views. make sure views are inited e.g.
            questionOpenEndedRow.vQuestionContent.setText(questionInRow.getContent());
            questionOpenEndedRow.vQuestionAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mListener.onQuestionAnswerUpdated(questionInRow, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            //TODO: change null to the view that you need to work with
            mListener.onQuestionViewInit(null);
            questionViewHolder.setOnClickListener(new QuestionsViewAdapter.QuestionViewHolder.IMyViewHolderClicks() {
                public void onClickQuestionRow(View caller) {
                    mListener.onQuestionViewClick(questions.get(position));
                }
            });
        } else if (questionInRow.getAnswerType().equals(Question.ANSWER_SCALE)) {
            QuestionScaleRow questionScaleRow = (QuestionScaleRow) questionViewHolder;
            //TODO: bind your model data to the views. make sure views are inited e.g.
            questionScaleRow.vQuestionContent.setText(questionInRow.getContent());
            questionScaleRow.vQuestionAnswer.setMax((int)questionInRow.getRatingLimitHigher());
            questionScaleRow.vQuestionAnswer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mListener.onQuestionAnswerUpdated(questionInRow, progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            //TODO: change null to the view that you need to work with
            mListener.onQuestionViewInit(null);
            questionViewHolder.setOnClickListener(new QuestionsViewAdapter.QuestionViewHolder.IMyViewHolderClicks() {
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
            inflate(R.layout.question_open_ended_row, viewGroup, false);
            return new QuestionOpenEndedRow(itemView, new QuestionViewHolder.IMyViewHolderClicks() {
                public void onClickQuestionRow(View caller) {
                    //TODO: do something
                }
            });
        } else if (viewType == VIEW_TYPE_QUESTION_SCALE) {
            //TODO: see if you want to change the name of the layout
            View itemView = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.question_seekbar_row, viewGroup, false);
            return new QuestionScaleRow(itemView, new QuestionViewHolder.IMyViewHolderClicks() {
                public void onClickQuestionRow(View caller) {
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
    public static class QuestionOpenEndedRow extends QuestionViewHolder {
        //TODO: add Views you wanna work with e.g.
        protected TextView vQuestionContent;
        protected EditText vQuestionAnswer;

        public QuestionOpenEndedRow(View v, IMyViewHolderClicks listener) {
            super(v, listener);
            //TODO: find Views you wanna work with and assign e.g.
            vQuestionContent = (TextView) v.findViewById(R.id.questions_question_content);
            vQuestionAnswer = (EditText) v.findViewById(R.id.questions_question_answer_text);
        }
    }
       /**
     * ViewHolder for Scale Question
     * Putting everything in a viewholder increases smoothness of scrolling. its the basis of recyclerview
     */
    public static class QuestionScaleRow extends QuestionViewHolder {
        //TODO: add Views you wanna work with e.g.
        protected TextView vQuestionContent;
        protected SeekBar vQuestionAnswer;

        public QuestionScaleRow(View v, IMyViewHolderClicks listener) {
            super(v, listener);
            //TODO: find Views you wanna work with and assign e.g.
            vQuestionContent = (TextView) v.findViewById(R.id.questions_question_content);
            vQuestionAnswer = (SeekBar) v.findViewById(R.id.questions_answer_seekbar);
        }
    }

    //endregion
    
}
