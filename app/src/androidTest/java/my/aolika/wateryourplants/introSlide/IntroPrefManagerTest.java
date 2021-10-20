package my.aolika.wateryourplants.introSlide;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class IntroPrefManagerTest {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static final String PREF = "IntroPrefManagerTest";
    public static final String KEY_STATUS = "status";

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void setPreferences() {
        editor = preferences.edit();
        editor.putBoolean(KEY_STATUS, true);
        editor.apply();
    }

    // checking Shared Preferences
    @Test
    public void testSharedPreferences() {
        setPreferences();
        boolean status = preferences.getBoolean(KEY_STATUS, false);
        assertTrue(status);
    }

    // checking Shared Preferences with status = null
    @Test
    public void testSharedPreferencesRemoveStatus() {
        setPreferences();
        editor.remove(KEY_STATUS).apply();
        boolean status = preferences.getBoolean(KEY_STATUS, false);
        assertFalse(status);
    }
}