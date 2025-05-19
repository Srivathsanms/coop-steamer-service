package no.coop.task.streamer.testhelper;

import no.coop.task.streamer.models.AuthorProfile;
import no.coop.task.streamer.models.PublicPost;

import java.util.Random;

public class StubPublicPostGenerator {

    private static final Random random = new Random();

    public static PublicPost getRandomBlogPost() {
        AuthorProfile authorProfile = new AuthorProfile();
        authorProfile.setBot(random.nextBoolean());
        authorProfile.setId("accountId_" + random.nextInt(10000));
        authorProfile.setDisplayName("DisplayName_" + random.nextInt(100));
        authorProfile.setUrl("https://example.com/user/" + random.nextInt(10000));
        authorProfile.setFollowersCount(random.nextInt(1000));
        authorProfile.setFollowingCount(random.nextInt(500));
        authorProfile.setUsername("username_" + random.nextInt(10000));
        authorProfile.setCreatedAt("CreatedAt_" + random.nextInt(100));
        authorProfile.setStatusesCount(random.nextInt(200));

        PublicPost publicPost = new PublicPost();
        publicPost.setId("postId_" + random.nextInt(10000));
        publicPost.setContent("Content_" + random.nextInt(100));
        publicPost.setCreatedAt("PostCreatedAt_" + random.nextInt(100));
        publicPost.setProfile(authorProfile);
        return publicPost;
    }

    public static PublicPost getStaticBlogPost() {
        AuthorProfile authorProfile = new AuthorProfile();
        authorProfile.setBot(true);
        authorProfile.setId("someAccountId");
        authorProfile.setDisplayName("someDisplayName");
        authorProfile.setUrl("someUrl");
        authorProfile.setFollowersCount(12);
        authorProfile.setFollowingCount(1);
        authorProfile.setUsername("someUserName");
        authorProfile.setCreatedAt("January1st");
        authorProfile.setStatusesCount(5);

        PublicPost publicPost = new PublicPost();
        publicPost.setId("someId");
        publicPost.setContent("someContent");
        publicPost.setCreatedAt("October1st");
        publicPost.setProfile(authorProfile);
        return publicPost;
    }
}
