package com.prashant.mywikipediaapp.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.prashant.mywikipediaapp.Model.Player;
import com.prashant.mywikipediaapp.Model.RandomArticles;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ArticlesDB";
    private static final String TABLE_NAME = "Articles";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE_URL = "imageUrl";
    private static final String KEY_CATEGORYLIST = "categoryList";
    private static final String KEY_EXTRACT = "extract";
    private static final String KEY_TYPE = "type";

    private static final String[] COLUMNS = { KEY_ID, KEY_TITLE, KEY_TYPE,
            KEY_IMAGE_URL, KEY_CATEGORYLIST, KEY_EXTRACT };

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Articles ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "title TEXT, "+ "type TEXT, "
                + "imageUrl BLOB, " + "categoryList TEXT, " +"extract TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(RandomArticles randomArticles) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(randomArticles.getPageId()) });
        db.close();
    }

    public RandomArticles getArticles(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        RandomArticles randomArticles = new RandomArticles();
        randomArticles.setPageId(cursor.getString(0));
        randomArticles.setTitle(cursor.getString(1));
        randomArticles.setType(cursor.getString(2));
        randomArticles.setImageUrl(cursor.getString(3));
        randomArticles.setCategory(cursor.getString(4));
        randomArticles.setExtract(cursor.getString(5));

        return randomArticles;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public List<RandomArticles> allArticles() {

        List<RandomArticles> randomArticlesLinkedList = new LinkedList<RandomArticles>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        RandomArticles randomArticles = null;

        if (cursor.moveToFirst()) {
            do {
                randomArticles = new RandomArticles();
                randomArticles.setPageId(cursor.getString(0));
                randomArticles.setTitle(cursor.getString(1));
                randomArticles.setType(cursor.getString(2));
                randomArticles.setImageByte(cursor.getBlob(3));
                randomArticles.setCategory(cursor.getString(4));
                randomArticles.setExtract(cursor.getString(5));
                randomArticlesLinkedList.add(randomArticles);
            } while (cursor.moveToNext());
        }

        return randomArticlesLinkedList;
    }

    public void addArticle(RandomArticles player, Bitmap img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(KEY_ID, player.getPageId());
        values.put(KEY_TITLE, player.getTitle());
        values.put(KEY_TYPE, player.getType());
        if (img!=null) {
            byte[] data = getBitmapAsByteArray(img);
            values.put(KEY_IMAGE_URL, data);// this is a function
        }
        values.put(KEY_CATEGORYLIST, player.getCategory());
        values.put(KEY_EXTRACT, player.getExtract());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updateArticle(RandomArticles player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, player.getPageId());
        values.put(KEY_TITLE, player.getTitle());
        values.put(KEY_TYPE, player.getType());
        values.put(KEY_CATEGORYLIST, player.getCategory());
        values.put(KEY_IMAGE_URL, player.getImageUrl());
        values.put(KEY_EXTRACT, player.getExtract());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(player.getPageId()) });

        db.close();

        return i;
    }
}
//
//public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
//
//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "PlayersDB";
//    private static final String TABLE_NAME = "Players";
//    private static final String KEY_ID = "id";
//    private static final String KEY_NAME = "name";
//    private static final String KEY_POSITION = "position";
//    private static final String KEY_HEIGHT = "height";
//    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_POSITION,
//            KEY_HEIGHT };
//
//    public SQLiteDatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATION_TABLE = "CREATE TABLE Players ( "
//                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
//                + "position TEXT, " + "height INTEGER )";
//
//        db.execSQL(CREATION_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // you can implement here migration process
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        this.onCreate(db);
//    }
//
//    public void deleteOne(Player player) {
//        // Get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(player.getId()) });
//        db.close();
//    }
//
//    public Player getPlayer(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_NAME, // a. table
//                COLUMNS, // b. column names
//                " id = ?", // c. selections
//                new String[] { String.valueOf(id) }, // d. selections args
//                null, // e. group by
//                null, // f. having
//                null, // g. order by
//                null); // h. limit
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Player player = new Player();
//        player.setId(Integer.parseInt(cursor.getString(0)));
//        player.setName(cursor.getString(1));
//        player.setPosition(cursor.getString(2));
//        player.setHeight(Integer.parseInt(cursor.getString(3)));
//
//        return player;
//    }
//
//    public List<Player> allPlayers() {
//
//        List<Player> players = new LinkedList<Player>();
//        String query = "SELECT  * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Player player = null;
//
//        if (cursor.moveToFirst()) {
//            do {
//                player = new Player();
//                player.setId(Integer.parseInt(cursor.getString(0)));
//                player.setName(cursor.getString(1));
//                player.setPosition(cursor.getString(2));
//                player.setHeight(Integer.parseInt(cursor.getString(3)));
//                players.add(player);
//            } while (cursor.moveToNext());
//        }
//
//        return players;
//    }
//
//    public void addPlayer(Player player) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, player.getName());
//        values.put(KEY_POSITION, player.getPosition());
//        values.put(KEY_HEIGHT, player.getHeight());
//        // insert
//        db.insert(TABLE_NAME,null, values);
//        db.close();
//    }
//
//    public int updatePlayer(Player player) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, player.getName());
//        values.put(KEY_POSITION, player.getPosition());
//        values.put(KEY_HEIGHT, player.getHeight());
//
//        int i = db.update(TABLE_NAME, // table
//                values, // column/value
//                "id = ?", // selections
//                new String[] { String.valueOf(player.getId()) });
//
//        db.close();
//
//        return i;
//    }
//
//    public void deleteAll(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from "+ TABLE_NAME);
//    }
//
//}
