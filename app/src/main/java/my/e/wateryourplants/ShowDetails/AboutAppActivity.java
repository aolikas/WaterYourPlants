package my.e.wateryourplants.ShowDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import my.e.wateryourplants.R;

public class AboutAppActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLinkProject, tvLinkArduino;
    private ClipboardManager mClipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        initViews();
        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @SuppressLint("WrongViewCast")
    private void initViews() {
        tvLinkProject = findViewById(R.id.about_app_link_project);
        tvLinkArduino = findViewById(R.id.about_app_link_arduino);
        ImageView btnCopyLinkProject = findViewById(R.id.about_app_btn_copy_link_project);
        ImageView btnCopyLinkArduino = findViewById(R.id.about_app_btn_copy_link_arduino);
        btnCopyLinkProject.setOnClickListener(this);
        btnCopyLinkArduino.setOnClickListener(this);
    }

    private void copyLinkProject() {
        ClipData mClipData = ClipData.newPlainText("linkProject",
                tvLinkProject.getText().toString());
        mClipboardManager.setPrimaryClip(mClipData);
        Toast.makeText(this, "Project",
                Toast.LENGTH_SHORT).show();
    }

    private void copyLinkArduino() { ClipData mClipData = ClipData.newPlainText("linkArduino",
            tvLinkArduino.getText().toString());
        mClipboardManager.setPrimaryClip(mClipData);
        Toast.makeText(this, "Arduino",
                Toast.LENGTH_SHORT).show();}

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_app_btn_copy_link_project:
                copyLinkProject();
                break;
            case R.id.about_app_btn_copy_link_arduino:
                copyLinkArduino();
                break;
        }
    }
}