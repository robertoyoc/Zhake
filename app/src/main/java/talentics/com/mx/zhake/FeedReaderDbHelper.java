package talentics.com.mx.zhake;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Roberto on 19/04/2017.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper{
    /* Database information */


    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRICES = "prices";

    // Common column names
    private static final String KEY_ID = "id";

    // users Table - column names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_SEX = "sex";
    private static final String KEY_AGE = "age";
    private static final String KEY_STREET = "street";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_COL = "col";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_CP = "cp";

    // prices Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";

    // users table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL
            + " TEXT," + KEY_PASS + " TEXT," + KEY_FNAME + " TEXT,"+ KEY_LNAME + " TEXT,"
            + KEY_SEX + " TEXT,"+ KEY_AGE + " TEXT,"+ KEY_STREET
            + " TEXT," + KEY_NUMBER + " TEXT," + KEY_COL + " TEXT,"+ KEY_CITY + " TEXT,"
            + KEY_STATE + " TEXT,"+ KEY_CP + " TEXT" + ")";

    // prices table create statement
    private static final String CREATE_TABLE_PRICES = "CREATE TABLE "
            + TABLE_PRICES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_PRICE + " FLOAT" + ")";

    private void insertPrices(String name, float value, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PRICE, value);
        db.insert(TABLE_PRICES, null, values);
        values.clear();
    }
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "Zhake.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRICES);
        db.execSQL(CREATE_TABLE_USERS);

        //Inserting original data
        insertPrices("Agua", 0.5f, db);
        insertPrices("Leche", 3f, db);
        insertPrices("Yogurt", 3f, db);
        insertPrices("Fresa", 4f, db);
        insertPrices("Platano", 1.5f, db);
        insertPrices("Blueberry", 12f, db);
        insertPrices("Frambuesa", 9f, db);
        insertPrices("Mango", 3.5f, db);
        insertPrices("Manzana", 5f, db);
        insertPrices("Chocolate", 2.5f, db);
        insertPrices("Miel", 2.5f, db);
        insertPrices("Azucar", 2.5f, db);
        insertPrices("Splenda", 2.5f, db);
        insertPrices("Crema Batida", 2f, db);
        insertPrices("Chispas", 2f, db);
        insertPrices("Granola", 2f, db);



        //inserting admin user

        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL, "admin@zhake.com");
        values.put(KEY_PASS, "admin123");
        values.put(KEY_FNAME, "Pedro");
        values.put(KEY_LNAME, "Lopez");
        values.put(KEY_SEX, "M");
        values.put(KEY_AGE, "18");
        values.put(KEY_STREET, "Felipe Ángeles");
        values.put(KEY_NUMBER, "2003");
        values.put(KEY_COL, "Venta Prieta");
        values.put(KEY_CITY, "Pachuca de Soto");
        values.put(KEY_STATE, "Hidalgo");
        values.put(KEY_CP, "42080");
        db.insert(TABLE_USERS, null, values);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
