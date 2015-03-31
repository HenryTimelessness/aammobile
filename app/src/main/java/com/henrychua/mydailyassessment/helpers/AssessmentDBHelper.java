package com.henrychua.mydailyassessment.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Database Helper
 * If you make any changes to the DB, remember to increase the version number (DB_VERSION_NUMBER)
 * Please view the Schema at http://dbdesigner.net/designer/schema/59 click export > export as mysql and you can see the schema
 */
public class AssessmentDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "assessments_db";
    private static final int DB_VERSION_NUMBER = 1;

    //region SQL Statements to create Tables or Drop tables

    private static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE questions (\n" +
            " question_id INT NOT NULL AUTO_INCREMENT,\n" +
            " question_type_id INT,\n" +
            " assessment_id INT,\n" +
            " content VARCHAR(255),\n" +
            " PRIMARY KEY (question_id)\n" +
            ");";

    private static final String CREATE_QUESTION_TYPES_TABLE = "CREATE TABLE question_types (\n" +
            " question_type_id INT NOT NULL AUTO_INCREMENT,\n" +
            " name VARCHAR(255),\n" +
            " PRIMARY KEY (question_type_id)\n" +
            ");";

    private static final String DROP_TABLES = "DROP TABLE questions IF EXISTS";

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
        //drop table and call onCreate again
        context.deleteDatabase(DB_NAME);
        onCreate(db);
    }

    //region Private methods

    private void createTables(SQLiteDatabase db) throws SQLException {
        db.execSQL(CREATE_QUESTIONS_TABLE);
    }

    private void linkForeignKeys(SQLiteDatabase db) throws SQLException {
        db.execSQL(CREATE_QUESTION_TYPES_TABLE);
    }

    //endregion


}
