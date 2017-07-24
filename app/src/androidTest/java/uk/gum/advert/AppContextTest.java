package uk.gum.advert;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class AppContextTest {

    private Settings settings;

    @Before
    public void setUp() throws Exception {
        settings = new Settings();
    }

    @Test
    public void useAppContext() throws Exception {
        final String appPackage = settings.isDebug()
                ? BuildConfig.APPLICATION_PACKAGE_NAME + BuildConfig.BUILD_PACKAGE_SUFFIX
                : BuildConfig.APPLICATION_PACKAGE_NAME;
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals(appPackage, appContext.getPackageName());
    }
}
