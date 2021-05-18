package my.e.wateryourplants.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import my.e.wateryourplants.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnLogin = findViewById(R.id.start_btn_login);
        Button btnForgotPassword = findViewById(R.id.start_btn_forgot_password);
        Button btnRegister = findViewById(R.id.start_btn_register);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn_login:
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                break;
            case R.id.start_btn_register:
                startActivity(new Intent(StartActivity.this, RegistrationActivity.class));
                break;
            case R.id.start_btn_forgot_password:
                startActivity(new Intent(StartActivity.this, ResetPasswordActivity.class));
                break;
        }
    }
}