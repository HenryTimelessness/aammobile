package com.henrychua.mydailyassessment.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.henrychua.mydailyassessment.models.Assessment;

import java.sql.SQLException;

/**
 * Created by henrychua on 2/4/15.
 */
public class AssessmentDBAdapter {
    AssessmentDBHelper assessmentDBHelper;

    public AssessmentDBAdapter(Context context) {
        this.assessmentDBHelper = new AssessmentDBHelper(context);
    }

    public long insertAssessment(Assessment assessment) {
        SQLiteDatabase sqLiteDatabase = assessmentDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", "SomeValue");
        long id = sqLiteDatabase.insert("questions", null, contentValues);
        return id;
    }

    static class AssessmentDBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "assessments_db";
        private static final int DB_VERSION_NUMBER = 9;

        //region SQL Statements to create Tables or Drop tables

        private static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE questions (\n" +
                " question_id INTEGER,\n" +
                " question_type_id INTEGER,\n" +
                " assessment_id INTEGER,\n" +
                " content VARCHAR(255),\n" +
                " PRIMARY KEY (question_id)\n" +
                ");";

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

        private static final String CREATE_DONE_ASSESSMENTS_TABLE = "CREATE TABLE done_assessments (\n" +
                "done_assessments_id INTEGER,\n" +
                "assessment_id INTEGER,\n" +
                "date TIMESTAMP,\n" +
                "PRIMARY KEY (done_assessments_id)\n" +
                ");";

        private static final String CREATE_MULTIPLE_CHOICE_ANSWERS = "CREATE TABLE multiple_choice_answers (\n" +
                "multiple_choice_answer_id INTEGER,\n" +
                "question_id INTEGER,\n" +
                "content TEXT,\n" +
                "order_position INTEGER,\n" +
                "is_correct BOOLEAN,\n" +
                "PRIMARY KEY (multiple_choice_answer_id)\n" +
                ");";

        private static final String CREATE_QUESTION_ANSWERS_TABLE = "CREATE TABLE question_answers (\n" +
                "question_details_id INTEGER,\n" +
                "question_id INTEGER,\n" +
                "scale_answer DECIMAL,\n" +
                "multiple_choice_answer_id INTEGER,\n" +
                "open_ended_answer TEXT,\n" +
                "PRIMARY KEY (question_details_id)\n" +
                ");";

        private static final String CREATE_SCALE_RANGES_TABLE = "CREATE TABLE scale_ranges (\n" +
                "scale_range_id INTEGER,\n" +
                "lower_limit DECIMAL,\n" +
                "upper_limit DECIMAL,\n" +
                "question_id INTEGER,\n" +
                "PRIMARY KEY (scale_range_id)\n" +
                ");";

        private static final String DROP_TABLES_1 = "DROP TABLE IF EXISTS questions";
        private static final String DROP_TABLES_2 = "DROP TABLE IF EXISTS question_types";
        private static final String DROP_TABLES_3 = "DROP TABLE IF EXISTS assessments";
        private static final String DROP_TABLES_4 = "DROP TABLE IF EXISTS done_assessments";
        private static final String DROP_TABLES_5 = "DROP TABLE IF EXISTS multiple_choice_answers";
        private static final String DROP_TABLES_6 = "DROP TABLE IF EXISTS question_answers";
        private static final String DROP_TABLES_7 = "DROP TABLE IF EXISTS scale_ranges";

        //endregion

        //region SQL Statements to Create

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
                this.linkForeignKeys(db);
                // if no exceptions until this point, transaction is successful
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
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
            db.execSQL(CREATE_QUESTIONS_TABLE);
            db.execSQL(CREATE_ASSESSMENTS_TABLE);
            db.execSQL(CREATE_DONE_ASSESSMENTS_TABLE);
            db.execSQL(CREATE_MULTIPLE_CHOICE_ANSWERS);
            db.execSQL(CREATE_QUESTION_ANSWERS_TABLE);
            db.execSQL(CREATE_QUESTION_TYPES_TABLE);
            db.execSQL(CREATE_SCALE_RANGES_TABLE);
        }

        private void linkForeignKeys(SQLiteDatabase db) throws SQLException {
//            db.execSQL(CREATE_QUESTION_TYPES_TABLE);
        }

        //endregion

    }
}
