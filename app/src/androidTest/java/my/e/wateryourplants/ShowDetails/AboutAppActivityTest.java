package my.e.wateryourplants.ShowDetails;

import android.content.ClipboardManager;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.MainActivity;
import my.e.wateryourplants.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AboutAppActivityTest {
    private ClipboardManager clipboardManager;
    private UiDevice uiDevice;
    Context context;

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);



    // checking a main menu items
    @Test
    public void testMenuItems() {

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        onView(withText(R.string.menu_main_log_out)).check(matches(withText("Log Out")));
        onView(withText(R.string.menu_main_account_details)).check(matches(withText("Account details")));
        onView(withText(R.string.menu_main_account_details))
                .perform(click());
        pressBack();
    }
}