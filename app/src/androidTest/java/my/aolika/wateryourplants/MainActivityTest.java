package my.aolika.wateryourplants;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import my.aolika.wateryourplants.auth.LoginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static my.aolika.wateryourplants.auth.LoginActivityTest.setChecked;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private String email, password, name, description;

    @Rule
    public ActivityScenarioRule<LoginActivity> rule =
            new ActivityScenarioRule<>(LoginActivity.class);


    @Before
    public void setUp() {
        email = "test@test.ru";
        password = "1234567";
        name = "This is a sensor name";
        description = "This is a sensor description";
        Intents.init();
    }

    // checking a main menu items
    @Test
    public void testMenuItems() throws InterruptedException {

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

        onView(withText(R.string.menu_main_log_out)).check(matches(withText("Log Out")));
        onView(withText(R.string.menu_main_account_details)).check(matches(withText("Account details")));
        onView(withText(R.string.menu_main_account_details))
                .perform(click());
        pressBack();

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        onView(withText(R.string.menu_main_about_app)).check(matches(withText("About App")));
        onView(withText(R.string.menu_main_about_app))
                .perform(click());
        pressBack();

    }

    // login, clicking on Fab, checking all textView amd editTexts, Buttons
    @Test
    public void testFabClickAndDialogViews() throws InterruptedException {

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

        //find a FAB
        onView(withId(R.id.main_fab)).check(matches(isDisplayed()));
        //click on it
        onView(withId(R.id.main_fab)).perform(click());

        // check Dialog title text
        onView(withId(R.id.dialog_create_title)).check(matches(withText("Add a new sensor")));

        //check EditText hints
        onView(withId(R.id.dialog_create_et_sensor_name))
                .check(matches(withHint(R.string.dialog_create_et_hint_sensor_name)))
                .check(matches(isEnabled()));
        onView(withId(R.id.dialog_create_et_sensor_description))
                .check(matches(withHint(R.string.dialog_create_et_hint_sensor_description)))
                .check(matches(isEnabled()));

        //find a Create Button in dialog
        onView(withText(R.string.dialog_create))
                .check(matches(withText("Create")));

        //find a Create Button in dialog
        onView(withText(R.string.dialog_cancel))
                .check(matches(withText("Cancel")));

        //click a Cancel Button
        onView(withText(R.string.dialog_cancel)).perform(click());
    }

    // login, clicking on Fab, enter with empty name sensor
    @Test
    public void testFabClickAndEmptyName() throws InterruptedException {

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

        //find a FAB
        onView(withId(R.id.main_fab)).check(matches(isDisplayed()));
        //click on it
        onView(withId(R.id.main_fab)).perform(click());
        // clear EditText Name
        onView(withId(R.id.dialog_create_et_sensor_name)).perform(clearText(), closeSoftKeyboard());
        // fill EditText Description
        onView(withId(R.id.dialog_create_et_sensor_description)).perform(clearText(),
                typeText(description), closeSoftKeyboard());
        // Create Button click
        onView(withText(R.string.dialog_create)).perform(click());

        // Toast show with error
        onView(withText(R.string.toast_dialog_empty_sensor_name)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_dialog_empty_sensor_name)).inRoot(new ToastMatcher())
                .check(matches(withText("Please try again and give your sensor a name")));
    }

    // login, clicking on Fab, create sensor successfully
    @Test
    public void testFabClickAndCreateSensor() throws InterruptedException {

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

        //find a FAB
        onView(withId(R.id.main_fab)).check(matches(isDisplayed()));
        //click on it
        onView(withId(R.id.main_fab)).perform(click());

        // type sensor name and description
        onView(withId(R.id.dialog_create_et_sensor_name)).perform(clearText(),
                typeText(name), closeSoftKeyboard());
        onView(withId(R.id.dialog_create_et_sensor_description)).perform(clearText(),
                typeText(description), closeSoftKeyboard());

        onView(withText(R.string.dialog_create)).perform(click());
    }

    // login, clicking on Fab, create sensor successfully for testing RecyclerView
    @Test
    public void testCreateSensors() throws InterruptedException {

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

        //find a FAB
        onView(withId(R.id.main_fab)).check(matches(isDisplayed()));
        //click on it
        onView(withId(R.id.main_fab)).perform(click());

        // type sensor name and description
        onView(withId(R.id.dialog_create_et_sensor_name)).perform(clearText(),
                typeText("Sensor 1"), closeSoftKeyboard());
        onView(withId(R.id.dialog_create_et_sensor_description)).perform(clearText(),
                typeText("Sensor 1 Description"), closeSoftKeyboard());

        onView(withText(R.string.dialog_create)).perform(click());

        Thread.sleep(2000);

        //click on it
        onView(withId(R.id.main_fab)).perform(click());

        // type sensor name and description
        onView(withId(R.id.dialog_create_et_sensor_name)).perform(clearText(),
                typeText("Sensor 2"), closeSoftKeyboard());
        onView(withId(R.id.dialog_create_et_sensor_description)).perform(clearText(),
                typeText("Sensor 2 Description"), closeSoftKeyboard());

        onView(withText(R.string.dialog_create)).perform(click());

        Thread.sleep(2000);

        //click on it
        onView(withId(R.id.main_fab)).perform(click());

        // type sensor name and description
        onView(withId(R.id.dialog_create_et_sensor_name)).perform(clearText(),
                typeText("Sensor 3"), closeSoftKeyboard());
        onView(withId(R.id.dialog_create_et_sensor_description)).perform(clearText(),
                typeText("Sensor 3 Description"), closeSoftKeyboard());

        onView(withText(R.string.dialog_create)).perform(click());

        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    // login, testing recycler view in a specific position, scrolling to position
    @Test
    public void testRecyclerView() throws InterruptedException {

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

        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        pressBack();

        Thread.sleep(2000);

        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText("Sensor 3")).check(matches(isDisplayed()));
        onView(withText("Sensor 3 Description")).check(matches(isDisplayed()));

        pressBack();
    }

    // login, testing recycler view in a specific position, scrolling to position
    @Test
    public void testIntents() throws InterruptedException {

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

        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}