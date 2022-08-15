package com.example.warehouseinventoryapp.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = ItemEntity.class, version = 1 )
public abstract class ItemDatabase extends RoomDatabase{

    public static final String DATABASE_NAME = "item_db";

    public abstract ItemDao itemDao();
    private static volatile ItemDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbWrite = Executors.newFixedThreadPool( NUMBER_OF_THREADS );

    static ItemDatabase getDatabaseInstance( Context context ){

        if( instance == null ) {

            synchronized ( ItemDatabase.class ){

                if( instance == null ){

                    instance = Room.databaseBuilder( context.getApplicationContext()
                    , ItemDatabase.class, DATABASE_NAME ).build();
                }
            }
        }

        return instance;
    }



}
