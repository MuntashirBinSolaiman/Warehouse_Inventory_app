package com.example.warehouseinventoryapp;

public class Item {

    private String theItemName, theQuantity, theCost, theDescription;

    private boolean theFrozen;

    public Item(String theItemName, String theQuantity, String theCost, String theDescription,
                boolean theFrozen) {
        this.theItemName = theItemName;
        this.theQuantity = theQuantity;
        this.theCost = theCost;
        this.theDescription = theDescription;
        this.theFrozen = theFrozen;


    }

    public String getTheItemName() {
        return theItemName;
    }

    public String getTheQuantity() {
        return theQuantity;
    }

    public String getTheCost() {
        return theCost;
    }

    public String getTheDescription() {
        return theDescription;
    }

    public boolean isTheFrozen() {
        return theFrozen;
    }
}
