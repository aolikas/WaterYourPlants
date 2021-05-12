package my.e.wateryourplants.IntroSlide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class IntroPrefManager {
    final SharedPreferences mPreferences;
    final SharedPreferences.Editor mEditor;
    final Context mContext;

    private static final String SHARED_PREF_NAME = "my.e.wateryourplants.IntroSlide";
    private static final String KEY_STATUS = "isFirstTimeStart";

    @SuppressLint("CommitPrefEdits")
    public IntroPrefManager(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public void setFirstTimeStart(boolean status) {
        mEditor.putBoolean(KEY_STATUS, status);
        mEditor.apply();
    }

    public boolean isFirstTimeStart() {
        return mPreferences.getBoolean(KEY_STATUS, true);
    }
}
