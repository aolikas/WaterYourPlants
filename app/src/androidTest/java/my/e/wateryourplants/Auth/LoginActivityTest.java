package my.e.wateryourplants.Auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Checkable;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.R;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    public static final String PREF = "pref";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private String email, password;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Before
    public void setUp() {
        email = "waterplantstest@gmail.com";
        password = "Te$TwAte4";
        Context context = ApplicationProvider.getApplicationContext();
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    @Rule
    public ActivityScenarioRule<LoginActivity> rule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testAllHints() {
        onView(withId(R.id.login_et_email)).check(matches(withHint(R.string.et_hint_email)))
                .check(matches(isEnabled()));
        onView(withId(R.id.login_et_password)).check(matches(withHint(R.string.et_hint_password)))
                .check(matches(isEnabled()));
    }

    @Test
    public void testButtonText() {
        onView(withId(R.id.login_btn_login)).check(matches(withText(R.string.btn_login)))
                .check(matches(isEnabled()));
    }

    @Test
    public void checkBoxIsEnabled() {
        onView(withId(R.id.login_cb_remember_me)).check(matches(withText(R.string.cb_remember_me)))
                .check(matches(isEnabled()));
    }

    @Test
    public void testAllViewsEmpty() {
        onView(withId(R.id.login_progress_bar)).check(matches(not(isDisplayed())));

        onView(withId(R.id.login_et_email)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.login_et_password)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.login_btn_login)).perform(click());
        onView(withText(R.string.toast_error_empty_fields)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_error_empty_fields)).inRoot(new ToastMatcher())
                .check(matches(withText("Please fill all required fields")));
    }

    @Test
    public void testInvalidEmail() {
        email = "111111";
        onView(withId(R.id.login_progress_bar)).check(matches(not(isDisplayed())));

        onView(withId(R.id.login_et_email)).perform(clearText(),
                typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_et_email)).check(matches(withText("111111")));
        onView(withId(R.id.login_et_password)).perform(clearText(),
                typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_et_password)).check(matches(withText("Te$TwAte4")));

        onView(withId(R.id.login_btn_login)).perform(click());
        onView(withId(R.id.login_et_email))
                .check(matches(hasErrorText("Please provide a valid email")))
                .check(matches(hasFocus()));
    }

    @Test
    public void testRememberMeIsChecked() {
        onView(withId(R.id.login_cb_remember_me)).perform(setChecked());
        onView(withId(R.id.login_cb_remember_me)).check(matches(isNotChecked()));
        onView(withId(R.id.login_cb_remember_me)).perform(click());
        onView(withId(R.id.login_cb_remember_me)).check(matches(isChecked()));
    }

    @Test
    public void testLoginSuccessWithoutRememberMe() throws InterruptedException {
        onView(withId(R.id.login_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.login_cb_remember_me)).perform(setChecked());

        //type name, email, password to a specific EditText and check it
        onView(withId(R.id.login_et_email)).perform(clearText(),
                typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_et_email)).check(matches(withText("waterplantstest@gmail.com")));
        onView(withId(R.id.login_et_password)).perform(clearText(),
                typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_et_password)).check(matches(withText("Te$TwAte4")));

        // click to Login Button
        onView(withId(R.id.login_btn_login)).perform(click());

        // check Toast - Login Success
        onView(withText(R.string.toast_login_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_login_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Login is successful")));

        Thread.sleep(2000);

        // find a menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // click Log Out Button on menu
        onView(withText(R.string.menu_main_log_out))
                .perform(click());

        // check Toast - Log out success
        onView(withText(R.string.toast_menu_log_out)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_menu_log_out)).inRoot(new ToastMatcher())
                .check(matches(withText("Logged Out")));

        Thread.sleep(2000);

        // find a Login Button on Start Activity and click again
        onView(withId(R.id.start_btn_login)).check(matches(isEnabled()));
        onView(withId(R.id.start_btn_login)).perform(click());

        // find Email und Password EditTexts and check their hints
        onView(withId(R.id.login_et_email)).check(matches(withHint(R.string.et_hint_email)))
                .check(matches(isEnabled()));
        onView(withId(R.id.login_et_password)).check(matches(withHint(R.string.et_hint_password)))
                .check(matches(isEnabled()));

        // check CheckBox is unChecked
        onView(withId(R.id.login_cb_remember_me)).check(matches(isNotChecked()));

    }

    @Test
    public void testLoginWithRememberMe() throws InterruptedException {
        onView(withId(R.id.login_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.login_cb_remember_me)).perform(setChecked());

        //type name, email, password to a specific EditText and check it
        onView(withId(R.id.login_et_email)).perform(clearText(),
                typeText(email), closeSoftKeyboard());
        onView(withId(R.id.login_et_email)).check(matches(withText("waterplantstest@gmail.com")));
        onView(withId(R.id.login_et_password)).perform(clearText(),
                typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_et_password)).check(matches(withText("Te$TwAte4")));

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

        // find a menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // click Log Out Button on menu
        onView(withText(R.string.menu_main_log_out)).perform(click());

        // check Toast - Log out success
        onView(withText(R.string.toast_menu_log_out)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_menu_log_out)).inRoot(new ToastMatcher())
                .check(matches(withText("Logged Out")));

        Thread.sleep(2000);

        // find a Login Button on Start Activity and click again
        onView(withId(R.id.start_btn_login)).check(matches(isEnabled()));
        onView(withId(R.id.start_btn_login)).perform(click());

        // find Email und Password EditTexts and check their hints
        onView(withId(R.id.login_et_email)).check(matches(withText(email)))
                .check(matches(isEnabled()));
        onView(withId(R.id.login_et_password)).check(matches(withText(password)))
                .check(matches(isEnabled()));

        // check CheckBox is unChecked
        onView(withId(R.id.login_cb_remember_me)).check(matches(isChecked()));

    }

    @Test
    public void testLoginAndNameUpdate() throws InterruptedException {
        String name = "Sam";
        String updatedName = "Sammin";
        email = "test@test.ru";
        password = "1234567";

        onView(withId(R.id.login_progress_bar)).check(matches(not(isDisplayed())));
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

        // find a menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // click Log Out Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // check a name in EditText
        onView(withId(R.id.user_account_et_name)).check(matches(withText(name)));

        //type a name for update
        onView(withId(R.id.user_account_et_name)).perform(clearText(),
                typeText(updatedName), closeSoftKeyboard());

        //click Update Button
        onView(withId(R.id.user_account_btn_update)).perform(click());

        // check Toast - Update Success
        onView(withText(R.string.toast_update_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_update_success)).inRoot(new ToastMatcher())
                .check(matches(withText("User details updated")));

        Thread.sleep(2000);

        // find a menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // click Log Out Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // check an updated name in EditText
        onView(withId(R.id.user_account_et_name)).check(matches(withText(updatedName)));

        Thread.sleep(2000);

        //type a previous name for update
        onView(withId(R.id.user_account_et_name)).perform(clearText(),
                typeText(name), closeSoftKeyboard());

        //click Update Button
        onView(withId(R.id.user_account_btn_update)).perform(click());

        // check Toast - Update Success
        onView(withText(R.string.toast_update_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_update_success)).inRoot(new ToastMatcher())
                .check(matches(withText("User details updated")));
    }

    @Test
    public void testLoginAndWrongEmailUpdate() throws InterruptedException {
        email = "test@test.ru";
        String updatedEmail = "testtest.rou";
        password = "1234567";

        onView(withId(R.id.login_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.login_cb_remember_me)).perform(setChecked());

        //type email, password to a specific EditText and check it
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

        // find a menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // click Log Out Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        Thread.sleep(2000);

        // check an email in EditText
        onView(withId(R.id.user_account_et_email)).check(matches(withText(email)));

        //type an email for update
        onView(withId(R.id.user_account_et_email)).perform(clearText(),
                typeText(updatedEmail), closeSoftKeyboard());

        //click Update Button
        onView(withId(R.id.user_account_btn_update)).perform(click());

        // check Toast - Update Failed
        onView(withId(R.id.user_account_et_email))
                .check(matches(hasErrorText("Please provide a valid email")))
                .check(matches(hasFocus()));
    }

    @Test
    public void testLoginAndEmailUpdate() throws InterruptedException {
        email = "test@test.ru";
        String updatedEmail = "test@test.rou";
        password = "1234567";

        onView(withId(R.id.login_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.login_cb_remember_me)).perform(setChecked());

        //type email, password to a specific EditText and check it
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

        // find a menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // click Log Out Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // check an email in EditText
        onView(withId(R.id.user_account_et_email)).check(matches(withText(email)));

        //type a new email for update
        onView(withId(R.id.user_account_et_email)).perform(clearText(),
                typeText(updatedEmail), closeSoftKeyboard());

        //click Update Button
        onView(withId(R.id.user_account_btn_update)).perform(click());

        // check Toast - Update Success
        onView(withText(R.string.toast_update_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_update_success)).inRoot(new ToastMatcher())
                .check(matches(withText("User details updated")));

        Thread.sleep(2000);

        // find a menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // click Log Out Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // check an updated name in EditText
        onView(withId(R.id.user_account_et_email)).check(matches(withText(updatedEmail)));

        Thread.sleep(2000);

        //then type a previous email
        onView(withId(R.id.user_account_et_email)).perform(clearText(),
                typeText(email), closeSoftKeyboard());

        //click Update Button
        onView(withId(R.id.user_account_btn_update)).perform(click());

        // check Toast - Update Success
        onView(withText(R.string.toast_update_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_update_success)).inRoot(new ToastMatcher())
                .check(matches(withText("User details updated")));

        Thread.sleep(2000);

        // find a menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // click Log Out Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // check an updated name in EditText
        onView(withId(R.id.user_account_et_email)).check(matches(withText(email)));

    }

    public void setPreferences() {
        editor = preferences.edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    @Test
    public void testSharedPreferences() {
        setPreferences();

        String prefEmail = preferences.getString(EMAIL, "no email value");
        String prefPassword = preferences.getString(PASSWORD, "no password value");

        assertEquals(email, prefEmail);
        assertEquals(password, prefPassword);

    }

    @Test
    public void testSharedPreferencesRemoveEmail() {
        setPreferences();

        editor.remove(EMAIL).apply();

        String prefEmail = preferences.getString(EMAIL, "no email value");
        String prefPassword = preferences.getString(PASSWORD, "no password value");

        assertEquals("no email value", prefEmail);
        assertEquals(password, prefPassword);

    }

    @Test
    public void testSharedPreferencesRemovePassword() {
        setPreferences();

        editor.remove(PASSWORD).apply();

        String prefEmail = preferences.getString("email", "no email value");
        String prefPassword = preferences.getString("password", "no password value");

        assertEquals(email, prefEmail);
        assertEquals("no password value", prefPassword);

    }


    @After
    public void tearDown() {
        preferences.edit().putString(PREF, null).apply();
    }

    public static ViewAction setChecked() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return new Matcher<View>() {
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeMismatch(Object item, Description mismatchDescription) {

                    }

                    @Override
                    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

                    }

                    @Override
                    public void describeTo(Description description) {

                    }
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                if (checkableView.isChecked()) {
                    //  click().perform(uiController, view);
                    checkableView.setChecked(false);
                }
            }
        };
    }
}