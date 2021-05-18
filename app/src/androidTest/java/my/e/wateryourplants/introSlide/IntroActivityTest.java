package my.e.wateryourplants.introSlide;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import my.e.wateryourplants.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class IntroActivityTest {

    @Rule
    public ActivityScenarioRule<IntroActivity> rule
            = new ActivityScenarioRule<>(IntroActivity.class);

    // check all slide Views
    @Test
    public void testIntroSlide() throws InterruptedException {

        // find a Button Next, check text button, clicking
        onView(withId(R.id.intro_btn_next)).check(matches(withText(R.string.intro_btn_next)))
                .check(matches(isEnabled())).check(matches(isClickable()));

        // find a Button Skip, check text button, clicking
        onView(withId(R.id.intro_btn_skip)).check(matches(withText(R.string.intro_btn_skip)))
                .check(matches(isEnabled())).check(matches(isClickable()));

        //find ImageView at a first slide
        onView(withId(R.id.intro_img_screen_one)).check(matches(isEnabled()));

        //find TextView at a first slide, check text matching
        onView(withId(R.id.intro_screen_one_sen_1))
                .check(matches(withText(R.string.intro_screen_one_sen_1)))
                .check(matches(isEnabled()));
        //find TextView at a first slide, check text matching
        onView(withId(R.id.intro_screen_one_sen_2))
                .check(matches(withText(R.string.intro_screen_one_sen_2)))
                .check(matches(isEnabled()));

        // click Button Next
        onView(withId(R.id.intro_btn_next)).perform(click());

        Thread.sleep(2000);

        //find ViewPager and swipe back
        onView(withId(R.id.intro_view_pager)).perform(swipeRight());

        Thread.sleep(2000);

        //find ViewPager and swipe further
        onView(withId(R.id.intro_view_pager)).perform(swipeLeft());

        //find ImageView at a second slide
        onView(withId(R.id.intro_img_screen_two)).check(matches(isEnabled()));

        //find TextView at a second slide, check text matching
        onView(withId(R.id.intro_screen_two_sen_1))
                .check(matches(withText(R.string.intro_screen_two_sen_1)))
                .check(matches(isEnabled()));
        //find TextView at a first slide, check text matching
        onView(withId(R.id.intro_screen_two_sen_2))
                .check(matches(withText(R.string.intro_screen_two_sen_2)))
                .check(matches(isEnabled()));
        //find TextView at a first slide, check text matching
        onView(withId(R.id.intro_screen_two_sen_3))
                .check(matches(withText(R.string.intro_screen_two_sen_3)))
                .check(matches(isEnabled()));

        //Skip Button is enabled
        onView(withId(R.id.intro_btn_skip)).check(matches(isEnabled()));

        // click Button Next
        onView(withId(R.id.intro_btn_next)).perform(click());

        Thread.sleep(2000);

        //find ViewPager and swipe back
        onView(withId(R.id.intro_view_pager)).perform(swipeRight());

        Thread.sleep(2000);

        //find ViewPager and swipe further
        onView(withId(R.id.intro_view_pager)).perform(swipeLeft());

        Thread.sleep(2000);

        //find ImageView at a third slide
        onView(withId(R.id.intro_img_screen_three)).check(matches(isEnabled()));

        //find TextView at a second slide, check text matching
        onView(withId(R.id.intro_screen_three_sen_1))
                .check(matches(withText(R.string.intro_screen_three_sen_1)))
                .check(matches(isEnabled()));
        //find TextView at a first slide, check text matching
        onView(withId(R.id.intro_screen_three_sen_2))
                .check(matches(withText(R.string.intro_screen_three_sen_2)))
                .check(matches(isEnabled()));

        //Skip Button is not enabled
        onView(withId(R.id.intro_btn_skip)).check(matches(not(isDisplayed())));

        //Next Button becomes Get Started Button
        onView(withId(R.id.intro_btn_next)).check(matches(withText(R.string.intro_btn_start)))
                .check(matches((isEnabled())));

        // click Button Next
        onView(withId(R.id.intro_btn_next)).perform(click());

        Thread.sleep(2000);

        //verify startActivity Page check if buttons on StartPage are displayed

            onView(withId(R.id.start_btn_login)).check(matches(isDisplayed()));
            onView(withId(R.id.start_btn_register)).check(matches(isDisplayed()));
            onView(withId(R.id.start_btn_forgot_password)).check(matches(isDisplayed()));
    }

}