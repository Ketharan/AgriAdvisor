package com.advisor.agriot.zeon.agriadvisor;

/**
 * Created by Haran on 12/07/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class stateRepo {
    private DBHelper dbHelper;

    public stateRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(stateVariable statevariable) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(stateVariable.KEY_status, statevariable.state);


        // Inserting Row
        long state_Id = db.insert(stateVariable.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) state_Id;
    }

    public void delete(int state_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(stateVariable.TABLE, stateVariable.KEY_ID + "= ?", new String[] { String.valueOf(state_Id) });
        db.close(); // Closing database connection
    }

    public void update(stateVariable statevariable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(stateVariable.KEY_status, statevariable.state);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(stateVariable.TABLE, values, stateVariable.KEY_ID + "= ?", new String[] { String.valueOf(statevariable.state_id) });
        db.close(); // Closing database connection
    }



    public stateVariable getstateVariableById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                stateVariable.KEY_ID + "," +
                stateVariable.KEY_status +
                " FROM " + stateVariable.TABLE
                + " WHERE " +
                stateVariable.KEY_ID + "=" + String.valueOf(Id);// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        stateVariable state = new stateVariable();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

       /* if (cursor.moveToFirst()) {
            do {
                state.state_id =cursor.getInt(cursor.getColumnIndex(stateVariable.KEY_ID));
                state.state =cursor.getString(cursor.getColumnIndex(stateVariable.KEY_status));


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();*/
        return state;
    }

}