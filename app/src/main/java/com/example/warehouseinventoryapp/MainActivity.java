package com.example.warehouseinventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.warehouseinventoryapp.provider.ItemEntity;
import com.example.warehouseinventoryapp.provider.ItemsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {


    private static final String PREF_COST = "PREF_COST";
    private static final String PREF_DESCRIPTION = "PREF_DESCRIPTION";
    private static final String PREF_ITEM_NAME = "PREF_ITEM_NAME";
    private static final String PREF_QUANTITY = "PREF_QUANTITY";
    private static final String PREF_FROZEN = "PREF_FROZEN";

    private static final String ITEM_NAME_KEY = "ITEM_NAME_KEY";
    private static final String QUANTITY_KEY = "QUANTITY_KEY";
    private static final String COST_KEY = "COST_KEY";
    private static final String DESCRIPTION_KEY = "DESCRIPTION_NAME";
    private static final String FROZEN_KEY = "FROZEN_KEY";

    private EditText mItemNameEditText, mQuantityEditText, mCostEditText, mDescriptionEditText;
    private Button mAddNewItemButton, mClearAllItemButton;
    private ToggleButton mYesNoToggleButton;

    private String mItemName, mQuantity, mCost, mDescription;
    private boolean mFrozen;

    private SharedPreferences mStorage;

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    protected DrawerLayout mDrawerLayout;
    private FloatingActionButton mfloatingActionButton;

    public ArrayList<Item> dataSource;
    private String converted;

//-----------------Week 7-----------------------------------
   private String mItemNameDB;
   private int mQuantityDB;
   private double mCostDB;
   private String mDescriptionDB;

//---------------Week 10------------------------------------
    View mView;
    int action, firstXCoordinate, secondXCoordinate;


   //--------------------------------------------------------------------------------------------------
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);


        mItemNameEditText = findViewById(R.id.item_name_entry_id);
        mQuantityEditText = findViewById(R.id.quantity_entry_id);
        mCostEditText = findViewById(R.id.cost_entry_id);
        mDescriptionEditText = findViewById(R.id.description_entry_id);
        mYesNoToggleButton = findViewById(R.id.frozen_item_toggle_id);
        mAddNewItemButton = findViewById(R.id.add_new_item_id);
        mClearAllItemButton = findViewById(R.id.clear_all_item_id);

//----------------------------------------Week 5--------------------------------------------------//
        mToolbar = findViewById(R.id.toolbar_id);
        mNavigationView = findViewById(R.id.navigation_view_id);
        mDrawerLayout = findViewById(R.id.drawerLayout_id);
        mfloatingActionButton = findViewById(R.id.fab_button_id);

        setSupportActionBar(mToolbar); // Toolbar is enabled

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.drawer_opened_id, R.string.drawer_closed_id);

        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new TheNavigationListener());

        mfloatingActionButton.setOnClickListener(new FloatingActionButtonListener());

//--------------------------------------------Week 6----------------------------------------------//
        dataSource = new ArrayList<>();

//--------------------------------------------Week 7----------------------------------------------//





//-------------------------------------- Broadcast receiver --------------------------------- //
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        MainActivityBroadCastReceiver myBroadCastReceiver = new MainActivityBroadCastReceiver();

        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

