package cl.ufro.dci.proyectosmartcity;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("cl.ufro.dci.proyecto_smart_city", appContext.getPackageName());
    }

    /* First test, tutorial Espresso. Está comentado porque se pretendía realizar una prueba
    aplicada al mapa, pero luego se decidió por no hacerla, por temas de tiempo. Se dejaron
    las dependencias para utilizarlas mas adelante.
    @Rule
    public ActivityScenarioRule<Aviso> activityRule = new ActivityScenarioRule<Aviso>(Aviso.class);

    @Test
    public void listGoesOverTheFold() {
        //onView(withText("Hello World!")).check(matches(isDisplayed()));
    }

     */

}
