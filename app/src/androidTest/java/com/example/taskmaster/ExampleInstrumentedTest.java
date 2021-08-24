package com.example.taskmaster;

import android.content.Context;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

    @ Test
    public void testMainActivity(){
        onView(withId(R.id.textView)).check(matches(withText("My Tasks")));
    }

    @Test
    public void settingActivity(){
        onView(withId(R.id.setting)).perform(click());
        onView(withId(R.id.textView7)).check(matches(withText("Setting")));
    }

    @Test
    public void userNameSetting(){
        onView(withId(R.id.setting)).perform(click());
        onView(withId(R.id.username))
                .perform(typeText("Ahmad"),closeSoftKeyboard());

        onView(withId(R.id.save))
                .perform(click());
        pressBack();
        onView(withId(R.id.showuser))
                .check(matches(withText("Ahmad")));

    }

    @Test
    public void AddTaskTest(){
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.editText)).perform(typeText("forTest"),closeSoftKeyboard());
        onView(withId(R.id.editText3)).perform(typeText("This only for Test"),closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());
        pressBack();
        onView(withId(R.id.button2)).perform(click());
        onView(ViewMatchers.withId(R.id.list)).check(matches(isDisplayed()));
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.taskmaster", appContext.getPackageName());
    }
}