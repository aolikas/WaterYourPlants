package my.e.wateryourplants.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import my.e.wateryourplants.MainActivity;
import my.e.wateryourplants.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private MaterialCheckBox cbRememberMe;
    private Button btnLogin;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private String mEmail, mPassword;
    private Boolean mIsLogin;
    private SharedPreferences mPreferences;

    private static final String SHARED_PREF_NAME = "checkBoxRememberMe";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_LOGIN = "is_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidgets();
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                mEmail = etEmail.getText().toString().trim();
                mPassword = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(LoginActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                    etEmail.setError("Please provide valid email");
                    etEmail.requestFocus();
                } else {
                    if(cbRememberMe.isChecked()) {
                        mIsLogin = cbRememberMe.isChecked();
                        mPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putString(KEY_EMAIL, mEmail);
                        editor.putString(KEY_PASSWORD, mPassword);
                        editor.putBoolean(KEY_IS_LOGIN, mIsLogin);
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "Data saved in SP", Toast.LENGTH_SHORT).show();
                    } else {
                        mPreferences.edit().clear().apply();
                    }

                    loginUser(mEmail, mPassword);
                }
            }
        });

        getPreferencesData();
    }

    public void getPreferencesData() {
        mPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = mPreferences.getString(KEY_EMAIL, "");
        etEmail.setText(email);

        String password = mPreferences.getString(KEY_PASSWORD, "");
        etPassword.setText(password);

        boolean isLogin = mPreferences.getBoolean(KEY_IS_LOGIN, false);
        cbRememberMe.setChecked(isLogin);



    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //  finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //init the activity widgets
    private void initWidgets() {
        etEmail = findViewById(R.id.login_et_email);
        etPassword = findViewById(R.id.login_et_password);
        cbRememberMe = findViewById(R.id.login_cb_remember_me);
        progressBar = findViewById(R.id.login_progress_bar);
        btnLogin = findViewById(R.id.login_btn_login);
        progressBar.setVisibility(View.GONE);
    }
}