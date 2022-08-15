package com.example.warehouseinventoryapp.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemsRepository {

    private ItemDao itemDao;
    LiveData<List<ItemEntity>> items;

    public ItemsRepository(Application app) {

        ItemDatabase db = ItemDatabase.getDatabaseInstance( app );
        itemDao = db.itemDao();
        items = itemDao.getAllItems();
    }

    LiveData<List<ItemEntity>> getAllTasksRepo(){

        return itemDao.getAllItems();
    }

    void insertItemRepo( ItemEntity item ){

        ItemDatabase.dbWrite.execute(()-> itemDao.insertItem( item ));
    }

    void deleteAllItemsRepo(){

        ItemDatabase.dbWrite.execute(()-> itemDao.deleteAllItems());
    }

    void deleteItemByIdRepo( int id ){

        ItemDatabase.dbWrite.execute(()-> itemDao.deleteItemById( id ));
    }
}
