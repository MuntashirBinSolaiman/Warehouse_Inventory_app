package com.example.warehouseinventoryapp.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.warehouseinventoryapp.Item;

import java.util.List;

public class ItemsViewModel extends AndroidViewModel {

    ItemsRepository itemsRepository;
    LiveData<List<ItemEntity>> myItems;

    public ItemsViewModel( @NonNull Application app ) {

        super( app );

        itemsRepository = new ItemsRepository( app );
        myItems = itemsRepository.getAllTasksRepo();
    }

    public LiveData<List<ItemEntity>> getAllItemsVM(){

        return myItems;
    }

    public void insertItemVM( ItemEntity item ){

        itemsRepository.insertItemRepo( item );
    }

    public void deleteItemByIdVM( int id ){

        itemsRepository.deleteItemByIdRepo( id );
    }

    public void deleteAllItemsVM(){

        itemsRepository.deleteAllItemsRepo();
    }
}
