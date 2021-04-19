package my.e.wateryourplants.Auth;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class StartActivityTest {

    @Rule
    public ActivityScenarioRule<StartActivity> rule =
            new ActivityScenarioRule<StartActivity>(StartActivity.class);


   @Test
   public void isButtonsEnabled() {
       onView(withId(R.id.start_btn_login)).check(matches(isEnabled()));
       onView(withId(R.id.start_btn_register)).check(matches(isEnabled()));
       onView(withId(R.id.start_btn_forgot_password)).check(matches(isEnabled()));
   }

   @Test
   public void isButtonsDisplayed() {
       onView(withId(R.id.start_btn_login)).check(matches(isDisplayed()));
       onView(withId(R.id.start_btn_register)).check(matches(isDisplayed()));
       onView(withId(R.id.start_btn_forgot_password)).check(matches(isDisplayed()));
   }

   @Test
   public void isButtonsClickable() {
       onView(withId(R.id.start_btn_login)).check(matches(isClickable()));
       onView(withId(R.id.start_btn_register)).check(matches(isClickable()));
       onView(withId(R.id.start_btn_forgot_password)).check(matches(isClickable()));
   }

   
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}