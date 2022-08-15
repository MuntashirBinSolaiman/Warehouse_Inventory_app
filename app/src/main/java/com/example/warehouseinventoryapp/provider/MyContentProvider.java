package com.example.warehouseinventoryapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "fit2081.app.Muntashir";
    public static final Uri CONTENT_URI = Uri.parse( "content://" + CONTENT_AUTHORITY );

    private static final int MULTIPLE_ROWS_ITEM = 1;
    private static final int SINGLE_ROW_ITEM = 2;

    private static final UriMatcher matcher = createUriMatcher();

    ItemDatabase db;

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {

        db = ItemDatabase.getDatabaseInstance( getContext() );
        return true;
    }

    private static UriMatcher createUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher( UriMatcher.NO_MATCH );

        uriMatcher.addURI( CONTENT_AUTHORITY, ItemEntity.ITEM_ENTITY_TABLE_NAME, MULTIPLE_ROWS_ITEM );
        uriMatcher.addURI( CONTENT_AUTHORITY, ItemEntity.ITEM_ENTITY_TABLE_NAME + "/#",
                SINGLE_ROW_ITEM );

        return uriMatcher;
    }

    @Override
    public String getType(Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables( ItemEntity.ITEM_ENTITY_TABLE_NAME );
/*
        int operation = matcher.match( uri );
        switch (operation) {

            case MULTIPLE_ROWS_ITEM:
                break;
            case SINGLE_ROW_ITEM:
                selection = "id = ?";
                String id = uri.getLastPathSegment();
                selectionArgs = new String[] {id};
                break;
            default:
                throw new IllegalArgumentException("Unknown operation");
        }
   */
        String query = builder.buildQuery( projection, selection, null, null,
                sortOrder, null );
        Cursor cursor = db.getOpenHelper().getReadableDatabase().query( query, selectionArgs );


        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

      int numberOfRowsDeleted = db.getOpenHelper().getWritableDatabase().delete( ItemEntity.ITEM_ENTITY_TABLE_NAME, selection,
              selectionArgs );

      return numberOfRowsDeleted;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long returnedId = db.getOpenHelper().getWritableDatabase().insert(
                ItemEntity.ITEM_ENTITY_TABLE_NAME, 0,
                values );

        Uri uriToBeSent = ContentUris.withAppendedId( CONTENT_URI, returnedId );

        return uriToBeSent;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int numberOfRows = db.getOpenHelper().getWritableDatabase().update(
                ItemEntity.ITEM_ENTITY_TABLE_NAME, 0, values, selection,
                selectionArgs );

        return numberOfRows;
    }
}
