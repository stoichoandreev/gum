package uk.gum.advert.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class AdDetailsTest {

    @Test
    public void test_instanceCreation() throws Exception {
        final AdDetails objectWithDefaultValues = new AdDetails();
        assertThat(objectWithDefaultValues.getAddress()).isNullOrEmpty();
        assertThat(objectWithDefaultValues.getContactName()).isNullOrEmpty();
        assertThat(objectWithDefaultValues.getContactNumber()).isNullOrEmpty();
        assertThat(objectWithDefaultValues.getDate()).isNullOrEmpty();
        assertThat(objectWithDefaultValues.getDescription()).isNullOrEmpty();
        assertThat(objectWithDefaultValues.getFuelType()).isNullOrEmpty();
        assertThat(objectWithDefaultValues.getId()).isEqualTo(0);
        assertThat(objectWithDefaultValues.getImages()).isNullOrEmpty();
        assertThat(objectWithDefaultValues.getPrice()).isNullOrEmpty();
        assertThat(objectWithDefaultValues.getTitle()).isNullOrEmpty();
    }
}