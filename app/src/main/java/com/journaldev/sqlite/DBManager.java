package com.journaldev.sqlite;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String student_id, String student_name, String student_per) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.STUDENTID, student_id);
        contentValue.put(DatabaseHelper.STUDENTNAME, student_name);
        contentValue.put(DatabaseHelper.STUDENTPER, student_per);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.STUDENTID, DatabaseHelper.STUDENTNAME, DatabaseHelper.STUDENTPER };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String student_id, String student_name, String student_per) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.STUDENTID, student_id);
        contentValues.put(DatabaseHelper.STUDENTNAME, student_name);
        contentValues.put(DatabaseHelper.STUDENTPER, student_per);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
