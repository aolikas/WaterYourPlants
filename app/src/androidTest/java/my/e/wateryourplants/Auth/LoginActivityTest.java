package my.e.wateryourplants.Auth;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Before
    public void setUp() {
    }

    @Rule
   public ActivityScenarioRule<LoginActivity> rule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @After
    public void tearDown() throws Exception {
    }
}