package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    TextView welcomeText;
    CardView memoryGameCard, moleGameCard, flappyBirdCard;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("GAMEZONE");
        }

        // View'ları bağla
        welcomeText = findViewById(R.id.welcomeText);
        memoryGameCard = findViewById(R.id.memoryGameCard);
        moleGameCard = findViewById(R.id.moleGameCard);
        flappyBirdCard = findViewById(R.id.flappyBirdCard);
        btnLogout = findViewById(R.id.btnLogout);

        // Kullanıcı adını al ve göster
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            welcomeText.setText("Welcome " + username + "!");
        } else {
            welcomeText.setText("Welcome!");
        }

        // CardView tıklamaları
        memoryGameCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MemoryCardActivity.class);
            startActivity(intent);
        });

        moleGameCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoleGameActivity.class);
            startActivity(intent);
        });

        flappyBirdCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FlappyBirdActivity.class);
            startActivity(intent);
        });

        // Logout butonu
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
