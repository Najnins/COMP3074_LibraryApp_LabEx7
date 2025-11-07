package com.example.libraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "library.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "books";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_YEAR = "year";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create table
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT book_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_GENRE + " TEXT, " +
                COLUMN_YEAR + " INTEGER" +
                ");";

        db.execSQL(sql);

        // Insert sample data
        insertSampleData(db);
    }

    // Insert sample data at first install
    private void insertSampleData(SQLiteDatabase db) {

        insert(db, "The Great Gatsby", "F. Scott Fitzgerald", "Novel", 1925);
        insert(db, "To Kill a Mockingbird", "Harper Lee", "Drama", 1960);
        insert(db, "1984", "George Orwell", "Dystopian", 1949);
    }

    private void insert(SQLiteDatabase db, String title, String author, String genre, int year) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_YEAR, year);
        db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    //-------------------
    // CRUD METHODS
    //-------------------

    public boolean addBook(String title, String author, String genre, Integer year) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_YEAR, year);

        return db.insert(TABLE_NAME, null, cv) != -1;
    }

    public boolean updateBook(long id, String title, String author, String genre, Integer year) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_YEAR, year);

        return db.update(TABLE_NAME, cv, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteBook(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC", null);
    }

    public Cursor getBook(long id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                new String[]{String.valueOf(id)});
    }
}
