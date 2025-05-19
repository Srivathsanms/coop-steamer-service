package no.coop.task.streamer.services;

import no.coop.task.streamer.config.FederatedPlatformProperties;
import no.coop.task.streamer.models.PublicPost;
import no.coop.task.streamer.services.implementations.FederatedPlatformPostService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicPostAggregatorService {

    private final List<PublicPostProvider> providerServices;

    public PublicPostAggregatorService(FederatedPlatformProperties properties) {
        this.providerServices = properties.getPlatforms().stream()
                .map(p -> new FederatedPlatformPostService(p.getName(),p.getLiveUrl(), p.getBufferedUrl(), p.getAuthToken()))
                .collect(Collectors.toList());
    }

    public Flux<PublicPost> streamLivePosts() {
        return providerServices.stream()
                .map(PublicPostProvider::getAllRealTimePosts)
                .reduce(Flux::mergeWith)
                .orElse(Flux.empty());
    }

    public Mono<List<PublicPost>> fetchAvailablePosts() {
        return providerServices.stream()
                .map(service -> service.getAllBufferedPosts().flatMapMany(Flux::fromIterable))
                .reduce(Flux::mergeWith)
                .orElse(Flux.empty())
                .collectList();
    }
}




/*
package no.coop.task.streamer.services;

import no.coop.task.streamer.models.PublicPost;
import no.coop.task.streamer.services.implementations.MastodonPostProviderService;
import no.coop.task.streamer.services.implementations.PawooBloggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class PublicPostAggregatorService {

    private final WebClient webClient;
    private final Environment environment;


    private List<PublicPostProvider> providerServices;

    @Autowired
    public PublicPostAggregatorService(WebClient webClient,
                                       Environment environment) {
        this.webClient = webClient;
        this.environment = environment;

    }

    @PostConstruct
    private void initializeProviderServices() {
        this.providerServices = List.of(
                new PawooBloggingService(webClient, environment),
                new MastodonPostProviderService(webClient,environment)
        );
    }

    */
/**
     * Get the real time posts
     *//*

    public Flux<PublicPost> streamLivePosts() {
        return providerServices.stream()
                .map(PublicPostProvider::getAllRealTimePosts)
                .reduce(Flux::mergeWith)
                .orElse(Flux.empty());
        }


    */
/**
     * Get the buffered posts
     *//*

    public Mono<List<PublicPost>> fetchAvailablePosts() {
        return providerServices.stream()
                .map(service -> service.getAllBufferedPosts().flatMapMany(Flux::fromIterable))
                .reduce(Flux::mergeWith)
                .orElse(Flux.empty())
                .collectList();
        }

    // - This is for testing flexibility
    void setProviderServices(List<PublicPostProvider> services) {
        this.providerServices = services;
    }
}
*/


