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
import java.util.Objects;

import my.e.wateryourplants.MainActivity;
import my.e.wateryourplants.Model.UserData;
import my.e.wateryourplants.R;

public class SensorDataActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtSensorId;
    private TextInputEditText etSensorName, etSensorDescription;
    private Button btnCopy, btnUpdate, btnDelete;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    private ClipboardManager mClipboardManager;

    private String mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        initWidget();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String userId = mUser.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userId).child("userSensors");

        mKey = getIntent().getStringExtra("key");

        getSensorData();

        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

    }

    private void initWidget() {
        etSensorName = findViewById(R.id.sensor_data_et_name);
        etSensorDescription = findViewById(R.id.sensor_data_et_description);
        txtSensorId = findViewById(R.id.sensor_data_txt_id);

        btnCopy = findViewById(R.id.sensor_data_btn_copy);
        btnUpdate = findViewById(R.id.sensor_data_btn_update);
        btnDelete = findViewById(R.id.sensor_data_btn_delete);

        btnCopy.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void getSensorData() {
        mRef.child(mKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserData userData = snapshot.getValue(UserData.class);
                    assert userData != null;
                    etSensorName.setText(userData.getUserSensorName());
                    etSensorDescription.setText(userData.getUserSensorDescription());
                    txtSensorId.setText(mKey);
                } else {
                    Toast.makeText(SensorDataActivity.this, "Sensor does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SensorDataActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sensor_data_btn_copy:
                copySensorId();
                break;
            case R.id.sensor_data_btn_update:
                updateNewSensorData();
                break;
            case R.id.sensor_data_btn_delete:
                deleteSensor();
                break;
        }
    }

    private void copySensorId() {
        //  String sensorId = txtSensorId.getText().toString();
        ClipData mClipData = ClipData.newPlainText("sensorId", mKey);
        mClipboardManager.setPrimaryClip(mClipData);

        Toast.makeText(this,
                "Sensor Id copied to Clipboard.",
                Toast.LENGTH_SHORT).show();
    }

    private void updateNewSensorData() {
        String name = Objects.requireNonNull(etSensorName.getText()).toString();
        String description = Objects.requireNonNull(etSensorDescription.getText()).toString();

        UserData userData = new UserData(name, description, null);
        Map<String, Object> dataUpdate = userData.toMapSensorData();

        mRef.child(mKey).updateChildren(dataUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SensorDataActivity.this, "Sensor Data updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SensorDataActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(SensorDataActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteSensor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SensorDataActivity.this);
        builder.setMessage(R.string.sensor_data_dialog_delete_msg);
        builder.setPositiveButton(R.string.sensor_data_dialog_delete_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mRef.child(mKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SensorDataActivity.this,
                                    "Sensor deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = (new Intent(SensorDataActivity.this, MainActivity.class));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SensorDataActivity.this,
                                    Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
}