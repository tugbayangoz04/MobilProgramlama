package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;


public class SignUpActivity extends AppCompatActivity {

    EditText editUsername, editEmail, editPassword;
    Button btnSignUp;
    TextView textLoginLink;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("GAMEZONE");
        }


        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        textLoginLink = findViewById(R.id.textLoginLink);

        dbHelper = new DBHelper(this);

        btnSignUp.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.insertUser(username, email, password);
                if (inserted) {
                    Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Bu e-posta zaten kayıtlı!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}


