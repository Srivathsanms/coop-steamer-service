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






