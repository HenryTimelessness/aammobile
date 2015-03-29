package com.henrychua.mydailyassessment.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by henrychua on 21/01/2015.
 */
public class Assessment extends SugarRecord<Assessment> implements Parcelable {
    private String title;
    private List<Question> questionList;
    private boolean isAnswered;
    private boolean isExported;
    private Customer customer;
    private Date dateAnswered;

    public Assessment(String title, List<Question> questionList, boolean isAnswered, boolean isExported, Customer customer, Date dateAnswered) {
        this.title = title;
        this.questionList = questionList;
        this.isAnswered = isAnswered;
        this.isExported = isExported;
        this.customer = customer;
        this.dateAnswered = dateAnswered;
    }

    public void addQuestion(Question question) {
        this.questionList.add(question);
    }

    public void deleteQuestion(Question question) {
        this.questionList.remove(question);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public boolean isExported() {
        return isExported;
    }

    public void setExported(boolean isExported) {
        this.isExported = isExported;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDateAnswered() {
        return dateAnswered;
    }

    public void setDateAnswered(Date dateAnswered) {
        this.dateAnswered = dateAnswered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeList(this.questionList);
        dest.writeByte(isAnswered ? (byte) 1 : (byte) 0);
        dest.writeByte(isExported ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.customer, 0);
        dest.writeLong(dateAnswered != null ? dateAnswered.getTime() : -1);
    }

    private Assessment(Parcel in) {
        this.title = in.readString();
        this.questionList = new ArrayList<Question>();
        in.readList(this.questionList, null);
        this.isAnswered = in.readByte() != 0;
        this.isExported = in.readByte() != 0;
        this.customer = in.readParcelable(Customer.class.getClassLoader());
        long tmpDateAnswered = in.readLong();
        this.dateAnswered = tmpDateAnswered == -1 ? null : new Date(tmpDateAnswered);
    }

    public static final Creator<Assessment> CREATOR = new Creator<Assessment>() {
        public Assessment createFromParcel(Parcel source) {
            return new Assessment(source);
        }

        public Assessment[] newArray(int size) {
            return new Assessment[size];
        }
    };
}
