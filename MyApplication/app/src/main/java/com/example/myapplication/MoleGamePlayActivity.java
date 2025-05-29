package com.example.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MoleGamePlayActivity extends AppCompatActivity {

    private TextView scoreText, timeText;
    private ImageView[] moles;
    private int score = 0;
    private int activeMoleIndex = -1;

    private CountDownTimer gameTimer, moleTimer;
    private final int GAME_DURATION = 60000;
    private final int MOLE_INTERVAL = 800;

    private MediaPlayer hitSound, popupSound;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_game_play);

        scoreText = findViewById(R.id.txtScore);
        timeText = findViewById(R.id.txtTime);

        moles = new ImageView[] {
                findViewById(R.id.mole1), findViewById(R.id.mole2), findViewById(R.id.mole3),
                findViewById(R.id.mole4), findViewById(R.id.mole5), findViewById(R.id.mole6),
                findViewById(R.id.mole7), findViewById(R.id.mole8), findViewById(R.id.mole9)
        };

        hitSound = MediaPlayer.create(this, R.raw.hit_sound);
        popupSound = MediaPlayer.create(this, R.raw.popup_sound);
        random = new Random();

        setupMoleListeners();
        startGame();
    }

    private void setupMoleListeners() {
        for (int i = 0; i < moles.length; i++) {
            int index = i;
            moles[i].setVisibility(View.INVISIBLE);
            moles[i].setTag(false); // false: görünmüyor

            moles[i].setOnClickListener(v -> {
                if ((boolean) moles[index].getTag()) {
                    score++;
                    scoreText.setText("Skor: " + score);
                    moles[index].setVisibility(View.INVISIBLE);
                    moles[index].setTag(false);
                    hitSound.start();
                }
            });
        }
    }

    private void startGame() {
        score = 0;
        scoreText.setText("Skor: 0");

        gameTimer = new CountDownTimer(GAME_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Süre: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Süre: 0");
                stopGame();
            }
        }.start();

        moleTimer = new CountDownTimer(GAME_DURATION, MOLE_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                showRandomMole();
            }

            @Override
            public void onFinish() {}
        }.start();
    }

    private void showRandomMole() {
        // Önceki köstebeği gizle
        if (activeMoleIndex != -1) {
            moles[activeMoleIndex].setVisibility(View.INVISIBLE);
            moles[activeMoleIndex].setTag(false);
        }

        // Yeni köstebeği seç ve göster
        activeMoleIndex = random.nextInt(moles.length);
        moles[activeMoleIndex].setVisibility(View.VISIBLE);
        moles[activeMoleIndex].setTag(true);
        popupSound.start();
    }

    private void stopGame() {
        if (moleTimer != null) moleTimer.cancel();
        if (gameTimer != null) gameTimer.cancel();

        // Tüm köstebekleri gizle
        for (ImageView mole : moles) {
            mole.setVisibility(View.INVISIBLE);
            mole.setTag(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupSound != null) popupSound.release();
        if (hitSound != null) hitSound.release();
    }
}
