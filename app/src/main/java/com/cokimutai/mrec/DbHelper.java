package com.cokimutai.mrec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by cokimutai on 1/4/2017.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    public static final String DATABASE_NAME = "mrec.db";
    public static final int DATABASE_VERSION = 1;
    public static final String REG_TBL = "reg";
    public static final String MLK_TBL = "milk";

    public static final String COL_ID = "suppliaId";
    public static final String COL_FNAME = "fname";
    public static final String COL_LNAME = "lname";
    public static final String COL_RDNC = "place";
    public static final String COL_PHONE = "number";

    public static final String COL_LTRS = "litres";
    public static final String COL_DATE = "cdate";
    public static final String FOREIGN_KEY = "FOREIGN KEY";

    private static DbHelper mDbHelper;

    public DbHelper(Context context ){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static synchronized DbHelper getInstance(Context context){
        if (mDbHelper == null){
            mDbHelper = new DbHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CRT_TBL_REG = "CREATE TABLE " + REG_TBL +
                "(" + COL_ID + " INTEGER NOT NULL, " +
                COL_FNAME + " TEXT NOT NULL, " +
                COL_LNAME + " TEXT NOT NULL," +
                COL_RDNC + " TEXT, " +
                COL_PHONE + " TEXT " +
                ")" ;

        String CRT_MILK_TBL = " CREATE TABLE " + MLK_TBL + "(" +
                COL_ID + " INTEGER NOT NULL, " +
                COL_LTRS + " INTEGER NOT NULL, " +
                COL_DATE + " DATETIME, " +
                FOREIGN_KEY + "(" + COL_ID +
                ")" + "REFERENCES " +
                REG_TBL + "(" + COL_ID + ")" +
                ")";
        db.execSQL(CRT_TBL_REG);
        db.execSQL(CRT_MILK_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXIST " + REG_TBL);
            db.execSQL("DROP TABLE IF EXIST " + MLK_TBL);
            onCreate(db);
        }
    }
    public void createSupplia(SuppliaData suppliaData){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ID, suppliaData.suppliaId);
            values.put(COL_FNAME, suppliaData.fname);
            values.put(COL_LNAME, suppliaData.lname);
            values.put(COL_RDNC, suppliaData.resdnc);
            values.put(COL_PHONE, suppliaData.phone);

            db.insertOrThrow(REG_TBL, null, values);
            db.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG,
                    "Error while trying to post to" +
                            " the database");
        }finally {
            db.endTransaction();
        }
    }

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void insertMilk(SuppliaData suppliaData){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ID, suppliaData.getSuppliaId());
            values.put(COL_LTRS, suppliaData.litres);
            values.put(COL_DATE, getDateTime());

            db.insertOrThrow(MLK_TBL,null, values);
            db.setTransactionSuccessful();
        }catch (SQLException e ){
            e.printStackTrace();
            Log.d(TAG, "Error while trying to insert to" +
                    " the database");
        }finally {
            db.endTransaction();
        }
    }
    public ArrayList<HashMap<String, String>>getPaysupplias(){
        ArrayList<HashMap<String, String>>suppliersArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + REG_TBL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()){
                do {
                    HashMap<String, String>sMap = new HashMap();
                    sMap.put("suppliaId", cursor.getString(0));
                    sMap.put("fname", cursor.getString(1));
                    sMap.put("lname", cursor.getString(2));
                    sMap.put("place", cursor.getString(3));
                    sMap.put("number", cursor.getString(4));

                    suppliersArrayList.add(sMap);
                }while (cursor.moveToNext());
            }
        }catch (SQLException e){
            Log.d(TAG, "Error while trying to get posts from the database");
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
            return suppliersArrayList;

    }

    public List<SuppliaData>getAllsupplias(){
        List<SuppliaData>suppliaInfo = new ArrayList<>();
        String query = "SELECT * FROM " + REG_TBL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()){
                do {
                    SuppliaData suppliaData = new SuppliaData();
                    suppliaData.suppliaId = (cursor.getString(
                            cursor.getColumnIndex(COL_ID)));
                    suppliaData.fname = (cursor.getString(
                            cursor.getColumnIndex(COL_FNAME)));
                    suppliaData.lname = (cursor.getString(
                            cursor.getColumnIndex(COL_LNAME)));
                    suppliaData.resdnc = (cursor.getString(
                            cursor.getColumnIndex(COL_RDNC)));
                    suppliaData.phone = (cursor.getString(
                            cursor.getColumnIndex(COL_PHONE)));

                    suppliaInfo.add(suppliaData);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG, "Error while trying to get posts from the database");
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return suppliaInfo;
    }
    public ArrayList<HashMap<String, String>>getDailySupplly(String id){
        ArrayList<HashMap<String, String>>dailyMap = new ArrayList<>();
        String query = "SELECT * FROM " + MLK_TBL +
                " WHERE suppliaId = '" +
                id + "'" + " AND cdate >= date('now', '-31 days')" +
                " ORDER BY " + COL_DATE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String,String>suppliaMap = new HashMap<>();
                suppliaMap.put("suppliaId", String.valueOf(Integer.parseInt(
                        cursor.getString(0))));
                suppliaMap.put("litres", cursor.getString(1));
                suppliaMap.put("cdate", cursor.getString(2));

                dailyMap.add(suppliaMap);
            }while (cursor.moveToNext());
        }
        return dailyMap;
    }

    public HashMap<String, String>getPersonInfo(String id){
        HashMap<String,String>personMap = new HashMap<>();
        String pQuery = "SELECT * FROM " + REG_TBL +
                " WHERE suppliaId  = '" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(pQuery,null);
        if (cursor.moveToFirst()){
            do {
                personMap.put("fname", cursor.getString(1));
                personMap.put("lname", cursor.getString(2));
                personMap.put("place", cursor.getString(3));
                personMap.put("number", cursor.getString(4));
            }while (cursor.moveToNext());
        }
        return personMap;
    }

    public int getDailyTotal(String id ){
        int total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM( " + COL_LTRS + ") FROM " + MLK_TBL +
                        " WHERE suppliaId = '" + id + "'" +
                        " AND cdate >= date('now', '-12 hours')",
                null);
        if (cursor.moveToFirst()){
            total = cursor.getInt(0);
        }
        return total;
    }

    public int getWeeklyTotal(String id){
        int total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM( " + COL_LTRS + ") FROM " + MLK_TBL +
                        " WHERE suppliaId = '" + id + "'" +
                        " AND cdate >= date('now', '-7 days')",
                null);
        if (cursor.moveToFirst()){
            total = cursor.getInt(0);
        }
        return total;
    }

    public int getMonthlyTotal(String id ){
        int total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM( " + COL_LTRS + ") FROM " + MLK_TBL +
                        " WHERE suppliaId = '" + id + "'" +
                        " AND cdate >= date('now', '-31 days')",
                null);
        if (cursor.moveToFirst()){
            total = cursor.getInt(0);
        }
        return total;
    }

    void deleteSupplia(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + REG_TBL +
            " WHERE suppliaId = '" + id + "'");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d(TAG, " Error while trying to delete the supplier from the database");
        }finally {
            db.endTransaction();
        }
    }





}
