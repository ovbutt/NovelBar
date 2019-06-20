package com.ovais.www.novalbar.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "novalbar.db";
    private static final int DATABASE_VERSION = 2 ;
    public static final String TABLE_NAME = "ingredients";
    public static final String COLUMN_INGREDIENT_ID = "_id";
    public static final String COLUMN_INGREDIENT_BITTERNESSFLAG = "bitternessFlag";
    public static final String COLUMN_INGREDIENT_CALORIES = "calories";
    public static final String COLUMN_INGREDIENT_CARBCALORIES = "carbCalorie";
    public static final String COLUMN_INGREDIENT_CATEGORY = "category";
    public static final String COLUMN_INGREDIENT_FATCALORIES = "fatCalorie";
    public static final String COLUMN_INGREDIENT_INGREDIENT_NAME = "name";
    public static final String COLUMN_INGREDIENT_PORTION_FLAG = "portionFlag";
    public static final String COLUMN_INGREDIENT_PORTION_SIZE = "portionSize";
    public static final String COLUMN_INGREDIENT_PROTEIN_CALORIE = "proteinCalorie";
    public static final String COLUMN_INGREDIENT_INGREDIENT_QUANTITY = "quantity";
    public static final String COLUMN_INGREDIENT_RDA = "rda";
    public static final String COLUMN_INGREDIENT_SERVINGSIZE = "servingSize";
    public static final String COLUMN_INGREDIENT_UNHEALTHYFLAG = "unhealthyFlag";

    public SQLiteHelper(Context context)
    {
        super (context , DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + "(" +
        COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_INGREDIENT_BITTERNESSFLAG + " TEXT NOT NULL, " +
                        COLUMN_INGREDIENT_CALORIES + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_CARBCALORIES + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_CATEGORY + " TEXT NOT NULL, " +
                        COLUMN_INGREDIENT_FATCALORIES + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_INGREDIENT_NAME + " TEXT NOT NULL, " +
                        COLUMN_INGREDIENT_PORTION_FLAG + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_PORTION_SIZE + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_PROTEIN_CALORIE + " REAL NOT NULL, " +
                        COLUMN_INGREDIENT_INGREDIENT_QUANTITY + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_RDA + " TEXT NOT NULL, " +
                        COLUMN_INGREDIENT_SERVINGSIZE + " INTEGER NOT NULL, " +
                        COLUMN_INGREDIENT_UNHEALTHYFLAG + " INTEGER NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void addData (IngredientData ingredientData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INGREDIENT_BITTERNESSFLAG, ingredientData.getBitternessFlag());
        cv.put(COLUMN_INGREDIENT_CALORIES, ingredientData.getCalories());
        cv.put(COLUMN_INGREDIENT_CARBCALORIES, ingredientData.getCarbCalorie());
        cv.put(COLUMN_INGREDIENT_CATEGORY, ingredientData.getCategory());
        cv.put(COLUMN_INGREDIENT_FATCALORIES, ingredientData.getFatCalorie());
        cv.put(COLUMN_INGREDIENT_INGREDIENT_NAME, ingredientData.getName());
        cv.put(COLUMN_INGREDIENT_PORTION_FLAG, ingredientData.getPortionFlag());
        cv.put(COLUMN_INGREDIENT_PORTION_SIZE, ingredientData.getPortionSize());
        cv.put(COLUMN_INGREDIENT_PROTEIN_CALORIE, ingredientData.getProteinCalorie());
        cv.put(COLUMN_INGREDIENT_INGREDIENT_QUANTITY, ingredientData.getQuantity());
        cv.put(COLUMN_INGREDIENT_RDA, ingredientData.getRda());
        cv.put(COLUMN_INGREDIENT_SERVINGSIZE, ingredientData.getServingSize());
        cv.put(COLUMN_INGREDIENT_UNHEALTHYFLAG, ingredientData.getUnhealthyFlag());
        cv.put(COLUMN_INGREDIENT_INGREDIENT_QUANTITY, 0);

        Cursor res = getAllData();

        if (res.getCount() >= 23)
        {
            Log.d("SQLiteHelper", "addData: NoDataAdded ");
        }
        else{db.insert(TABLE_NAME, null, cv);
            db.close();}
    }

    public void addQuantity (String ingredient, int quantity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INGREDIENT_INGREDIENT_QUANTITY, quantity);

        db.update(TABLE_NAME, cv, COLUMN_INGREDIENT_INGREDIENT_NAME + " = " + "'" + ingredient +"'", null);
    }

    public Cursor getAllData () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * from " + TABLE_NAME , null);
        return res;
    }

    public Cursor getDataToPost () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * from " +  TABLE_NAME + " where quantity > 0 " , null);
        return res;
    }

    public void setQuantityZero ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_INGREDIENT_INGREDIENT_QUANTITY, 0);

        db.update(TABLE_NAME,cv,COLUMN_INGREDIENT_INGREDIENT_QUANTITY + "=" + "quantity",null);
    }

    public Cursor infoData (String ingredient)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * from " +  TABLE_NAME + " where " + COLUMN_INGREDIENT_INGREDIENT_NAME + " = " + "'" + ingredient +  "'" , null);
        return res;
    }

}
