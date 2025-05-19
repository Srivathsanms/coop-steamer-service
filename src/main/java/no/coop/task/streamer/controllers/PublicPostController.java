package no.coop.task.streamer.controllers;

import no.coop.task.streamer.models.PublicPost;
import no.coop.task.streamer.services.PublicPostAggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * REST API Controller providing access to public blog posts,
 * supporting both buffered and real-time streaming modes.
 */
@RestController
@RequestMapping("/api/v1/posts")
public class PublicPostController {

    private static final Logger log = LoggerFactory.getLogger(PublicPostController.class);

    private final PublicPostAggregatorService aggregatorService;

    /**
     * Constructor injecting the aggregator service.
     *
     * @param aggregatorService Service aggregating posts from external sources.
     */
    public PublicPostController(PublicPostAggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    /**
     * Streams live public posts as Server-Sent Events (SSE).
     *
     * @return Flux stream of BlogPost objects.
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PublicPost> streamLivePublicPosts() {
        log.info("Received request to stream live public posts via SSE");
        return aggregatorService.streamLivePosts();
    }

    /**
     * Retrieves a buffered list of recent public posts as JSON.
     *
     * @return Mono containing a list of BlogPost objects.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<PublicPost>> getBufferedPublicPosts() {
        log.info("Received request to retrieve buffered public posts as JSON");
        return aggregatorService.fetchAvailablePosts();
    }
}
