package my.e.wateryourplants.ShowDetails;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.Auth.ToastMatcher;
import my.e.wateryourplants.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UserAccountActivityTest {

    private String name, email;

    @Rule
    public ActivityScenarioRule<UserAccountActivity> rule =
            new ActivityScenarioRule<>(UserAccountActivity.class);

    @Before
    public void setUp() throws Exception {
        name = "Tom";
        email = "waterplantstest@gmail.com";
    }

    @Test
    public void testAllTexts() {
        onView(withId(R.id.user_account_title))
                .check(matches(withText(R.string.user_account_title)));

        onView(withId(R.id.user_account_id_title))
                .check(matches(withText(R.string.user_account_id_title)));
    }

    @Test
    public void testAllButtonTexts() {
        onView(withId(R.id.user_account_btn_copy))
                .check(matches(withText(R.string.user_account_btn_copy)))
                .check(matches(isEnabled()));

        onView(withId(R.id.user_account_btn_update))
                .check(matches(withText(R.string.user_account_btn_update)))
                .check(matches(isEnabled()));

        onView(withId(R.id.user_account_btn_delete_user))
                .check(matches(withText(R.string.user_account_btn_delete)))
                .check(matches(isEnabled()));
    }

    @Test
    public void testDeleteAccountCancel() {

        onView(withId(R.id.user_account_btn_delete_user)).perform(click());

        onView(withText(R.string.user_account_dialog_delete_msg))
                .check(matches(withText("Are your sure to delete this account?")));

        onView(withText(R.string.dialog_cancel))
                .check(matches(withText("Cancel")));

        onView(withText(R.string.dialog_cancel)).perform(click());
    }

    @Test
    public void testDeleteAccount() {

        onView(withId(R.id.user_account_btn_delete_user)).perform(click());

        onView(withText(R.string.user_account_dialog_delete_msg))
                .check(matches(withText("Are your sure to delete this account?")));

        onView(withText(R.string.user_account_dialog_delete_positive_button))
                .check(matches(withText("Delete")));

        onView(withText(R.string.user_account_dialog_delete_positive_button)).perform(click());

        onView(withText(R.string.toast_dialog_delete_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        onView(withText(R.string.toast_dialog_delete_success)).inRoot(new ToastMatcher())
                .check(matches(withText("Account Deleted")));
    }

    @After
    public void tearDown() throws Exception {
    }
}