//----------------------------------------Week 10-------------------------------------------------//

        mView=findViewById(R.id.activity_main_id);
        mView.setOnTouchListener( new GestureListener() );
    }


    //--------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int selectedOptionsMenu = item.getItemId();

        if (selectedOptionsMenu == R.id.add_new_item_id) {

            extractValues();
            if (!checkEmpty()) {
                //addCurrentItemToDataSource();
                addItemToDataBase();
            }
            doToast();
        }

        else if (selectedOptionsMenu == R.id.clear_all_item_id) {
            revertDefault();
        }

        return true;
    }


    //--------------------------------------------------------------------------------------------------
    protected void onStart() {

        super.onStart();
        restorePreferences();
    }

    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putString(ITEM_NAME_KEY, mItemName);
        outState.putString(QUANTITY_KEY, mQuantity);
        outState.putString(COST_KEY, mCost);
        outState.putString(DESCRIPTION_KEY, mDescription);
        outState.putBoolean(FROZEN_KEY, mFrozen);
    }

    protected void onRestoreInstanceState(Bundle inState) {

        super.onRestoreInstanceState(inState);

        mItemName = inState.getString(ITEM_NAME_KEY);
        mQuantity = inState.getString(QUANTITY_KEY);
        mCost = inState.getString(COST_KEY);
        mDescription = inState.getString(DESCRIPTION_KEY);
        mFrozen = inState.getBoolean(FROZEN_KEY);
    }


    //--------------------------------------------------------------------------------------------------
    public void addNewItemButtonActionPerformed(View view) {

        extractValues();

        if ( checkEmptyDbVersion() ){ addItemToDataBase(); }

        doToastDbVersion();
    }

    public void clearAllItemButtonActionPerformed(View view) {

        revertDefault();
    }


    //--------------------------------------------------------------------------------------------------
    private void extractValues() {

        mItemName = mItemNameEditText.getText().toString();
        mQuantity = mQuantityEditText.getText().toString();
        mCost = mCostEditText.getText().toString();
        mDescription = mDescriptionEditText.getText().toString();
        mFrozen = mYesNoToggleButton.isChecked();



        mItemNameDB = mItemNameEditText.getText().toString();

        try {

            mQuantityDB = Integer.parseInt( mQuantityEditText.getText().toString() );
        }
        catch ( NumberFormatException e ){

            mQuantityDB = 0;
        }

        try {

            mCostDB = Double.parseDouble(mQuantityEditText.getText().toString());
        }
        catch ( NumberFormatException e ){

            mCostDB = 0.0;
        }

        mDescriptionDB = mDescriptionEditText.getText().toString();
        mFrozen = mYesNoToggleButton.isChecked();
    }

    private void revertDefault() {

        mItemNameEditText.setText("");
        mQuantityEditText.setText("");
        mCostEditText.setText("");
        mDescriptionEditText.setText("");
        mYesNoToggleButton.setChecked(false);
    }

    private boolean checkEmpty() {

        if (mItemName.isEmpty() || mQuantity.isEmpty() || mCost.isEmpty() || mDescription.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void doToast() {

        if (checkEmpty()) {

            Toast.makeText(getApplicationContext(), "Please do not leave any blanks",
                    Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(getApplicationContext(), "New Item (" + mItemName + ") has been added", Toast.LENGTH_SHORT).show();
        }
    }


    //--------------------------------------Week 6-----------------------------------------------------
    /*
        private void addCurrentItemToDataSource() {

            Item currentItem = new Item( mItemName, mQuantity, mCost, mDescription, mFrozen );
            dataSource.add(currentItem);
        }

        private void sendDataSource() {

            Intent week6Intent = new Intent(this, Week6.class);

            week6Intent.putExtra("key2", convertJSON() );

            startActivity(week6Intent);
    }

        private String convertJSON(){

            Gson gson = new Gson();
            String st = gson.toJson(dataSource);

            return st;
        }
    */

    //-----------------------------------Week 7--------------------------------------------------------
    private void addItemToDataBase(){

        ItemEntity itemDB = new ItemEntity( mItemNameDB, mQuantityDB, mCostDB, mDescriptionDB, mFrozen );
        ItemsViewModel mItemsViewModel = new ViewModelProvider(this).get(ItemsViewModel.class);
        mItemsViewModel.insertItemVM( itemDB );
    }

    private boolean checkEmptyDbVersion(){

        if( ( mItemNameDB.isEmpty() || mDescription.isEmpty() ) && ( ( mCostDB == 0.0 ) || ( mQuantityDB == 0 ) ) )
            return true;
        else{ return false; }
    }

    private void doToastDbVersion() {

        if( checkEmptyDbVersion() ) {

            Toast.makeText(getApplicationContext(), "Please do not leave any blanks",
                    Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(getApplicationContext(), "New Item (" + mItemNameDB + ") has been added", Toast.LENGTH_SHORT).show();
        }
    }


    //--------------------------------------------------------------------------------------------------
    private void restorePreferences() {


    }


    //--------------------------------------------------------------------------------------------------
    class MainActivityBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            StringTokenizer sT = new StringTokenizer(msg, ";");

            mItemName = sT.nextToken();
            mQuantity = sT.nextToken();
            mCost = sT.nextToken();
            mDescription = sT.nextToken();
            mFrozen = Boolean.parseBoolean(sT.nextToken());

            mItemNameEditText.setText(mItemName);
            mQuantityEditText.setText(mQuantity);
            mCostEditText.setText(mCost);
            mDescriptionEditText.setText(mDescription);
            mYesNoToggleButton.setChecked(mFrozen);
        }
    }

    class TheNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int selectedNavigationMenuItemId = item.getItemId();


            if (selectedNavigationMenuItemId == R.id.add_new_item_id) {

                extractValues();
                if (!checkEmpty()) {
                    //addCurrentItemToDataSource();
                    addItemToDataBase();
                }
                doToast();

            } else if (selectedNavigationMenuItemId == R.id.clear_all_item_id) {
                revertDefault();
            } else if (selectedNavigationMenuItemId == R.id.list_all_items_id) {

                //sendDataSource();

                Intent week6Intent = new Intent( getApplicationContext(), Week6.class );
                startActivity(week6Intent);
            }


            mDrawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }
    }

    class FloatingActionButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            extractValues();
            if ( checkEmpty() ) {

                Snackbar.make(v, "Please do not leave any blanks",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            else {

                extractValues();
                //addCurrentItemToDataSource();
                addItemToDataBase();

                Snackbar.make(v, "New Item (" + mItemName + ") has been added",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    class GestureListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            action = event.getActionMasked();

            switch (action) {

                case (MotionEvent.ACTION_DOWN) :

                    firstXCoordinate = (int)event.getX();
                    return true;

                case (MotionEvent.ACTION_MOVE) :

                    secondXCoordinate = (int)event.getX();
                    return true;

                case (MotionEvent.ACTION_UP) :

                    if( firstXCoordinate - secondXCoordinate < 0 ){

                        extractValues();

                        if ( checkEmptyDbVersion() ){ addItemToDataBase(); }

                        doToastDbVersion();
                    }
                    else {

                        revertDefault();
                    }
                    return true;

                default :
                    return false;
            }
        }
    }
}



