package com.example.warehouseinventoryapp.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "Items" )
public class ItemEntity {

    public static final String ITEM_ENTITY_TABLE_NAME = "Items";

    @PrimaryKey( autoGenerate = true )
    @NonNull
    @ColumnInfo( name = "itemId")
      private int id;

    @ColumnInfo( name = "itemName" )
      private String name;

    @ColumnInfo( name = "itemQuantity" )
      private int quantity;

    @ColumnInfo( name = "itemCost" )
      private double cost;

    @ColumnInfo( name = "itemDescription" )
      private String description;

    @ColumnInfo( name = "itemFrozen" )
      private boolean frozen;

    public ItemEntity( /* int id,*/ String name, int quantity, double cost, String description, boolean
            frozen ) {

        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
        this.description = description;
        this.frozen = frozen;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setId(int id) {
        this.id = id;
    }
}
