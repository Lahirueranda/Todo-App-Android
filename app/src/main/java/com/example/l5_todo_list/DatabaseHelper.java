package com.example.l5_todo_list;

//library import
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper{

    //initialize database and table name
    public static final String DATABASE_NAME = "TODOLIST.db";
    public static final String TABLENAME = "TO_DO_LIST_DATA";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TASK";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "STATUS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create  table " + TABLENAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT,TASK TEXT,DATE TEXT,STATUS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }

    //insert data to the table
    public  boolean insetData(String name,String task,String date,String status){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(COL_2,name);
        contentValues.put(COL_3,task);
        contentValues.put(COL_4,date);
        contentValues.put(COL_5,status);

        long result=  db.insert(TABLENAME,null,contentValues);

        if(result==-1) {
            return false;
        }else{
            return true;
        }
    }

    //get all data from table
    public Cursor getall()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result =db.rawQuery("select * from TO_DO_LIST_DATA",null );
        return  result;
    }

    //update data
    public boolean  updatedata(String id ,String name,String task, String date,String status)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(COL_2,name);
        contentValues.put(COL_3,task);
        contentValues.put(COL_4,date);
        contentValues.put(COL_5,status);

        long result=  db.update(TABLENAME,contentValues,"ID = ?",new String[] {id});
        return true;
    }

    //delete data from table
    public Integer deletData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLENAME,"ID = ?",new String[] {id});
    }

}
