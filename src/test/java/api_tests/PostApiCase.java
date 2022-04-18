package api_tests;

import api.pojos.PostPojo;
import api.utils.RestWrapper;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class PostApiCase {
    private static RestWrapper api;

    @BeforeClass
    public static void prepareClient() {
        api = RestWrapper.loginAsUser("g1@gmail.com", "12345678");
    }

    @Test
    @DisplayName("Should get post")
    public void shouldGetPostCase() {
        Assertions.assertThat(api.posts.getPosts()).extracting(PostPojo::getId).contains(1);
        Assertions.assertThat(api.posts.getPosts()).extracting(PostPojo::getCommentsCount).contains(83);
        Assertions.assertThat(api.posts.getPosts()).extracting(PostPojo::getViews).contains(1172);
        Assertions.assertThat(api.posts.getPosts()).extracting(PostPojo::getTitle).contains("The breed of my new dog is Pug.");
        Assertions.assertThat(api.posts.getPosts()).extracting(PostPojo::getText).contains("The pug is a breed of dog with " +
                "physically distinctive features of a wrinkly, short-muzzled face, and curled tail.");
    }
}
