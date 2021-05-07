package my.e.wateryourplants.ShowDetails;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.Auth.LoginActivity;
import my.e.wateryourplants.ToastMatcher;
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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static my.e.wateryourplants.Auth.LoginActivityTest.setChecked;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class UserAccountActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> rule =
            new ActivityScenarioRule<>(LoginActivity.class);

    String name, email, password;

    @Before
    public void setUp() {
        name = "Sam";
        email = "test@test.ru";
        password = "1234567";
    }


    // checking all TextViews, EditText hints for matching text
    @Test
    public void testAllTextsAndHints() throws InterruptedException {
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

        // click Account Details Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // checking all TextView for matching text string
        onView(withId(R.id.user_account_title))
                .check(matches(withText(R.string.user_account_title)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.user_account_id_title))
                .check(matches(withText(R.string.user_account_id_title)))
                .check(matches(isDisplayed()));

        // checking all EditText hints for matching text string
        onView(withId(R.id.user_account_et_name))
                .check(matches(withText(name)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.user_account_et_email))
                .check(matches(withText(email)))
                .check(matches(isDisplayed()));
    }

    // checking all Buttons Text for matching name
    @Test
    public void testAllButtonTexts() throws InterruptedException {
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

        // click Account Details Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // check Button Texts for matching
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


    // checking CopyId Button
    @Test
    public void testCopyIdButton() throws InterruptedException {
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

        // click Account Details Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // check Button Texts for matching
        onView(withId(R.id.user_account_btn_copy)).perform(click());

        // check Toast - Copy Id Success
        onView(withText(R.string.toast_copy_id_success)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withText(R.string.toast_copy_id_success)).inRoot(new ToastMatcher())
                .check(matches(withText("User Id successfully copied")));
    }


    //  checking successful login, and in User Account Activity check update name function
    // after that return old name
    @Test
    public void testLoginAndNameUpdate() throws InterruptedException {
        String updatedName = "Sammin";
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

    //  checking successful login, and in User Account Activity check update email function
    // with invalid email type
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

    //  checking successful login, and in User Account Activity check update email function
    // after that return previous email
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

    // checking Delete Dialog in UserActivity Class press Cancel Button
    @Test
    public void testDeleteAccountCancel() throws InterruptedException {

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

        // click Account Details Button on menu
        onView(withText(R.string.menu_main_account_details)).perform(click());

        // click on Delete button
        onView(withId(R.id.user_account_btn_delete_user)).perform(click());

        // checking Dialog Title for matching
        onView(withText(R.string.user_account_dialog_delete_msg))
                .check(matches(withText("Are your sure to delete this account?")));

        // find Cancel Button in dialog, checking for matching
        onView(withText(R.string.dialog_cancel))
                .check(matches(withText("Cancel")));

        // click on Cancel Button
        onView(withText(R.string.dialog_cancel)).perform(click());
    }

    @After
    public void tearDown() {
    }
}