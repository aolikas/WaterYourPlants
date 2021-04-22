package my.e.wateryourplants;



import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.Auth.ToastMatcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private String name, description;

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp() {
        name = "This is a sensor name";
        description = "This is a sensor description";

    }

    @Test
    public void testMenuItems() throws InterruptedException {

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        onView(withText(R.string.menu_main_log_out)).check(matches(withText("Log Out")));
        onView(withText(R.string.menu_main_account_details)).check(matches(withText("Account details")));
        onView(withText(R.string.menu_main_account_details))
                .perform(click());

    }

    @Test
    public void testFabClickAndDialogViews() {
        onView(withId(R.id.main_fab)).check(matches(isDisplayed()));
        onView(withId(R.id.main_fab)).perform(click());

        onView(withId(R.id.dialog_create_title)).check(matches(withText("Add a new sensor")));

        onView(withId(R.id.dialog_create_et_sensor_name))
                .check(matches(withHint(R.string.dialog_create_et_hint_sensor_name)))
                .check(matches(isEnabled()));
        onView(withId(R.id.dialog_create_et_sensor_description))
                .check(matches(withHint(R.string.dialog_create_et_hint_sensor_description)))
                .check(matches(isEnabled()));
    }

    @Test
    public void testFabClickAndEmptyName() {
        onView(withId(R.id.main_fab)).check(matches(isDisplayed()));
        onView(withId(R.id.main_fab)).perform(click());

        onView(withId(R.id.dialog_create_et_sensor_name)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.dialog_create_et_sensor_description)).perform(clearText(),
                typeText(description), closeSoftKeyboard());

        onView(withText(R.string.dialog_create)).perform(click());

        onView(withText(R.string.toast_dialog_empty_sensor_name)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_dialog_empty_sensor_name)).inRoot(new ToastMatcher())
                .check(matches(withText("Please try again and give your sensor a name")));
    }

    @Test
    public void testFabClickAndCancelClick() {

        onView(withId(R.id.main_fab)).check(matches(isDisplayed()));
        onView(withId(R.id.main_fab)).perform(click());

        onView(withId(R.id.dialog_create_et_sensor_name)).perform(clearText(),
                typeText(name), closeSoftKeyboard());
        onView(withId(R.id.dialog_create_et_sensor_description)).perform(clearText(),
                typeText(description), closeSoftKeyboard());

        onView(withText(R.string.dialog_cancel)).perform(click());

    }

    @Test
    public void testFabClickAndCreateSensor() {

        onView(withId(R.id.main_fab)).check(matches(isDisplayed()));
        onView(withId(R.id.main_fab)).perform(click());

        onView(withId(R.id.dialog_create_et_sensor_name)).perform(clearText(),
                typeText(name), closeSoftKeyboard());
        onView(withId(R.id.dialog_create_et_sensor_description)).perform(clearText(),
                typeText(description), closeSoftKeyboard());

        onView(withText(R.string.dialog_create)).perform(click());

        onView(withId(R.id.item_data_sensor_name))
                .check(matches(withText("This is a sensor name"))).check(matches(isDisplayed()));
        onView(withId(R.id.item_data_sensor_description))
                .check(matches(withText("This is a sensor description")))
                .check(matches(isDisplayed()));
    }


}