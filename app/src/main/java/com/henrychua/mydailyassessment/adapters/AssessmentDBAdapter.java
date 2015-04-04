package com.henrychua.mydailyassessment.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.henrychua.mydailyassessment.helpers.MyApplication;
import com.henrychua.mydailyassessment.models.Assessment;
import com.henrychua.mydailyassessment.models.Customer;
import com.henrychua.mydailyassessment.models.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by henrychua on 2/4/15.
 */
public class AssessmentDBAdapter {
    AssessmentDBHelper assessmentDBHelper;

    public AssessmentDBAdapter(Context context) {
        this.assessmentDBHelper = new AssessmentDBHelper(context);
    }

    //TODO: change return to return something sensisble
    public boolean saveDoneAssessment(Assessment assessment) {
        boolean isTransactionSuccessful = true;
        SQLiteDatabase sqLiteDatabase = assessmentDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", "SomeValue");
        long id = sqLiteDatabase.insert("questions", null, contentValues);
        return isTransactionSuccessful;
    }

    /**
     * Creates a new Assessment
     * @param assessment
     * @return
     */
    public boolean createNewAssessment(Assessment assessment) {
        boolean isTransactionSuccessful = true;
        SQLiteDatabase sqLiteDatabase = assessmentDBHelper.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        long id;
        // add to assessments table
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", assessment.getTitle());
        contentValues.put("description", assessment.getTitle());
        long assessmentId = id = sqLiteDatabase.insert("assessments", null, contentValues);
        long questionId = -1;
        long questionDetailsId = -1;
        isTransactionSuccessful = (assessmentId > 0);
        // loop to add questions
        List<Question> questionList = assessment.getQuestionsList();
        for (Question question : questionList) {
            if (isTransactionSuccessful) {
                // get question_type_id from question_types table first
                int questionTypeId = -1;
                String[] columns = {"question_type_id"};
                Cursor cursor = sqLiteDatabase.query("question_types", columns, "name = '" + question.getAnswerType() + "'", null, null, null, null);
                while (cursor.moveToNext()) {
                    int index1 = cursor.getColumnIndex("question_type_id");
                    questionTypeId = cursor.getInt(index1);
                }
                // add the question to questions table
                contentValues = new ContentValues();
                contentValues.put("question_type_id", questionTypeId);
                contentValues.put("assessment_id", assessmentId);
                contentValues.put("content", question.getContent());
                questionId = sqLiteDatabase.insert("questions", null, contentValues);
                isTransactionSuccessful = (questionId > 0);
                // add additional details for question
                if (question.getAnswerType().equals(Question.ANSWER_SCALE)) {
                    // add details to scale_ranges table
                    contentValues = new ContentValues();
                    contentValues.put("question_id", questionId);
                    contentValues.put("lower_limit", question.getRatingLimitLower());
                    contentValues.put("upper_limit", question.getRatingLimitHigher());
                    questionDetailsId = sqLiteDatabase.insert("scale_ranges", null, contentValues);
                    isTransactionSuccessful = (questionDetailsId > 0);
                }
            } else {
                break;
            }
        }
        if (isTransactionSuccessful) {
            sqLiteDatabase.setTransactionSuccessful();
        }
        sqLiteDatabase.endTransaction();

        return isTransactionSuccessful;
    }

