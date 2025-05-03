package com.example.cleanit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnSubmit;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // activity_register.xml과 연결

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        dbHelper = new DBHelper(this);

        btnSubmit.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "모든 정보를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.insertUser(username, password);
            if (success) {
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                finish(); // 로그인 화면으로 돌아가기
            } else {
                Toast.makeText(this, "이미 존재하는 사용자입니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
