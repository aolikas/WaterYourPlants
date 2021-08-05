package my.e.wateryourplants.showDetails;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.slider.Slider;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.auth.LoginActivity;
import my.e.wateryourplants.R;
import my.e.wateryourplants.ToastMatcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static my.e.wateryourplants.auth.LoginActivityTest.setChecked;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class SensorDataActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> rule =
            new ActivityScenarioRule<>(LoginActivity.class);

    String email, password, sensorId, sensorName, sensorDescription ;

    @Before
    public void setUp() {
        email = "test@test.ru";
        password = "1234567";
        sensorId = "-MgLXWoCs5qOxJ5G2JkP";
        sensorName  = "This is a sensor name";
        sensorDescription = "This is a sensor description";
    }

    // checking all TextViews, Button texts for matching text
    @Test
    public void testAllTexts() throws InterruptedException {

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // checking all Views in Sensor Data card for matching text string
        onView(withId(R.id.sensor_data_card_title))
                .check(matches(withText(R.string.sensor_data_title)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_txt_id_title))
                .check(matches(withText(R.string.sensor_data_id_title)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_txt_id))
                .check(matches(withText(sensorId)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_btn_copy))
                .check(matches(withText(R.string.sensor_data_btn_copy_sensor_id)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_txt_sleep_mode_title))
                .check(matches(withText(R.string.sensor_data_sleep_mode_title)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_sleep_mode_description))
                .check(matches(withText(R.string.sensor_data_sleep_mode_description)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_sleep_mode_auto))
                .check(matches(withText(R.string.sensor_data_sleep_mode_auto)))
                .check(matches(isDisplayed()));


        Thread.sleep(5000);

        // checking all Views in Watering card for matching text string
        onView(withId(R.id.sensor_data_watering_card_title))
                .check(matches(withText(R.string.sensor_data_watering_title)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_watering_description))
                .check(matches(withText(R.string.sensor_data_watering_duration)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_watering_auto))
                .check(matches(withText(R.string.sensor_data_watering_auto)))
                .check(matches(isDisplayed()));


        Thread.sleep(2000);

        // checking all Views in Editing card for matching text string
        onView(withId(R.id.sensor_data_editing_card_title)).perform(scrollTo())
                .check(matches(withText(R.string.sensor_data_editing_title)))
                .check(matches(isDisplayed()));


        onView(withId(R.id.sensor_data_et_name)).perform(scrollTo())
                .check(matches(withText(sensorName)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_et_description)).perform(scrollTo())
                .check(matches(withText(sensorDescription)))
                .check(matches(isEnabled()));

        onView(withId(R.id.sensor_data_btn_update)).perform(scrollTo())
                .check(matches(withText(R.string.sensor_data_btn_update)))
                .check(matches(isEnabled()));

        onView(withId(R.id.sensor_data_btn_delete)).perform(scrollTo())
                .check(matches(withText(R.string.sensor_data_btn_delete)))
                .check(matches(isEnabled()));

        pressBack();
    }

    // testing Copy Id Button in Sensor Data Card
    @Test
    public void testSensorIdButton() throws InterruptedException {

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.sensor_data_btn_copy))
                .check(matches(withText(R.string.sensor_data_btn_copy_sensor_id)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sensor_data_btn_copy)).perform(click());

        // check Toast - Copy Success
        onView(withText(R.string.toast_sensor_data_copy_id_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_sensor_data_copy_id_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Sensor Id successfully copied")));
        Thread.sleep(2000);

        pressBack();
    }

    // testing Slider in Watering Card, set a value
    @Test
    public void testSetValueSlider() throws InterruptedException {

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // set value 5
       onView(withId(R.id.sensor_data_slider_duration))
               .perform(setProgress(5));

       // check value
       onView(withId(R.id.sensor_data_slider_duration))
               .check(matches(withValue(5.0F)));

       Thread.sleep(1000);

        // set value 20
        onView(withId(R.id.sensor_data_slider_duration))
                .perform(setProgress(20));

        // check value
        onView(withId(R.id.sensor_data_slider_duration))
                .check(matches(withValue(20.0F)));

        pressBack();
    }

    // testing Toggle Button in Watering Card, turn on/off
    @Test
    public void testToggledButtonOnOff() throws InterruptedException {

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // switch button off
        onView(withId(R.id.sensor_data_switch_water_auto)).perform(setChecked());

        // check for a state
        onView(withId(R.id.sensor_data_switch_water_auto)).check(matches(isNotChecked()));

        // click to turn on
        onView(withId(R.id.sensor_data_switch_water_auto)).perform(click());

        // check Toast - turn on auto watering
        onView(withText(R.string.toast_sensor_data_auto_watering_on)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_sensor_data_auto_watering_on)).inRoot(new ToastMatcher())
                .check(matches(withText("Automatic Watering successfully turn on")));

        Thread.sleep(2000);

        // checking a state for a true
        onView(withId(R.id.sensor_data_switch_water_auto)).check(matches(isChecked()));

        // click on button
        onView(withId(R.id.sensor_data_switch_water_auto))
                .perform(click()).check(matches(isEnabled()));

        // check Toast - turn off auto watering
        onView(withText(R.string.toast_sensor_data_auto_watering_off)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_sensor_data_auto_watering_off)).inRoot(new ToastMatcher())
                .check(matches(withText("Automatic Watering turn off")));

        pressBack();
    }


    // testing Toggle Button in Watering Card, turn on/off
    @Test
    public void testToggledButton() throws InterruptedException {

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // switch button off
        onView(withId(R.id.sensor_data_switch_water_auto)).perform(setChecked());

        // check for a state
        onView(withId(R.id.sensor_data_switch_water_auto)).check(matches(isNotChecked()));

        // click to turn on
        onView(withId(R.id.sensor_data_switch_water_auto)).perform(click());

        // check Toast - turn on auto watering
        onView(withText(R.string.toast_sensor_data_auto_watering_on)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_sensor_data_auto_watering_on)).inRoot(new ToastMatcher())
                .check(matches(withText("Automatic Watering successfully turn on")));

        Thread.sleep(2000);

        // checking a state for a true
        onView(withId(R.id.sensor_data_switch_water_auto)).check(matches(isChecked()));

        // click on button
        onView(withId(R.id.sensor_data_switch_water_auto))
                .perform(click()).check(matches(isEnabled()));

        // check Toast - turn off auto watering
        onView(withText(R.string.toast_sensor_data_auto_watering_off)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_sensor_data_auto_watering_off)).inRoot(new ToastMatcher())
                .check(matches(withText("Automatic Watering turn off")));

        pressBack();
    }

    // testing Update Button in Editing Card with empty sensor name
    @Test
    public void testUpdateEmptySensorName() throws InterruptedException {

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // clear Edit text for sensor name
        onView(withId(R.id.sensor_data_et_name)).perform(clearText(), closeSoftKeyboard());

        Thread.sleep(1000);

        onView(withId(R.id.sensor_data_btn_update))
                .check(matches(withText(R.string.sensor_data_btn_update)))
                .check(matches(isEnabled()));

        // click Update Button
        onView(withId(R.id.sensor_data_btn_update)).perform(scrollTo(), click());

        // catch error state
        onView(withId(R.id.sensor_data_et_name))
                .check(matches(hasErrorText("Please give your sensor a name")))
                .check(matches(hasFocus()));

        pressBack();
    }


    // testing Update Button in Editing Card
    @Test
    public void testUpdateSensorData() throws InterruptedException {
        // a new data to update
        String newName = "This is a new name";
        String newDescription = "This is a new description";

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // type a new sensor name
        onView(withId(R.id.sensor_data_et_name)).perform(clearText(),
                typeText(newName),closeSoftKeyboard());
        // type a new sensor description
        onView(withId(R.id.sensor_data_et_description)).perform(clearText(),
                typeText(newDescription),closeSoftKeyboard());

        // click Update Button
        onView(withId(R.id.sensor_data_btn_update)).perform(scrollTo(),click());

        // check Toast - Update Success
        onView(withText(R.string.toast_sensor_data_update_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_sensor_data_update_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Sensor Data updated")));

        Thread.sleep(2000);

        // find a recycler view, click an item position 0 one more time
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // check new sensor name
        onView(withId(R.id.sensor_data_et_name)).check(matches(withText(newName)));

        // check new sensor description
        onView(withId(R.id.sensor_data_et_description)).check(matches(withText(newDescription)));

        // type a previous sensor name
        onView(withId(R.id.sensor_data_et_name)).perform(clearText(),
                typeText(sensorName),closeSoftKeyboard());

        // type a previous sensor description
        onView(withId(R.id.sensor_data_et_description)).perform(clearText(),
                typeText(sensorDescription),closeSoftKeyboard());

        // click Update Button
        onView(withId(R.id.sensor_data_btn_update)).perform(scrollTo(),click());

        // check Toast - Update Success
        onView(withText(R.string.toast_sensor_data_update_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_sensor_data_update_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Sensor Data updated")));

        pressBack();
    }

    // testing Delete Button in Editing Card, click cancel
    @Test
    public void testDeleteSensorCancel() throws InterruptedException {

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // click Delete Button
        onView(withId(R.id.sensor_data_btn_delete)).perform(scrollTo(),click());

        // checking Dialog Title for matching
        onView(withText(R.string.sensor_data_dialog_delete_msg))
                .check(matches(withText("Are you sure you want to delete this sensor?")));

        // find Cancel Button in dialog, checking for matching
        onView(withText(R.string.sensor_data_dialog_delete_positive_button))
                .check(matches(withText("Delete")));

        // click on Cancel Button
        onView(withText(R.string.sensor_data_dialog_delete_positive_button)).perform(click());

        // check Toast - Delete Success
        onView(withText(R.string.toast_sensor_data_dialog_delete)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_sensor_data_dialog_delete)).inRoot(new ToastMatcher())
                .check(matches(withText("Sensor deleted")));

        pressBack();
    }



    // testing Delete Sensor  in Editing Card
    @Test
    public void testDeleteSensor() throws InterruptedException {

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

        Thread.sleep(3000);

        // find a recycler view, click an item position 0
        onView(withId(R.id.main_recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // click Delete Button
        onView(withId(R.id.sensor_data_btn_delete)).perform(scrollTo(),click());

        // checking Dialog Title for matching
        onView(withText(R.string.sensor_data_dialog_delete_msg))
                .check(matches(withText("Are you sure you want to delete this sensor?")));

        // find Cancel Button in dialog, checking for matching
        onView(withText(R.string.dialog_cancel))
                .check(matches(withText("Cancel")));

        // click on Cancel Button
        onView(withText(R.string.dialog_cancel)).perform(click());

        pressBack();
    }

    //custom matcher for setting Value Slider
    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(Slider.class);
            }

            @Override
            public String getDescription() {
                return "Set a progress on a Slider";
            }

            @Override
            public void perform(UiController uiController, View view) {
                Slider slider = (Slider) view;
                slider.setValue(progress);
            }
        };
    }

  //  custom matcher for checking Slider Value
   public static Matcher<View> withValue(Float value) {
        return new BoundedMatcher<View, Slider>(Slider.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("expected:" + value);
            }

            @Override
            protected boolean matchesSafely(Slider item) {
                return item.getValue() == value;
            }
        };
   }
}