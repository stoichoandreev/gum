package uk.gum.advert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(PowerMockRunner.class)
@PrepareForTest(BuildConfig.class)
public class SettingsTest {

    private Settings settings;

    @Before
    public void setUp() throws Exception {
        settings = new Settings();
    }

    @Test
    public void test_isDebug() throws Exception {
        assertThat(settings.isDebug()).isTrue();
    }

}