package my.aolika.wateryourplants.auth;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.aolika.wateryourplants.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StartActivityTest {

    @Rule
    public ActivityScenarioRule<StartActivity> rule =
            new ActivityScenarioRule<>(StartActivity.class);

    // check if buttons on StartPage are displayed
    @Test
    public void isButtonsDisplayed() {
        onView(withId(R.id.start_btn_login)).check(matches(isDisplayed()));
        onView(withId(R.id.start_btn_register)).check(matches(isDisplayed()));
        onView(withId(R.id.start_btn_forgot_password)).check(matches(isDisplayed()));
    }


    // check if buttons on StartPage are enable and match with the name of Buttons
    @Test
    public void isButtonsEnabled() {
        onView(withId(R.id.start_btn_login)).check(matches(withText(R.string.btn_login)))
                .check(matches(isEnabled()));
        onView(withId(R.id.start_btn_register)).check(matches(withText(R.string.btn_register)))
                .check(matches(isEnabled()));
        onView(withId(R.id.start_btn_forgot_password))
                .check(matches(withText(R.string.btn_forgot_password)))
                .check(matches(isEnabled()));
    }


    // check if buttons on StartPage are clickable
    @Test
    public void isButtonsClickable() {
        onView(withId(R.id.start_btn_login)).perform(click());
        pressBack();
        onView(withId(R.id.start_btn_register)).perform(click());
        pressBack();
        onView(withId(R.id.start_btn_forgot_password)).perform(click());
        pressBack();
    }

}