package com.henrychua.mydailyassessment.models;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by henrychua on 21/01/2015.
 */
public class Question extends SugarRecord<Question> {
    public static final int ANSWER_OPEN_ENDED = 0;
    public static final int ANSWER_MULTIPLE_CHOICE = 1;
    public static final int ANSWER_YES_NO = 2;
    public static final int ANSWER_RATING = 3;

    private String title;
    private String content;
    private int answerType;
    private double ratingRange;
    private double ratingAnswer;
    private String openEndedAnswer;
    private List<String> multipleChoiceAnswers;
    private int selectedMultipleAnswer; //1 or 2 for yes no
    private boolean isAnswered;
    private Assessment assessment;

    public Question() {
        //required empty constructor
    }

    public Question(String title, String content, int answerType, double ratingRange, double ratingAnswer, String openEndedAnswer, List<String> multipleChoiceAnswers, int selectedMultipleAnswer, boolean isAnswered, Assessment assessment) {
        this.title = title;
        this.content = content;
        this.answerType = answerType;
        this.ratingRange = ratingRange;
        this.ratingAnswer = ratingAnswer;
        this.openEndedAnswer = openEndedAnswer;
        this.multipleChoiceAnswers = multipleChoiceAnswers;
        this.selectedMultipleAnswer = selectedMultipleAnswer;
        this.isAnswered = isAnswered;
        this.assessment = assessment;

    }
    public double getRatingAnswer() {
        return ratingAnswer;
    }

    public void setRatingAnswer(double ratingAnswer) {
        this.ratingAnswer = ratingAnswer;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
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

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public double getRatingRange() {
        return ratingRange;
    }

    public void setRatingRange(double ratingRange) {
        this.ratingRange = ratingRange;
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
}
