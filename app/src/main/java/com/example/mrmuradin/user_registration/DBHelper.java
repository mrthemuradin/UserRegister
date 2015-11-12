package com.example.mrmuradin.user_registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Muradin on 12.11.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DataBaseLogs";
    public static final String USER_INFO_TABLE_NAME = "mytable";
    public static final String USER_NAME = "name";
    public static final String USER_PASSWORD = "password";
    public static final String USER_SEX = "sex";
    public static final String USER_COUNTRY = "country";
    public static final String ID = "id";

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table mytable ("
                + ID + " integer primary key autoincrement,"
                + USER_NAME + " text,"
                + USER_PASSWORD + " text,"
                + USER_SEX + " text,"
                + USER_COUNTRY + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveUserInfo(String userName, String password, String sex, String country) {
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, userName);
        cv.put(USER_PASSWORD, password);
        cv.put(USER_SEX, sex);
        cv.put(USER_COUNTRY, country);
        getWritableDatabase().insert(USER_INFO_TABLE_NAME, null, cv);
    }

    public void printUserInfo() {
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        Cursor c = getReadableDatabase().query(USER_INFO_TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex(ID);
            int nameColIndex = c.getColumnIndex(USER_NAME);
            int passwordColIndex = c.getColumnIndex(USER_PASSWORD);
            int sexColIndex = c.getColumnIndex(USER_SEX);
            int countryColIndex = c.getColumnIndex(USER_COUNTRY);

            do {
                Log.d(LOG_TAG,
                        ID + " = " + c.getInt(idColIndex) +
                                ", " + USER_NAME + " = " + c.getString(nameColIndex) +
                                ", " + USER_PASSWORD + " = " + c.getString(passwordColIndex) +
                                ", " + USER_SEX + " = " + c.getString(sexColIndex) +
                                ", " + USER_COUNTRY + " = " +c.getString(countryColIndex));
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }
}
