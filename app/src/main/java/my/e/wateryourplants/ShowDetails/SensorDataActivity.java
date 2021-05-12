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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
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
    private Slider slWateringDuration;
    private SwitchMaterial swAutomaticWatering;

    private DatabaseReference mRef;

    private ClipboardManager mClipboardManager;

    private String mKey;
    private Float mSliderDuration;
    private Boolean mSwitchSaveWateringState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        initWidget();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String userId = mUser.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userId).child("userSensors");

        mKey = getIntent().getStringExtra("key");

        getSensorData();

        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        initSlider();

        initAutoWateringSwitch();
    }

    private void initWidget() {
        etSensorName = findViewById(R.id.sensor_data_et_name);
        etSensorDescription = findViewById(R.id.sensor_data_et_description);
        txtSensorId = findViewById(R.id.sensor_data_txt_id);
        slWateringDuration = findViewById(R.id.sensor_data_slider_duration);
        swAutomaticWatering = findViewById(R.id.sensor_data_switch_auto_water);
        SwitchMaterial swNotifyCondition = findViewById(R.id.sensor_data_switch_notify_condition);

        Button btnCopy = findViewById(R.id.sensor_data_btn_copy);
        Button btnUpdate = findViewById(R.id.sensor_data_btn_update);
        Button btnDelete = findViewById(R.id.sensor_data_btn_delete);

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

                    mSwitchSaveWateringState = userData.getUserSensorPumpWateringAutomatic();
                    swAutomaticWatering.setChecked(mSwitchSaveWateringState);

                   // mSwitchSaveNotifyState = userData.getUserSensorNotifyDryCondition();
                    //swNotifyCondition.setChecked(mSwitchSaveNotifyState);

                    mSliderDuration = userData.getUserSensorPumpWateringDuration();
                    slWateringDuration.setValue(mSliderDuration);

                 //   mMoistureCondition = userData.getUserSensorMoistureCondition();
                   // if(mSwitchSaveNotifyState && mMoistureCondition.equals("Dry")) {
                     //   notificationDryCondition(etSensorName.getText().toString());
               //     }


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

    private void initSlider() {
        slWateringDuration.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                mSliderDuration = slider.getValue();
               // Toast.makeText(SensorDataActivity.this, "Value Start" + mSliderDuration , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                mSliderDuration = slider.getValue();
                sendSliderDuration();
               // Toast.makeText(SensorDataActivity.this, "Value Stop" + mSliderDuration , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAutoWateringSwitch() {
        swAutomaticWatering.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked) {
                mSwitchSaveWateringState = true;
                SharedPreferences.Editor editor
                        = getSharedPreferences("my.e.wateryourplants", MODE_PRIVATE).edit();
                editor.putBoolean("SaveAutomaticWateringState", mSwitchSaveWateringState);
                editor.apply();

                UserData userData = new UserData(null,
                        null,
                        null, mSwitchSaveWateringState,
                        null);
                Map<String, Object> dataUpdate = userData.toMapSensorWateringAutomatic();
                mRef.child(mKey).updateChildren(dataUpdate)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SensorDataActivity.this,
                                        getString(R.string.toast_sensor_data_auto_watering_on),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SensorDataActivity.this,
                                        getString(R.string.toast_sensor_data_auto_watering_error),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                mSwitchSaveWateringState = false;
                SharedPreferences.Editor editor
                        = getSharedPreferences("my.e.wateryourplants", MODE_PRIVATE).edit();
                editor.putBoolean("SaveAutomaticWateringState", mSwitchSaveWateringState);
                editor.apply();

                UserData userData = new UserData(null,
                        null,null,
                        mSwitchSaveWateringState, null);
                Map<String, Object> dataUpdate = userData.toMapSensorWateringAutomatic();
                mRef.child(mKey).updateChildren(dataUpdate)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SensorDataActivity.this,
                                        getString(R.string.toast_sensor_data_auto_watering_off),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SensorDataActivity.this,
                                        getString(R.string.toast_sensor_data_auto_watering_error),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });
    }

/**
    private void initNotifyDrySwitch() {
        swNotifyCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mSwitchSaveNotifyState = true;
                    UserData userData = new UserData(null,
                            null, null,
                            null, mSwitchSaveNotifyState);
                    Map<String, Object> dataUpdate = userData.toMapSensorNotifyDryCondition();
                    mRef.child(mKey).updateChildren(dataUpdate)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SensorDataActivity.this,
                                                "Notification successfully sets",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SensorDataActivity.this,
                                                "Something is wrong. Setting impossible",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    mSwitchSaveNotifyState = false;
                    UserData userData = new UserData(null,
                            null, null,
                            null, mSwitchSaveNotifyState);
                    Map<String, Object> dataUpdate = userData.toMapSensorNotifyDryCondition();
                    mRef.child(mKey).updateChildren(dataUpdate)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SensorDataActivity.this,
                                                "Notification successfully sets",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SensorDataActivity.this,
                                                "Something is wrong. Setting impossible",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }
 */

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

    private void sendSliderDuration() {
        UserData userData = new UserData(null,
                null, mSliderDuration,
                null, null);
        Map<String, Object> dataUpdate = userData.toMapSensorWateringDuration();
        mRef.child(mKey).updateChildren(dataUpdate)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(SensorDataActivity.this,
                                (getString(R.string.toast_sensor_data_set_slider_success_1)
                                + Math.round(mSliderDuration)
                                + getString(R.string.toast_sensor_data_set_slider_success_2)),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SensorDataActivity.this,
                                getString(R.string.toast_sensor_data_set_slider_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void copySensorId() {
        //  String sensorId = txtSensorId.getText().toString();
        ClipData mClipData = ClipData.newPlainText("sensorId", mKey);
        mClipboardManager.setPrimaryClip(mClipData);

        Toast.makeText(this,
                getString(R.string.toast_sensor_data_copy_id_success),
                Toast.LENGTH_SHORT).show();
    }

    private void updateNewSensorData() {
        String name = Objects.requireNonNull(etSensorName.getText()).toString().trim();
        String description = Objects.requireNonNull(etSensorDescription.getText()).toString().trim();

        if(TextUtils.isEmpty(name)) {
           etSensorName.setError(getString(R.string.error_empty_sensor_name));
           etSensorName.requestFocus();
        } else {
            UserData userData = new UserData(name, description, null);
            Map<String, Object> dataUpdate = userData.toMapSensorData();

            mRef.child(mKey).updateChildren(dataUpdate)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SensorDataActivity.this,
                                    getString(R.string.toast_sensor_data_update_success),
                                    Toast.LENGTH_SHORT).show();
                            startActivity(
                                    new Intent(SensorDataActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(SensorDataActivity.this,
                                    getString(R.string.toast_sensor_data_update_failed),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

    private void deleteSensor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SensorDataActivity.this);
        builder.setMessage(R.string.sensor_data_dialog_delete_msg);
        builder.setPositiveButton(R.string.sensor_data_dialog_delete_positive_button, (dialogInterface, i) -> mRef.child(mKey).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(SensorDataActivity.this,
                        getString(R.string.toast_sensor_data_dialog_delete),
                        Toast.LENGTH_SHORT).show();
                Intent intent = (new Intent(SensorDataActivity.this, MainActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SensorDataActivity.this,
                        Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }))
                .setNegativeButton(R.string.dialog_cancel, (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}