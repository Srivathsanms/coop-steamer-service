package no.coop.task.streamer.services;

import no.coop.task.streamer.models.PublicPost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service responsible for getting the live posts and buffer posts from
 * third party blogging services
 */
public interface PublicPostProvider {
    /**
     * This method is used to stream all the public posts
     * from the third party blogging services that implement {@link PublicPostProvider}
     * @return Live posts from all the blogging service
     */
    Flux<PublicPost> getAllRealTimePosts();
    /**
     * This method is used to fetch all the buffered public posts
     * from the third party blogging services that implement {@link PublicPostProvider}
     * @return Buffered posts from all the blogging services
     */
    Mono<List<PublicPost>> getAllBufferedPosts();
}
