package no.coop.task.streamer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.coop.task.streamer.models.PublicPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;

import java.util.Objects;

import static no.coop.task.streamer.testhelper.StubPublicPostGenerator.getStaticBlogPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TestUtility {

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(Utility.class, "objectMapper", objectMapper);
    }

    @Test
    void testParseAndValidateResponse_ValidJson() throws Exception {
        String validJson = "{\n" +
                "  \"id\": \"someId\",\n" +
                "  \"content\": \"someContent\",\n" +
                "  \"createdAt\": \"October1st\",\n" +
                "  \"account\": {\n" +
                "    \"id\": \"someAccountId\",\n" +
                "    \"username\": \"someUserName\",\n" +
                "    \"displayName\": \"someDisplayName\",\n" +
                "    \"bot\": true,\n" +
                "    \"createdAt\": \"January1st\",\n" +
                "    \"url\": \"someUrl\",\n" +
                "    \"followersCount\": 12,\n" +
                "    \"followingCount\": 1,\n" +
                "    \"statusesCount\": 5\n" +
                "  }\n" +
                "}";
        PublicPost expectedPublicPost = getStaticBlogPost();

        when(objectMapper.readValue(validJson, PublicPost.class)).thenReturn(expectedPublicPost);

        Flux<PublicPost> result = Utility.parseAndValidateResponse(validJson);

        PublicPost publicPost = result.blockFirst();
        assert publicPost != null;
        assertEquals(expectedPublicPost.getId(), publicPost.getId());
        assertEquals(expectedPublicPost.getContent(), publicPost.getContent());
        verify(objectMapper, times(1)).readValue(validJson, PublicPost.class);
    }

    @Test
    void testParseAndValidateResponse_InvalidJson() throws Exception {
        String invalidJson = "{\"id\":\"1\", \"content\": ";
        when(objectMapper.readValue(invalidJson, PublicPost.class)).thenThrow(JsonProcessingException.class);
        Flux<PublicPost> result = Utility.parseAndValidateResponse(invalidJson);
        assertTrue(Objects.requireNonNull(result.collectList().block()).isEmpty());
        verify(objectMapper, times(1)).readValue(invalidJson, PublicPost.class);
    }
}