    /**
     * Gets all available Assessments that you can take from the Database
     * @return
     */
    public List<Assessment> getAllAvailableAssessments() {
        // get all Assessments and its id
        // for each Assessment, find its list of questions
        // get all questions and the detail in the assessment from tables questions, question_types, scale_ranges, multiple choice
        // get a
        // Uses SQLiteQueryBuilder to query multiple tables
        SQLiteDatabase sqLiteDatabase = assessmentDBHelper.getWritableDatabase();
        List<Assessment> assessmentList = new ArrayList<Assessment>();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("assessments");

        String[] columns = {"assessments.assessment_id", "assessments.title", "assessments.description"};
        // Get cursor
        Cursor cursor = queryBuilder.query(sqLiteDatabase, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int assessmentIdIndex = cursor.getColumnIndex("assessment_id");
            int assessmentTitleIndex = cursor.getColumnIndex("title");
            int assessmentDescriptionIndex = cursor.getColumnIndex("description");
            int assessmentId = cursor.getInt(assessmentIdIndex);
            String assessmentTitle = cursor.getString(assessmentTitleIndex);
            String assessmentDescription = cursor.getString(assessmentDescriptionIndex);
            List<Question> questionList = getQuestionsInAssessment(assessmentId);
            assessmentList.add(new Assessment(assessmentTitle, assessmentDescription, false, false, null, null, questionList));
        }
        cursor.close();
        return assessmentList;
    }


    public void getQuestionsInAssessment(Assessment assessment) {

    }

    public List<Question> getQuestionsInAssessment(int assessmentId) {
        SQLiteDatabase sqLiteDatabase = assessmentDBHelper.getWritableDatabase();
        List<Question> questionList = new ArrayList<Question>();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder
                .setTables("questions"
                        + " LEFT JOIN "
                        + "question_types ON question_types.question_type_id = questions.question_type_id"
                        + " LEFT JOIN "
                        + "scale_ranges ON scale_ranges.question_id = questions.question_id");

        String[] columns = {"questions.content", "question_types.name", "scale_ranges.lower_limit", "scale_ranges.upper_limit"};
        // Get cursor
        Cursor cursor = queryBuilder.query(sqLiteDatabase, columns, "questions.assessment_id = " + String.valueOf(assessmentId), null, null, null, null);
        while (cursor.moveToNext()) {
            int questionContentIndex = cursor.getColumnIndex("content");
            int questionTypeIndex = cursor.getColumnIndex("name");
            int questionScaleLowerLimitIndex = cursor.getColumnIndex("lower_limit");
            int questionScaleUpperLimitIndex = cursor.getColumnIndex("upper_limit");
            String questionContent = cursor.getString(questionContentIndex);
            String questionType = cursor.getString(questionTypeIndex);
            double questionScaleLowerLimit = cursor.getDouble(questionScaleLowerLimitIndex);
            double questionScaleUpperLimit = cursor.getDouble(questionScaleUpperLimitIndex);
            questionList.add(new Question("A question", questionContent, questionType, questionScaleLowerLimit, questionScaleUpperLimit, 0, "", null, 0, false));
        }
        cursor.close();
        return questionList;
    }

    /**
     * Gets all Done Assessments from the database
     */
    public void getAllDoneAssessments() {

    }

    public void getAllDoneAndExportedAssessments() {

    }

    static class AssessmentDBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "assessments_db";
        private static final int DB_VERSION_NUMBER = 19;

        //region SQL Statements to initialize tables or Drop tables

        private static final String CREATE_QUESTION_TYPES_TABLE = "CREATE TABLE question_types (\n" +
                " question_type_id INTEGER,\n" +
                " name VARCHAR(255),\n" +
                " PRIMARY KEY (question_type_id)\n" +
                ");";

        private static final String CREATE_ASSESSMENTS_TABLE = "CREATE TABLE assessments (\n" +
                "assessment_id INTEGER,\n" +
                "title VARCHAR(255),\n" +
                "description VARCHAR(255),\n" +
                "PRIMARY KEY (assessment_id)\n" +
                ");";

