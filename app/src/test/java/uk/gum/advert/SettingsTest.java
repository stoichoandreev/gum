package uk.gum.advert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
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