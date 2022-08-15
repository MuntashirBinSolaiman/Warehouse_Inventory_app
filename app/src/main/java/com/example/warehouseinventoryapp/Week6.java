package com.example.warehouseinventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.warehouseinventoryapp.provider.ItemEntity;
import com.example.warehouseinventoryapp.provider.ItemsViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Week6 extends AppCompatActivity {

    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    TheAdapter adapterInstance;

    ArrayList<Item> receivedDataSource;

    private ItemsViewModel mItemsViewModel; // Week 7
    //List<ItemEntity> receivedDbItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week6);

        recyclerView = findViewById( R.id.week_6_recycler_view_id );

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager( new LinearLayoutManager( this ));//layout manager for
                                                                              // recycler

        //theRecyclerViewAdapter = new Week6RecyclerViewAdapter( receivedDataSource );

        receivedDataSource = new ArrayList<>();

        //recyclerView.setAdapter( adapterInstance );

     //-----------------------Retrieving the ArrayList form Activity 1 -----------------------------
        /*
            String st = getIntent().getExtras().getString("key2");

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Item>>(){}.getType();
            receivedDataSource = gson.fromJson( st, type );
            adapterInstance = new TheAdapter( receivedDataSource );
            recyclerView.setAdapter( adapterInstance );
        */


     //---------------------------------------Week 7----------------------------------------------//
        adapterInstance = new TheAdapter();
        recyclerView.setAdapter( adapterInstance );

        mItemsViewModel = new ViewModelProvider(this).get( ItemsViewModel.class); /* Factory
                                                                                           method */

        mItemsViewModel.getAllItemsVM().observe( this, newData -> {

            adapterInstance.setData( newData );
            adapterInstance.notifyDataSetChanged();
        } );
    }
}
