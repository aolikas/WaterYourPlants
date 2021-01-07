package my.e.wateryourplants.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import my.e.wateryourplants.MainActivity;
import my.e.wateryourplants.Model.UserData;
import my.e.wateryourplants.R;

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

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName = etName.getText().toString().trim();
                String txtEmail = etEmail.getText().toString().trim();
                String txtPassword = etPassword.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEmail)
                        || TextUtils.isEmpty(txtPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                    etEmail.setError("Please provide valid email");
                    etEmail.requestFocus();
                } else if (txtPassword.length() < 6) {
                    Toast.makeText(RegistrationActivity.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    register(txtName, txtEmail, txtPassword);
                }
            }
        });
    }

    private void register(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String userId = user.getUid();
                            mRef = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId);
                            UserData currentUser = new UserData(name, email);
                            mRef.setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, "Failed to register. Try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegistrationActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        }

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
}