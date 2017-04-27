package com.journaldev.sqlite;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "STUDENTS";

    // Table columns
    public static final String _ID = "_id";
    public static final String STUDENTID = "StudentID";
    public static final String STUDENTNAME = "StudentName";
    public static final String STUDENTPER = "StudentPeriod";

    // Database Information
    static final String DB_NAME = "STUDENTDB.DB";

    // database version
    static final int DB_VERSION = 6;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STUDENTID + " TEXT NOT NULL, " + STUDENTNAME + " TEXT NOT NULL, " + STUDENTPER + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
