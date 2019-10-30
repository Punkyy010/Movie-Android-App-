package com.example.stoic.googleatelier_app;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class myDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "moviesDB.db";
    public static final String TABLE_NAME = "Movie";
    public static final String TABLE_NAME3 = "Movie_Seen";
    public static final String TABLE_NAME2 = "Movies_Unseen";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_GENRE = "COLUMN_GENRE";
    public static final String COLUMN_DESCRIPTION = "COLUMN_DESCRIPTION";
    public static final String COLUMN_RATING = "COLUMN_RATING";
    public static final String COLUMN_PHOTO = "COLUMN_PHOTO";
    public static final String COLUMN_ACTORS = "COLUMN_ACTORS";
    public static final String COLUMN_YEAR = "COLUMN_YEAR";
    public static final String COLUMN_IMDB_LINK = "COLUMN_IMDB_LINK";
    public static final String COLUMN_CHECKED = "COLUMN_CHECKED";

    //initialize the database
    public myDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int intversion) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME + " TEXT ," + COLUMN_GENRE + " TEXT ," + COLUMN_DESCRIPTION + " TEXT ,"+
                COLUMN_RATING + " FLOAT ," + COLUMN_PHOTO + " TEXT ," + COLUMN_ACTORS + " TEXT ," + COLUMN_YEAR + " INTEGER ," + COLUMN_IMDB_LINK + " TEXT ," +  COLUMN_CHECKED  + " TEXT );";
        db.execSQL(CREATE_TABLE);
        String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME2 + "(" + COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME + " TEXT ," + COLUMN_GENRE + " TEXT ," + COLUMN_DESCRIPTION + " TEXT ,"+
                COLUMN_RATING + " FLOAT ," + COLUMN_PHOTO + " TEXT ," + COLUMN_ACTORS + " TEXT ," + COLUMN_YEAR + " INTEGER ," + COLUMN_IMDB_LINK + " TEXT ," + COLUMN_CHECKED  + " TEXT );";
        db.execSQL(CREATE_TABLE2);
        String CREATE_TABLE3 = "CREATE TABLE " + TABLE_NAME3 + "(" + COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME + " TEXT ," + COLUMN_GENRE + " TEXT ," + COLUMN_DESCRIPTION + " TEXT ,"+
                COLUMN_RATING + " FLOAT ," + COLUMN_PHOTO + " TEXT ," + COLUMN_ACTORS + " TEXT ," + COLUMN_YEAR + " INTEGER ," + COLUMN_IMDB_LINK + " TEXT ,"  + COLUMN_CHECKED  + " TEXT );";
        db.execSQL(CREATE_TABLE3);
    }
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    public List<Movie> getUnseenMovies(){
        List<Movie> movie_list = new ArrayList<Movie>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] field = {COLUMN_ID, COLUMN_NAME, COLUMN_GENRE,COLUMN_DESCRIPTION,COLUMN_RATING,COLUMN_PHOTO,COLUMN_ACTORS,COLUMN_YEAR,
                COLUMN_IMDB_LINK,COLUMN_CHECKED};
        Cursor c = db.query(TABLE_NAME2, field, null, null, null, null, null);

        int iid = c.getColumnIndex(COLUMN_ID);
        int iname = c.getColumnIndex(COLUMN_NAME);
        int igenre = c.getColumnIndex(COLUMN_GENRE);
        int idescription = c.getColumnIndex(COLUMN_DESCRIPTION);
        int irating = c.getColumnIndex(COLUMN_RATING);
        int iphoto = c.getColumnIndex(COLUMN_PHOTO);
        int iactors = c.getColumnIndex(COLUMN_ACTORS);
        int iyear = c.getColumnIndex(COLUMN_YEAR);
        int iimdb = c.getColumnIndex(COLUMN_IMDB_LINK);
        int ichecked = c.getColumnIndex(COLUMN_CHECKED);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String id = c.getString(iid);
            String name = c.getString(iname);
            String genre = c.getString(igenre);
            String description = c.getString(idescription);
            String rating = c.getString(irating);
            String photo = c.getString(iphoto);
            String actors = c.getString(iactors);
            String year = c.getString(iyear);
            String imdb = c.getString(iimdb);
            String checked = c.getString(ichecked);
            System.out.println(description  );

            //byte[] pic = c.getBlob(photo);
            movie_list.add(new Movie(name, Float.valueOf(rating), genre, description, actors, photo, null,checked));

        }

        return movie_list;
    }

    public List<Movie> getSeenMovies(){
        List<Movie> movie_list = new ArrayList<Movie>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] field = {COLUMN_ID, COLUMN_NAME, COLUMN_GENRE,COLUMN_DESCRIPTION,COLUMN_RATING,COLUMN_PHOTO,COLUMN_ACTORS,COLUMN_YEAR,
                COLUMN_IMDB_LINK,COLUMN_CHECKED};
        Cursor c = db.query(TABLE_NAME3, field, null, null, null, null, null);

        int iid = c.getColumnIndex(COLUMN_ID);
        int iname = c.getColumnIndex(COLUMN_NAME);
        int igenre = c.getColumnIndex(COLUMN_GENRE);
        int idescription = c.getColumnIndex(COLUMN_DESCRIPTION);
        int irating = c.getColumnIndex(COLUMN_RATING);
        int iphoto = c.getColumnIndex(COLUMN_PHOTO);
        int iactors = c.getColumnIndex(COLUMN_ACTORS);
        int iyear = c.getColumnIndex(COLUMN_YEAR);
        int iimdb = c.getColumnIndex(COLUMN_IMDB_LINK);
        int ichecked = c.getColumnIndex(COLUMN_CHECKED);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String id = c.getString(iid);
            String name = c.getString(iname);
            String genre = c.getString(igenre);
            String description = c.getString(idescription);
            String rating = c.getString(irating);
            String photo = c.getString(iphoto);
            String actors = c.getString(iactors);
            String year = c.getString(iyear);
            String imdb = c.getString(iimdb);
            String checked = c.getString(ichecked);

            System.out.println(description  );

            //byte[] pic = c.getBlob(photo);
            movie_list.add(new Movie(name, Float.valueOf(rating), genre, description, actors, photo, null,checked));

        }

        return movie_list;
    }

    public List<Movie> getMovies(){
        List<Movie> movie_list = new ArrayList<Movie>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] field = {COLUMN_ID, COLUMN_NAME, COLUMN_GENRE,COLUMN_DESCRIPTION,COLUMN_RATING,COLUMN_PHOTO,COLUMN_ACTORS,COLUMN_YEAR,
                COLUMN_IMDB_LINK,COLUMN_CHECKED};
        Cursor c = db.query(TABLE_NAME, field, null, null, null, null, null);

        int iid = c.getColumnIndex(COLUMN_ID);
        int iname = c.getColumnIndex(COLUMN_NAME);
        int igenre = c.getColumnIndex(COLUMN_GENRE);
        int idescription = c.getColumnIndex(COLUMN_DESCRIPTION);
        int irating = c.getColumnIndex(COLUMN_RATING);
        int iphoto = c.getColumnIndex(COLUMN_PHOTO);
        int iactors = c.getColumnIndex(COLUMN_ACTORS);
        int iyear = c.getColumnIndex(COLUMN_YEAR);
        int iimdb = c.getColumnIndex(COLUMN_IMDB_LINK);
        int ichecked = c.getColumnIndex(COLUMN_CHECKED);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String id = c.getString(iid);
            String name = c.getString(iname);
            String genre = c.getString(igenre);
            String description = c.getString(idescription);
            String rating = c.getString(irating);
            String photo = c.getString(iphoto);
            String actors = c.getString(iactors);
            String year = c.getString(iyear);
            String imdb = c.getString(iimdb);
            String checked = c.getString(ichecked);

            System.out.println(description );

            //byte[] pic = c.getBlob(photo);
            movie_list.add(new Movie(name, Float.valueOf(rating), genre, description, actors, photo, null,checked));

        }

        return movie_list;
    }

    public boolean changeCheckColumn(String tableName,String movieName,String columnName,boolean check){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(columnName, Boolean.toString(check));
        return db.update(tableName, args, COLUMN_NAME + "=" + movieName, new String[]{movieName}) > 0;
    }

    public void addHandler(Movie movie) {
        ContentValues values = new ContentValues();
       // values.put(COLUMN_ID, movie.getID());
        values.put(COLUMN_NAME, movie.getName());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_ACTORS, movie.getActors());
        values.put(COLUMN_PHOTO, movie.getPhotoBase64());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_DESCRIPTION, movie.getShortDesc());
        values.put(COLUMN_CHECKED,movie.getChecked());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addUnseenHandler(Movie movie) {
        ContentValues values = new ContentValues();
        // values.put(COLUMN_ID, movie.getID());
        values.put(COLUMN_NAME, movie.getName());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_ACTORS, movie.getActors());
        values.put(COLUMN_PHOTO, movie.getPhotoBase64());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_DESCRIPTION, movie.getShortDesc());
        values.put(COLUMN_CHECKED,movie.getChecked());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME2, null, values);
        db.close();
    }
    public void addSeenHandler(Movie movie) {
        ContentValues values = new ContentValues();
        // values.put(COLUMN_ID, movie.getID());
        values.put(COLUMN_NAME, movie.getName());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_ACTORS, movie.getActors());
        values.put(COLUMN_PHOTO, movie.getPhotoBase64());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_DESCRIPTION, movie.getShortDesc());
        values.put(COLUMN_CHECKED,movie.getChecked());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME3, null, values);
        db.close();
    }

    public Movie findHandler(String studentname) {
        String query = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_NAME + " = " + "'" + studentname + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Movie student = new Movie();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            student.setID(Integer.parseInt(cursor.getString(0)));
            student.setName(cursor.getString(1));
            cursor.close();
        } else {
            student = null;
        }
        db.close();
        return student;
    }
    public boolean deleteHandler(String table_name, String name) {
        boolean result = false;
        String query = "Select*FROM " + table_name + " WHERE " + COLUMN_NAME + " = '" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Movie movie = new Movie();
        if (cursor.moveToFirst()) {
            movie.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(table_name, COLUMN_ID + "=?",
                    new String[] {
                String.valueOf(movie.getID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_NAME, name);
        args.put(COLUMN_GENRE, name);
        args.put(COLUMN_DESCRIPTION, name);
        args.put(COLUMN_RATING, name);
        args.put(COLUMN_PHOTO, name);
        args.put(COLUMN_ACTORS, name);
        args.put(COLUMN_YEAR, name);
        args.put(COLUMN_IMDB_LINK, name);
        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + ID, null) > 0;
    }
}