package my.aolika.wateryourplants.auth;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import my.aolika.wateryourplants.MainActivity;
import my.aolika.wateryourplants.model.UserData;
import my.aolika.wateryourplants.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnRegistration;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initWidgets();

        mAuth = FirebaseAuth.getInstance();

        btnRegistration.setOnClickListener(view -> {
            String txtName = etName.getText().toString().trim();
            String txtEmail = etEmail.getText().toString().trim();
            String txtPassword = etPassword.getText().toString().trim();
            progressBar.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEmail)
                    || TextUtils.isEmpty(txtPassword)) {
                Toast.makeText(RegistrationActivity.this,
                        getString(R.string.toast_error_empty_fields), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                hideKeyboard();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                etEmail.setError(getString(R.string.error_invalid_email));
                etEmail.requestFocus();
                progressBar.setVisibility(View.GONE);
                hideKeyboard();
            } else if (txtPassword.length() < 6) {
                etPassword.setError(getString(R.string.error_invalid_password));
                etPassword.requestFocus();
                progressBar.setVisibility(View.GONE);
                hideKeyboard();
            } else {
                register(txtName, txtEmail, txtPassword);
                hideKeyboard();
            }
        });
    }

    private void register(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        String userId = user.getUid();
                        mRef = FirebaseDatabase.getInstance().getReference("Users")
                                .child(userId);
                        UserData currentUser = new UserData(name, email);
                        mRef.setValue(currentUser).addOnCompleteListener(task1 -> {
                            progressBar.setVisibility(View.GONE);
                            if (task1.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this,
                                        getString(R.string.toast_reg_success), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this,
                                        MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegistrationActivity.this,
                                        getString(R.string.toast_reg_failed), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(RegistrationActivity.this,
                                Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                });
    }

    //init the activity widgets
    private void initWidgets() {
        etName = findViewById(R.id.reg_et_name);
        etEmail = findViewById(R.id.reg_et_email);
        etPassword = findViewById(R.id.reg_et_password);
        btnRegistration = findViewById(R.id.reg_btn_registration);
        progressBar = findViewById(R.id.reg_progress_bar);
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