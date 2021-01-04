package com.example.spree.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spree.Interface.ItemsClickedListener;
import com.example.spree.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice, txtProductQuantity;

    private ItemsClickedListener itemsClickedListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_item_quantity);


    }

    @Override
    public void onClick(View v) {

        itemsClickedListener.onClick(v, getAdapterPosition(), false);
    }
}
