package com.example.calendarquickstart;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import java.util.ArrayList;
        import java.util.HashMap;

public class EvenRepo {
    private DBHelper dbHelper;

    public EvenRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Even even) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Even.KEY_task, even.task);
        values.put(Even.KEY_des, even.des);
        values.put(Even.KEY_name, even.name);

        // Inserting Row
        long even_Id = db.insert(Even.TABLE, null, values);
        db.close();
        return (int) even_Id;
    }

    public void delete(int even_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Even.TABLE, Even.KEY_ID + "= ?", new String[] { String.valueOf(even_Id) });
        db.close(); // Closing database connection
    }

    public void update(Even even) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Even.KEY_task, even.task);
        values.put(Even.KEY_des, even.des);
        values.put(Even.KEY_name, even.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Even.TABLE, values, even.KEY_ID + "= ?", new String[] { String.valueOf(even.even_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getEvenList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Even.KEY_ID + "," +
                Even.KEY_name + "," +
                Even.KEY_des + "," +
                Even.KEY_task +
                " FROM " + Even.TABLE;

        //Even even = new even();
        ArrayList<HashMap<String, String>> evenList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> even = new HashMap<String, String>();
                even.put("id", cursor.getString(cursor.getColumnIndex(Even.KEY_ID)));
                even.put("name", cursor.getString(cursor.getColumnIndex(Even.KEY_name)));
                evenList.add(even);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return evenList;

    }

    public Even getEvenById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Even.KEY_ID + "," +
                Even.KEY_name + "," +
                Even.KEY_des + "," +
                Even.KEY_task +
                " FROM " + Even.TABLE
                + " WHERE " +
                Even.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Even even = new Even();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                even.even_ID =cursor.getInt(cursor.getColumnIndex(Even.KEY_ID));
                even.name =cursor.getString(cursor.getColumnIndex(Even.KEY_name));
                even.des  =cursor.getString(cursor.getColumnIndex(Even.KEY_des));
                even.task =cursor.getInt(cursor.getColumnIndex(Even.KEY_task));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return even;
    }

}