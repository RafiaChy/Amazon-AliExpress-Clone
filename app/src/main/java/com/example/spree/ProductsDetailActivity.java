package com.example.spree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.spree.Models.Products;
import com.example.spree.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ProductsDetailActivity extends AppCompatActivity {

    private Button addToCartBtnPd;
    private ImageView pdImage;
    private TextView pdName, pdDescription, pdPrice;
    private String productID = "";
    private  ElegantNumberButton numberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_detail);


        pdImage = findViewById(R.id.product_image_detail);
        pdName = findViewById(R.id.product_name_detail);
        pdDescription = findViewById(R.id.product_description_detail);
        pdPrice = findViewById(R.id.product_price_detail);
        addToCartBtnPd = findViewById(R.id.pd_add_to_cart_btn);
        numberButton = findViewById(R.id.pd_number_btn);

        productID = getIntent().getStringExtra("pid");

        getProductDetails(productID);

        addToCartBtnPd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingProducts();
            }
        });
    }

    private void addingProducts() {

        String saveCurrentTime, saveCurrentDate;
        Calendar callForDate = Calendar.getInstance();

        SimpleDateFormat currentTime = new SimpleDateFormat("MMM-dd-yyyy");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        SimpleDateFormat currentDate = new SimpleDateFormat(" HH:mm:ss a");
        saveCurrentDate = currentDate.format(callForDate.getTime());

      final  DatabaseReference cartList = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", pdName.getText().toString());
        cartMap.put("price", pdPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount", "");

        cartList.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            cartList.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Products").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

//                                                Toast.makeText(ProductsDetailActivity.this, "Added to cart successfully.", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(ProductsDetailActivity.this, HomeActivity.class);
//                                                startActivity(intent);


                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void getProductDetails(String productID) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);

                    pdName.setText(products.getPname());
                    pdDescription.setText(products.getDescription());
                    pdPrice.setText(products.getPrice());

                    Picasso.get().load(products.getImage()).into(pdImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}