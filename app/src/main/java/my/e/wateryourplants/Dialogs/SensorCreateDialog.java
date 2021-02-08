package my.e.wateryourplants.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import my.e.wateryourplants.Model.UserData;
import my.e.wateryourplants.R;

public class SensorCreateDialog extends AppCompatDialogFragment {

    private EditText etSensorName;
    private EditText etSensorDescription;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_sensor, null);

        builder.setView(view)
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.dialog_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = etSensorName.getText().toString();
                        String description = etSensorDescription.getText().toString();
                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        String userId = user.getUid();
                        mRef = FirebaseDatabase.getInstance().getReference("Users")
                                .child(userId).child("userSensors");
                        String key = mRef.push().getKey();

                        UserData currentUser = new UserData(name, description, 5.0f, false);
                        assert key != null;
                        mRef.child(key).setValue(currentUser);
                    }
                });

        etSensorName = view.findViewById(R.id.dialog_create_et_sensor_name);
        etSensorDescription = view.findViewById(R.id.dialog_create_et_sensor_description);

        return builder.create();
    }
}
