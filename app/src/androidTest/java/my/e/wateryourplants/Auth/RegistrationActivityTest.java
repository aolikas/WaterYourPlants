package my.e.wateryourplants.Auth;

import android.os.IBinder;
import android.view.WindowManager;


import androidx.test.espresso.Root;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;

import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest {

    @Rule
    public ActivityScenarioRule<RegistrationActivity> rule =
            new ActivityScenarioRule<>(RegistrationActivity.class);

    String name, email, password;

    @Before
    public void setUp() {
        name = "Tom";
        email = "waterplantstest@gmail.com";
        password = "Te$TwAte4";
    }

    @Test
    public void testAllHints() {
        onView(withId(R.id.reg_et_name)).check(matches(withHint(R.string.et_hint_name)))
                .check(matches(isEnabled()));
        onView(withId(R.id.reg_et_email)).check(matches(withHint(R.string.et_hint_email)))
        .check(matches(isEnabled()));
        onView(withId(R.id.reg_et_password)).check(matches(withHint(R.string.et_hint_password)))
        .check(matches(isEnabled()));
    }

    @Test
    public void testButtonText() {
        onView(withId(R.id.reg_btn_registration)).check(matches(withText(R.string.btn_register)))
                .check(matches(isEnabled()));
    }

    @Test
    public void testAllViewsEmpty() {
        onView(withId(R.id.reg_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.reg_et_name)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.reg_et_email)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.reg_et_password)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.reg_btn_registration)).perform(click());
        onView(withText(R.string.toast_error_empty_fields)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_error_empty_fields)).inRoot(new ToastMatcher())
                .check(matches(withText("Please fill all required fields")));
    }

    @Test
    public void testInvalidEmail() {
        email = "111111";
        onView(withId(R.id.reg_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.reg_et_name)).perform(clearText(), typeText(name), closeSoftKeyboard());
        onView(withId(R.id.reg_et_email)).perform(clearText(), typeText(email), closeSoftKeyboard());
        onView(withId(R.id.reg_et_password)).perform(clearText(), typeText(password), closeSoftKeyboard());

        onView(withId(R.id.reg_btn_registration)).perform(click());
        onView(withId(R.id.reg_et_email))
                .check(matches(hasErrorText("Please provide a valid email")))
                .check(matches(hasFocus()));
    }


    @Test
    public void testInvalidPassword() {
        password = "11";
        onView(withId(R.id.reg_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.reg_et_name)).perform(clearText(), typeText(name), closeSoftKeyboard());
        onView(withId(R.id.reg_et_email)).perform(clearText(), typeText(email), closeSoftKeyboard());
        onView(withId(R.id.reg_et_password)).perform(clearText(), typeText(password), closeSoftKeyboard());

        onView(withId(R.id.reg_btn_registration)).perform(click());
        onView(withId(R.id.reg_et_password))
                .check(matches(hasErrorText("Password should be at least 6 characters")))
                .check(matches(hasFocus()));
    }

    @Test
    public void testRegSuccess() {
        onView(withId(R.id.reg_progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.reg_et_name)).perform(clearText(), typeText(name), closeSoftKeyboard());
        onView(withId(R.id.reg_et_email)).perform(clearText(), typeText(email), closeSoftKeyboard());
        onView(withId(R.id.reg_et_password)).perform(clearText(), typeText(password), closeSoftKeyboard());
        onView(withId(R.id.reg_btn_registration)).perform(click());

        onView(withText(R.string.toast_reg_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_reg_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Registration is successful")));

    }


    @After
    public void tearDown() {
    }

}