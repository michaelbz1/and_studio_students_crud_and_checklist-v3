package com.journaldev.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBBehaviorManager {

    private static final String TAG = "BehaviorDbAdapter";
    private databaseBehaviorHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBBehaviorManager(Context c) {
        context = c;
    }

    public DBBehaviorManager open() throws SQLException {
        dbHelper = new databaseBehaviorHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String student_id, String student_name, String student_per, String student_cons, String student_parent, String student_comments) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(databaseBehaviorHelper.BEHAVIORID, student_id);
        contentValue.put(databaseBehaviorHelper.BEHAVIORNAME, student_name);
        contentValue.put(databaseBehaviorHelper.BEHAVIORDATE, student_per);
        contentValue.put(databaseBehaviorHelper.BEHAVIORCONSEQUENCE, student_cons);
        contentValue.put(databaseBehaviorHelper.BEHAVIORPARENTCONTACT, student_parent);
        contentValue.put(databaseBehaviorHelper.BEHAVIORCOMMENTS, student_comments);
        database.insert(databaseBehaviorHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {

        String[] columns = new String[] { databaseBehaviorHelper._ID, databaseBehaviorHelper.BEHAVIORID, databaseBehaviorHelper.BEHAVIORNAME, databaseBehaviorHelper.BEHAVIORDATE, databaseBehaviorHelper.BEHAVIORCONSEQUENCE, databaseBehaviorHelper.BEHAVIORPARENTCONTACT, databaseBehaviorHelper.BEHAVIORCOMMENTS };
        Cursor cursor = database.query(databaseBehaviorHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchDistictBEHAVIORDATEiod() {

        String[] columns = new String[] { databaseBehaviorHelper._ID, databaseBehaviorHelper.BEHAVIORID, databaseBehaviorHelper.BEHAVIORNAME, databaseBehaviorHelper.BEHAVIORDATE, databaseBehaviorHelper.BEHAVIORCONSEQUENCE, databaseBehaviorHelper.BEHAVIORPARENTCONTACT, databaseBehaviorHelper.BEHAVIORCOMMENTS };
        Cursor cursor = database.query(databaseBehaviorHelper.TABLE_NAME, columns, databaseBehaviorHelper.BEHAVIORDATE + " like '%" + "" + "%'", null, databaseBehaviorHelper.BEHAVIORDATE, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String student_id, String student_name, String student_per, String student_cons, String student_parent, String student_comments) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseBehaviorHelper.BEHAVIORID, student_id);
        contentValues.put(databaseBehaviorHelper.BEHAVIORNAME, student_name);
        contentValues.put(databaseBehaviorHelper.BEHAVIORDATE, student_per);
        contentValues.put(databaseBehaviorHelper.BEHAVIORCONSEQUENCE, student_cons);
        contentValues.put(databaseBehaviorHelper.BEHAVIORPARENTCONTACT, student_parent);
        contentValues.put(databaseBehaviorHelper.BEHAVIORCOMMENTS, student_comments);
        int i = database.update(databaseBehaviorHelper.TABLE_NAME, contentValues, databaseBehaviorHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(databaseBehaviorHelper.TABLE_NAME, databaseBehaviorHelper._ID + "=" + _id, null);
    }

    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        //Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = database.query(databaseBehaviorHelper.TABLE_NAME, new String[] {databaseBehaviorHelper._ID,
                            databaseBehaviorHelper.BEHAVIORID, databaseBehaviorHelper.BEHAVIORNAME, databaseBehaviorHelper.BEHAVIORDATE,
                            databaseBehaviorHelper.BEHAVIORCONSEQUENCE, databaseBehaviorHelper.BEHAVIORPARENTCONTACT, databaseBehaviorHelper.BEHAVIORCOMMENTS},
                    null, null, null, null, null);

        }
        else {
            mCursor = database.query(true, databaseBehaviorHelper.TABLE_NAME, new String[] {databaseBehaviorHelper._ID,
                            databaseBehaviorHelper.BEHAVIORID, databaseBehaviorHelper.BEHAVIORNAME, databaseBehaviorHelper.BEHAVIORDATE,
                            databaseBehaviorHelper.BEHAVIORCONSEQUENCE, databaseBehaviorHelper.BEHAVIORPARENTCONTACT, databaseBehaviorHelper.BEHAVIORCOMMENTS},
                    databaseBehaviorHelper.BEHAVIORID + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
}
