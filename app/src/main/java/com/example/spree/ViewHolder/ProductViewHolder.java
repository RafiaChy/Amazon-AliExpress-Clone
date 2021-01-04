package com.example.spree.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spree.Interface.ItemsClickedListener;
import com.example.spree.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textProductName, textProductDescription, textProductPrice;
    public ImageView imageView;
    public ItemsClickedListener listener;


    public ProductViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_item_image);
        textProductName = (TextView) itemView.findViewById(R.id.product_item_name);
        textProductDescription = (TextView) itemView.findViewById(R.id.product_item_description);
        textProductPrice = (TextView) itemView.findViewById(R.id.product_item_price);
    }

    public void setItemClickListner(ItemsClickedListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
