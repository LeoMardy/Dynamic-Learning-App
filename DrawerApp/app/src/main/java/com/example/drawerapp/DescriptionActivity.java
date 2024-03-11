package com.example.drawerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drawerapp.databinding.ActivityDescriptionBinding;
import com.squareup.picasso.Picasso;

public class DescriptionActivity extends AppCompatActivity {

    ActivityDescriptionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        String itemName = intent.getStringExtra("keyItem");
        String description = intent.getStringExtra("keyDes");
        String imagelink = intent.getStringExtra("keyImage");


       binding.detailsTitle.setText(itemName);
       binding.detailsDescription.setText(description);

       binding.detailsImageView.setVisibility(View.VISIBLE);
       Picasso.get()
               .load(imagelink)
               .into(binding.detailsImageView);


    }
}