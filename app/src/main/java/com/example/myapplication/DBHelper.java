package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_name = "UserInformation.db";
    private static final String TableName = "USER_INFORMATION";
    private static final String Id = "NAME";
    private static final String LastName = "LAST_NAME";
    private static final String FirstName = "FIRST_NAME";
    private static final String Email = "EMAIL";
    private static final String Username = "USERNAME";
    private static final String Password = "PASSWORD";

    public DBHelper(SignUpFragment context) {

        super(context.requireContext(), DB_name, null, 1);
        SQLiteDatabase UserInformationDB = this.getWritableDatabase();
    }

    public DBHelper(Context context) {

        super(context, DB_name, null, 1);
        SQLiteDatabase UserInformationDB = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase UserInformationDB) {

        String create_table = "CREATE TABLE " + TableName + " (" + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                              Username + " TEXT, " + LastName  + " TEXT, " + FirstName  + " TEXT, " + Email + " TEXT, " +
                              Password + " TEXT)";
        UserInformationDB.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase UserInformationDB, int oldVersion, int newVersion) {

        UserInformationDB.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(UserInformationDB);
    }

    public Boolean createUser(String username, String lastName, String firstName, String email, String password){

        SQLiteDatabase UserInformationDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Username, username);
        values.put(LastName, lastName);
        values.put(FirstName, firstName);
        values.put(Email, email);
        values.put(Password, password);

        long new_row = UserInformationDB.insert(TableName, null, values);
        if(new_row == -1)
            return false;
        else return true;
    }

    public Boolean checkUser(String username, String email){

        String[] columns = {Id};
        SQLiteDatabase UserInformationDB = this.getReadableDatabase();
        String selection = Username + " = ? " + " AND " + Email + " = ? ";
        String[] selectionArgs = {username, email};
        Cursor cursor = UserInformationDB.query(TableName, columns, selection, selectionArgs, null, null, null);
        if(cursor.getCount() > 0)
            return false;
        else return true;
    }

    public Boolean checkAcoount(String username, String password){

        String[] columns = {Id};
        SQLiteDatabase UserInformationDB = this.getReadableDatabase();
        String selection = Username + " = ? " + " AND " + Password + " = ? ";
        String[] selectionArgs = {username, password};
        Cursor cursor = UserInformationDB.query(TableName, columns, selection, selectionArgs, null, null, null);
        if(cursor.getCount() > 0)
            return false;
        else return true;
    }
}
