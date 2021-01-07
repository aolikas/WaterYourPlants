package my.e.wateryourplants.ShowDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import my.e.wateryourplants.Auth.StartActivity;
import my.e.wateryourplants.MainActivity;
import my.e.wateryourplants.Model.UserData;
import my.e.wateryourplants.R;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtUserId;
    private TextInputEditText etUserName, etUserEmail;
    private Button btnUpdate, btnDelete, btnCopy;

    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String mUserId;

    private ClipboardManager mClipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        initWidgets();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        mUserId = mUser.getUid();
        mRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(mUserId);

        retrieveUserData();

        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    private void retrieveUserData() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserData userData = snapshot.getValue(UserData.class);
                    assert userData != null;
                    etUserName.setText(userData.getUserName());
                    etUserEmail.setText(userData.getUserEmail());
                    txtUserId.setText(mUserId);
                } else {
                    Toast.makeText(UserAccountActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserAccountActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWidgets() {
        txtUserId = findViewById(R.id.user_account_id);
        etUserName = findViewById(R.id.user_account_et_name);
        etUserEmail = findViewById(R.id.user_account_et_email);
        btnUpdate = findViewById(R.id.user_account_btn_update);
        btnDelete = findViewById(R.id.user_account_btn_delete_user);
        btnCopy = findViewById(R.id.user_account_btn_copy);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_account_btn_delete_user:
                deleteUserAccount();
                break;
            case R.id.user_account_btn_update:
                updateNewData();
                break;
            case R.id.user_account_btn_copy:
                copyUserId();
                break;
        }
    }

    private void deleteUserAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserAccountActivity.this);
        builder.setMessage(R.string.user_account_dialog_delete_msg);
        builder.setPositiveButton(R.string.user_account_dialog_delete_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                user.delete();
                mRef.removeValue();
                Toast.makeText(UserAccountActivity.this,
                        "Account Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = (new Intent(UserAccountActivity.this, StartActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateNewData() {
        String name = etUserName.getText().toString();
        String email = etUserEmail.getText().toString();

        mUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    UserData userData = new UserData(name, email);

                    Map<String, Object> dataUpdate = userData.toMapUserNameEmail();
                    mRef.updateChildren(dataUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(UserAccountActivity.this, MainActivity.class));
                                Toast.makeText(UserAccountActivity.this, "Data updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserAccountActivity.this, "Something is wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void copyUserId() {
        String userId = txtUserId.getText().toString();
        ClipData mClipData = ClipData.newPlainText("id", userId);
        mClipboardManager.setPrimaryClip(mClipData);
        Toast.makeText(this, "User Id copied to Clipboard.", Toast.LENGTH_SHORT).show();
    }
}