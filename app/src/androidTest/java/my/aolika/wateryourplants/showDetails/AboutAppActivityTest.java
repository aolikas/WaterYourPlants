package my.aolika.wateryourplants.showDetails;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.aolika.wateryourplants.auth.LoginActivity;
import my.aolika.wateryourplants.R;
import my.aolika.wateryourplants.ToastMatcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static my.aolika.wateryourplants.auth.LoginActivityTest.setChecked;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class AboutAppActivityTest {
    private ClipboardManager clipboardManager;
    private ClipData clipData;
    private Context context;
    private String email, password;

    @Rule
    public ActivityScenarioRule<LoginActivity> rule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        email = "test@test.ru";
        password = "1234567";
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    // checking all views matching
    @Test
    public void testAllViews() throws InterruptedException {

        onView(withId(R.id.login_cb_remember_me)).perform(setChecked());

        //type name, email, password to a specific EditText and check it
        onView(withId(R.id.login_et_email)).perform(clearText(),
                typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_et_email)).check(matches(withText("test@test.ru")));
        onView(withId(R.id.login_et_password)).perform(clearText(),
                typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_et_password)).check(matches(withText("1234567")));

        // find a CheckBox and set it Checked
        onView(withId(R.id.login_cb_remember_me)).check(matches(isNotChecked()));
        onView(withId(R.id.login_cb_remember_me)).perform(click());

        //click to Login Button
        onView(withId(R.id.login_btn_login)).perform(click());

        // check Toast - Login Success
        onView(withText(R.string.toast_login_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_login_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Login is successful")));

        Thread.sleep(2000);

        // open menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // check item menu and click
        onView(withText(R.string.menu_main_about_app)).check(matches(withText("About App")));
        onView(withText(R.string.menu_main_about_app))
                .perform(click());

        Thread.sleep(2000);

        // find all viewTexts and checking
        onView(withId(R.id.about_app_title)).check(matches(withText(R.string.about_app_title)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.about_app_part_1)).check(matches(withText(R.string.about_app_part_1)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.about_app_part_2)).check(matches(withText(R.string.about_app_part_2)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.about_app_part_3)).check(matches(withText(R.string.about_app_part_3)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.about_app_link_project))
                .check(matches(withText(R.string.about_app_link_project)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.about_app_link_arduino))
                .check(matches(withText(R.string.about_app_link_arduino)))
                .check(matches(isDisplayed()));

        // find Copy ImageViews and checking for clicking
        onView(withId(R.id.about_app_btn_copy_link_project))
                .check(matches(isEnabled())).check(matches(isClickable()));
        onView(withId(R.id.about_app_btn_copy_link_arduino))
                .check(matches(isEnabled())).check(matches(isClickable()));
    }

    // checking all views matching
    @Test
    public void testImageButtons() throws InterruptedException {

        onView(withId(R.id.login_cb_remember_me)).perform(setChecked());

        //type name, email, password to a specific EditText and check it
        onView(withId(R.id.login_et_email)).perform(clearText(),
                typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_et_email)).check(matches(withText("test@test.ru")));
        onView(withId(R.id.login_et_password)).perform(clearText(),
                typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_et_password)).check(matches(withText("1234567")));

        // find a CheckBox and set it Checked
        onView(withId(R.id.login_cb_remember_me)).check(matches(isNotChecked()));
        onView(withId(R.id.login_cb_remember_me)).perform(click());

        //click to Login Button
        onView(withId(R.id.login_btn_login)).perform(click());

        // check Toast - Login Success
        onView(withText(R.string.toast_login_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_login_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Login is successful")));

        Thread.sleep(2000);

        // open menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // check item menu and click
        onView(withText(R.string.menu_main_about_app)).check(matches(withText("About App")));
        onView(withText(R.string.menu_main_about_app))
                .perform(click());

        Thread.sleep(2000);

        // find Copy ImageViews and click
        onView(withId(R.id.about_app_btn_copy_link_project)).perform(click());

        // check Toast - Copied Success
        onView(withText(R.string.toast_about_add_copied_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_about_add_copied_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Link successfully copied")));

        Thread.sleep(2000);

        // find Copy ImageViews and click
        onView(withId(R.id.about_app_btn_copy_link_arduino)).perform(click());

        // check Toast - Copied Success
        onView(withText(R.string.toast_about_add_copied_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_about_add_copied_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Link successfully copied")));
    }

    @Test
    public void testAccessText() {
        String test = "test";
        CharSequence textToPaste;
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipData = ClipData.newPlainText("text", test);
        clipboardManager.setPrimaryClip(clipData);

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            textToPaste = clipboard.getPrimaryClip().getItemAt(0).getText();
        } catch (Exception e) {
            return;
        }

        assertEquals(test, textToPaste);
    }

    @Test
    public void testHasText() {
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        clipData = ClipData.newPlainText("text", "");
        clipboardManager.setPrimaryClip(clipData);
        assertFalse(clipboardManager.hasPrimaryClip());

        clipData = ClipData.newPlainText("text", null);
        clipboardManager.setPrimaryClip(clipData);
        assertFalse(clipboardManager.hasPrimaryClip());
    }
}