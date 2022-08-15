package com.example.warehouseinventoryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseinventoryapp.provider.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public class TheAdapter extends RecyclerView.Adapter<TheAdapter.ViewHolder> {

    //ArrayList<Item> _dataSource;

    //--------Week 7------//
    List<ItemEntity> _dbItem;

    public TheAdapter( /*ArrayList<Item> dataSourceParameter*/ ) {

        //_dataSource = dataSourceParameter;
    }

    public void setData( List<ItemEntity> dbItem ){

        _dbItem = dbItem;
    }

    @NonNull
    @Override
    public TheAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View referenceToCard = LayoutInflater.from( parent.getContext())
                .inflate( R.layout.card_view, parent, false );

        ViewHolder theViewHolder = new ViewHolder( referenceToCard );
        return theViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TheAdapter.ViewHolder holder, int position) {

        /*
        holder.nameTextView.setText( "Name: " + _dataSource.get( position ).getTheItemName() );
        holder.costTextView.setText( "Cost: " + String.valueOf( _dataSource.get( position ).getTheCost() ) );
        holder.quantityTextView.setText( "Quantity: " + String.valueOf( _dataSource.get( position ).getTheQuantity() ) );
        holder.descriptionTextView.setText( "Description: " + _dataSource.get( position ).getTheDescription() );
        holder.freezeTextView.setText( String.valueOf( "Freeze: " + _dataSource.get( position ).isTheFrozen() ) );
        */

        holder.nameTextView.setText( "Name: " + _dbItem.get( position ).getName() );
        holder.costTextView.setText( "Cost: " + String.valueOf( _dbItem.get( position ).getCost() ) );
        holder.quantityTextView.setText( "Quantity: " + String.valueOf( _dbItem.get( position ).getQuantity() ) );
        holder.descriptionTextView.setText( "Description: " + _dbItem.get( position ).getDescription() );
        holder.freezeTextView.setText( String.valueOf( "Freeze: " + _dbItem.get( position ).isFrozen() ) );
    }

    @Override
    public int getItemCount() {

        //return _dataSource.size();

        if ( _dbItem == null)
            return 0;
        else
            return _dbItem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView costTextView;
        public TextView quantityTextView;
        public TextView descriptionTextView;
        public TextView freezeTextView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            nameTextView = itemView.findViewById( R.id.name_card_view_id );
            costTextView = itemView.findViewById( R.id.cost_card_view_id );
            quantityTextView = itemView.findViewById( R.id.quantity_card_view_id );
            descriptionTextView = itemView.findViewById( R.id.description_card_view_id );
            freezeTextView = itemView.findViewById( R.id.freeze_card_view_id );
        }
    }
}
