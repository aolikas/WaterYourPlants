package my.e.wateryourplants.auth;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.checkbox.MaterialCheckBox;
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
    private SharedPreferences.Editor mPreferencesEditor;

    private static final String SHARED_PREF_NAME = "checkBoxRememberMe";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_SAVE_LOGIN = "save_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidgets();
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences mPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        mPreferencesEditor = mPreferences.edit();

        boolean mSaveLogin = mPreferences.getBoolean(KEY_SAVE_LOGIN, false);

        if (mSaveLogin) {
            etEmail.setText(mPreferences.getString(KEY_EMAIL, ""));
            etPassword.setText(mPreferences.getString(KEY_PASSWORD, ""));
            cbRememberMe.setChecked(true);
        }

        btnLogin.setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);
            mEmail = etEmail.getText().toString().trim();
            mPassword = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword)) {
                Toast.makeText(LoginActivity.this,
                        getString(R.string.toast_error_empty_fields), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                hideKeyboard();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                etEmail.setError(getString(R.string.error_invalid_email));
                etEmail.requestFocus();
                progressBar.setVisibility(View.GONE);
                hideKeyboard();
            } else {
                if (cbRememberMe.isChecked()) {
                    stayLogIn();
                } else {
                    //  removeLogIn();
                    mPreferencesEditor.clear();
                    mPreferencesEditor.apply();
                }

                loginUser(mEmail, mPassword);
            }
        });

    }

    private void stayLogIn() {
        mPreferencesEditor.putString(KEY_EMAIL, mEmail);
        mPreferencesEditor.putString(KEY_PASSWORD, mPassword);
        mPreferencesEditor.putBoolean(KEY_SAVE_LOGIN, true);
        mPreferencesEditor.apply();
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this,
                                getString(R.string.toast_login_success), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        //  finish();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                getString(R.string.toast_login_failed), Toast.LENGTH_SHORT).show();
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

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}