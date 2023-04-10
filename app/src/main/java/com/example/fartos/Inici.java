package com.example.fartos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Inici extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici);
        getSupportActionBar().hide();
        ImageView backgroundImageView = findViewById(R.id.background_image);
        Glide.with(this).load(R.raw.dsadasdsadfas).into(backgroundImageView);

        Button jugar = findViewById(R.id.button2);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Jugadores j = new Jugadores();
                j.show(fm, "hola");
            }
        });
    }
}