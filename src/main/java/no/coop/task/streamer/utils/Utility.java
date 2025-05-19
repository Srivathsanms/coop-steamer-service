package no.coop.task.streamer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.coop.task.streamer.models.PublicPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * Utility class, responsible for common tasks that services need to do
 */
public class Utility {

    private static final Logger logger = LoggerFactory.getLogger(Utility.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Flux<PublicPost> parseAndValidateResponse(String responseBody) {
        try {
            PublicPost responseObject = objectMapper.readValue(responseBody, PublicPost.class);
            return Flux.just(responseObject);
        } catch (JsonProcessingException e) {
            //TODO: Report to monitoring system
            logger.error("Error parsing Json response: {}", e.getMessage());
            return Flux.empty();
        }
    }
}
