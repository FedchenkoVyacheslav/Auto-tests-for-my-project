package api_tests;

import api.pojos.PostPojo;
import api.utils.RestWrapper;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DisplayName("Should change the number of posts to view")
    public void shouldChangeNumberOfPosts() {
        assertEquals(api.posts.getPostsLimit(5).size(), 5);
        assertEquals(api.posts.getPostsLimit(10).size(), 10);
    }

    @Test
    @DisplayName("Should get posts depending on the number of views")
    public void shouldGetPostsDependingOnViews() {
        List<PostPojo> posts = api.posts.getPostsByFilter("views",100, 500);
        for (PostPojo post:posts) {
           int numberOfViews = post.getViews();
           assertTrue(numberOfViews > 100);
           assertTrue(numberOfViews < 500);
        }

        posts = api.posts.getPostsByFilter("views",500, 1000);
        for (PostPojo post:posts) {
            int numberOfViews = post.getViews();
            assertTrue(numberOfViews > 500);
            assertTrue(numberOfViews < 1000);
        }

        posts = api.posts.getPostsByFilter("views",1000, 2000);
        for (PostPojo post:posts) {
            int numberOfViews = post.getViews();
            assertTrue(numberOfViews > 1000);
            assertTrue(numberOfViews < 2000);
        }
    }

    @Test
    @DisplayName("Should get posts depending on the number of comments")
    public void shouldGetPostsDependingOnComments() {
        List<PostPojo> posts = api.posts.getPostsByFilter("commentsCount",0, 0);
        for (PostPojo post:posts) {
            int numberOfComments = post.getCommentsCount();
            assertTrue(numberOfComments == 0);
        }

        posts = api.posts.getPostsByFilter("commentsCount",0, 1);
        for (PostPojo post:posts) {
            int numberOfComments = post.getCommentsCount();
            assertTrue(numberOfComments <= 1);
        }

        posts = api.posts.getPostsByFilter("commentsCount",1, 50);
        for (PostPojo post:posts) {
            int numberOfComments = post.getCommentsCount();
            assertTrue(numberOfComments > 1);
            assertTrue(numberOfComments < 50);
        }
    }
}
