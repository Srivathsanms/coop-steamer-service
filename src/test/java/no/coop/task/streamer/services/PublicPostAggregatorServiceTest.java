/*
package no.coop.task.streamer.services;

import no.coop.task.streamer.config.FederatedPlatformProperties;
import no.coop.task.streamer.config.FederatedPlatformProperties.Platform;
import no.coop.task.streamer.models.PublicPost;
import no.coop.task.streamer.services.implementations.FederatedPlatformPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PublicPostAggregatorServiceTest {

    private PublicPostAggregatorService aggregatorService;

    @BeforeEach
    void setUp() {
        // Mocking properties with 2 platforms
        FederatedPlatformProperties mockProperties = mock(FederatedPlatformProperties.class);

        Platform platform1 = new Platform();
        platform1.setName("Mastodon");
        platform1.setLiveUrl("https://mastodon/live");
        platform1.setBufferedUrl("https://mastodon/buffered");
        platform1.setAuthToken("token1");

        Platform platform2 = new Platform();
        platform2.setName("Pawoo");
        platform2.setLiveUrl("https://pawoo/live");
        platform2.setBufferedUrl("https://pawoo/buffered");
        platform2.setAuthToken("token2");

        when(mockProperties.getPlatforms()).thenReturn(List.of(platform1, platform2));

        aggregatorService = new PublicPostAggregatorService(mockProperties);
    }

    @Test
    void shouldStreamLivePosts() {
        // Example post
        PublicPost post = new PublicPost();
        post.setId("1");
        post.setContent("Test content");

        // Manually inject mock services since constructor builds real ones
        PublicPostProvider mockProvider = mock(PublicPostProvider.class);
        when(mockProvider.getAllRealTimePosts()).thenReturn(Flux.just(post));

        aggregatorService = new PublicPostAggregatorService(platformsWithSingleMockProvider(mockProvider));

        List<PublicPost> result = aggregatorService.streamLivePosts().collectList().block();

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        verify(mockProvider).getAllRealTimePosts();
    }

    @Test
    void shouldFetchBufferedPosts() {
        PublicPost post = new PublicPost();
        post.setId("2");
        post.setContent("Buffered content");

        PublicPostProvider mockProvider = mock(PublicPostProvider.class);
        when(mockProvider.getAllBufferedPosts()).thenReturn(Mono.just(List.of(post)));

        aggregatorService = new PublicPostAggregatorService(platformsWithSingleMockProvider(mockProvider));

        List<PublicPost> result = aggregatorService.fetchAvailablePosts().block();

        assertEquals(1, result.size());
        assertEquals("2", result.get(0).getId());
        verify(mockProvider).getAllBufferedPosts();
    }

    // Utility to inject mocks into service
    private FederatedPlatformProperties platformsWithSingleMockProvider(PublicPostProvider mockProvider) {
        // Create a mock properties object
        FederatedPlatformProperties properties = mock(FederatedPlatformProperties.class);

        // Build a platform entry using setters
        FederatedPlatformProperties.Platform mockPlatform = new FederatedPlatformProperties.Platform();
        mockPlatform.setName("Mock");
        mockPlatform.setLiveUrl("mockLiveUrl");
        mockPlatform.setBufferedUrl("mockBufferedUrl");
        mockPlatform.setAuthToken("mockToken");

        // Provide this platform as the single platform in the properties
        when(properties.getPlatforms()).thenReturn(List.of(mockPlatform));

        return properties;
    }

}
*/
