package com.example.warehouseinventoryapp.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insertItem( ItemEntity theItemEntity );

    @Query( "select * from Items" )
    LiveData<List<ItemEntity>> getAllItems();

    @Query( "delete from Items")
    void deleteAllItems();

    @Query( "delete from Items where itemId = :id" )
    void deleteItemById( int id );
}
