package com.journaldev.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBBehaviorManager {

    private static final String TAG = "BehaviorDbAdapter";
    private DatabaseBehaviorHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBBehaviorManager(Context c) {
        context = c;
    }

    public DBBehaviorManager open() throws SQLException {
        dbHelper = new DatabaseBehaviorHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String student_id, String student_name, String student_per) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseBehaviorHelper.BEHAVIORID, student_id);
        contentValue.put(DatabaseBehaviorHelper.BEHAVIORNAME, student_name);
        contentValue.put(DatabaseBehaviorHelper.BEHAVIORDATE, student_per);
        database.insert(DatabaseBehaviorHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {

        String[] columns = new String[] { DatabaseBehaviorHelper._ID, DatabaseBehaviorHelper.BEHAVIORID, DatabaseBehaviorHelper.BEHAVIORNAME, DatabaseBehaviorHelper.BEHAVIORDATE };
        Cursor cursor = database.query(DatabaseBehaviorHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchDistictBEHAVIORDATEiod() {

        String[] columns = new String[] { DatabaseBehaviorHelper._ID, DatabaseBehaviorHelper.BEHAVIORID, DatabaseBehaviorHelper.BEHAVIORNAME, DatabaseBehaviorHelper.BEHAVIORDATE };
        Cursor cursor = database.query(DatabaseBehaviorHelper.TABLE_NAME, columns, DatabaseBehaviorHelper.BEHAVIORDATE + " like '%" + "" + "%'", null, DatabaseBehaviorHelper.BEHAVIORDATE, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String student_id, String student_name, String student_per) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseBehaviorHelper.BEHAVIORID, student_id);
        contentValues.put(DatabaseBehaviorHelper.BEHAVIORNAME, student_name);
        contentValues.put(DatabaseBehaviorHelper.BEHAVIORDATE, student_per);
        int i = database.update(DatabaseBehaviorHelper.TABLE_NAME, contentValues, DatabaseBehaviorHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseBehaviorHelper.TABLE_NAME, DatabaseBehaviorHelper._ID + "=" + _id, null);
    }

    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = database.query(DatabaseBehaviorHelper.TABLE_NAME, new String[] {DatabaseBehaviorHelper._ID,
                            DatabaseBehaviorHelper.BEHAVIORID, DatabaseBehaviorHelper.BEHAVIORNAME, DatabaseBehaviorHelper.BEHAVIORDATE},
                    null, null, null, null, null);

        }
        else {
            mCursor = database.query(true, DatabaseBehaviorHelper.TABLE_NAME, new String[] {DatabaseBehaviorHelper._ID,
                            DatabaseBehaviorHelper.BEHAVIORID, DatabaseBehaviorHelper.BEHAVIORNAME, DatabaseBehaviorHelper.BEHAVIORDATE},
                    DatabaseBehaviorHelper.BEHAVIORID + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
}
