package api_tests;

import api.pojos.TagPojo;
import api.utils.RestWrapper;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class ApiTagITCase {
    private static RestWrapper api;

    @BeforeClass
    public static void prepareClient() {
        api = RestWrapper.loginAsUser("g1@gmail.com", "12345678");
    }

    @Test
    @DisplayName("Should get tag")
    public void shouldGetTagCase() {
        Assertions.assertThat(api.tags.getTags()).extracting(TagPojo::getId).contains(1);
        Assertions.assertThat(api.tags.getTags()).extracting(TagPojo::getColor).contains("#2176FF");
    }
}
