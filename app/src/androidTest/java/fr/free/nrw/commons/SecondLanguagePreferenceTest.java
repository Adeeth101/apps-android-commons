package fr.free.nrw.commons;

import android.content.Context;
import android.widget.ListView;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import fr.free.nrw.commons.settings.SettingsActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import android.view.View;

@RunWith(AndroidJUnit4.class)

public class SecondLanguagePreferenceTest {


    @Rule
    public ActivityScenarioRule<SettingsActivity> activityScenarioRule = new ActivityScenarioRule<SettingsActivity>(
        SettingsActivity.class);

    @Before
    public void setUp() {
        Context context = androidx.test.core.app.ApplicationProvider.getApplicationContext();

    }

    @Test
    public void testSelectSecondaryDescriptionLanguage() {
        // Click on the secondary description language preference
        onView(withText(R.string.secondary_description_language))
            .perform(click());

        // Check that the dialog is displayed
        onView(withId(R.id.language_order_preference))
            .check(matches(isDisplayed()));

        // Check language_list ListView
        onView(withId(R.id.language_list)).check(matches(isDisplayed()));

        activityScenarioRule.getScenario().onActivity(activity -> {
            // Get ListView
            View dialogView = activity.findViewById(R.id.language_order_preference);
            if (dialogView != null) {
                ListView listView = dialogView.findViewById(R.id.language_list);


                assertNotNull("Adapter should not be null", listView.getAdapter());
                assertTrue("Adapter should have items", listView.getAdapter().getCount() > 0);

                // Simulate selecting a language from the list
                onData(anything())
                    .inAdapterView(withId(R.id.language_list))
                    .atPosition(0)
                    .perform(click());
            }
        });

        //Verify that "English" text is displayed
        onView(withText(containsString("English")))
            .check(ViewAssertions.matches(isDisplayed()));
    }
}