        private static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE questions (\n" +
                " question_id INTEGER,\n" +
                " question_type_id INTEGER,\n" +
                " assessment_id INTEGER,\n" +
                " content VARCHAR(255),\n" +
                " PRIMARY KEY (question_id),\n" +
                " FOREIGN KEY (question_type_id) REFERENCES question_types(question_type_id),\n" +
                " FOREIGN KEY (assessment_id) REFERENCES assessments(assessment_id)\n" +
                ");";

        private static final String CREATE_DONE_ASSESSMENTS_TABLE = "CREATE TABLE done_assessments (\n" +
                "done_assessments_id INTEGER,\n" +
                "assessment_id INTEGER,\n" +
                "date TIMESTAMP,\n" +
                "PRIMARY KEY (done_assessments_id),\n" +
                "FOREIGN KEY (assessment_id) REFERENCES assessments(assessment_id)\n" +
                ");";

        private static final String CREATE_MULTIPLE_CHOICE_ANSWERS = "CREATE TABLE multiple_choice_answers (\n" +
                "multiple_choice_answer_id INTEGER,\n" +
                "question_id INTEGER,\n" +
                "content TEXT,\n" +
                "order_position INTEGER,\n" +
                "is_correct BOOLEAN,\n" +
                "PRIMARY KEY (multiple_choice_answer_id),\n" +
                "FOREIGN KEY (question_id) REFERENCES questions(question_id)\n" +
                ");";

        private static final String CREATE_QUESTION_ANSWERS_TABLE = "CREATE TABLE question_answers (\n" +
                "question_details_id INTEGER,\n" +
                "question_id INTEGER,\n" +
                "scale_answer DECIMAL,\n" +
                "multiple_choice_answer_id INTEGER,\n" +
                "open_ended_answer TEXT,\n" +
                "PRIMARY KEY (question_details_id)\n" +
                "FOREIGN KEY (question_id) REFERENCES questions(question_id),\n" +
                "FOREIGN KEY (multiple_choice_answer_id) REFERENCES multiple_choice_answers(multiple_choice_answer_id)\n" +
                ");";

        private static final String CREATE_SCALE_RANGES_TABLE = "CREATE TABLE scale_ranges (\n" +
                "scale_range_id INTEGER,\n" +
                "lower_limit DECIMAL,\n" +
                "upper_limit DECIMAL,\n" +
                "question_id INTEGER,\n" +
                "PRIMARY KEY (scale_range_id)\n" +
                "FOREIGN KEY (question_id) REFERENCES questions(question_id)\n" +
                ");";


        private static final String DROP_TABLES_1 = "DROP TABLE IF EXISTS questions";
        private static final String DROP_TABLES_2 = "DROP TABLE IF EXISTS question_types";
        private static final String DROP_TABLES_3 = "DROP TABLE IF EXISTS assessments";
        private static final String DROP_TABLES_4 = "DROP TABLE IF EXISTS done_assessments";
        private static final String DROP_TABLES_5 = "DROP TABLE IF EXISTS multiple_choice_answers";
        private static final String DROP_TABLES_6 = "DROP TABLE IF EXISTS question_answers";
        private static final String DROP_TABLES_7 = "DROP TABLE IF EXISTS scale_ranges";


        //endregion

        //region SQL Statements to insert to Tables

        /**
         * Creates a new Assessment
         * @param assessment
         * @return
         */
        public boolean createNewAssessment(Assessment assessment, SQLiteDatabase db) {
            boolean isTransactionSuccessful = true;
            db.beginTransaction();
            long id;
            // add to assessments table
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", assessment.getTitle());
            contentValues.put("description", assessment.getTitle());
            long assessmentId = id = db.insert("assessments", null, contentValues);
            long questionId = -1;
            long questionDetailsId = -1;
            isTransactionSuccessful = (assessmentId > 0);
            // loop to add questions
            List<Question> questionList = assessment.getQuestionsList();
            for (Question question : questionList) {
                if (isTransactionSuccessful) {
                    // get question_type_id from question_types table first
                    int questionTypeId = -1;
                    String[] columns = {"question_type_id"};
                    Cursor cursor = db.query("question_types", columns, "name = '" + question.getAnswerType() + "'", null, null, null, null);
                    while (cursor.moveToNext()) {
                        int index1 = cursor.getColumnIndex("question_type_id");
                        questionTypeId = cursor.getInt(index1);
                    }
                    // add the question to questions table
                    contentValues = new ContentValues();
                    contentValues.put("question_type_id", questionTypeId);
                    contentValues.put("assessment_id", assessmentId);
                    contentValues.put("content", question.getContent());
                    questionId = db.insert("questions", null, contentValues);
                    isTransactionSuccessful = (questionId > 0);
                    // add additional details for question
                    if (question.getAnswerType().equals(Question.ANSWER_SCALE)) {
                        // add details to scale_ranges table
                        contentValues = new ContentValues();
                        contentValues.put("question_id", questionId);
                        contentValues.put("lower_limit", question.getRatingLimitLower());
                        contentValues.put("upper_limit", question.getRatingLimitHigher());
                        questionDetailsId = db.insert("scale_ranges", null, contentValues);
                        isTransactionSuccessful = (questionDetailsId > 0);
                    }
                } else {
                    break;
                }
            }
            if (isTransactionSuccessful) {
                db.setTransactionSuccessful();
            }
            db.endTransaction();
            return isTransactionSuccessful;
        }

        //endregion

        //region SQL Statements to Retrieve

        //endregion

        //region SQL Statements to Update

        //endregion

        //region SQL Statements to Delete

        //endregion


        private Context context;

        public AssessmentDBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION_NUMBER);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.beginTransaction();
            try {
                Log.v("AssessmentDBHelper", "Creating tables.");
                this.createTables(db);
                // if no exceptions until this point, transaction is successful
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            try {
                insertInitialData(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.v("AssessmentDBHelper", "Dropping tables and recreating tables.");
            //drop table and call onCreate again
            db.execSQL(DROP_TABLES_1);
            db.execSQL(DROP_TABLES_2);
            db.execSQL(DROP_TABLES_3);
            db.execSQL(DROP_TABLES_4);
            db.execSQL(DROP_TABLES_5);
            db.execSQL(DROP_TABLES_6);
            db.execSQL(DROP_TABLES_7);
            onCreate(db);
        }

        //region Private methods

        private void createTables(SQLiteDatabase db) throws SQLException {
            db.execSQL(CREATE_QUESTION_TYPES_TABLE);
            db.execSQL(CREATE_ASSESSMENTS_TABLE);
            db.execSQL(CREATE_DONE_ASSESSMENTS_TABLE);
            db.execSQL(CREATE_QUESTIONS_TABLE);
            db.execSQL(CREATE_MULTIPLE_CHOICE_ANSWERS);
            db.execSQL(CREATE_QUESTION_ANSWERS_TABLE);
            db.execSQL(CREATE_SCALE_RANGES_TABLE);
        }

        private void insertInitialData(SQLiteDatabase db) throws SQLException {
            // insert available question types
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "MULTIPLE CHOICE");
            long id = db.insert("question_types", null, contentValues);
            contentValues = new ContentValues();
            contentValues.put("name", "OPEN ENDED");
            id = db.insert("question_types", null, contentValues);
            contentValues = new ContentValues();
            contentValues.put("name", "SCALE");
            id = db.insert("question_types", null, contentValues);


            //TODO: Add in Assessments you wanna initialize with

            //create test data
            List<Assessment> listOfAssessment = new ArrayList<Assessment>();
            List<Question> questions = new ArrayList<Question>();

            questions.add(new Question("Titlez", "Some qns", Question.ANSWER_SCALE, 0.0, 10.0, 5.0, null, null, 0, false));
            questions.add(new Question("Titlez2", "Some qns2", Question.ANSWER_OPEN_ENDED, 0.0, 0.0, 0.0, "This is an open ended answer", null, 0, false));
            Assessment assessment1 = new Assessment("PANAS", "A PANAS test", true, true, new Customer("Chua", 123456789, "string@string.com", null), new Date(), questions);
            Assessment assessment2 = new Assessment("PANASShort", "A PANASShort test", true, true, new Customer("Chua", 123456789, "string@string.com", null), new Date(), questions);
            listOfAssessment.add(assessment1);
            listOfAssessment.add(assessment2);
            for (Assessment assessment : listOfAssessment) {
                this.createNewAssessment(assessment,db);
            }
        }

        //endregion

    }
}
