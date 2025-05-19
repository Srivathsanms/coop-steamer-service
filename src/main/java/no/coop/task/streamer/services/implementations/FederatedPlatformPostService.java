package no.coop.task.streamer.services.implementations;

import no.coop.task.streamer.exceptions.ApiClientRequestException;
import no.coop.task.streamer.models.PublicPost;
import no.coop.task.streamer.services.PublicPostProvider;
import no.coop.task.streamer.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FederatedPlatformPostService implements PublicPostProvider {

    private static final Logger logger = LoggerFactory.getLogger(FederatedPlatformPostService.class);
    private final String sourceName;
    private final String liveUrl;
    private final String bufferedUrl;
    private final WebClient webClient;

    public FederatedPlatformPostService(String sourceName,String liveUrl, String bufferedUrl, String authToken) {
        this.sourceName = sourceName;
        this.liveUrl = liveUrl;
        this.bufferedUrl = bufferedUrl;
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .build();
    }

    @Override
    public Flux<PublicPost> getAllRealTimePosts() {
        return webClient.get()
                .uri(liveUrl)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .map(line -> {
                    logger.debug("[{}] Raw SSE Line: {}", sourceName, line);
                    if (line.startsWith("data:")) {
                        return line.substring(5).trim();
                    }
                    return "";
                })
                .filter(line -> !line.isEmpty())
                .flatMap(Utility::parseAndValidateResponse)
                .doOnNext(post -> logger.debug(" [{}] Parsed Post: {}", sourceName, post.getId()))
                .onErrorResume(ex -> {
                    logger.error("Streaming error: {}", ex.getMessage());
                    return Flux.error(new ApiClientRequestException("Error while fetching live posts: " + ex.getMessage()));
                })
                .doOnCancel(() -> logger.info("Stream canceled by client"));
    }

    @Override
    public Mono<List<PublicPost>> getAllBufferedPosts() {
        return webClient.get()
                .uri(bufferedUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PublicPost>>() {})
                .doOnSubscribe(sub -> logger.debug("[{}] Fetching buffered posts", sourceName))
                .onErrorResume(ex -> {
                    logger.error("Buffered fetch error: {}", ex.getMessage());
                    return Mono.error(new ApiClientRequestException("Error fetching buffered posts: " + ex.getMessage()));
                });
    }
}
