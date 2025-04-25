package com.example.proje;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword;
    Button btnRegister, btnGoToLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnSignUp);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        dbHelper = new DatabaseHelper(this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
                } else {
                    if (!checkUserExists(email)) {
                        if (registerUser(username, email, password)) {
                            Toast.makeText(RegisterActivity.this, "Kayıt başarılı! Giriş yapabilirsiniz.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Kayıt başarısız! Tekrar deneyin.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Bu e-posta zaten kayıtlı!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }


    private boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);

        long result = db.insert("users", null, values);
        return result != -1;
    }


    private boolean checkUserExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email=?";
        android.database.Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
