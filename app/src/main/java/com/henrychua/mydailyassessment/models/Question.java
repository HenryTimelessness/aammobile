package com.henrychua.mydailyassessment.models;

import java.util.List;

/**
 * Created by henrychua on 21/01/2015.
 */
public class Question {
    public static final String ANSWER_MULTIPLE_CHOICE = "MULTIPLE CHOICE";
    public static final String ANSWER_OPEN_ENDED = "OPEN ENDED";
    public static final String ANSWER_SCALE = "SCALE";
    public static final String ANSWER_YES_NO = "DICHOTOMOUS";

    private String title;
    private String content;
    private String answerType;
    private double ratingLimitLower;
    private double ratingLimitHigher;
    private double ratingAnswer;
    private String openEndedAnswer;
    private List<String> multipleChoiceAnswers;
    private int selectedMultipleAnswer; //1 or 2 for yes no
    private boolean isAnswered;

    private int questionId = -1; // if >1 it exists in database else it doesn't

    public Question() {
        //required empty constructor
    }

    public Question(String title, String content, String answerType, double ratingLimitLower, double ratingLimitHigher, double ratingAnswer, String openEndedAnswer, List<String> multipleChoiceAnswers, int selectedMultipleAnswer, boolean isAnswered) {
        this.title = title;
        this.content = content;
        this.answerType = answerType;
        this.ratingLimitLower = ratingLimitLower;
        this.ratingLimitHigher = ratingLimitHigher;
        this.ratingAnswer = ratingAnswer;
        this.openEndedAnswer = openEndedAnswer;
        this.multipleChoiceAnswers = multipleChoiceAnswers;
        this.selectedMultipleAnswer = selectedMultipleAnswer;
        this.isAnswered = isAnswered;
    }

    public Question(String title, String content, String answerType, double ratingLimitLower, double ratingLimitHigher, double ratingAnswer, String openEndedAnswer, List<String> multipleChoiceAnswers, int selectedMultipleAnswer, boolean isAnswered, int questionId) {
        this.title = title;
        this.content = content;
        this.answerType = answerType;
        this.ratingLimitLower = ratingLimitLower;
        this.ratingLimitHigher = ratingLimitHigher;
        this.ratingAnswer = ratingAnswer;
        this.openEndedAnswer = openEndedAnswer;
        this.multipleChoiceAnswers = multipleChoiceAnswers;
        this.selectedMultipleAnswer = selectedMultipleAnswer;
        this.isAnswered = isAnswered;
        this.questionId = questionId;
    }

    public double getRatingAnswer() {
        return ratingAnswer;
    }

    public void setRatingAnswer(double ratingAnswer) {
        this.ratingAnswer = ratingAnswer;
    }

    public void answerMultipleChoice(int selectedAnswer) {
        this.selectedMultipleAnswer = selectedAnswer;
        this.isAnswered = true;
    }

    public void answerOpenEnded(String openEndedAnswer) {
        this.openEndedAnswer = openEndedAnswer;
        this.isAnswered = true;
    }

    public void answerRating(double ratingAnswer) {
        this.ratingAnswer = ratingAnswer;
        this.isAnswered = true;
    }

    public double getRatingLimitLower() {
        return ratingLimitLower;
    }

    public void setRatingLimitLower(double ratingLimitLower) {
        this.ratingLimitLower = ratingLimitLower;
    }

    public double getRatingLimitHigher() {
        return ratingLimitHigher;
    }

    public void setRatingLimitHigher(double ratingLimitHigher) {
        this.ratingLimitHigher = ratingLimitHigher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getOpenEndedAnswer() {
        return openEndedAnswer;
    }

    public void setOpenEndedAnswer(String openEndedAnswer) {
        this.openEndedAnswer = openEndedAnswer;
    }

    public List<String> getMultipleChoiceAnswers() {
        return multipleChoiceAnswers;
    }

    public void setMultipleChoiceAnswers(List<String> multipleChoiceAnswers) {
        this.multipleChoiceAnswers = multipleChoiceAnswers;
    }

    public int getSelectedMultipleAnswer() {
        return selectedMultipleAnswer;
    }

    public void setSelectedMultipleAnswer(int selectedMultipleAnswer) {
        this.selectedMultipleAnswer = selectedMultipleAnswer;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
