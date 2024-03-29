package my.aolika.wateryourplants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import my.aolika.wateryourplants.auth.StartActivity;
import my.aolika.wateryourplants.dialogs.SensorCreateDialog;
import my.aolika.wateryourplants.model.UserData;
import my.aolika.wateryourplants.showDetails.AboutAppActivity;
import my.aolika.wateryourplants.showDetails.SensorDataActivity;
import my.aolika.wateryourplants.showDetails.UserAccountActivity;
import my.aolika.wateryourplants.viewHolder.MyViewHolder;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<UserData, MyViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(view -> openCreateSensorDialog());

        initRecyclerView();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String userId = mUser.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("userSensors");

        FirebaseRecyclerOptions<UserData> options = new FirebaseRecyclerOptions.Builder<UserData>()
                .setQuery(mRef, UserData.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<UserData, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull UserData model) {
                String key = getRef(position).getKey();
                holder.sensorName.setText(model.getUserSensorName()); // "" +
                holder.sensorDescription.setText(model.getUserSensorDescription());
                holder.sensorMoistureCondition.setText(model.getUserSensorMoistureCondition());
                holder.sensorTemperature.setText(String.format(Locale.GERMANY,
                        "%.2f",model.getUserSensorTemperature()));

                holder.view.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), SensorDataActivity.class);
                    intent.putExtra("key", key);
                    startActivity(intent);
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,
                        parent, false);
                return new MyViewHolder(view);
            }
        };

        mAdapter.notifyDataSetChanged();
        //  mAdapter.startListening();
        mRecyclerView.setAdapter(mAdapter);
    }



    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //   mRecyclerView.setHasFixedSize(true);
    }


    private void openCreateSensorDialog() {
        SensorCreateDialog sensorCreateDialog = new SensorCreateDialog();
        sensorCreateDialog.show(getSupportFragmentManager(), "sensor dialog");
    }


    private void logOutUser() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, getString(R.string.toast_menu_log_out),
                Toast.LENGTH_SHORT).show();
        Intent intent = (new Intent(MainActivity.this, StartActivity.class));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                logOutUser();
                return true;

            case R.id.show_account_info:
                startActivity(new Intent(MainActivity.this, UserAccountActivity.class));
                return true;

            case R.id.about_app:
                startActivity(new Intent(MainActivity.this, AboutAppActivity.class));
                return true;

                default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAdapter != null) {
            mAdapter.stopListening();
        }
    }
}