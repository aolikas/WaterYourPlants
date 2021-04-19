package my.e.wateryourplants.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import my.e.wateryourplants.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = findViewById(R.id.reset_et_email);
        Button btnReset = findViewById(R.id.reset_btn_reset);
        progressBar = findViewById(R.id.reset_progress_bar);
        mAuth = FirebaseAuth.getInstance();


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String txtEmail = etEmail.getText().toString().trim();
                if(TextUtils.isEmpty(txtEmail)) {
                    Toast.makeText(ResetPasswordActivity.this,
                            getString(R.string.toast_error_empty_fields), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    hideKeyboard();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                    etEmail.setError(getString(R.string.error_invalid_email));
                    progressBar.setVisibility(View.GONE);
                    hideKeyboard();
                } else {
                    resetUserPassword(txtEmail);
                    hideKeyboard();
                }
            }
        });
    }

    private void resetUserPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this,
                                    getString(R.string.toast_reset_success),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this,
                                    Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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