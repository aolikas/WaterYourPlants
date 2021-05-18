package my.e.wateryourplants.auth;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import my.e.wateryourplants.R;
import my.e.wateryourplants.ToastMatcher;

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


public class ResetPasswordActivityTest {

    String email;

    @Rule
    public ActivityScenarioRule<ResetPasswordActivity> rule =
            new ActivityScenarioRule<>(ResetPasswordActivity.class);


    @Before
    public void setUp() {
        email = "waterplantstest@gmail.com";
    }

    // checking  EditText Hint
    @Test
    public void testAllHints() {
        onView(withId(R.id.reset_et_email)).check(matches(withHint(R.string.et_hint_email)))
                .check(matches(isEnabled()));
    }

    // check a Reset Password Button match with button name
    @Test
    public void testButtonText() {
        onView(withId(R.id.reset_btn_reset)).check(matches(withText(R.string.btn_reset_password)))
                .check(matches(isEnabled()));
    }

    // checking with empty edit text
    @Test
    public void testEmailEmpty() {
        onView(withId(R.id.reset_progress_bar)).check(matches(not(isDisplayed())));

        onView(withId(R.id.reset_et_email)).perform(clearText(), closeSoftKeyboard());

        onView(withId(R.id.reset_btn_reset)).perform(click());

        onView(withText(R.string.toast_error_empty_fields)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_error_empty_fields)).inRoot(new ToastMatcher())
                .check(matches(withText("Please fill all required fields")));
    }

    // check registration with a wrong email type
    @Test
    public void testInvalidEmail() {
        email = "111111";
        onView(withId(R.id.reset_progress_bar)).check(matches(not(isDisplayed())));

        onView(withId(R.id.reset_et_email)).perform(clearText(),
                typeText(email), closeSoftKeyboard());
        onView(withId(R.id.reset_et_email)).check(matches(withText("111111")));

        onView(withId(R.id.reset_btn_reset)).perform(click());

        onView(withId(R.id.reset_et_email))
                .check(matches(hasErrorText("Please provide a valid email")))
                .check(matches(hasFocus()));
    }

    @Test
    public void testResetSuccess() {
        onView(withId(R.id.reset_progress_bar)).check(matches(not(isDisplayed())));

        onView(withId(R.id.reset_et_email)).perform(clearText(),
                typeText(email), closeSoftKeyboard());
        onView(withId(R.id.reset_et_email)).check(matches(withText("waterplantstest@gmail.com")));

        onView(withId(R.id.reset_btn_reset)).perform(click());

        onView(withText(R.string.toast_reset_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_reset_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Password send to your email")));
    }
}